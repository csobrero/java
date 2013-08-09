package com.mpx.birjan.tweeter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mpx.birjan.core.TwitterManager;

import twitter4j.DirectMessage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TwitterMessageHandler {

	final Logger logger = LoggerFactory.getLogger(TwitterMessageHandler.class);

	@Autowired
	private TwitterManager twitterManager;

	@Autowired
	private TwitterFactory twitterFactory;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Async
	public void handle(DirectMessage directMessage) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(directMessage.getSenderId()
						+ "", ""));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String message = twitterManager.process(directMessage);
		logger.debug(directMessage.getSenderId() + " : " + message);

		Twitter twitterSender = twitterFactory.getInstance();
		send(twitterSender, directMessage.getSenderId(), message);

	}

	private void send(Twitter twitterSender, long senderId, String message) {
		try {
			twitterSender.sendDirectMessage(senderId, message);
		} catch (TwitterException e) {
			if (e.getErrorCode() == 151)
				send(twitterSender, senderId, message + ".");
			logger.error("Exception sending: " + e.getMessage());
		}

	}

}
