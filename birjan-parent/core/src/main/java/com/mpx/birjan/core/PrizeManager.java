package com.mpx.birjan.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mpx.birjan.common.Lottery;

@Component
public class PrizeManager {
	
	final Logger logger = LoggerFactory.getLogger(PrizeManager.class);
	
	@Autowired
	private PriceBoardWebService priceBoardWebService;
	
	private Map<Lottery, Future<String[]>> results = new HashMap<Lottery, Future<String[]>>();

	private DateTime now;
	
	@Scheduled(fixedRate=15000) //10 mins.
	void updatePrizes(){
		
		for (Entry<Lottery, Future<String[]>> result : results.entrySet()) {
			try {
				if(result.getValue().isDone() && result.getValue().get().length==20){
					validatePrizes(result.getKey(), result.getValue().get(), now);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		now = new DateTime();
		for (Lottery lottery : Lottery.values()) {
			if(lottery.getRule().getTo().isBefore(now)){
				results.put(lottery, priceBoardWebService.retrieve(lottery));
			}
		}
	}
	
	@Async
	void validatePrizes(Lottery lottery, String[] numbers, DateTime date){
		System.out.println(lottery);
		System.out.println(numbers);
		System.out.println(date);
	}
	
}
