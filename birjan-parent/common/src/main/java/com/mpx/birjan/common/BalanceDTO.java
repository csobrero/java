package com.mpx.birjan.common;

import java.io.Serializable;

import org.joda.time.DateTime;

public class BalanceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DateTime date;
	private boolean closed = false;
	private float cash = 0f;
	private float payments = 0f;
	private float income = 0f;
	private float commission = 0f;
	private float prizes = 0f;
	private int winners = 0;

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean active) {
		this.closed = active;
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

	public void setCash(float cash) {
		this.cash = cash;
	}

	public void setPayments(float payments) {
		this.payments = payments;
	}

	public void setIncome(float income) {
		this.income = income;
	}

	public void setCommission(float commission) {
		this.commission = commission;
	}

	public void setPrizes(float prizes) {
		this.prizes = prizes;
	}

	public void setWinners(int winners) {
		this.winners = winners;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

}
