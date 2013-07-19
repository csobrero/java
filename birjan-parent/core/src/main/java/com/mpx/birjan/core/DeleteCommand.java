package com.mpx.birjan.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.tweeter.TwitterParser;

import twitter4j.DirectMessage;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DeleteCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(DeleteCommand.class);

	private DirectMessage directMessage;

	@Autowired
	private BirjanManager birjanManager;

	@Override
	public String execute() {		
		String ticket = TwitterParser.unmarshal(directMessage.getText());
		
		birjanManager.deleteTicket(ticket);
		
		return "Ticket " + ticket + " ha sido invalidado.";
	}

	@Override
	public void setDirectMessage(DirectMessage directMessage) {
		this.directMessage = directMessage;
	}

}
