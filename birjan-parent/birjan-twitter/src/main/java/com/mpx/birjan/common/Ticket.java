package com.mpx.birjan.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket implements Serializable {
	
	private static final long serialVersionUID = 4590141740563452067L;
	
	private String day;
	
	private List<Payment> payments;
	
	private Object[][] data;
	
	public Ticket() {
	}
	
	public Ticket(String day, Object[][] data) {
		this.day = day;
		this.data = data;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}
	
	public void addPayment(Payment payment){
		if(payments==null)
			this.payments = new ArrayList<Payment>();
		this.payments.add(payment);
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	
}
