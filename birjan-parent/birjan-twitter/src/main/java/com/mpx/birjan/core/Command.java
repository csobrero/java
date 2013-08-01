package com.mpx.birjan.core;

import twitter4j.DirectMessage;

public interface Command<T> {
	
	T execute(DirectMessage directMessage);

}
