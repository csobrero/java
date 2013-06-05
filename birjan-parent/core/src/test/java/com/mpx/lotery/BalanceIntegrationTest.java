package com.mpx.lotery;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.bean.Authorities;
import com.mpx.birjan.bean.Balance;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.User;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.common.Status;
import com.mpx.birjan.core.TransactionalManager;
import com.mpx.birjan.service.dao.IGenericDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
@Transactional
public class BalanceIntegrationTest {

	private IGenericDAO<User> usersDao;

	private IGenericDAO<Authorities> authoritiesDao;

	private IGenericDAO<Balance> balanceDao;
	

	final DateTime today = new DateTime();
	final DateTime yesterday = today.minusDays(1);
	final DateTime tomorow = today.plusDays(1);
	
	@Autowired
	private TransactionalManager manager;

	@Test
	@Rollback(value = false)
	@SuppressWarnings("static-access")
	public void createGames() throws InterruptedException {

		final Lottery[] lotteries = Lottery.values();//new Lottery[]{Lottery.NACIONAL_MATUTINA};
		
		final List<Object[][]> games = new ArrayList<Object[][]>();
		
		Thread t1 = new Thread(new Runnable() {public void run() {
				getUser("u1");
				
				games.add(new Object[][]{{"xx01",1,1f}});
				for (Object[][] data : games) {
					try {
						manager.createGames(lotteries , today , data );
						Thread.currentThread().sleep(10);
						manager.createGames(lotteries , tomorow , data );
						Thread.currentThread().sleep(10);
					} catch (InterruptedException e) {}
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {public void run() {
				getUser("u2");
				
				games.add(new Object[][]{{"xx01",1,1f}});
				for (Object[][] data : games) {
					try {
						manager.createGames(lotteries , today , data );
						Thread.currentThread().sleep(10);
						manager.createGames(lotteries , tomorow , data );
						Thread.currentThread().sleep(10);
					} catch (InterruptedException e) {}
				}
			}
		});
		
		t1.run();
		t2.run();
	}
	
	@Test
	@Rollback(value = false)
	public void createDraw() {
		getUser("u1");
		
		String[] numbers = new String[]{"1101","1111","1111","1111","1111",
				"1111","1111","1111","1111","1111","1111","1111","1111","1111","1111",
				"1111","1111","1111","1111","1111"};
		
		Lottery[] lotteries = new Lottery[]{Lottery.NACIONAL_MATUTINA};
		
		for (Lottery lottery : lotteries) {
			manager.createDraw(lottery, today, numbers);
//			manager.createDraw(lottery, yesterday, numbers);
		}
		
	}
	
	@Test
	@Rollback(value = false)
	public void validateDraw() {
		getUser("u1");
		
		Lottery[] lotteries = new Lottery[]{Lottery.NACIONAL_MATUTINA};
		
		for (Lottery lottery : lotteries) {
			manager.validateDraw(lottery, today);
//			manager.validateDraw(lottery, yesterday);
		}
		
	}
	
	@Test
	@Rollback(value = false)
	public void payGames() {
		getUser("u2");
		
		User user = manager.identify("u2");
		
		List<Game> winners = manager.retriveGames(Status.WINNER, null, null, 
				null, user);
		
		for (Game game : winners) {
			manager.payTicket(game.getWager().getHash(),true);
		}
		
	}

	private void getUser(String user) {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(user, ""));
	}

	@Resource(name = "genericJpaDAO")
	public final void setUserDao(final IGenericDAO<User> daoToSet) {
		usersDao = daoToSet;
		usersDao.setClazz(User.class);
	}

	@Resource(name = "genericJpaDAO")
	public final void setAuthoritiesDao(final IGenericDAO<Authorities> daoToSet) {
		authoritiesDao = daoToSet;
		authoritiesDao.setClazz(Authorities.class);
	}

	@Resource(name = "genericJpaDAO")
	public final void setBalanceDao(final IGenericDAO<Balance> daoToSet) {
		balanceDao = daoToSet;
		balanceDao.setClazz(Balance.class);
	}

}
