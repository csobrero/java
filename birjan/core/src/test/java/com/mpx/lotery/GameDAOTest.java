package com.mpx.lotery;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
import com.mpx.birjan.service.dao.FilterDao;
import com.mpx.birjan.service.dao.IGenericDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
@Transactional
public class GameDAOTest {

	private IGenericDAO<Game> gameDao;
	
	@Autowired
	private FilterDao filterDao;

	@Test
	public void gameCreate() throws IOException, ClassNotFoundException {
		Wager wager = new Wager(1f);
		
		Object[][] s = new Object[][] { { "1", 2.50f, "xxxx", 1 },
				{ "1", 2.50f, "xxxx", 1 }, { "1", 2.50f, "xxxx", 1 },
				{ "1", 2.50f, "xxxx", 1 }, { "1", 2.50f, "xxxx", 1 },
				{ "1", 2.50f, "xxxx", 1 }, { "1", 2.50f, "xxxx", 1 },
				{ "1", 2.50f, "xxxx", 1 }, { "1", 2.50f, "xxxx", 1 },
				{ "1", 2.50f, "xxxx", 1 }, { "1", 2.50f, "xxxx", 1 },
				{ "1", 2.50f, "xxxx", 1 }, { "1", 2.50f, "xxxx", 1 },
				{ "1", 2.50f, "xxxx", 1 }, { "1", 2.50f, "xxxx", 1 },
				{ "1", 2.50f, "xxxx", 1 }, { "1", 2.50f, "xxxx", 1 },
				{ "1", 2.50f, "xxxx", 1 }, { "1", 2.50f, "xxxx", 1 },
				{ "1", 2.50f, "xxxx", 1 } };
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(s);
		byte[] data = baos.toByteArray();
		
		
		Game game = null;//new Game(Lottery.NACIONAL_PRIMERA, new Date(), data);
		gameDao.create(game);

		Game game2 = gameDao.getById(game.getId());
		
		ObjectInputStream ois = new ObjectInputStream (new ByteArrayInputStream(game2.getData()));  
		s = (Object[][]) ois.readObject ();  
	    System.out.println();
	}

	@Test
	public void findByFilter() {

//		gameCreate();

		List<Game> all = gameDao.getAll();
		assertTrue(all.size() > 3);

//		all = filterDao.findGameByFilter(Status.OPEN, Lottery.NACIONAL_PRIMERA,null, null);
//		System.out.println(all.size());
//		assertTrue(all.size() == 1);
//		
//		DateTime dt = new DateTime(new Date());
//		DateTime from = new DateTime(dt.year().get(), dt.monthOfYear().get(),
//				dt.dayOfMonth().get(), 0, 0, 0, 0); //today 00h
//		DateTime to = from.plusDays(1); //today 24h
//
//		all = filterDao.findGameByFilter(null, Lottery.NACIONAL_PRIMERA, from, to);
//		System.out.println(all.size());
//		assertTrue(all.size() > 2);
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

	@Resource(name="genericJpaDAO")
	public final void setEmployeeDao(final IGenericDAO<Game> daoToSet) {
		gameDao = daoToSet;
		gameDao.setClazz(Game.class);
	}

}
