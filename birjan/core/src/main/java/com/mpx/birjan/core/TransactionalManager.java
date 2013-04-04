package com.mpx.birjan.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.SerializationUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.Draw;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Person;
import com.mpx.birjan.bean.Status;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.core.Rule.Nacional;
import com.mpx.birjan.service.IPersonService;
import com.mpx.birjan.service.dao.FilterDao;
import com.mpx.birjan.service.dao.IGenericDAO;
import com.mpx.birjan.service.impl.BirjanUtils;

@Controller
public class TransactionalManager {

	@Autowired
	private IPersonService personService;
	
	@Autowired
	private FilterDao filterDao;
	
	private IGenericDAO<Draw> drawDao;

	private IGenericDAO<Game> gameDao;

	@Transactional(readOnly = true)
	public Object[][] retrieveByHash(String hash) {
		Game game = filterDao.findGameByHash(hash);
		if (game != null) {
			Object[][] data = (Object[][])SerializationUtils.deserialize(game.getData());
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void processWinners(Lottery lottery, DateTime date) {

		Game winGame = retrieveWinGame(lottery, date);

		// if (winGame != null) {
		// List<Game> list = retrieveCandidates(lottery, date);
		//
		// if (!list.isEmpty()) {
		// for (Game candidate : list) {
		//
		// Map<Integer, Integer> winPositions = matchWinNumbers(
		// winGame.getNumbers(), candidate.getNumbers());
		//
		// if (winPositions != null) {
		//
		// float betAmount = candidate.getWager().getBetAmount();
		// float winAmount = lottery.getRule().calculateWinAmount(betAmount,
		// winPositions);
		//
		// candidate.getWager().setWinAmount(winAmount);
		// candidate.setStatus(Status.WINNER);
		//
		// } else {
		// candidate.setStatus(Status.LOSER);
		// }
		// }
		// }
		// }
	}

	private Map<Integer, Integer> matchWinNumbers(String patterns,
			String candidate) {
		Map<Integer, Integer> hits = null;
		int k = 0;
		for (int i = 3; i < 80; i += 4) {
			if (candidate.charAt(i) == patterns.charAt(i)) {
				k = 1;
				for (int j = (i - 1); j > (i - 4); j--) {
					if (candidate.charAt(j) == patterns.charAt(j))
						k++;
					else if (candidate.charAt(j) == 'x')
						break;
					else {
						k = 0;
						break;
					}
				}
				if (k > 0) {
					if (hits == null)
						hits = new HashMap<Integer, Integer>();
					hits.put((i + 1) / 4, k);
					k = 0;
				}
			}
		}
		return hits;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public synchronized void createDraw(String lotteryName, String variant, String day,
			String[] numbers) {
		Preconditions.checkNotNull(lotteryName);
		Preconditions.checkNotNull(variant);
		Preconditions.checkNotNull(day);
		Preconditions.checkNotNull(numbers);
		if (numbers.length == 20) {
			Date date = BirjanUtils.getDate(day).toDate();	
			Lottery lottery = Lottery.valueOf((lotteryName+"_"+variant).toUpperCase());
			
			List<Draw> list = filterDao.findDrawByFilter(lottery, date);
			if(CollectionUtils.isNotEmpty(list)){
				Draw draw = list.get(0);
				BirjanUtils.mergeDraw(draw.getNumbers(), numbers);
				drawDao.update(draw);
			} else {
				drawDao.create(new Draw(lottery, date, numbers));
			}
		}
	}

	public void validateDraw(String lotteryName, String variant, String day) {
		Preconditions.checkNotNull(lotteryName);
		Preconditions.checkNotNull(variant);
		Preconditions.checkNotNull(day);
		Date date = BirjanUtils.getDate(day).toDate();	
		Lottery lottery = Lottery.valueOf((lotteryName+"_"+variant).toUpperCase());
		List<Draw> list = filterDao.findDrawByFilter(lottery, date);
		if(CollectionUtils.isNotEmpty(list)){
			Draw draw = list.get(0);
			draw.setStatus(Status.VALID);
			drawDao.update(draw);
		}
	}
	
	public String[] retrieveDraw(String lottery, String variant, String day) {
		Date date = BirjanUtils.getDate(day).toDate();
		Lottery lott = Lottery.valueOf((lottery + "_" + variant).toUpperCase());
		List<Draw> list = filterDao.findDrawByFilter(null, lott, date);
		if (list != null && list.size() == 1)
			return list.get(0).getNumbers();
		return null;
	}

	private List<Game> retrieveCandidates(Lottery lottery, DateTime date) {
		return filterDao.findGameByFilter(Status.VALID, lottery, lottery.getRule()
				.getFrom(date), lottery.getRule().getTo(date));
	}

	private Game retrieveWinGame(Lottery lottery, DateTime date) {
		List<Game> list = filterDao.findGameByFilter(Status.OPEN, lottery, lottery
				.getRule().getFrom(date), lottery.getRule().getTo(date));

		if (list.size() == 1)
			return list.get(0);

		return null;
	}

	public String[] getComboOptions(String combo, String day) {
		List<String> list = new ArrayList<String>();
		if(combo.equalsIgnoreCase("loteria")){
			return new String[]{"NACIONAL","PROVINCIA"};
		}
		
		list = BirjanUtils.retrieveVariantAvailability(Rule.National, day);
		
//		list = Nacional.VARIANT.toList();
		return list.toArray(new String[list.size()]);
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String createGame(String lotteryName, String variant, String day,
			Object[][] data) {
		
//		Person person = (personId != null) ? personDao.getById(personId) : null;

		float totalBet = 0;
		for (Object[] row : data) {
			totalBet += (Float)row[1];			
		}
		
		Wager wager = new Wager(totalBet);
		
		Date date = BirjanUtils.getDate(day).toDate();
		
		Lottery lottery = Lottery.valueOf((lotteryName+"_"+variant).toUpperCase());
		
		byte[] serialData = SerializationUtils.serialize(data);

		Game game = new Game(lottery, date, wager, serialData);
		gameDao.create(game);
		

		System.out.println(game.getHash());
		return game.getHash();
	}

	@Resource(name="genericJpaDAO")
	public final void setDrawDao(final IGenericDAO<Draw> daoToSet) {
		drawDao = daoToSet;
		drawDao.setClazz(Draw.class);
	}

	@Resource(name="genericJpaDAO")
	public final void setGameDao(final IGenericDAO<Game> daoToSet) {
		gameDao = daoToSet;
		gameDao.setClazz(Game.class);
	}

}
