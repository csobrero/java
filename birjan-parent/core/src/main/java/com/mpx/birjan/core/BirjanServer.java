package com.mpx.birjan.core;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
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
		Date date = new DateTime(2013,6,14,22,0,0,0).toDate();
		DateTimeUtils.setCurrentMillisOffset(date.getTime()-new Date().getTime());
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
