package com.mpx.birjan.service.dao;

import org.springframework.stereotype.Repository;

import com.mpx.birjan.dto.EmployeeDTO;

@Repository("employeeDAO")
public class EmployeeDAO extends AbstractJpaDAO<EmployeeDTO> {
    
	public EmployeeDAO() {
		super();
	}
	
}

