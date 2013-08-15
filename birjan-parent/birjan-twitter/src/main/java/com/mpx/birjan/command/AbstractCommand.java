package com.mpx.birjan.command;

import org.springframework.beans.factory.annotation.Autowired;

import twitter4j.DirectMessage;

import com.mpx.birjan.core.manager.BirjanManager;
import com.mpx.birjan.core.manager.TransactionalManager;

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
