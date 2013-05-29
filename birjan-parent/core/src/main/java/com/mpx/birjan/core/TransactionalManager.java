package com.mpx.birjan.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.Authorities;
import com.mpx.birjan.bean.Balance;
import com.mpx.birjan.bean.Draw;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.User;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.common.BalanceDTO;
import com.mpx.birjan.common.Item;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.common.Payment;
import com.mpx.birjan.common.Rule;
import com.mpx.birjan.common.Rule.VARIANT;
import com.mpx.birjan.common.Status;
import com.mpx.birjan.common.Ticket;
import com.mpx.birjan.service.dao.Filter;
import com.mpx.birjan.service.dao.GenericJpaDAO;
import com.mpx.birjan.util.BirjanUtils;

@Controller
public class TransactionalManager {

	private GenericJpaDAO<Draw> drawDao;

	private GenericJpaDAO<Game> gameDao;

	private GenericJpaDAO<Wager> wagerDao;

	private GenericJpaDAO<User> usersDao;

	private GenericJpaDAO<Authorities> authoritiesDao;

	private GenericJpaDAO<Balance> balanceDao;

	@Transactional(rollbackFor = Exception.class)
	public Ticket processWinners(String hash, boolean pay) {
		Filter<String> filter = new Filter<String>("hash", hash);
		Wager wager = wagerDao.findUniqueByFilter(filter);
		if (wager != null) {
			
			DateTime dt = new DateTime(wager.getGame().get(0).getDate());
			String day = dt.toString("EEEE", new Locale("es")).toUpperCase()
					+ "  " + dt.getDayOfMonth();

			Object[][] data = wager
					.getGame().get(0).getData();

			Ticket ticket = new Ticket(day, data);
			for (Game game : wager.getGame()) {
				ticket.addPayment(new Payment(game.getLottery(), game.getStatus(), game.getPrize()));
				if(pay && game.getStatus().equals(Status.WINNER))
					game.setStatus(Status.PAID);
			}
			return ticket;
		}
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	public synchronized void createDraw(Lottery lottery, DateTime date,
			String[] numbers) {

		User user = identify(null);

		Filter<Lottery> lotteryFilter = new Filter<Lottery>("lottery", lottery);
		Filter<Date> dateFilter = new Filter<Date>("date", date.toDate());

		Draw draw = drawDao.findUniqueByFilter(lotteryFilter, dateFilter);

		if (draw != null) {
			BirjanUtils.mergeDraw(draw.getNumbers(), numbers);
			drawDao.update(draw);
		} else {
			drawDao.create(new Draw(lottery, date.toDate(), user, numbers));
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public synchronized void validateDraw(Lottery lottery, DateTime date) {

		Filter<Status> statusFilter = new Filter<Status>("status", Status.OPEN);//Status.VALID
		Filter<Lottery> lotteryFilter = new Filter<Lottery>("lottery", lottery);
		Filter<Date> dateFilter = new Filter<Date>("date", date.toDateMidnight().toDate());

		Draw draw = drawDao.findUniqueByFilter(statusFilter, lotteryFilter, dateFilter);
		
		if(draw!=null){	
			String[] winnerNumbers = draw.getNumbers();
			for (String winnerNumber : winnerNumbers) {
				Preconditions.checkArgument(Pattern.matches("\\d{4}", winnerNumber), "Can not validate Draw.");
			}
			
//			List<Game> winners = retriveGames(Status.WINNER, lottery, date, null);
//			List<Game> losers = retriveGames(Status.LOSER, lottery, date, null);
			List<Game> games = retriveGames(Status.VALID, lottery, date, null, null);
//			games.addAll(winners);
//			games.addAll(losers);
			for (Game game : games) {
				Object[][] data = game.getData();
				boolean win= true;
				for (Object[] row : data) {
					char[] winnerNumber = winnerNumbers[(Integer)row[0]-1].toCharArray();
					char[] number = ((String)row[1]).toCharArray();
					for (int i = 0; win && i < 4; i++) {
						win &= number[i]=='x'||number[i]==winnerNumber[i];
					}
				}
				
				game.setStatus(Status.LOSER);
				if (win) {
					Float winAmount = 0f;
					for (Object[] row : data) {
						int hits = 3 - ((String) row[1]).lastIndexOf('x');
						winAmount += ((Float) row[2])* Rule.defaultWinRatios[hits-1];
					}
					game.setStatus(Status.WINNER);
					game.setPrize(winAmount);
				}
				gameDao.update(game);

			}
		}

	}

	public Draw retrieveDraw(Lottery lottery, DateTime date) {

		Filter<Lottery> lotteryFilter = new Filter<Lottery>("lottery", lottery);
		Filter<Date> dateFilter = new Filter<Date>("date", date.toDateMidnight().toDate());

		Draw draw = drawDao.findUniqueByFilter(lotteryFilter, dateFilter);

		return draw;
	}

	public String[] getComboOptions(String view, String combo, String day) {
		if (combo.equalsIgnoreCase("loteria")) {
			return new String[] { "NACIONAL", "PROVINCIA" };
		}

		List<String> list = new ArrayList<String>();
		if (isDevelopment()) {
			VARIANT[] values = VARIANT.values();
			for (VARIANT variant : values) {
				list.add(variant.name());
			}
		} else {
			list = BirjanUtils.retrieveVariantAvailability(view, Rule.National,
					day);
		}
		return list.toArray(new String[list.size()]);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String createGames(Lottery[] lotteries, DateTime date,
			Object[][] data) {
		
		float totalBet = 0;
		for (Object[] row : data) {
			totalBet += (Float) row[2];
		}
		User user = identify(null);
		Wager wager = new Wager(totalBet*lotteries.length, user);
		
		for (Lottery lottery : lotteries) {

			Game game = new Game(lottery, date.toDate(), wager, data);
			gameDao.create(game);
		}
		
		return wager.getHash();
	}

	public List<Game> retriveGames(Status status, Lottery lottery, DateTime date, DateTime created, User user) {

		Filter<Status> statusFilter = new Filter<Status>("status", status);
		Filter<Lottery> lotteryFilter = new Filter<Lottery>("lottery", lottery);
		Filter<Date> dateFilter = new Filter<Date>("date", (date!=null)?date.toDateMidnight().toDate():null);
		Filter<Date> createdFilter = new Filter<Date>("created", (created!=null)?created.toDateMidnight().toDate():null);
		Filter<User> userFilter = new Filter<User>("wager.user", user);

		List<Game> games = gameDao.findByFilter(statusFilter, lotteryFilter,
				dateFilter, createdFilter, userFilter);

		return games;
	}

	@Transactional(readOnly=true)
	public User identify(String userName) {
		Filter<String> userFilter = new Filter<String>("username",
				(userName != null) ? userName : SecurityContextHolder
						.getContext().getAuthentication().getName());

		User user = usersDao.findUniqueByFilter(userFilter);
		
		return user;
	}

	@Resource(name = "genericJpaDAO")
	public final void setDrawDao(final GenericJpaDAO<Draw> daoToSet) {
		drawDao = daoToSet;
		drawDao.setClazz(Draw.class);
	}

	@Resource(name = "genericJpaDAO")
	public final void setWagerDao(final GenericJpaDAO<Wager> daoToSet) {
		wagerDao = daoToSet;
		wagerDao.setClazz(Wager.class);
	}

	@Resource(name = "genericJpaDAO")
	public final void setGameDao(final GenericJpaDAO<Game> daoToSet) {
		gameDao = daoToSet;
		gameDao.setClazz(Game.class);
	}

	@Resource(name = "genericJpaDAO")
	public final void setUsersDao(final GenericJpaDAO<User> daoToSet) {
		usersDao = daoToSet;
		usersDao.setClazz(User.class);
	}

	@Resource(name = "genericJpaDAO")
	public final void setBalanceDao(final GenericJpaDAO<Balance> daoToSet) {
		balanceDao = daoToSet;
		balanceDao.setClazz(Balance.class);
	}

	@Resource(name = "genericJpaDAO")
	public final void setAuthoritiesDao(final GenericJpaDAO<Authorities> daoToSet) {
		authoritiesDao = daoToSet;
		authoritiesDao.setClazz(Authorities.class);
	}

	public boolean isDevelopment() {
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BalanceDTO[] closeBalance(DateTime date, Boolean close) {
	
		Filter<Status> openFilter = new Filter<Status>("state", Status.OPEN);
		Filter<Status> closeFilter = new Filter<Status>("state", Status.CLOSE);
		Filter<Date> dateFilter = new Filter<Date>("date", date.toDateMidnight().toDate());		
		List<Balance> openBalances = balanceDao.findByFilter(openFilter, dateFilter);
		
		List<Balance> balances = balanceDao.findByFilter(closeFilter, dateFilter);
		if(balances==null)
			return null;
		balances.addAll(openBalances);
		
		PropertyUtilsBean utilsBean = new PropertyUtilsBean();
		
		BalanceDTO balanceDTO;
		List<BalanceDTO> list = new ArrayList<BalanceDTO>();
		for (Balance balance : balances) {
			if(balance.getState().equals(Status.OPEN)){
				balanceDTO = performBalance(date, balance.getUser(), false);
			} else {
				balanceDTO = new BalanceDTO();
				try {
					utilsBean.copyProperties(balanceDTO, balance);
				} catch (Exception e) {new RuntimeException(e);}
			}
			list.add(balanceDTO);
		}
		
		return list.toArray(new BalanceDTO[list.size()]);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public synchronized void activateBalance(DateTime date, User user, float clearance){
		Filter<Status> activeFilter = new Filter<Status>("state", Status.CLOSE);
		Filter<User> userFilter = new Filter<User>("user", user);
		
		Balance balance=balanceDao.findUniqueByFilter(activeFilter, userFilter);
		
		if(balance!=null){
			if(date.toDateMidnight().isEqual(new DateTime(balance.getDate()).toDateMidnight())){
				return;
			}
			
			balance.setState(Status.DONE);
		}
		Balance nextBalance = new Balance(date.toDate(), user, clearance);//OPEN
		balanceDao.create(nextBalance);
		
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BalanceDTO performBalance(DateTime date, User user, boolean close) {
				
		BalanceDTO balanceDTO = new BalanceDTO();
		PropertyUtilsBean utilsBean = new PropertyUtilsBean();

		Filter<Status> activeFilter = new Filter<Status>("state", Status.OPEN);
		Filter<User> userFilter = new Filter<User>("user", user);		
		Balance balance=balanceDao.findUniqueByFilter(activeFilter, userFilter);
		
		if(balance==null){
			activeFilter = new Filter<Status>("state", Status.CLOSE);
			balance=balanceDao.findUniqueByFilter(activeFilter, userFilter);
			try {
				utilsBean.copyProperties(balanceDTO, balance);
			} catch (Exception e) {new RuntimeException(e);}
			
		} else {
			DateTime balanceDate = new DateTime(balance.getDate());
			if(!date.toDateMidnight().isEqual(balanceDate.toDateMidnight())){
				date = balanceDate;
			}
			
			balanceDTO.setDate(date.toDate());
			balanceDTO.setState(Status.OPEN);
			balanceDTO.setCash(balance.getBalance()-balance.getClearance());
			
			List<Game> games = retriveGames(null, null, date, null, user);
			
			Map<Status, Item> map = new HashMap<Status, Item>();
			for (Game game : games) {
				Item item = map.get(game.getStatus());
				if(item==null){
					item = new Item(game.getStatus().name());
					map.put(game.getStatus(), item);
				}
				item.add(game.getBetAmount(), game.getPrize());
			}
			
			Item[] items = new Item[]{map.get(Status.WINNER),map.get(Status.LOSER),map.get(Status.VALID)};	
			for (Item item : items) {
				if(item!=null){
					balanceDTO.addIncome(item.getAmounts()[0]);
				}
			}
			
			balanceDTO.addCommission(balanceDTO.getIncome()*user.getCommisionRate());
			
			
			List<Game> winners = retriveGames(Status.WINNER, null, null, null, user);
			for (Game game : winners) {
				balanceDTO.addPrizes(game.getPrize());
			}
			
			List<Game> paidToday = retriveGames(Status.PAID, null, null, date, user);
			for (Game game : paidToday) {	
				balanceDTO.addPayments(game.getPrize());
			}
			
			if(close){
				balance.setState(Status.CLOSE);
				try {
					utilsBean.copyProperties(balance, balanceDTO);
				} catch (Exception e) {new RuntimeException(e);}
			}
		}
		
		return balanceDTO;
		
	}

	@Transactional(readOnly=true)
	public List<Authorities> getAuthorities() {
		Filter<String> userFilter = new Filter<String>("username",SecurityContextHolder
						.getContext().getAuthentication().getName());

		return authoritiesDao.findByFilter(userFilter);
		
	}
}
