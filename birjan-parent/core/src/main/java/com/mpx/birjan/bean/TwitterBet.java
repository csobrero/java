package com.mpx.birjan.bean;

import java.util.Collection;

import org.joda.time.DateTime;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

@AutoProperty
public final class TwitterBet extends BetImpl {

	private Collection<String> lotteryNames;
	private Collection<String> variantNames;

	public TwitterBet(String number, Integer position, Float amount, DateTime date, Collection<String> lotteryNames,
			Collection<String> variantNames) {
		super(number, position, amount, date);
		this.lotteryNames = lotteryNames;
		this.variantNames = variantNames;
	}
	
	public String asText(){
		return number + (position!=null?position:"") + amount + 
				lotteryNames.toArray().toString() + variantNames.toArray().toString();
	}

	public Collection<String> getLotteryNames() {
		return lotteryNames;
	}

	public Collection<String> getVariantNames() {
		return variantNames;
	}

}
