package com.mpx.lotery;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

import com.mpx.birjan.bean.Authorities;
import com.mpx.birjan.bean.Users;
import com.mpx.birjan.service.dao.FilterDao;
import com.mpx.birjan.service.dao.IGenericDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
@Transactional
public class UsersDAOTest {

	private IGenericDAO<Users> usersDao;

	private IGenericDAO<Authorities> authoritiesDao;

	@Autowired
	private FilterDao filterDao;

	@Test
	@Rollback(value = false)
	public void usersCreate() {

		Users users = new Users("xris", "xris", true);
		usersDao.create(users);

	}
	
	@Test
	public void retrieveCreate() {

		List<Users> all = usersDao.getAll();
		assertNotNull(all);

	}

	@Resource(name = "genericJpaDAO")
	public final void setUserDao(final IGenericDAO<Users> daoToSet) {
		usersDao = daoToSet;
		usersDao.setClazz(Users.class);
	}
	
	@Resource(name = "genericJpaDAO")
	public final void setAuthoritiesDao(final IGenericDAO<Authorities> daoToSet) {
		authoritiesDao = daoToSet;
		authoritiesDao.setClazz(Authorities.class);
	}

}
