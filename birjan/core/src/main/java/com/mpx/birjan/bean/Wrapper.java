package com.mpx.birjan.bean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.mpx.birjan.common.Status;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Wrapper implements Serializable {
	
	private static final long serialVersionUID = 4590141740563452067L;

	private String hash;
	
	private Object[][] dataVector;
	
	private String status;
	
	private Float prize;
	
	private String username;
	
	private Date createdDate;
	
	@SuppressWarnings("unused")
	private Wrapper() {
	}

	public Wrapper(String hash, Object[][] dataVector, Status status, Float prize, String username, Date createdDate) {
		this.hash = hash;
		this.dataVector = dataVector;
		this.status = status.name();
		this.prize = prize;
		this.username = username;
		this.createdDate = createdDate;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Object[][] getDataVector() {
		return dataVector;
	}

	public void setDataVector(Object[][] dataVector) {
		this.dataVector = dataVector;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Float getPrize() {
		return prize;
	}

	public void setPrize(Float prize) {
		this.prize = prize;
	}
	
}
