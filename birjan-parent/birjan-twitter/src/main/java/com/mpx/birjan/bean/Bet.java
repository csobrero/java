package com.mpx.birjan.bean;

import java.util.Set;

import org.joda.time.DateTime;

import com.mpx.birjan.common.Lottery;

public interface Bet {

	String getNumber();

	Integer getPosition();

	Float getAmount();

	Set<Lottery> getLotteries();
	
	DateTime getDate();

}
