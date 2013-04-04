package com.mpx.lotery;

import static org.junit.Assert.*;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.bean.Draw;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Status;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.service.dao.FilterDao;
import com.mpx.birjan.service.dao.IGenericDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
@Transactional
public class DrawDAOTest {

	private IGenericDAO<Draw> drawDao;
	
	@Autowired
	private FilterDao filterDao;

	@Test
	@Rollback(value=false)
	public void drawCreate() {
		
		String[] numbers = { "1234", "1234", "1234", "1234", "1234", "1234",
				"1234", "1234", "1234", "1234", "1234", "1234", "1234", "1234",
				"1234", "1234", "1234", "1234", "1234", "1234" };

		Draw draw = new Draw(Lottery.NACIONAL_MATUTINA, new Date(), numbers);
		drawDao.create(draw);
		
		Date date = new DateTime(new Date()).plusDays(1).toDate();
		
		draw = new Draw(Lottery.NACIONAL_VESPERTINA, new Date(), numbers);
		drawDao.create(draw);
		
		
	}

	@Test
	public void findByFilter() {
		
		List<Draw> filter = filterDao.findDrawByFilter(null, null, new Date());
		
		assertEquals(2, filter.size());
		
		filter = filterDao.findDrawByFilter(null, Lottery.NACIONAL_MATUTINA, null);
		
		assertEquals(1, filter.size());
		
	}
	
//	@Test
	public void findAll() {
		
		List<Draw> all = drawDao.getAll();
		
		System.out.println();
		
	}

	@Resource(name="genericJpaDAO")
	public final void setEmployeeDao(final IGenericDAO<Draw> daoToSet) {
		drawDao = daoToSet;
		drawDao.setClazz(Draw.class);
	}

}
