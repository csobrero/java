package com.mpx.birjan.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Person;
import com.mpx.birjan.bean.Status;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.service.ITicketService;
import com.mpx.birjan.service.dao.IGenericDAO;
import com.mpx.birjan.service.dao.PersonDao;

@Service
public class TicketServiceImpl implements ITicketService {

	private IGenericDAO<Game> gameDao;

	private IGenericDAO<Wager> wagerDao;

	@Autowired
	private PersonDao personDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public long createGame(Lottery lottery, Integer[] numbers) {
		Preconditions.checkNotNull(lottery);
		Preconditions.checkNotNull(numbers);

		Game game = new Game(lottery, numbers);
		gameDao.create(game);

		return game.getId();

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void invalidGame(long gameId) {
		Game game = gameDao.getById(gameId);
		game.setStatus(Status.INVALID);
		gameDao.update(game);
	}

	/**
	 * Will invalidate gameId then will create a new game.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public long updateGame(long gameId, Lottery lottery, Integer[] numbers) {
		Preconditions.checkNotNull(lottery);
		Preconditions.checkNotNull(numbers);

		invalidGame(gameId);

		return createGame(lottery, numbers);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public long createWager(float betAmount, long gameId, Long personId) {

		Person person = (personId != null) ? personDao.getById(personId) : null;
		Game game = gameDao.getById(gameId);

		Wager wage = new Wager(betAmount, game, person);
		wagerDao.create(wage);

		return wage.getId();
	}

	@Resource(name = "genericJpaDAO")
	public final void gameDao(final IGenericDAO<Game> daoToSet) {
		gameDao = daoToSet;
		gameDao.setClazz(Game.class);
	}

	@Resource(name = "genericJpaDAO")
	public final void wagerDao(final IGenericDAO<Wager> daoToSet) {
		wagerDao = daoToSet;
		wagerDao.setClazz(Wager.class);
	}

}
