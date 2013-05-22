package com.mpx.birjan.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.Draw;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.User;
import com.mpx.birjan.bean.Wager;
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
		Filter<Date> dateFilter = new Filter<Date>("date", date.toDate());

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
		Filter<Date> dateFilter = new Filter<Date>("date", date.toDate());

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
	public String createGames(List<Lottery> lotteries, DateTime date,
			Object[][] data) {
		
		float totalBet = 0;
		for (Object[] row : data) {
			totalBet += (Float) row[2];
		}
		User user = identify(null);
		Wager wager = new Wager(totalBet*lotteries.size(), user);
		
		for (Lottery lottery : lotteries) {

			Game game = new Game(lottery, date.toDate(), wager, data);
			gameDao.create(game);
		}
		
		return wager.getHash();
	}

	public List<Game> retriveGames(Status status, Lottery lottery, DateTime date, DateTime created, User user) {

		Filter<Status> statusFilter = new Filter<Status>("status", status);
		Filter<Lottery> lotteryFilter = new Filter<Lottery>("lottery", lottery);
		Filter<Date> dateFilter = new Filter<Date>("date", (date!=null)?date.toDate():null);
		Filter<Date> createdFilter = new Filter<Date>("created", (created!=null)?created.toDate():null);
		Filter<User> userFilter = new Filter<User>("wager.user", user);

		List<Game> games = gameDao.findByFilter(statusFilter, lotteryFilter,
				dateFilter, createdFilter, userFilter);

		return games;
	}

	public User identify(String userName) {
		Filter<String> filter = new Filter<String>("username",
				(userName!=null)?userName:SecurityContextHolder.getContext().getAuthentication()
						.getName());
		User user = usersDao.findUniqueByFilter(filter);
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

	public boolean isDevelopment() {
		return true;
	}
}
