package com.mpx.birjan.command;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import twitter4j.DirectMessage;

import com.mpx.birjan.bean.Agency;
import com.mpx.birjan.bean.TwitterBet;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.core.manager.BirjanManager;
import com.mpx.birjan.core.manager.NotificationManager;
import com.mpx.birjan.core.manager.TransactionalManager;
import com.mpx.birjan.tweeter.TwitterParser;
import com.mpx.birjan.util.Utils;
import com.mpx.birjan.util.WorkbookHandler.WorkbookHolder;

@Repository
public class ControlCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(ControlCommand.class);

	@Autowired
	private BirjanManager birjanManager;
	
	@Autowired
	private TransactionalManager txManager;
	
	@Autowired
	private NotificationManager notificationManager;

	@Override
	@Transactional
	public String execute(DirectMessage directMessage) {

		TwitterBet twitterBet = validate(directMessage);
		
		Agency agency = txManager.identifyMe().getAgency();
		List<WorkbookHolder> workbooks = birjanManager.getWorkbooks(twitterBet.getLotteries(), new DateTime(), agency);
		
		notificationManager.send(agency.getEmail(), "Control de Jugadas", 
				"Ver planillas de control adjuntas.", Utils.transform(workbooks));
				
		return "Control enviado al manager.";
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
