package com.mpx.lotery;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.dto.EmployeeDTO;
import com.mpx.birjan.service.dao.IGenericDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
// @Transactional
public class EmployeeDAOTest {

	private IGenericDAO<EmployeeDTO> employeeDao;

	@Autowired
	public final void setEmployeeDao(final IGenericDAO<EmployeeDTO> daoToSet) {
		employeeDao = daoToSet;
		employeeDao.setClazz(EmployeeDTO.class);
	}

	@Test
	public void employeeDAONotNulltest() {
		assertNotNull(employeeDao);
	}

	@Test
	@Transactional
	@Rollback(value = false)
	public void employeeDAOtest() {
		EmployeeDTO employee = new EmployeeDTO();
		employee.setEmployeeName("dummy");
		employee.setEmployeeSurname("dummy");

		assertNull(employee.getEmployeeId());

		employeeDao.create(employee);
	}

	@Test
	public void employeeDAORetrieve() {

		List<EmployeeDTO> all = employeeDao.getAll();

		assertTrue(all.size() > 0);
	}

}
