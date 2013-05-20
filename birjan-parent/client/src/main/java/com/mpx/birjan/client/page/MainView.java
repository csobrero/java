package com.mpx.birjan.client.page;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.client.BirjanClient;

@Repository
public class MainView extends JFrame {

	private static final long serialVersionUID = 5923805043303278341L;
	
	@Autowired
	private BirjanClient controller;

	public MainView() {

		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		JMenu filemenu = new JMenu("Archivo");
		JMenu helpmenu = new JMenu("Ayuda");
		JMenuItem checkItem1 = new JMenuItem("Check");
		checkItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Check");
			}
		});
		JMenuItem checkItem2 = new JMenuItem("Loteria");
		checkItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Loteria");
			}
		});
		JMenuItem checkItem3 = new JMenuItem("Jugar");
		checkItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Jugar");
			}
		});
		JMenuItem checkItem4 = new JMenuItem("Control");
		checkItem4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Control");
			}
		});
		JMenuItem checkItem5 = new JMenuItem("Balance");
		checkItem5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Balance");
			}
		});
		JMenuItem helpItem1 = new JMenuItem("Acerca");
		filemenu.add(checkItem1);
		filemenu.add(checkItem2);
		filemenu.add(checkItem3);
		filemenu.add(checkItem4);
		filemenu.add(checkItem5);
		helpmenu.add(helpItem1);
		menuBar.add(filemenu);
		menuBar.add(helpmenu);
		contentPane.add(menuBar, BorderLayout.NORTH);

		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
		
//		this.pack();
	}

}
