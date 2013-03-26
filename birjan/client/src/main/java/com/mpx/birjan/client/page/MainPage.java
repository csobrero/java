package com.mpx.birjan.client.page;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class MainPage extends AbstractJFrame {
	
	private static final long serialVersionUID = 5923805043303278341L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final ApplicationContext context = new ClassPathXmlApplicationContext(
							CONFIG_PATH);
					MainPage window = context.getBean(MainPage.class);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@PostConstruct
	public void init() {
		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = this.getContentPane();
//		JPanel contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		JMenu filemenu = new JMenu("Archivo");
		JMenu helpmenu = new JMenu("Ayuda");
		JMenuItem fileItem1 = new JMenuItem("Ticket");
		JMenuItem helpItem1 = new JMenuItem("Acerca");
		filemenu.add(fileItem1);
		helpmenu.add(helpItem1);
		menuBar.add(filemenu);
		menuBar.add(helpmenu);
		contentPane.add(menuBar, BorderLayout.NORTH);

		JPanel centerPanel = getBean(Ticket.class);
		contentPane.add(centerPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
	}

}
