package com.mpx.birjan.core;

import twitter4j.DirectMessage;

public interface Command<T> {

	void setDirectMessage(DirectMessage directMessage);
	
	T execute();

}
