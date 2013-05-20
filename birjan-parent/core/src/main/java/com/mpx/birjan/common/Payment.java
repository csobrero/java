package com.mpx.birjan.common;

import java.io.Serializable;

import com.mpx.birjan.bean.Lottery;

public class Payment implements Serializable{

	private static final long serialVersionUID = -8710730024314982088L;

	private Lottery lottery;
	
	private Status status;
	
	private Float amount;

	private Payment() {
	}
	
	public Payment(Lottery lottery, Status status, Float amount) {
		this.lottery = lottery;
		this.status = status;
		this.amount = amount;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
}
