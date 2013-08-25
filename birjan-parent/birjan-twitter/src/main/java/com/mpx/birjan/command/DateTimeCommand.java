package com.mpx.birjan.command;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import twitter4j.DirectMessage;

import com.mpx.birjan.tweeter.TwitterParser;

@Repository
public class DateTimeCommand implements Command<String> {

	private static final Logger logger = LoggerFactory.getLogger(DateTimeCommand.class);

	@Override
	public String execute(DirectMessage directMessage) {

		String date = TwitterParser.unmarshal(directMessage.getText());
		
		DateTime actualTime = new DateTime();
		
		return "Hora actual: " + actualTime.toString("dd/MM HH:mm:ss");
	}

}
