package com.mpx.birjan.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
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
import com.mpx.birjan.bean.User;
import com.mpx.birjan.command.NotifyManagerCommand;
import com.mpx.birjan.common.Lottery;

@Component
public class PrizeManager {
	
	final Logger logger = LoggerFactory.getLogger(PrizeManager.class);
	
	@Autowired
	private PriceBoardWebService priceBoardWebService;
	
	@Autowired
	private TransactionalManager txManager;
	
	@Autowired
	private NotificationManager notificationManager;
	
	@Autowired
	private NotifyManagerCommand command;
	
	private Map<Lottery, Future<String[]>> results = new HashMap<Lottery, Future<String[]>>();
	
	private Set<Lottery> notified = new HashSet<Lottery>();

	private DateTime now;
	
	@Scheduled(fixedRate=60000) //15 mins.
	public void updatePrizes(){
		
		SecurityContextHolder.getContext().setAuthentication(
					new UsernamePasswordAuthenticationToken("ma", ""));

		for (Entry<Lottery, Future<String[]>> result : results.entrySet()) {
			try {
				if (result.getValue().isDone() && result.getValue().get().length > 0){ 
					if(result.getValue().get().length == 20) {	
					Draw draw = txManager.retrieveDraw(result.getKey(), now);
						if(draw==null || draw.getNumbers().length<20){
							
							txManager.createDraw(result.getKey(), now, result.getValue().get());
							txManager.validateDraw(result.getKey(), now);
							
							logger.info("Validated: " + result.getKey().name() + " || Date: " + now);
						}
					}
					if(result.getValue().get()[0]!=null && !notified.contains(result.getKey())){
						notified.add(result.getKey());
						command.notifyAllManagersByTwitter("Premio: " + result.getKey().getLotteryName() + " " + result.getKey().getVariantName()
								+ " a la cabeza: " + result.getValue().get()[0]);
					}
				}
			} catch (InterruptedException e) {
				logError(e);
			} catch (ExecutionException e) {
				logError(e);
			}
		}
		
		
		now = new DateTime();
		for (Lottery lottery : Lottery.values()) {
			if (lottery.getRule().getTo().isBefore(now) && lottery.getRule().getTo().plusHours(2).isAfter(now)) {
				results.put(lottery, priceBoardWebService.retrieve(lottery, now));
			} else {
				notified.remove(lottery);
				Future<String[]> result = results.remove(lottery);
				try {
					if(result!=null && result.isDone() && result.get().length < 20){		
						User admin = txManager.identify("1491378438");
						command.notifyUserByTwitter(admin, "PREMIO " + lottery.getLotteryName() + " " +
								lottery.getVariantName() + " INCOMPLETO.");
					}
				} catch (Exception e) {
					logError(e);
				}
			}
		}
	}
	
	@Scheduled(cron = "0 28 11 * * 1-5")
	private void schedule1(){
		notificationManager.sendControlSheetToManagers();
	}
	
	@Scheduled(cron = "0 58 13 * * 1-5")
	private void schedule2(){
		notificationManager.sendControlSheetToManagers();
	}
	
	@Scheduled(cron = "0 28 17 * * 1-5")
	private void schedule3(){
		notificationManager.sendControlSheetToManagers();
	}
	
	@Scheduled(cron = "0 58 20 * * 1-5")
	private void schedule4(){
		notificationManager.sendControlSheetToManagers();
	}

	private void logError(Exception e) {
		e.printStackTrace();
		logger.error("Exception: " + e.getClass().getName() + " || Message:  " + e.getMessage());
	}
	
}
