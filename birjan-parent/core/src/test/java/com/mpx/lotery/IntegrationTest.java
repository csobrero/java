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
public class IntegrationTest {

	private IGenericDAO<User> usersDao;

	private IGenericDAO<Authorities> authoritiesDao;

	private IGenericDAO<Balance> balanceDao;
	

	DateTime date = new DateTime(new Date());
	DateTime yesterday = date.minusDays(1);
	
	@Autowired
	private TransactionalManager manager;

	@Test
	@Rollback(value = false)
	public void createGames() throws InterruptedException {
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("xris", "xris"));
		

		Lottery[] lotteries = new Lottery[]{Lottery.NACIONAL_MATUTINA};
		
		List<Object[][]> games = new ArrayList<Object[][]>();
		games.add(new Object[][]{{1,"xxx2",2.45f}});//$5
		
		for (Object[][] data : games) {//today $100
			manager.createGames(lotteries , date , data );
			Thread.currentThread().sleep(50);
			manager.createGames(lotteries , yesterday , data );
			Thread.currentThread().sleep(50);
		}
		
		List<Game> retriveGames = manager.retriveGames(null, Lottery.NACIONAL_MATUTINA, date, null, null);
		
		String[] numbers = new String[]{"3332","1111","1111","1111","1111",
				"1111","1111","1111","1111","1111","1111","1111","1111","1111","1111",
				"1111","1111","1111","1111","4472"};
		
		for (Lottery lottery : lotteries) { //$70x4=280
			manager.createDraw(lottery, date, numbers);
			manager.createDraw(lottery, yesterday, numbers);
		}
		
		for (Lottery lottery : lotteries) { //$70x4=280
			manager.validateDraw(lottery, date);
			manager.validateDraw(lottery, yesterday);
		}
		

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
