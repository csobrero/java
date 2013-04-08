package com.mpx.birjan.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Wrapper implements Serializable {
	
	private static final long serialVersionUID = 4590141740563452067L;

	private String hash;
	
	private byte[] dataVector;
	
	public Wrapper() {
	}

	public Wrapper(String hash, byte[] dataVector) {
		super();
		this.hash = hash;
		this.dataVector = dataVector;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public byte[] getDataVector() {
		return dataVector;
	}

	public void setDataVector(byte[] dataVector) {
		this.dataVector = dataVector;
	}
	
}
