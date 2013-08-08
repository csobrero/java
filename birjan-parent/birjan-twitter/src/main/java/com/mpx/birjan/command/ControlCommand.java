package com.mpx.birjan.command;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.BirjanUtils;
import com.mpx.birjan.bean.TwitterBet;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.core.BirjanManager;
import com.mpx.birjan.tweeter.TwitterParser;

import twitter4j.DirectMessage;

@Repository
public class ControlCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(ControlCommand.class);

	@Autowired
	private BirjanManager birjanManager;
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public String execute(DirectMessage directMessage) {

		TwitterBet twitterBet = validate(directMessage);
		
		SimpleMailMessage message = new SimpleMailMessage();
		 
		message.setFrom("fruten");
		message.setTo("csobrero@gmail.com");
		message.setSubject("test");
		message.setText("ola");
//		mailSender.send(message);
		
		return "mail sent.";
	}
	
	private TwitterBet validate(DirectMessage directMessage) {
		TwitterBet bet = TwitterParser.unmarshalControl(directMessage.getText());

		for (String lotteryName : bet.getLotteryNames()) {
			for (String variantName : bet.getVariantNames()) {
				Lottery lottery = Lottery.valueOf(lotteryName + "_" + variantName);
				bet.add(lottery);
			}
		}
		logger.debug(bet.toString());

		return bet;
	}

}
