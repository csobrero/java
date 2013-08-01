package com.mpx.birjan.core;

import java.util.Collection;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import twitter4j.DirectMessage;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.mpx.birjan.bean.BirjanUtils;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.tweeter.TwitterParser;

@Repository
public class ShowCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(ShowCommand.class);

	@Autowired
	private BirjanManager birjanManager;

	@Override
	@Transactional
	public String execute(DirectMessage directMessage) {		
		String hash = TwitterParser.unmarshal(directMessage.getText());
		
		Collection<Game> games = birjanManager.showTicket(hash);
		
		String message = buildMessage(games, hash);
		
		return message;
	}

	@Transactional
	private String buildMessage(Collection<Game> games, String hash) {

		String message = hash;

		if (games != null) {
			
			Collection<String> lotteries = Collections2.transform(games, new Function<Game, String>() {
						public String apply(Game game) {
							return game.getLottery().getLotteryName().substring(0, 2)
									+ game.getLottery().getVariantName().substring(0, 1);
						}
					});

			Game game = Iterables.getFirst(games, null);
			DateTime date = new DateTime(game.getDate());

			message += " : $" + BirjanUtils.money.format(game.getAmount() * games.size())
					+ " : " + game.getNumber() + " " + game.getPosition() 
					+ " $" + BirjanUtils.money.format(game.getAmount()) + " " + Joiner.on(',').join(lotteries) 
					+ " : " + date.getDayOfMonth() + "/" + date.getMonthOfYear();
		
		} else {
			message += " : TICKET NO VALIDO.";
		}

		return message.toUpperCase();
	}

}
