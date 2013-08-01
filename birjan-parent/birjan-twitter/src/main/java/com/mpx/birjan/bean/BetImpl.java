package com.mpx.birjan.bean;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.mpx.birjan.common.Lottery;

@AutoProperty
public class BetImpl implements Bet {

	protected String number;
	protected Integer position;
	protected Float amount;
	protected Set<Lottery> lotteries;
	protected DateTime date;

	public BetImpl(String number, Integer position, Float amount, DateTime date) {
		super();
		this.number = number;
		this.position = position;
		this.amount = amount;
		this.date = date;
	}

	public DateTime getDate() {
		return this.date;
	}

	public String getNumber() {
		return number;
	}

	public Integer getPosition() {
		return position;
	}

	public Float getAmount() {
		return amount;
	}

	public Set<Lottery> getLotteries() {
		return lotteries;
	}

	public void add(Lottery lottery) {
		if (lotteries == null)
			this.lotteries = new HashSet<Lottery>();
		this.lotteries.add(lottery);

	}

	@Override
	public boolean equals(Object o) {
		return Pojomatic.equals(this, o);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

}
