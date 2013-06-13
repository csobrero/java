package com.mpx.birjan.core;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import twitter4j.DirectMessage;

import com.google.common.base.Preconditions;
import com.mpx.birjan.bean.BirjanUtils;
import com.mpx.birjan.bean.TwitterBet;
import com.mpx.birjan.common.Lottery;
import com.mpx.birjan.tweeter.TwitterParser;

@Repository
@Secured("ROLE_USER")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CreateCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

	private DirectMessage directMessage;

	@Autowired
	private BirjanManager birjanManager;

	@Override
	public String execute() {
		TwitterBet bet = validate(directMessage);

		String hash = birjanManager.createGames(bet.getDate(), bet.getNumber(), bet.getPosition(), bet.getAmount(),
						bet.getLotteries().toArray(new Lottery[bet.getLotteries().size()]));

		String message = hash + " : " + bet.asText() + " : " + bet.getDate().getDayOfMonth() + "/"
				+ bet.getDate().getMonthOfYear();

		return message;
	}

	private TwitterBet validate(DirectMessage directMessage) {
		TwitterBet bet = TwitterParser.unmarshal(directMessage.getText());

		// twitterBet.add(Lottery.valueOf(l+"_"+v));
		DateTime date = bet.getDate();
		List<Lottery> lotteries = new ArrayList<Lottery>();
		for (String lotteryName : bet.getLotteryNames()) {
			for (String variantName : bet.getVariantNames()) {
				Lottery lottery = Lottery.valueOf(lotteryName + "_" + variantName);
				Preconditions.checkArgument(BirjanUtils.isValid(lottery, date, true),
						"[" + lottery.name() + "]" + " HORARIO NO VALIDO");
				lotteries.add(lottery);
			}
		}
		logger.debug(bet.toString());

		return bet;
	}

	public void setDirectMessage(DirectMessage directMessage) {
		this.directMessage = directMessage;
	}

}
