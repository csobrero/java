package com.mpx.lotery;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.bean.Person;
import com.mpx.birjan.service.dao.IGenericDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
public class PersonDAOTest {

	private IGenericDAO<Person> personDao;

	@Resource(name="genericJpaDAO")
	public final void setEmployeeDao(final IGenericDAO<Person> daoToSet) {
		personDao = daoToSet;
		personDao.setClazz(Person.class);
	}

	@Test
	public void personDaoNotNull() {
		assertNotNull(personDao);
	}

	@Test
	@Transactional
	@Rollback(value = false)
	public void personCreate() {
		Person person = new Person();
		person.setName("dummy");
		personDao.create(person);
	}

	@Test
	public void personRetrieve() {

		List<Person> all = personDao.getAll();
		assertTrue(all.size() > 0);
		
		for (Person person : all) {
			System.out.println(person + " hasCode: " + person.hashCode());		
		}
	}

}
