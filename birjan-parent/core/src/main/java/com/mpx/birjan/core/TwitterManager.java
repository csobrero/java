package com.mpx.birjan.core;

import twitter4j.DirectMessage;

import com.mpx.birjan.tweeter.TwitterParser;

public abstract class TwitterManager {

	public String process(final DirectMessage directMessage) {

		if (directMessage.getText().toUpperCase().matches(TwitterParser.tweetBetPattern)) {
			CreateCommand command = getCreateCommand();
			command.setDirectMessage(directMessage);
			return command.execute();
		}

		return "fail.";
	}

	protected abstract CreateCommand getCreateCommand();

}
