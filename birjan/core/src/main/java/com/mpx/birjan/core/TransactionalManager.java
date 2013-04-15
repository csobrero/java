package com.mpx.birjan.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.SerializationUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.Draw;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Person;
import com.mpx.birjan.bean.Status;
import com.mpx.birjan.bean.User;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.core.Rule.VARIANT;
import com.mpx.birjan.service.IPersonService;
import com.mpx.birjan.service.dao.Filter;
import com.mpx.birjan.service.dao.GenericJpaDAO;
import com.mpx.birjan.service.impl.BirjanUtils;

@Controller
public class TransactionalManager {

	@Autowired
	private IPersonService personService;

	private GenericJpaDAO<Draw> drawDao;

	private GenericJpaDAO<Game> gameDao;

	private GenericJpaDAO<User> usersDao;

	@Transactional(readOnly = true)
	public Object[][] retrieveByHash(String hash) {
		Filter<String> filter = new Filter<String>("hash", hash);
		Game game = gameDao.findUniqueByFilter(filter);
		if (game != null) {
			Object[][] data = (Object[][]) SerializationUtils.deserialize(game
					.getData());
			return data;
		}
		return null;
	}

	public long saveOrUpdatePerson(Long id, String name, String surname,
			String movile) {
		return personService.saveOrUpdatePerson(id, name, surname, movile);
	}

	public List<Person> findByFilter(String name, String surname, String movile) {
		return personService.findByFilter(name, surname, movile);
	}

	@Transactional(rollbackFor = Exception.class)
	public synchronized void createDraw(Lottery lottery, DateTime date,
			String[] numbers) {

		User user = identify();

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
			
			List<Game> games = retriveGames(Status.VALID, lottery, date, null);
			for (Game game : games) {
				Object[][] data = (Object[][])SerializationUtils.deserialize(game.getData());
				Map<Integer, Integer> hits = new HashMap<Integer, Integer>();
				for (Object[] row : data) {
					float betAmount = (Float)row[1];
					char[] number = ((String)row[2]).toCharArray();
					char[] winnerNumber = winnerNumbers[(Integer)row[3]].toCharArray();
					
					int i = 3;
					for (boolean win=true; win && i >= 0 ; i--) {
						win &= (number[i]==winnerNumber[i]);
					}
					if (i < 3 && number[i] == 'x') {
						hits.put((Integer) row[3], 3 - i);
//						game.addHits((Float)row[1])
						
//						lottery.getRule().calculateWinAmount(betAmount, winPositions);
					}
				}
			}
			
	
			if (draw != null) {
				draw.setStatus(Status.VALID);
				drawDao.update(draw);
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
	public String createGame(Lottery lottery, DateTime date, Object[][] data) {
		float totalBet = 0;
		for (Object[] row : data) {
			totalBet += (Float) row[1];
		}
		User user = identify();
		Wager wager = new Wager(totalBet, user);

		byte[] serialData = SerializationUtils.serialize(data);

		Game game = new Game(lottery, date.toDate(), wager, serialData);
		gameDao.create(game);

		return game.getHash();
	}

	public List<Game> retriveGames(Status status, Lottery lottery, DateTime date, User user) {

		Filter<Status> statusFilter = new Filter<Status>("status", status);
		Filter<Lottery> lotteryFilter = new Filter<Lottery>("lottery", lottery);
		Filter<Date> dateFilter = new Filter<Date>("date", date.toDate());
		Filter<User> userFilter = new Filter<User>("user", user);

		List<Game> games = gameDao.findByFilter(statusFilter, lotteryFilter,
				dateFilter, userFilter);

		return games;
	}

	@Resource(name = "genericJpaDAO")
	public final void setDrawDao(final GenericJpaDAO<Draw> daoToSet) {
		drawDao = daoToSet;
		drawDao.setClazz(Draw.class);
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

	private User identify() {
		Filter<String> filter = new Filter<String>("username",
				SecurityContextHolder.getContext().getAuthentication()
						.getName());
		User user = usersDao.findUniqueByFilter(filter);
		return user;
	}

	// private Map<Integer, Integer> matchWinNumbers(String patterns,
	// String candidate) {
	// Map<Integer, Integer> hits = null;
	// int k = 0;
	// for (int i = 3; i < 80; i += 4) {
	// if (candidate.charAt(i) == patterns.charAt(i)) {
	// k = 1;
	// for (int j = (i - 1); j > (i - 4); j--) {
	// if (candidate.charAt(j) == patterns.charAt(j))
	// k++;
	// else if (candidate.charAt(j) == 'x')
	// break;
	// else {
	// k = 0;
	// break;
	// }
	// }
	// if (k > 0) {
	// if (hits == null)
	// hits = new HashMap<Integer, Integer>();
	// hits.put((i + 1) / 4, k);
	// k = 0;
	// }
	// }
	// }
	// return hits;
	// }

}
