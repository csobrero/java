package com.mpx.birjan.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twitter4j.DirectMessage;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.UserStreamAdapter;

@Service
public class BirjanServer {

	@Autowired(required=false)
	private TwitterStream twitter;

	@Autowired
	private ObjectFactory<TwitterMessageHandler> twitterHandlerFactory;

	@PostConstruct
	public void start() throws TwitterException {
		if(twitter!=null){
			final long id = twitter.getId();
			twitter.addListener(new UserStreamAdapter() {
				@Override
				public void onDirectMessage(DirectMessage directMessage) {
					if (id != directMessage.getSenderId()) {
						TwitterMessageHandler twitterMessageHandler = twitterHandlerFactory.getObject();
						twitterMessageHandler.handle(directMessage);
					}
				}
			});
			twitter.user();
		}
	}

}