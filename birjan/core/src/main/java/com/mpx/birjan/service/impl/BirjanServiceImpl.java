package com.mpx.birjan.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.SerializationUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Person;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.core.BirjanController;
import com.mpx.birjan.service.IBirjanService;
import com.mpx.birjan.service.dao.IGenericDAO;
import com.mpx.birjan.service.dao.PersonDao;

@Service
public class BirjanServiceImpl implements IBirjanService {

	@Autowired
	private BirjanController controller;

	@Autowired
	private PersonDao personDao;

	private IGenericDAO<Game> gameDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void processWinners(final Lottery lottery, final Date date) {

		controller.processWinners(lottery, new DateTime(date));

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String createGame(String lotteryName, String variant, String day,
			Object[][] data) {
		
//		Person person = (personId != null) ? personDao.getById(personId) : null;

		float totalBet = 0;
		for (Object[] row : data) {
			totalBet += (Float)row[1];			
		}
		
		Wager wager = new Wager(totalBet);
		
		Date date = BirjanUtils.getDate(day);
		
		Lottery lottery = Lottery.valueOf((lotteryName+"_"+variant).toUpperCase());
		
		byte[] serialData = SerializationUtils.serialize(data);

		Game game = new Game(lottery, date, wager, serialData);
		gameDao.create(game);
		

		System.out.println(game.getHash());
		return game.getHash();
	}

	@Override
	public void setWinnerGame(final Lottery lottery,
			final Date date, final Object[][] data) {
		Preconditions.checkNotNull(lottery);
		Preconditions.checkNotNull(data);
		Preconditions.checkNotNull(date);

		byte[] serialData = SerializationUtils.serialize(data);
		
		gameDao.create(new Game(lottery, date, serialData));
	}

	@Resource(name = "genericJpaDAO")
	public final void gameDao(final IGenericDAO<Game> daoToSet) {
		gameDao = daoToSet;
		gameDao.setClazz(Game.class);
	}

}
