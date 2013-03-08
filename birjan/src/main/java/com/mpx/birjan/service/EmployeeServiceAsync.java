package com.mpx.birjan.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mpx.birjan.dto.EmployeeDTO;

public interface EmployeeServiceAsync {

	void deleteEmployee(long employeeId, AsyncCallback<Void> callback);

	void findEmployee(long employeeId, AsyncCallback<EmployeeDTO> callback);

	void saveEmployee(long employeeId, String name, String surname,
			String jobDescription, AsyncCallback<Void> callback);

	void saveOrUpdateEmployee(long employeeId, String name, String surname,
			String jobDescription, AsyncCallback<Void> callback);

	void updateEmployee(long employeeId, String name, String surname,
			String jobDescription, AsyncCallback<Void> callback);

}
