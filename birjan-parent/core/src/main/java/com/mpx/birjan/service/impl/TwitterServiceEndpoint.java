package com.mpx.birjan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import twitter4j.DirectMessage;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.Bet;
import com.mpx.birjan.bean.BirjanUtils;
import com.mpx.birjan.bean.TwitterBet;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.core.BirjanManager;
import com.mpx.birjan.tweeter.TwitterParser;

@Service
@Secured("ROLE_USER")
public class TwitterServiceEndpoint {

	@Autowired
	private BirjanManager manager;

	public Bet validate(DirectMessage directMessage){
		TwitterBet bet = TwitterParser.unmarshal(directMessage.getText());
		
//		twitterBet.add(Lottery.valueOf(l+"_"+v));
		DateTime date = bet.getDate();
		List<Lottery> lotteries = new ArrayList<Lottery>();
		for (String lotteryName : bet.getLotteryNames()) {
			for (String variantName : bet.getVariantNames()) {
				Lottery lottery = Lottery.valueOf(lotteryName + "_" + variantName);
				Preconditions.checkArgument(BirjanUtils.isValid(lottery, date, true), "[" + lottery.name() + "]"+ " HORARIO NO VALIDO");
				lotteries.add(lottery);
			}
		}
		
		
		return bet;
	}
	
	public String createGames(String number, Integer position, Float amount, String day, String... lotteryNames) {
		Preconditions.checkNotNull(number);
		Preconditions.checkNotNull(amount);
		Preconditions.checkNotNull(day);
		Preconditions.checkNotNull(lotteryNames);


		String hash = null;//manager.createGames(date, number, position, amount,
//				lotteries.toArray(new Lottery[lotteries.size()]));

		System.out.println(hash);

		return hash;
	}

}
