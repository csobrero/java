package com.mpx.birjan.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Jugada implements Serializable {
	
	private static final long serialVersionUID = 4590141740563452067L;
	
	private String day;
	
	private String[] lottery;
	
	private Object[][] data;
	
	public Jugada() {
	}
	
	public Jugada(String day, String[] lottery, Object[][] data) {
		this.day = day;
		this.lottery = lottery;
		this.data = data;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String[] getLottery() {
		return lottery;
	}

	public void setLottery(String[] lottery) {
		this.lottery = lottery;
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}
	
}
