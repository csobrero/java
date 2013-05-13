package com.mpx.birjan.common;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Jugada implements Serializable {
	
	private static final long serialVersionUID = 4590141740563452067L;
	
	private String day;
	
	@XmlJavaTypeAdapter(MapAdapter.class)
	private Map<String, Float> lotteries;
	
	private Object[][] data;
	
	public Jugada() {
	}
	
	public Jugada(String day, Map<String, Float> lotteries, Object[][] data) {
		this.day = day;
		this.lotteries = lotteries;
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

	public Map<String, Float> getLotteries() {
		return lotteries;
	}

	public void setLotteries(Map<String, Float> lotteries) {
		this.lotteries = lotteries;
	}
	
}
