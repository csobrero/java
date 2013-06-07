package com.mpx.birjan.bean;

import java.util.Collection;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

@AutoProperty
public final class TweetBet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Property(policy = PojomaticPolicy.EQUALS_TO_STRING)
	private Long id;
	
	private int number;
	private int position;
	private float amount;
	
	@Transient
	private Collection<String> lotteries;
	@Transient
	private Collection<String> variants;

	public TweetBet(int number, int position, float amount, Collection<String> lotteries, 
			Collection<String> variants) {
		this.number = number;
		this.position = position;
		this.amount = amount;
		this.lotteries = lotteries;
		this.variants = variants;
	}

	public Long getId() {
		return id;
	}

	public int getNumber() {
		return number;
	}

	public int getPosition() {
		return position;
	}

	public float getAmount() {
		return amount;
	}

	public Collection<String> getLotteries() {
		return lotteries;
	}

	public Collection<String> getVariants() {
		return variants;
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
