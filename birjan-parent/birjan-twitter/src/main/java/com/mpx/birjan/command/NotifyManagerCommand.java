package com.mpx.birjan.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import com.mpx.birjan.util.Utils;

@Repository
public class NotifyManagerCommand extends AbstractCommand<String> {

	final Logger logger = LoggerFactory.getLogger(NotifyManagerCommand.class);

	@Autowired
	private TwitterFactory twitterFactory;

	
	public void notifyManager(String message){
		
		String username = txManager.identifyMe().getAgency().getPrincipal().getUsername();
		logger.debug("Notifying Manager: " + username + " - message: " + message);
		
		Twitter twitterSender = twitterFactory.getInstance();
		Utils.send(twitterSender, Long.parseLong(username), message, logger);
	}
	
	
}
