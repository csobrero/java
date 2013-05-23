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
public class IntegrationTest {

	private IGenericDAO<User> usersDao;

	private IGenericDAO<Authorities> authoritiesDao;

	private IGenericDAO<Balance> balanceDao;
	

	DateTime date = new DateTime(2013, 5, 23, 0, 0, 0, 0);
	DateTime yesterday = date.minusDays(1);
	
	@Autowired
	private TransactionalManager manager;

	@Test
	@Rollback(value = false)
	public void usersCreate() {
		
		User user = new User("xris", "xris", true);
		usersDao.create(user);
		
		manager.activateBalance(date, user, 100f);

		Authorities authorities = new Authorities("xris", "ROLE_ADMIN");
		authoritiesDao.create(authorities);
		authorities = new Authorities("xris", "ROLE_MANAGER");
		authoritiesDao.create(authorities);
		authorities = new Authorities("xris", "ROLE_USER");
		authoritiesDao.create(authorities);
		

	}

	@Test
	@Rollback(value = false)
	public void createGames() {
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("xris", "xris"));
		
		List<Balance> all = balanceDao.getAll();

		List<Lottery> lotteries = new ArrayList<Lottery>();
		for (Lottery lottery : Lottery.values()) {
			lotteries.add(lottery);			
		}
		
		List<Object[][]> games = new ArrayList<Object[][]>();
		games.add(new Object[][]{{1,"xxx2",2.45f},{20,"xx02",2.55f}});
		games.add(new Object[][]{{1,"xxx2",2.45f}});
		games.add(new Object[][]{{1,"xxx2",2.45f},{20,"x002",2.55f}});
		games.add(new Object[][]{{1,"xxx2",2.45f},{19,"xx02",2.55f}});
		
		for (Object[][] data : games) {//today
			manager.createGames(lotteries , date , data );
		}
		
		for (Object[][] data : games) {//yesterday
			manager.createGames(lotteries , yesterday , data );
		}

	}
	
//	@Test
	@Rollback(value = false)
	public void createDraw() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("xris", "xris"));
		
		
		String[] numbers = new String[]{"3332","1111","1111","1111","1111",
				"1111","1111","1111","1111","1111","1111","1111","1111","1111","1111",
				"1111","1111","1111","1111","4402"};
		
		for (Lottery lottery : Lottery.values()) {
			manager.createDraw(lottery, date, numbers);
			manager.validateDraw(lottery, date);
		}
		
		for (Lottery lottery : Lottery.values()) {
			manager.createDraw(lottery, yesterday, numbers);
			manager.validateDraw(lottery, yesterday);
		}
		
	}
	
//	@Test
	@Rollback(value = false)
	public void payGames() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("xris", "xris"));
		
		
		List<Game> winners = manager.retriveGames(Status.WINNER, Lottery.NACIONAL_MATUTINA, null, 
				yesterday, null);
		
		for (Game game : winners) {
			manager.processWinners(game.getWager().getHash(),true);
		}
		
	}
	
//	@Test
//	@Rollback(value = false)
	public void createDraw2() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("xris", "xris"));
		
		DateTime date = new DateTime(2013, 5, 15, 0, 0, 0, 0);
		
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
