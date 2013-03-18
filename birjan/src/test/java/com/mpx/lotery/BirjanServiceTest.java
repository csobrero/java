package com.mpx.lotery;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.bean.Person;
import com.mpx.birjan.bean.Wager;
import com.mpx.birjan.service.IPersonService;
import com.mpx.birjan.service.ITicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
public class BirjanServiceTest {

	@Autowired
	private ITicketService ticketService;

	@Autowired
	private IPersonService personService;

	@Test
	public void ticketServiceNotNull() {
		assertNotNull(ticketService);
	}

	@Test
	public void ticketCreate() {
		long personId = personService.saveOrUpdatePerson(null, "dummy", null,
				"11211");

		long ticketId = ticketService.createGame(Lottery.NACIONAL_PRIMERA, "26", 1f, personId);
		assertNotNull(ticketId);
	}

}
