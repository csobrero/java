package com.mpx.birjan.core;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.bean.Authorities;
import com.mpx.birjan.bean.Balance;
import com.mpx.birjan.bean.Draw;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.User;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.service.dao.GenericJpaDAO;

@Controller
public class BirjanManager {
	
	@Autowired
	private TransactionalManager txManager;

	private GenericJpaDAO<Draw> drawDao;

	private GenericJpaDAO<Game> gameDao;

	private GenericJpaDAO<Wager> wagerDao;

	private GenericJpaDAO<User> usersDao;

	private GenericJpaDAO<Authorities> authoritiesDao;

	private GenericJpaDAO<Balance> balanceDao;

	@Transactional(rollbackFor = Exception.class)
	public String createGames(DateTime date, String number, Integer position, Float amount, Lottery... lotteries) {
		
		User user = txManager.identifyMe();
		Wager wager = new Wager(amount * lotteries.length, user);

		for (Lottery lottery : lotteries) {

			Game game = new Game(lottery, date.toDate(), number, position, amount, wager);
			gameDao.create(game);
		}

		txManager.openBalance();

		return wager.getHash();
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

}
