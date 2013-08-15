package com.mpx.birjan.core.manager;

import static com.mpx.birjan.common.Status.ACTIVE;
import static com.mpx.birjan.common.Status.CLOSE;
import static com.mpx.birjan.common.Status.DONE;
import static com.mpx.birjan.common.Status.LOSER;
import static com.mpx.birjan.common.Status.OPEN;
import static com.mpx.birjan.common.Status.PAID;
import static com.mpx.birjan.common.Status.VALID;
import static com.mpx.birjan.common.Status.WINNER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.mpx.birjan.bean.Agency;
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
import com.mpx.birjan.common.Status;
import com.mpx.birjan.common.Ticket;
import com.mpx.birjan.service.dao.Filter;
import com.mpx.birjan.service.dao.GenericJpaDAO;
import com.mpx.birjan.util.Utils;

@Controller
public class TransactionalManager {

	private GenericJpaDAO<Draw> drawDao;

	private GenericJpaDAO<Game> gameDao;

	private GenericJpaDAO<Wager> wagerDao;

	private GenericJpaDAO<User> usersDao;

	private GenericJpaDAO<Agency> agencyDao;

	private GenericJpaDAO<Authorities> authoritiesDao;

	private GenericJpaDAO<Balance> balanceDao;

	@Transactional(rollbackFor = Exception.class)
	public Ticket payTicket(String hash, boolean pay) {
		Filter<String> hashFilter = new Filter<String>("hash", hash);
		Wager wager = wagerDao.findUniqueByFilter(hashFilter);
		if (wager != null) {

			DateTime dt = new DateTime(wager.getGame().get(0).getDate());
			String day = dt.toString("EEEE", new Locale("es")).toUpperCase() + "  " + dt.getDayOfMonth();

			Object[][] data = wager.getGame().get(0).getData();

			Ticket ticket = new Ticket(day, data);
			for (Game game : wager.getGame()) {
				ticket.addPayment(new Payment(game.getLottery(), game.getStatus(), game.getPrize()));
				if (pay && game.is(WINNER)) {
					game.setStatus(PAID);
					openBalance();
				}
			}
			return ticket;
		}
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	public synchronized void createDraw(Lottery lottery, DateTime date, String[] numbers) {

		User user = identifyMe();

		Filter<Lottery> lotteryFilter = new Filter<Lottery>("lottery", lottery);
		Filter<Date> dateFilter = new Filter<Date>("date", date.toDateMidnight().toDate());

		Draw draw = drawDao.findUniqueByFilter(lotteryFilter, dateFilter);

		if (draw != null) {
			Utils.mergeDraw(draw.getNumbers(), numbers);
			drawDao.update(draw);
		} else {
			drawDao.create(new Draw(lottery, date.toDate(), user, numbers));
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public synchronized void validateDraw(Lottery lottery, DateTime date) {

		Filter<Status> statusFilter = new Filter<Status>("status", OPEN);// VALID
		Filter<Lottery> lotteryFilter = new Filter<Lottery>("lottery", lottery);
		Filter<Date> dateFilter = new Filter<Date>("date", date.toDateMidnight().toDate());

		Draw draw = drawDao.findUniqueByFilter(statusFilter, lotteryFilter, dateFilter);

		if (draw != null) {
			String[] winnerNumbers = draw.getNumbers();
			String[] borratina = new String[100];
			for (int i = 0; i < winnerNumbers.length; i++) {
				Preconditions.checkArgument(Pattern.matches("\\d{4}", winnerNumbers[i]), "Can not validate Draw.");
				if(i<20){
					int number = Integer.parseInt(winnerNumbers[i].substring(2));
					borratina[number] = (borratina[number] != null) ? borratina[number] + "," + i : "|" + i;
				}
			}

			List<Game> games = retriveGames(VALID, lottery, date, null, null);
			for (Game game : games) {
				Object[][] data = game.getData();
				boolean win = true;
				if(data.length==1){
					char[] winnerNumber = winnerNumbers[(Integer) data[0][1] - 1].toCharArray();
					char[] number = ((String) data[0][0]).toCharArray();
					for (int i = 0; win && i < 4; i++) {
						win &= number[i] == 'x' || number[i] == winnerNumber[i];
					}
					
				} else if (data.length==5) {
					String w = "";
					for (int i = 0; i < data.length; i++) {
						int in = Integer.parseInt(((String)data[i][0]).substring(2));
						w += borratina[in]!=null?borratina[in]:"";
						
					}
				}
				

				if (win) {
					Float winAmount = 0f;
					for (Object[] row : data) {
						int hits = 3 - ((String) row[0]).lastIndexOf('x');
						winAmount += ((Float) row[2]) * Rule.defaultWinRatios[hits - 1];
					}
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
		list = Utils.retrieveVariantAvailability(view, Rule.National, day);
		return list.toArray(new String[list.size()]);
	}

	@Transactional(rollbackFor = Exception.class)
	public String createGames(Lottery[] lotteries, DateTime date, Object[][] data) {

		float totalBet = 0;
		for (Object[] row : data) {
			totalBet += (Float) row[2];
		}
		User user = identifyMe();
		Wager wager = new Wager(totalBet * lotteries.length, user);

		for (Lottery lottery : lotteries) {

			Game game = new Game(lottery, date.toDate(), wager, data);
			gameDao.create(game);
		}

		openBalance();

		return wager.getHash();
	}

	public List<Game> retriveGames(Lottery lottery, DateTime date, User user, Agency agency) {
		return retriveGames(null, lottery, date, null, user, agency);
	}
	
	public List<Game> retriveGames(Status status, Lottery lottery, DateTime date, DateTime created, User user) {
		return retriveGames(status, lottery, date, created, user, null);
	}
	
	public List<Game> retriveGames(Status status, Lottery lottery, DateTime date, DateTime created, User user, Agency agency) {

		Filter<Status> statusFilter = new Filter<Status>("status", status);
		Filter<Lottery> lotteryFilter = new Filter<Lottery>("lottery", lottery);
		Filter<Date> dateFilter = new Filter<Date>("date", (date != null) ? date.toDateMidnight().toDate() : null);
		Filter<Date> createdFilter = new Filter<Date>("createdDate", (created != null) ? created.toDateMidnight()
				.toDate() : null);
		Filter<User> userFilter = new Filter<User>("wager.user", user);
		Filter<Agency> agencyFilter = new Filter<Agency>("wager.user.agency", agency);

		List<Game> games = gameDao.findByFilter(statusFilter, lotteryFilter, dateFilter, createdFilter, userFilter, agencyFilter);

		return games;
	}

	@Transactional(readOnly = true)
	public User identifyMe() {
		return identify(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@Transactional(readOnly = true)
	public User identify(String userName) {
		Filter<String> userFilter = new Filter<String>("username", userName);
		User user = usersDao.findUniqueByFilter(userFilter);
		return user;
	}
	
	@Transactional(readOnly = true)
	public List<Agency> getAllAgencies() {
		return agencyDao.getAll();
	}

	@Resource(name = "genericJpaDAO")
	public void setDrawDao(final GenericJpaDAO<Draw> daoToSet) {
		drawDao = daoToSet;
		drawDao.setClazz(Draw.class);
	}

	@Resource(name = "genericJpaDAO")
	public void setWagerDao(final GenericJpaDAO<Wager> daoToSet) {
		wagerDao = daoToSet;
		wagerDao.setClazz(Wager.class);
	}

	@Resource(name = "genericJpaDAO")
	public void setGameDao(final GenericJpaDAO<Game> daoToSet) {
		gameDao = daoToSet;
		gameDao.setClazz(Game.class);
	}

	@Resource(name = "genericJpaDAO")
	public void setUsersDao(final GenericJpaDAO<User> daoToSet) {
		usersDao = daoToSet;
		usersDao.setClazz(User.class);
	}

	@Resource(name = "genericJpaDAO")
	public void setBalanceDao(final GenericJpaDAO<Balance> daoToSet) {
		balanceDao = daoToSet;
		balanceDao.setClazz(Balance.class);
	}

	@Resource(name = "genericJpaDAO")
	public void setAgencyDao(final GenericJpaDAO<Agency> daoToSet) {
		agencyDao = daoToSet;
		agencyDao.setClazz(Agency.class);
	}

	@Resource(name = "genericJpaDAO")
	public void setAuthoritiesDao(final GenericJpaDAO<Authorities> daoToSet) {
		authoritiesDao = daoToSet;
		authoritiesDao.setClazz(Authorities.class);
	}

	@Transactional(rollbackFor = Exception.class)
	public BalanceDTO[] closeBalance(DateTime date, Boolean close) {

		PropertyUtilsBean utilsBean = new PropertyUtilsBean();

		Filter<Status> doneFilter = new Filter<Status>("state", DONE);
		Filter<Date> dateFilter = new Filter<Date>("date", date.toDateMidnight().toDate());
		List<Balance> balances = balanceDao.findByFilter(doneFilter, dateFilter);

		if (balances.isEmpty()) {
			Filter<Status> closeFilter = new Filter<Status>("state", CLOSE);
			balances = balanceDao.findByFilter(closeFilter);
			if (close) {
				for (Balance closedBalance : balances) {
					closedBalance.setState(DONE);
					Balance nextBalance = new Balance(new DateTime().toDate(), closedBalance.getUser(),
							closedBalance.getBalance());// ACTIVE
					balanceDao.create(nextBalance);
				}
			} else {
				Filter<Status> openFilter = new Filter<Status>("state", OPEN);
				List<Balance> openBalances = balanceDao.findByFilter(openFilter);
				balances.addAll(openBalances);
			}
		}

		BalanceDTO closeBalanceDTO = new BalanceDTO();
		closeBalanceDTO.setState(OPEN);
		BalanceDTO balanceDTO;
		List<BalanceDTO> list = new ArrayList<BalanceDTO>();
		for (Balance balance : balances) {
			if (balance.is(OPEN)) {
				balanceDTO = performBalance(date, balance.getUser(), false);
			} else {
				balanceDTO = new BalanceDTO(balance.getUser().getUsername());
				closeBalanceDTO.setState(balance.getState());
				closeBalanceDTO.addCash(balance.getCash());
				closeBalanceDTO.addPayments(balance.getPayments());
				closeBalanceDTO.addIncome(balance.getIncome());
				closeBalanceDTO.addCommission(balance.getCommission());
				closeBalanceDTO.addPrizes(balance.getPrizes());
				try {
					utilsBean.copyProperties(balanceDTO, balance);
				} catch (Throwable t) {
					Throwables.propagate(t);
				}
			}
			list.add(balanceDTO);
		}
		list.add(closeBalanceDTO);

		return list.toArray(new BalanceDTO[list.size()]);
	}

	@Transactional(rollbackFor = Exception.class)
	public BalanceDTO performBalance(DateTime date, User user, boolean close) {

		BalanceDTO balanceDTO = new BalanceDTO(user.getUsername());
		PropertyUtilsBean utilsBean = new PropertyUtilsBean();

		Filter<Status> openFilter = new Filter<Status>("state", OPEN);
		Filter<User> userFilter = new Filter<User>("user", user);
		Filter<Date> dateFilter = new Filter<Date>("date", date.toDateMidnight().toDate());
		Balance balance = balanceDao.findUniqueByFilter(openFilter, userFilter,
				date.toDateMidnight().isBefore(new DateMidnight()) ? dateFilter : null);

		if (balance == null) {
			Filter<Status> closeFilter = new Filter<Status>("state", CLOSE);
			balance = balanceDao.findUniqueByFilter(closeFilter, userFilter,
					date.toDateMidnight().isBefore(new DateMidnight()) ? dateFilter : null);
			if (balance != null) {
				try {
					utilsBean.copyProperties(balanceDTO, balance);
				} catch (Throwable t) {
					Throwables.propagate(t);
				}
			}
		} else {

			balanceDTO.setDate(balance.getDate());
			balanceDTO.setState(OPEN);
			balanceDTO.setCash(balance.getBalance() - balance.getClearance());

			List<Game> games = retriveGames(null, null, null, date, user);

			Map<Status, Item> map = new HashMap<Status, Item>();
			for (Game game : games) {
				Item item = map.get(game.getStatus());
				if (item == null) {
					item = new Item(game.getStatus().name());
					map.put(game.getStatus(), item);
				}
				item.add(game.getAmount(), game.getPrize());
			}

			Item[] items = new Item[] { map.get(WINNER), map.get(LOSER), map.get(VALID), map.get(OPEN) };
			for (Item item : items) {
				if (item != null) {
					balanceDTO.addIncome(item.getAmounts()[0]);
				}
			}

			balanceDTO.addCommission(balanceDTO.getIncome() * user.getCommisionRate());

			List<Game> winners = retriveGames(WINNER, null, null, null, user);
			for (Game game : winners) {
				balanceDTO.addPrizes(game.getPrize());
			}

			List<Game> paidToday = retriveGames(PAID, null, null, date, user);
			for (Game game : paidToday) {
				balanceDTO.addPayments(game.getPrize());
			}

			if (close) {
				try {
					balanceDTO.setState(CLOSE);
					utilsBean.copyProperties(balance, balanceDTO);
				} catch (Throwable t) {
					Throwables.propagate(t);
				}
			}
		}

		return balanceDTO;

	}

	public Balance getActiveBalance(String userName) {
		Filter<Status> activeFilter = new Filter<Status>("state", ACTIVE);
		Filter<Status> openFilter = new Filter<Status>("state", OPEN);
		Filter<Date> dateFilter = new Filter<Date>("date", new DateMidnight().toDate());
		Filter<String> userFilter = new Filter<String>("user.username", userName);
		Balance balance = balanceDao.findUniqueByFilter(activeFilter, userFilter);
		if (balance == null)
			balance = balanceDao.findUniqueByFilter(openFilter, userFilter, dateFilter);
		if(balance == null)
			throw new RuntimeException("NOT ACTIVE BALANCE");
		return balance;
	}

	public void openBalance() {
		openBalance(new DateTime(), identifyMe());
	}

	@Transactional
	public void openBalance(DateTime date, User user) {
		Balance balance = getActiveBalance(user.getUsername());
		if (balance.is(ACTIVE)) {
			balance.setState(OPEN);
			balance.setDate(date.toDate());
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<String> getOnlyUsers() {
		Filter<String> managerFilter = new Filter<String>("authority", "ROLE_MANAGER");
		List<Authorities> managers = authoritiesDao.findByFilter(managerFilter);
		Collection<String> collect = CollectionUtils.collect(managers, new Transformer() {
			public Object transform(Object input) {
				return ((Authorities) input).getUsername();
			}
		});
		List<User> users = usersDao.getAll();
		Collection<String> list = CollectionUtils.collect(users, new Transformer() {
			public Object transform(Object input) {
				return ((User) input).getUsername();
			}
		});
		list.removeAll(collect);

		return list;
	}

}
