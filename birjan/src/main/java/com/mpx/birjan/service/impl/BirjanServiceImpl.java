package com.mpx.birjan.service.impl;

import java.util.Date;

import javax.annotation.Resource;

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
	public long createGame(final Lottery lottery, final String numbers,
			float betAmount, final Long personId) {
		Preconditions.checkNotNull(lottery);
		Preconditions.checkNotNull(numbers);

		Person person = personDao.getById(personId);
		Wager wager = new Wager(betAmount, person);

		Game game = new Game(lottery, wager, numbers);
		gameDao.create(game);

		return game.getWager().getId();

	}

	@Override
	public void setWinnerGame(final Lottery lottery, final String numbers,
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
