package com.mpx.birjan.core;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import twitter4j.DirectMessage;

import com.google.common.base.Optional;
import com.mpx.birjan.bean.BirjanUtils;
import com.mpx.birjan.bean.User;
import com.mpx.birjan.common.BalanceDTO;
import com.mpx.birjan.tweeter.TwitterParser;

@Repository
public class BalanceCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(BalanceCommand.class);

	@Autowired
	private BirjanManager birjanManager;
	
	@Autowired
	private TransactionalManager txManager;

	@Override
	public String execute(DirectMessage directMessage) {
		String day = TwitterParser.unmarshal(directMessage.getText());
		
		DateTime date = Optional.fromNullable(BirjanUtils.getDate(day)).or(new DateTime());
		User user = txManager.identifyMe();
		
		BalanceDTO balance = txManager.performBalance(date, user, false);
		
		String message = buildMessage(balance);
		return message;
	}
	
	private String buildMessage(BalanceDTO balance) {
		
		DateTime date = new DateTime(balance.getDate());
		String message = date.getDayOfMonth() + "/" + date.getMonthOfYear();
		
		message += " : RECAUDACION=$" + BirjanUtils.money.format(balance.getIncome()) + " : COMISION=$"
				+ BirjanUtils.money.format(balance.getCommission()) + " : PREMIOS PAGADOS=$"
				+ BirjanUtils.money.format(balance.getPayments()) + " : (" + BirjanUtils.money.format(balance.getWinners())
				+ ")PREMIOS A PAGAR=$" + BirjanUtils.money.format(balance.getPrizes()) + " : CAJA INICIAL=$"
				+ BirjanUtils.money.format(balance.getCash());
		
		return message;
	}

}
