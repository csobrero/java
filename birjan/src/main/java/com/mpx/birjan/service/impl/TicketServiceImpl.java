package com.mpx.birjan.service.impl;

import java.util.Date;

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
	public long createGame(final Lottery lottery, final String numbers, float betAmount, Long personId) {
		Preconditions.checkNotNull(lottery);
		Preconditions.checkNotNull(numbers);
		
		Person person = personDao.getById(personId);
		Wager wager = new Wager(betAmount, person);

		Game game = new Game(lottery, wager, numbers);
		gameDao.create(game);

		return game.getWager().getId();

	}
	
//	@Override
	public void setWinnerGame(final Lottery lottery, final String numbers, final Date date) {
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

	@Resource(name = "genericJpaDAO")
	public final void wagerDao(final IGenericDAO<Wager> daoToSet) {
		wagerDao = daoToSet;
		wagerDao.setClazz(Wager.class);
	}

}
