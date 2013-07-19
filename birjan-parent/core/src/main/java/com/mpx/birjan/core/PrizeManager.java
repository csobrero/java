package com.mpx.birjan.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mpx.birjan.bean.Draw;
import com.mpx.birjan.common.Lottery;

@Component
public class PrizeManager {
	
	final Logger logger = LoggerFactory.getLogger(PrizeManager.class);
	
	@Autowired
	private PriceBoardWebService priceBoardWebService;
	
	@Autowired
	private TransactionalManager txManager;
	
	private Map<Lottery, Future<String[]>> results = new HashMap<Lottery, Future<String[]>>();

	private DateTime now;
	
//	@Scheduled(fixedRate=15000) //10 mins.
	public void updatePrizes(){
		
		SecurityContextHolder.getContext().setAuthentication(
					new UsernamePasswordAuthenticationToken("ma", ""));

		for (Entry<Lottery, Future<String[]>> result : results.entrySet()) {
			try {
				if (result.getValue().isDone() && result.getValue().get().length == 20) {
					Draw draw = txManager.retrieveDraw(result.getKey(), now);
					if(draw==null || draw.getNumbers().length<20){
						txManager.createDraw(result.getKey(), now, result.getValue().get());
						txManager.validateDraw(result.getKey(), now);
						logger.info("Validated: " + result.getKey().name() + " || Date: " + now);
					}
				}
			} catch (Exception e) {
				logger.error("Exception: " + e.getClass().getName() + " || Message:  " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		now = new DateTime();
		for (Lottery lottery : Lottery.values()) {
			if (lottery.getRule().getTo().isBefore(now)) {
				results.put(lottery, priceBoardWebService.retrieve(lottery, now));
			}
		}
	}
	
}
