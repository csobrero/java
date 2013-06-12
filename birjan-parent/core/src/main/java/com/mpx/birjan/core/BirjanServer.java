package com.mpx.birjan.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import twitter4j.DirectMessage;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.UserStreamAdapter;

@Service
public class BirjanServer {

	@Autowired
	private TwitterStream twitter;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private ObjectFactory<TwitterMessageHandler> twitterHandlerFactory;

	@PostConstruct
	public void start() throws TwitterException {
		final long id = twitter.getId();
		twitter.addListener(new UserStreamAdapter() {
			@Override
			public void onDirectMessage(DirectMessage directMessage) {
				if (id != directMessage.getSenderId()) {
					TwitterMessageHandler twitterHandler = twitterHandlerFactory.getObject();
					twitterHandler.setDirectMessage(directMessage);
					taskExecutor.execute(twitterHandler);
				}
			}
		});
		twitter.user();
	}

}
