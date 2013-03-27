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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Repository
public class MainPage extends AbstractJFrame {
	
	private static final long serialVersionUID = 5923805043303278341L;
	
	@Autowired
	private Ticket centerPanel;

	public MainPage() {
		init();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final ApplicationContext context = new ClassPathXmlApplicationContext(
							CONFIG_PATH);
					JFrame window = context.getBean(MainPage.class);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@PostConstruct
	public void init() {
		if(applicationContext==null)
			return;
		
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
		fileItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				centerPanel.reset();
			}
		});
		JMenuItem helpItem1 = new JMenuItem("Acerca");
		filemenu.add(fileItem1);
		helpmenu.add(helpItem1);
		menuBar.add(filemenu);
		menuBar.add(helpmenu);
		contentPane.add(menuBar, BorderLayout.NORTH);

		if(centerPanel==null)
			centerPanel = new Ticket();
		contentPane.add(centerPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
	}

}
