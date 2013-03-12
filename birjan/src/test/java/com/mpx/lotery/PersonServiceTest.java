package com.mpx.lotery;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.bean.Person;
import com.mpx.birjan.service.IPersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
public class PersonServiceTest {

	@Autowired
	private IPersonService personService;

	@Test
	public void personServiceNotNull() {
		assertNotNull(personService);
	}
	
	@Test
	public void personCreate() {
		
		personService.saveOrUpdatePerson(null, "dummy", null, null);
	}

}
