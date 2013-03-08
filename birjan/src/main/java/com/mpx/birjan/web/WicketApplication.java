package com.mpx.birjan.web;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see wicket.myproject.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	public void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this));
	}

	public WicketApplication() {
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

}
