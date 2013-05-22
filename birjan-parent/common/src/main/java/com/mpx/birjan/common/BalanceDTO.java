package com.mpx.birjan.common;

import java.io.Serializable;

public class BalanceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean active = true;
	private float cash = 0f;
	private float payments = 0f;
	private float income = 0f;
	private float commission = 0f;
	private float prizes = 0f;
	private int winners = 0;


	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public float getIncome() {
		return income;
	}

	public void addIncome(float income) {
		this.income += income;
	}

	public float getCommission() {
		return commission;
	}

	public void addCommission(float commission) {
		this.commission += commission;
	}

	public float getCash() {
		return cash;
	}

	public void addCash(float cash) {
		this.cash += cash;
	}

	public float getPayments() {
		return payments;
	}

	public void addPayments(float payments) {
		this.payments += payments;
	}

	public float getPrizes() {
		return prizes;
	}

	public void addPrizes(float prizes) {
		this.winners++;
		this.prizes += prizes;
	}

	public int getWinners() {
		return winners;
	}

}
