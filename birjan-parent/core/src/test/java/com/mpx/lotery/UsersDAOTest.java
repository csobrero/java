package com.mpx.lotery;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.bean.Authorities;
import com.mpx.birjan.bean.User;
import com.mpx.birjan.service.dao.IGenericDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
@Transactional
public class UsersDAOTest {

	private IGenericDAO<User> usersDao;

	private IGenericDAO<Authorities> authoritiesDao;

	@Test
	@Rollback(value = false)
	public void usersCreate() {

		User users = new User("xris", "xris", true);
		usersDao.create(users);

		Authorities authorities = new Authorities("xris", "ROLE_ADMIN");
		authoritiesDao.create(authorities);
		authorities = new Authorities("xris", "ROLE_MANAGER");
		authoritiesDao.create(authorities);
		authorities = new Authorities("xris", "ROLE_USER");
		authoritiesDao.create(authorities);

	}

	@Test
	public void retrieveCreate() {

		List<User> all = usersDao.getAll();
		assertNotNull(all);

		List<Authorities> allAuth = authoritiesDao.getAll();
		assertNotNull(allAuth);

	}

	@Resource(name = "genericJpaDAO")
	public final void setUserDao(final IGenericDAO<User> daoToSet) {
		usersDao = daoToSet;
		usersDao.setClazz(User.class);
	}

	@Resource(name = "genericJpaDAO")
	public final void setAuthoritiesDao(final IGenericDAO<Authorities> daoToSet) {
		authoritiesDao = daoToSet;
		authoritiesDao.setClazz(Authorities.class);
	}

}
