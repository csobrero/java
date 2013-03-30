package com.mpx.birjan.client.page;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


public abstract class AbstractJFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	protected static final String CONFIG_PATH = "classpath*:applicationContextClient.xml";

	@Autowired
	protected ApplicationContext applicationContext;
	
	protected abstract void init();

	protected <T> T getBean(Class<T> clazz) {
		try {
			return (applicationContext != null) ? applicationContext
					.getBean(clazz) : clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Error creating bean.");
	}

}
