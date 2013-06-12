package com.mpx.birjan.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import twitter4j.DirectMessage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TwitterMessageHandler implements Runnable {

	final Logger logger = LoggerFactory.getLogger(TwitterMessageHandler.class);

	private DirectMessage directMessage;

	@Autowired
	private TwitterManager twitterManager;

	@Autowired
	private TwitterFactory twitterFactory;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void run() {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(directMessage.getSenderId()
						+ "", ""));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String message = twitterManager.process(directMessage);
		logger.debug(directMessage.getSenderId() + " : " + message);			

		Twitter twitterSender = twitterFactory.getInstance();
		try {
			twitterSender.sendDirectMessage(directMessage.getSenderId(), message);
		} catch (TwitterException e) {
			logger.error(e.getMessage());
		}

	}

	public DirectMessage getDirectMessage() {
		return directMessage;
	}

	public void setDirectMessage(DirectMessage directMessage) {
		this.directMessage = directMessage;
	}

}
