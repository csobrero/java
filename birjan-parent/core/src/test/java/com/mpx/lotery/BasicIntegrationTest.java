package com.mpx.lotery;

import java.util.ArrayList;
import java.util.Date;
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
public class BasicIntegrationTest {

	private IGenericDAO<User> usersDao;

	private IGenericDAO<Authorities> authoritiesDao;

	private IGenericDAO<Balance> balanceDao;
	

	DateTime date = new DateTime(new Date());
	DateTime yesterday = date.minusDays(1);
	
	@Autowired
	private TransactionalManager manager;

	@Test
	@Rollback(value = false)
	public void usersCreate() {
		
		User user = new User("xris", "xris", true);
		usersDao.create(user);
		
		manager.activateBalance(date, user, -100f);

		Authorities authorities = new Authorities("xris", "ROLE_ADMIN");
		authoritiesDao.create(authorities);
		authorities = new Authorities("xris", "ROLE_MANAGER");
		authoritiesDao.create(authorities);
		authorities = new Authorities("xris", "ROLE_USER");
		authoritiesDao.create(authorities);
		

	}

	@Test
	@Rollback(value = false)
	public void createGames() throws InterruptedException {
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("xris", "xris"));
		
		List<Balance> all = balanceDao.getAll();
		
		Lottery[] lotteries = new Lottery[]{Lottery.NACIONAL_MATUTINA};
		
		List<Object[][]> games = new ArrayList<Object[][]>();
		games.add(new Object[][]{{1,"xxx2",2.45f}});//$5
		games.add(new Object[][]{{1,"xxx9",20f},{20,"xx02",25f}});//$5
		games.add(new Object[][]{{1,"xxx9",2.55f}});//$5
		games.add(new Object[][]{{1,"xxx9",10f},{20,"x002",10f}});//$5
		games.add(new Object[][]{{1,"xxx9",10f},{19,"xx02",20f}});//$5
		
		for (Object[][] data : games) {//today $100
			manager.createGames(lotteries , date , data );
			Thread.currentThread().sleep(10);
		}
		
		for (Object[][] data : games) {//yesterday $100
			manager.createGames(lotteries , yesterday , data );
			Thread.currentThread().sleep(10);
		}
		

	}
	
	@Test
	@Rollback(value = false)
	public void createDraw() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("xris", "xris"));
		
		
		String[] numbers = new String[]{"3332","1111","1111","1111","1111",
				"1111","1111","1111","1111","1111","1111","1111","1111","1111","1111",
				"1111","1111","1111","1111","4472"};
		
		for (Lottery lottery : Lottery.values()) { //$70x4=280
			manager.createDraw(lottery, date, numbers);
			manager.validateDraw(lottery, date);
		}
		
		for (Lottery lottery : Lottery.values()) { //$70x4=280
			manager.createDraw(lottery, yesterday, numbers);
			manager.validateDraw(lottery, yesterday);
		}
		
	}
	
	@Test
	@Rollback(value = false)
	public void payGames() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("xris", "xris"));
		
		
		List<Game> winners = manager.retriveGames(Status.WINNER, Lottery.NACIONAL_MATUTINA, yesterday, 
				null, null);
		
		for (Game game : winners) { //pago 2.45x7=17.15
			manager.processWinners(game.getWager().getHash(),true);
		}
		
	}
	
//	@Test
//	@Rollback(value = false)
	public void createDraw2() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("xris", "xris"));
		
		String[] numbers = new String[]{"3332","1111","1111","1111","1111",
				"1111","1111","1111","1111","1111","1111","1111","1111","1111","1111",
				"1111","1111","1111","1111","4402"};
		

		manager.createDraw(Lottery.NACIONAL_NOCTURNA, date, numbers);
		manager.validateDraw(Lottery.NACIONAL_NOCTURNA, date);
		
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