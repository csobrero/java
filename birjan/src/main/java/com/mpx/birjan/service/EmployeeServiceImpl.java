package com.mpx.birjan.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpx.birjan.dto.EmployeeDTO;

//@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

//	@Autowired
//	private EmployeeDAO employeeDAO;

	@PostConstruct
	public void init() throws Exception {
	}

	@PreDestroy
	public void destroy() {
	}

	public EmployeeDTO findEmployee(long employeeId) {

//		return employeeDAO.getById(employeeId);
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveEmployee(long employeeId, String name, String surname,
			String jobDescription) throws Exception {

//		EmployeeDTO employeeDTO = employeeDAO.getById(employeeId);
//
//		if (employeeDTO == null) {
//			employeeDTO = new EmployeeDTO(employeeId, name, surname,
//					jobDescription);
//			employeeDAO.create(employeeDTO);
//		}

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateEmployee(long employeeId, String name, String surname,
			String jobDescription) throws Exception {

//		EmployeeDTO employeeDTO = employeeDAO.getById(employeeId);
//
//		if (employeeDTO != null) {
//			employeeDTO.setEmployeeName(name);
//			employeeDTO.setEmployeeSurname(surname);
//			employeeDTO.setJob(jobDescription);
//		}

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteEmployee(long employeeId) throws Exception {

//		EmployeeDTO employeeDTO = employeeDAO.getById(employeeId);
//
//		if (employeeDTO != null)
//			employeeDAO.delete(employeeDTO);

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveOrUpdateEmployee(long employeeId, String name,
			String surname, String jobDescription) throws Exception {

//		EmployeeDTO employeeDTO = new EmployeeDTO(employeeId, name, surname,
//				jobDescription);
//
//		employeeDAO.update(employeeDTO);

	}

}
