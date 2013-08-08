package com.mpx.birjan.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import twitter4j.DirectMessage;

import com.mpx.birjan.core.BirjanManager;
import com.mpx.birjan.tweeter.TwitterParser;

@Repository
public class DeleteCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(DeleteCommand.class);

	@Autowired
	private BirjanManager birjanManager;

	@Override
	public String execute(DirectMessage directMessage) {
		String ticket = TwitterParser.unmarshal(directMessage.getText());

		birjanManager.deleteTicket(ticket);

		return "Ticket " + ticket + " ha sido invalidado.";
	}

}
