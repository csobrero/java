package com.mpx.birjan.core.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twitter4j.DirectMessage;

import com.mpx.birjan.command.BalanceCommand;
import com.mpx.birjan.command.Command;
import com.mpx.birjan.command.ControlCommand;
import com.mpx.birjan.command.CreateCommand;
import com.mpx.birjan.command.DateTimeCommand;
import com.mpx.birjan.command.DeleteCommand;
import com.mpx.birjan.command.PayCommand;
import com.mpx.birjan.command.ShowCommand;
import com.mpx.birjan.tweeter.TwitterParser;

@Component
public class TwitterManager {

	private static final Logger logger = LoggerFactory.getLogger(TwitterManager.class);

	private static final Map<String, Class<? extends Command<String>>> map = new HashMap<String, Class<? extends Command<String>>>();

	static {
		map.put(TwitterParser.tweetBetPattern, CreateCommand.class);
		map.put(TwitterParser.tweetShowPattern, ShowCommand.class);
		map.put(TwitterParser.tweetPayPattern, PayCommand.class);
		map.put(TwitterParser.tweetDeletePattern, DeleteCommand.class);
		map.put(TwitterParser.tweetBalancePattern, BalanceCommand.class);
		map.put(TwitterParser.tweetDateTimePattern, DateTimeCommand.class);
		
		map.put(TwitterParser.tweetControlPattern, ControlCommand.class);
	}

	@Autowired
	private BeanFactory beanFactory;

	public String process(final DirectMessage directMessage) {

		String dm = directMessage.getText().toUpperCase();
		logger.debug("Message received: " + dm);

		try {
			for (Entry<String, Class<? extends Command<String>>> entry : map.entrySet()) {
				if (dm.matches(entry.getKey()))
					return beanFactory.getBean(entry.getValue()).execute(directMessage);
			}
		} catch (IllegalArgumentException e){
			return e.getMessage();
		} catch (Throwable t) {
			t.printStackTrace();
			logger.error("Exception: " + t.getClass().getName() + " || Message:  " + t.getMessage());
		}

		return "ERROR |" + directMessage.getText() + "|";
	}

}
