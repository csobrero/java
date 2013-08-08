package com.mpx.birjan.command;

import twitter4j.DirectMessage;

public interface Command<T> {
	
	T execute(DirectMessage directMessage);

}
