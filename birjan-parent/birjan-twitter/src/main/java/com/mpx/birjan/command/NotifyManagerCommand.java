package com.mpx.birjan.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import com.mpx.birjan.bean.Agency;
import com.mpx.birjan.bean.User;
import com.mpx.birjan.core.TransactionalManager;
import com.mpx.birjan.util.Utils;

@Repository
public class NotifyManagerCommand extends AbstractCommand<String> {

	final Logger logger = LoggerFactory.getLogger(NotifyManagerCommand.class);

	@Autowired
	private TwitterFactory twitterFactory;
	
	@Autowired
	private TransactionalManager txManager;

	@Async
	public void notifyUserByTwitter(User user, String message){
		
		String username = user.getUsername();
		logger.debug("Notifying Manager: " + username + " - message: " + message);
		
		Twitter twitterSender = twitterFactory.getInstance();
		Utils.send(twitterSender, Long.parseLong(username), message, logger);
	}


	@Transactional
	public void notifyAllManagersByTwitter(String message) {
		for (Agency agency : txManager.getAllAgencies()) {
			notifyUserByTwitter(agency.getPrincipal(), message);
		}
		
	}
	
	
}
