package com.mpx.lotery;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.dto.EmployeeDTO;
import com.mpx.birjan.service.dao.EmployeeDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-appCtx.xml")
//@Transactional
public class EmployeeDAOTest {

	@Resource(name = "employeeDAO")
	private EmployeeDAO employeeDao;

	@Test
	public void employeeDAONotNulltest() {
		assertNotNull(employeeDao);
	}
	
	@Test
	@Transactional
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
//		
		assertNotNull(all);
	}

}
