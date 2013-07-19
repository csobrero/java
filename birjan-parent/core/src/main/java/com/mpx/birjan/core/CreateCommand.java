package com.mpx.birjan.core;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import twitter4j.DirectMessage;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.BirjanUtils;
import com.mpx.birjan.bean.TwitterBet;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.tweeter.TwitterParser;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CreateCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

	private DirectMessage directMessage;

	@Autowired
	private BirjanManager birjanManager;

	@Override
	public String execute() {
		TwitterBet bet = validate(directMessage);

		String hash = birjanManager.createTicket(bet.getDate(), bet.getNumber(), bet.getPosition(), bet.getAmount(),
						bet.getLotteries().toArray(new Lottery[bet.getLotteries().size()]));

		String message = buildMessage(bet, hash);

		return message;
	}

	private String buildMessage(TwitterBet bet, String hash) {
		
		String message = hash
				+ " : $"
				+ BirjanUtils.money.format(bet.getAmount()
						* bet.getLotteries().size()) + " : " + bet.asText()
				+ " : " + bet.getDate().getDayOfMonth() + "/"
				+ bet.getDate().getMonthOfYear();

		return message.toUpperCase();
	}

	private TwitterBet validate(DirectMessage directMessage) {
		TwitterBet bet = TwitterParser.unmarshalBet(directMessage.getText());

		// twitterBet.add(Lottery.valueOf(l+"_"+v));
		DateTime date = bet.getDate();
		for (String lotteryName : bet.getLotteryNames()) {
			for (String variantName : bet.getVariantNames()) {
				Lottery lottery = Lottery.valueOf(lotteryName + "_" + variantName);
				Preconditions.checkArgument(BirjanUtils.isValid(lottery, date, true),
						"[" + lottery.name() + "]" + " HORARIO NO VALIDO");
				bet.add(lottery);
			}
		}
		logger.debug(bet.toString());

		return bet;
	}

	@Override
	public void setDirectMessage(DirectMessage directMessage) {
		this.directMessage = directMessage;
	}

}
