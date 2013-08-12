package com.mpx.birjan.core;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import twitter4j.DirectMessage;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.UserStreamAdapter;

import com.mpx.birjan.tweeter.TwitterMessageHandler;

@Service
public class BirjanServer {
	
	private static final String CONFIG_PATH = "classpath*:applicationContext.xml";

	final Logger logger = LoggerFactory.getLogger(BirjanServer.class);
	
	@Autowired
	private TwitterStream twitter;

	@Autowired
	private ObjectFactory<TwitterMessageHandler> twitterHandlerFactory;

	@PostConstruct
	public void start() throws TwitterException {
		Date date = new DateTime(2013,8,9,11,0,0,0).toDate();
//		Date date = new DateTime().toDate();
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
		logger.info("Birjan Server Started.");
	}
	
	public static void main(String[] args) {
		final ApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_PATH);
	}

}
