package com.mpx.lotery;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Status;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.service.dao.GameDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
@Transactional
public class GameDAOTest {

	@Autowired
	private GameDao gameDao;

	public void gameCreate() {
		Wager wager = new Wager(1f, null);
		
		Game game = new Game(Lottery.NACIONAL_PRIMERA, wager, "26");
		game.setStatus(Status.OPEN);
		gameDao.create(game);

		game = new Game(Lottery.NACIONAL_PRIMERA, wager, "26");
		gameDao.create(game);

		game = new Game(Lottery.NACIONAL_NOCTURNA, wager, "26");
		game.setStatus(Status.OPEN);
		gameDao.create(game);
	}

	@Test
	public void findByFilter() {

		gameCreate();

		List<Game> all = gameDao.getAll();
		assertTrue(all.size() > 3);

		all = gameDao.findByFilter(Lottery.NACIONAL_PRIMERA, Status.OPEN, null, null);
		System.out.println(all.size());
		assertTrue(all.size() == 1);
		
		DateTime dt = new DateTime(new Date());
		DateTime from = new DateTime(dt.year().get(), dt.monthOfYear().get(),
				dt.dayOfMonth().get(), 0, 0, 0, 0); //today 00h
		DateTime to = from.plusDays(1); //today 24h

		all = gameDao.findByFilter(Lottery.NACIONAL_PRIMERA, null, from, to);
		System.out.println(all.size());
		assertTrue(all.size() > 2);
	}
	


//	@Test
//	public void findByFilter2() {
//
//		gameCreate();
//
//		List<Game> all = gameDao.getAll();
//		assertTrue(all.size() == 3);
//
//		all = gameDao.findByFilter(Lottery.NACIONAL_PRIMERA, Status.OPEN, null, null);
//		System.out.println(all.size());
//		assertTrue(all.size() == 1);
//	}

}
