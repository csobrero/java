package com.mpx.birjan.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.pojomatic.annotations.AutoProperty;

import com.mpx.birjan.common.Status;

@Entity
@AutoProperty
@Table(name = "BALANCE")
public class Balance extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 3981181364194237076L;

	@Enumerated(EnumType.STRING)
	private Status state;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date date;
	
	private Float clearance;
	private Float cash;
	private Float payments;
	private Float income;
	private Float commission;
	private Float prizes;

	@NotNull
	@ManyToOne
	private User user;

	public Balance() {
	}
	
	public Balance(Date date, User user, float clearance) {
		this.state = Status.OPEN;
		this.date = date;
		this.user = user;
		this.clearance = clearance;
		this.cash = 0f;
		this.payments = 0f;
		this.income = 0f;
		this.commission = 0f;
		this.prizes = 0f;
	}
	
	public Float getBenefits() {
		return income - commission - prizes;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getIncome() {
		return income;
	}

	public void setIncome(Float income) {
		this.income = income;
	}

	public Float getCommission() {
		return commission;
	}

	public void setCommission(Float commission) {
		this.commission = commission;
	}

	public Float getCash() {
		return cash;
	}

	public void setCash(Float cash) {
		this.cash = cash;
	}

	public Float getPayments() {
		return payments;
	}

	public void setPayments(Float payments) {
		this.payments = payments;
	}

	public Float getPrizes() {
		return prizes;
	}

	public void setPrizes(Float prizes) {
		this.prizes = prizes;
	}

	public User getUser() {
		return user;
	}

	public Float getClearance() {
		return clearance;
	}
	
	public float getBalance() {
		return getCash() - getPayments() + getIncome() - getCommission()
				- getPrizes();
	}

	public Status getState() {
		return state;
	}

	public void setState(Status state) {
		this.state = state;
	}

	public void close(float income, float commission, float payments,float prizes) {
		this.income = income;
		this.commission = commission;
		this.payments = payments;
		this.prizes = prizes;
		this.state = Status.CLOSE;
	}

}
