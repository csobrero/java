package com.mpx.birjan.command;

import org.springframework.beans.factory.annotation.Autowired;

import twitter4j.DirectMessage;

import com.mpx.birjan.core.BirjanManager;
import com.mpx.birjan.core.TransactionalManager;

public abstract class AbstractCommand<T> implements Command<T>{

	@Autowired
	protected BirjanManager birjanManager;
	
	@Autowired
	protected TransactionalManager txManager;

	@Override
	public T execute(DirectMessage directMessage) {
		return null;
	}
	
}
