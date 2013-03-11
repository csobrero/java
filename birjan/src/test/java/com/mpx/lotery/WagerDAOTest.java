package com.mpx.lotery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Person;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.service.dao.IGenericDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
public class WagerDAOTest {

	private IGenericDAO<Wager> wagerDao;

	@Autowired
	public final void setDao(final IGenericDAO<Wager> daoToSet) {
		wagerDao = daoToSet;
		wagerDao.setClazz(Wager.class);
	}

	@Test
	@Transactional
	@Rollback(value = false)
	public void wagerCreate() {
		Game game = new Game(Lottery.NACIONAL, new int[] { 26 });
		Person person = new Person("Carlos", "Paredes", "1554337788");
		Wager wager = new Wager(1f, game, person);

		wagerDao.create(wager);

		assertNotNull(wager);
	}

	@Test
	public void retrieveWager() {
		List<Wager> list = wagerDao.getAll();
		assertTrue(list.size() > 0);
		Wager wager = list.get(0);
		System.out.println(wager);
		assertNotNull(wager.getId());
		assertNull(wager.getWinAmount());
		assertNotNull(wager.getGame());
		assertEquals(26, wager.getGame().getNumbers()[0]);
		assertNotNull(wager.getPerson());
		assertNotNull(wager.getPerson().getName());
	}

}
