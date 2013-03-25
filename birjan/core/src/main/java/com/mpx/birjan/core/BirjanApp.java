package com.mpx.birjan.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BirjanApp {

	private static final String CONFIG_PATH = "classpath*:applicationContext.xml";

	public static void main(final String[] args) {
		try {
			final ApplicationContext context = new ClassPathXmlApplicationContext(
					CONFIG_PATH);
			Object lock = new Object();
			synchronized (lock) {
				lock.wait();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
