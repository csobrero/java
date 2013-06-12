package com.mpx.birjan.core;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mpx.birjan.tweeter.TwitterParser;

import twitter4j.DirectMessage;

@Component
public abstract class TwitterManager {

	
	public String process(final DirectMessage directMessage) {
		
		if(directMessage.getText().matches(TwitterParser.tweetBetPattern)){
			CreateCommand command = getCreateCommand();
			command.setDirectMessage(directMessage);
			return command.execute();
		}

		return "fail.";
	}

	@Resource(name = "createCommand")
	protected abstract CreateCommand getCreateCommand();

}
