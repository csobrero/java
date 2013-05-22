package com.mpx.birjan.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.pojomatic.annotations.AutoProperty;

@Entity
@AutoProperty
@Table(name = "BALANCE")
public class Balance extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 3981181364194237076L;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@NotNull
	private Boolean active;

	private Float income;

	private Float commission;

	@NotNull
	private Float cash;

	private Float payments;

	private Float prizes;

	public Balance(Date date, Float cash) {
		this.date = date;
		this.active = true;
		this.cash = cash;
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

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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

}
