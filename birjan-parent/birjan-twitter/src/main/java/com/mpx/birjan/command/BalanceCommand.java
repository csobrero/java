package com.mpx.birjan.command;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import twitter4j.DirectMessage;

import com.google.common.base.Optional;
import com.mpx.birjan.bean.User;
import com.mpx.birjan.common.BalanceDTO;
import com.mpx.birjan.core.manager.BirjanManager;
import com.mpx.birjan.core.manager.TransactionalManager;
import com.mpx.birjan.tweeter.TwitterParser;
import com.mpx.birjan.util.Utils;

@Repository
public class BalanceCommand implements Command<String> {

	private static final Logger logger = LoggerFactory.getLogger(BalanceCommand.class);

	@Autowired
	private BirjanManager birjanManager;
	
	@Autowired
	private TransactionalManager txManager;

	@Override
	public String execute(DirectMessage directMessage) {
		String day = TwitterParser.unmarshal(directMessage.getText());
		
		DateTime date = Optional.fromNullable(Utils.getDate(day)).or(new DateTime());
		User user = txManager.identifyMe();
		
		BalanceDTO balance = txManager.performBalance(date, user, false);
		
		String message = buildMessage(balance);
		return message;
	}

	@Scheduled(cron = "0 0 21 * * 1-6")
	public void closeAll() {
		birjanManager.closeAll();
	}

	@Scheduled(cron = "0 0 0 * * 1-6")
	public void activateAll() {
		birjanManager.activateAll();
	}

	private String buildMessage(BalanceDTO balance) {
		
		DateTime date = new DateTime(balance.getDate());
		String message = date.getDayOfMonth() + "/" + date.getMonthOfYear();
		
		message += " : RECAUDACION=$" + Utils.money.format(balance.getIncome()) + " : COMISION=$"
				+ Utils.money.format(balance.getCommission()) + " : PREMIOS PAGADOS=$"
				+ Utils.money.format(balance.getPayments()) + " : (" + Utils.money.format(balance.getWinners())
				+ ")PREMIOS A PAGAR=$" + Utils.money.format(balance.getPrizes()) + " : CAJA INICIAL=$"
				+ Utils.money.format(balance.getCash());
		
		return message;
	}

}
