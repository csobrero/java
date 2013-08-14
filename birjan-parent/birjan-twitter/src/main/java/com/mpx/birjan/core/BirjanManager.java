package com.mpx.birjan.core;

import static com.mpx.birjan.common.Status.CLOSE;
import static com.mpx.birjan.common.Status.DONE;
import static com.mpx.birjan.common.Status.OPEN;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.mpx.birjan.bean.Authorities;
import com.mpx.birjan.bean.Balance;
import com.mpx.birjan.bean.Draw;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.User;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.common.Status;
import com.mpx.birjan.service.dao.Filter;
import com.mpx.birjan.service.dao.GenericJpaDAO;

@Controller
public class BirjanManager {
	
	final Logger logger = LoggerFactory.getLogger(BirjanManager.class);
	
	@Autowired
	private TransactionalManager txManager;

	private GenericJpaDAO<Draw> drawDao;

	private GenericJpaDAO<Game> gameDao;

	private GenericJpaDAO<Wager> wagerDao;

	private GenericJpaDAO<User> usersDao;

	private GenericJpaDAO<Authorities> authoritiesDao;

	private GenericJpaDAO<Balance> balanceDao;

	@Transactional(rollbackFor = Exception.class)
	public String createTicket(DateTime date, String number, Integer position, Float amount, Lottery... lotteries) {
		
		User user = txManager.identifyMe();
		Wager wager = new Wager(amount * lotteries.length, user);

		for (Lottery lottery : lotteries) {
			Game game = new Game(lottery, date.toDate(), number, position, amount, wager);
			gameDao.create(game);
		}

		txManager.openBalance();
		
		String hash = wager.getHash();
		logger.debug(hash);
		return hash;
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteTicket(String hash) {
		Filter<String> hashFilter = new Filter<String>("hash", hash);
		Wager wager = wagerDao.findUniqueByFilter(hashFilter);
		if (wager != null) {
			for (Game game : wager.getGame()) {
				if(game.is(Status.VALID))
					game.setStatus(Status.INVALID);
			}
		}
	}
	
	@Transactional
	public Collection<Game> showTicket(String hash) {
		Filter<String> hashFilter = new Filter<String>("hash", hash);
		Wager wager = wagerDao.findUniqueByFilter(hashFilter);
		if (wager != null) {
			return Collections2.filter(wager.getGame(), new Predicate<Game>() {
				public boolean apply(Game game){
					return !game.is(Status.INVALID);
				}
			});
		}
		return null;
	}

	@Transactional
	public void closeAll() {
		Filter<Status> openFilter = new Filter<Status>("state", OPEN);
		Filter<Date> dateFilter = new Filter<Date>("date", (new DateMidnight()).toDate());
		List<Balance> balances = balanceDao.findByFilter(openFilter, dateFilter);
		if(balances!=null)
			for (Balance balance : balances) {
				balance.setState(CLOSE);
			}
		
	}

	@Transactional
	public void activateAll() {
		Filter<Status> closeFilter = new Filter<Status>("state", CLOSE);
//		Filter<Date> dateFilter = new Filter<Date>("date", (new DateMidnight()).minusDays(1).toDate());
		List<Balance> balances = balanceDao.findByFilter(closeFilter);
		if(!balances.isEmpty()){
			float clearence = 0f;
			for (Balance closedBalance : balances) {
				closedBalance.setState(DONE);
				clearence += closedBalance.getBalance();
			}
			Balance nextBalance = new Balance(new DateTime().toDate(), balances.get(0).getUser(), clearence);// ACTIVE
			balanceDao.create(nextBalance);
		}
		
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
