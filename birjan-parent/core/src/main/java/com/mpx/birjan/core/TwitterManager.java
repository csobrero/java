package com.mpx.birjan.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.DirectMessage;

import com.mpx.birjan.tweeter.TwitterParser;

public abstract class TwitterManager {
	
	final Logger logger = LoggerFactory.getLogger(TwitterManager.class);

	public String process(final DirectMessage directMessage) {

		logger.debug("Message received: "+ directMessage.getText());
		Command<String> command = null;
		try {
			if (directMessage.getText().toUpperCase().matches(TwitterParser.tweetBetPattern)) {
				command = this.getCreateCommand();
			} else if(directMessage.getText().toUpperCase().matches(TwitterParser.tweetDeletePattern)) {
				command = this.getDeleteCommand();
			} else if(directMessage.getText().toUpperCase().matches(TwitterParser.tweetShowPattern)) {
				command = this.getShowCommand();
			} else if(directMessage.getText().toUpperCase().matches(TwitterParser.tweetPayPattern)) {
				command = this.getPayCommand();
			}
			command.setDirectMessage(directMessage);
			return command.execute();
		} catch (Throwable t) {
			t.printStackTrace();
			logger.error("Exception: " + t.getClass().getName() + " || Message:  " + t.getMessage());
		}

		return "ERROR |" + directMessage.getText() + "|";
	}

	protected abstract CreateCommand getCreateCommand();

	protected abstract DeleteCommand getDeleteCommand();

	protected abstract ShowCommand getShowCommand();

	protected abstract PayCommand getPayCommand();

}
