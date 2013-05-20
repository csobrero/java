package com.mpx.birjan.client;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BirjanApplet extends JApplet {
	
	private static final long serialVersionUID = -7121769101357144892L;
	
	private static final String CONFIG_PATH = "classpath*:applicationContextClient.xml";
	
	public void init() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                	final ApplicationContext context = new ClassPathXmlApplicationContext(
							CONFIG_PATH);
					context.getBean(BirjanClient.class).start();
                }
            });
        } catch (Exception e) { 
            System.err.println("createGUI didn't successfully complete");
        }
        
    }

}
