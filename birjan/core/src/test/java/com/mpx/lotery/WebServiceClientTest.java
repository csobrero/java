package com.mpx.lotery;

import static org.junit.Assert.assertNotNull;

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
	

}
