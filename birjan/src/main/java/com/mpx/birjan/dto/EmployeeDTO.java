package com.mpx.birjan.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
public class EmployeeDTO implements Serializable {

	private static final long serialVersionUID = 7440297955003302414L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "employee_id")
	private Long employeeId;

	@Column(name = "employee_name", nullable = false, length = 30)
	private String employeeName;

	@Column(name = "employee_surname", nullable = false, length = 30)
	private String employeeSurname;

	@Column(name = "job", length = 50)
	private String job;

	public EmployeeDTO() {
	}

	public EmployeeDTO(long employeeId) {
		this.employeeId = employeeId;
	}

	public EmployeeDTO(long employeeId, String employeeName,
			String employeeSurname, String job) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeSurname = employeeSurname;
		this.job = job;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeSurname() {
		return employeeSurname;
	}

	public void setEmployeeSurname(String employeeSurname) {
		this.employeeSurname = employeeSurname;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

}
