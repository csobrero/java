package com.mpx.birjan.command;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import twitter4j.DirectMessage;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.TwitterBet;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.core.manager.BirjanManager;
import com.mpx.birjan.tweeter.TwitterParser;
import com.mpx.birjan.util.Utils;

@Repository
public class CreateCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

	@Autowired
	private BirjanManager birjanManager;

	@Override
	public String execute(DirectMessage directMessage) {
		TwitterBet bet = validate(directMessage);

		String hash = birjanManager.createTicket(bet.getDate(), bet.getNumber(), bet.getPosition(), bet.getAmount(),
				bet.getLotteries().toArray(new Lottery[bet.getLotteries().size()]));

		String message = buildMessage(bet, hash);

		return message;
	}

	private String buildMessage(TwitterBet bet, String hash) {

		String message = hash + " : $" + Utils.money.format(bet.getAmount() * bet.getLotteries().size()) + " : "
				+ bet.asText() + " : " + bet.getDate().getDayOfMonth() + "/" + bet.getDate().getMonthOfYear();

		return message.toUpperCase();
	}

	private TwitterBet validate(DirectMessage directMessage) {
		TwitterBet bet = TwitterParser.unmarshalBet(directMessage.getText());

		// twitterBet.add(Lottery.valueOf(l+"_"+v));
		DateTime date = bet.getDate();
		for (String lotteryName : bet.getLotteryNames()) {
			for (String variantName : bet.getVariantNames()) {
				Lottery lottery = Lottery.valueOf(lotteryName + "_" + variantName);
				Preconditions.checkArgument(Utils.isValid(lottery, date, true), "[" + lottery.getLotteryName() +
						" " + lottery.getVariantName() + "]" + " HORARIO FINALIZADO");
				bet.add(lottery);
			}
		}
		logger.debug(bet.toString());

		return bet;
	}

}
