package com.mpx.lotery;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mpx.birjan.service.impl.BirjanWebService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx-WS.xml")
public class WebServiceClientTest {

	@Autowired
	private BirjanWebService webService;

	@Test
	public void ticketServiceNotNull() {
		assertNotNull(webService);
	}

	@Test
	public void ticketServiceNotNull2() throws InterruptedException {
		Float[] betAmount = new Float[20];
		String[] numbers = new String[20];
		betAmount[1] = 1f;
		numbers[1] = "1";
		String id = webService.createGame("nacional","matutina", "31", new Object[][]{});
		System.out.println(id);
		Thread.sleep(1000);
		id = webService.createGame("nacional","matutina", "31", new Object[][]{});
		System.out.println(id);
		assertNotNull(id);
	}
	

}
