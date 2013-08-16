package com.mpx.birjan.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import twitter4j.DirectMessage;

import com.mpx.birjan.bean.User;
import com.mpx.birjan.core.manager.NotificationManager;

@Repository
public class NotifyManagerCommand implements Command<String> {

	final Logger logger = LoggerFactory.getLogger(NotifyManagerCommand.class);

	@Autowired
	private NotificationManager notificationManager;

	@Async
	public void notifyUserByTwitter(User user, String message){
		
		String username = user.getUsername();
		logger.debug("Notifying Manager: " + username + " - message: " + message);
		
		notificationManager.send(Long.parseLong(username), message);
	}

	@Override
	public String execute(DirectMessage directMessage) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
