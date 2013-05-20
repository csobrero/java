package com.mpx.lotery;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Transactional
	public void personCreate() {
		long personId = personService.saveOrUpdatePerson(null, "dummy", null, "11211");
		personService.saveOrUpdatePerson(null, "dummy", "surname", "332");
		personService.saveOrUpdatePerson(null, "xxx", null, "244");
		
		List<Person> list = personService.findByFilter("um", "sur", null);
		assertTrue(list.size() == 1);

		list = personService.findByFilter("um", null, null);
		assertTrue(list.size() == 2);

		list = personService.findByFilter(null, null, "2");
		assertTrue(list.size() == 3);

		list = personService.findByFilter("du", "sur", "5");
		assertTrue(list.isEmpty());

		list = personService.findByFilter(null, "", null);//same as getAll
		assertTrue(list.size()>3);
		

		
		personService.saveOrUpdatePerson(personId, "dummy", null, "11311");
		list = personService.findByFilter(null, null, "2");
		assertTrue(list.size() == 2);
	}

}
