package com.mpx.birjan.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import twitter4j.DirectMessage;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.mpx.birjan.bean.BirjanUtils;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.bean.TwitterBet;
import com.mpx.birjan.tweeter.TwitterParser;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PayCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(PayCommand.class);

	private DirectMessage directMessage;

	@Autowired
	private BirjanManager birjanManager;

	@Override
	public String execute() {		
		String hash = TwitterParser.unmarshal(directMessage.getText());
		
		Collection<Game> games = birjanManager.payTicket(hash);
		
		String message = buildMessage(games, hash);
		
		return message;
	}
	
	private String buildMessage(Collection<Game> games, String hash) {

		String message = hash;

		if (games != null) {
			
			List<String> toPay = new ArrayList<String>();
			List<String> paid = new ArrayList<String>();
			for (Game game : games) {
				String lotteryName = game.getLottery().getLotteryName().substring(0, 2)
				+ game.getLottery().getVariantName().substring(0, 1);
				if(game.isRecentlyPaid())
					toPay.add(lotteryName);
				else
					paid.add(lotteryName);
			}

			Game game = Iterables.getFirst(games, null);
			DateTime date = new DateTime(game.getDate());

			if (!toPay.isEmpty()) {
				message += " : PAGA $" + BirjanUtils.money.format(game.getAmount() * games.size()) + " ["
						+ Joiner.on(',').join(toPay) + "]";

			}
			if (!paid.isEmpty()) {
				message += " : YA COBRADO [" + Joiner.on(',').join(paid) + "]";
			}
		
		} else {
			message += " : SIN PREMIO";
		}
		

		return message.toUpperCase();
	}

	@Override
	public void setDirectMessage(DirectMessage directMessage) {
		this.directMessage = directMessage;
	}

}
