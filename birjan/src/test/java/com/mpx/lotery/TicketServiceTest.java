package com.mpx.lotery;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mpx.birjan.bean.Lottery;
import com.mpx.birjan.service.IPersonService;
import com.mpx.birjan.service.ITicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
public class TicketServiceTest {

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

		long gameId = ticketService.createGame(Lottery.NACIONAL_PRIMERA, new Integer[] {
				26, null, 3 });

		long ticketId = ticketService.createWager(1f, gameId, personId);
		assertNotNull(ticketId);
	}

}
