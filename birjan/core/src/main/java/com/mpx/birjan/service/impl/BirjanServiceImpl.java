package com.mpx.birjan.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
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
	public String createGame(final Lottery lottery, Float[] betAmount,
			final String[] numbers, final Long personId) {

		float totalBet = 0;
		for (Float amount : betAmount)
			totalBet += amount!=null?amount:0;
		
		Person person = (personId != null) ? personDao.getById(personId) : null;
		
		Wager wager = new Wager(totalBet, person);

		Game game = new Game(lottery, wager, betAmount, numbers);
		gameDao.create(game);
		

		return game.getHash();

	}

	@Override
	public void setWinnerGame(final Lottery lottery, final String[] numbers,
			final Date date) {
		Preconditions.checkNotNull(lottery);
		Preconditions.checkNotNull(numbers);
		Preconditions.checkNotNull(date);

		gameDao.create(new Game(lottery, numbers, date));
	}

	@Resource(name = "genericJpaDAO")
	public final void gameDao(final IGenericDAO<Game> daoToSet) {
		gameDao = daoToSet;
		gameDao.setClazz(Game.class);
	}

}
