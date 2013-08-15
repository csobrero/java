package com.mpx.birjan.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
import com.google.common.collect.Ordering;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.core.manager.BirjanManager;
import com.mpx.birjan.tweeter.TwitterParser;
import com.mpx.birjan.util.Utils;

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
			
			List<String> lotteries = new ArrayList<String>(Collections2.transform(games, new Function<Game, String>() {
						public String apply(Game game) {
							return game.getLottery().nameShort();
						}
					}));

			Game game = Iterables.getFirst(games, null);
			DateTime date = new DateTime(game.getDate());

			Collections.sort(lotteries);
			message += " : $" + Utils.money.format(game.getAmount() * games.size())
					+ " : " + game.getNumber() + " " + game.getPosition() 
					+ " $" + Utils.money.format(game.getAmount()) + " " + Joiner.on(',').join(lotteries) 
					+ " : " + date.getDayOfMonth() + "/" + date.getMonthOfYear();
		
		} else {
			message += " : TICKET NO VALIDO.";
		}

		return message.toUpperCase();
	}

}
