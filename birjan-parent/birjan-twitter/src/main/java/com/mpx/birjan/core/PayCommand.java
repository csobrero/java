package com.mpx.birjan.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import twitter4j.DirectMessage;

import com.google.common.base.Joiner;
import com.mpx.birjan.bean.BirjanUtils;
import com.mpx.birjan.bean.Game;
import com.mpx.birjan.common.Status;
import com.mpx.birjan.tweeter.TwitterParser;

@Repository
public class PayCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(PayCommand.class);

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

	private String buildMessage(Collection<Game> games, String hash) {

		String message = hash;

		if (games != null) {
			if(!games.isEmpty()){
				List<String> toPay = new ArrayList<String>();
				List<String> paid = new ArrayList<String>();
				float totalPrize = 0f;
				for (Game game : games) {
					String lotteryName = game.getLottery().getLotteryName().substring(0, 2)
							+ game.getLottery().getVariantName().substring(0, 1);
					if (game.is(Status.PAID))
						paid.add(lotteryName);
					if (game.is(Status.WINNER)) {
						game.setStatus(Status.PAID);
						totalPrize += game.getPrize();
						toPay.add(lotteryName);
					} 
				}
	
				if (!toPay.isEmpty()) {
					message += " : PAGA $" + BirjanUtils.money.format(totalPrize) + " [" + Joiner.on(',').join(toPay) + "]";
	
				}
				if (!paid.isEmpty()) {
					message += " : YA COBRADO [" + Joiner.on(',').join(paid) + "]";
				}
	
			} else {
				message += " : SIN PREMIO";
			}
		} else {
			message += " : TICKET NO VALIDO";
		}

		return message.toUpperCase();
	}

}
