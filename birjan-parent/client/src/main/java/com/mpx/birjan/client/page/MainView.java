package com.mpx.birjan.client.page;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.client.BirjanClient;

@Repository
public class MainView extends JFrame {

	private static final long serialVersionUID = 5923805043303278341L;
	
	@Autowired
	private BirjanClient controller;

	private JMenuBar menuBar;
	
	private List<JComponent> components;

	public MainView() {

		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout(0, 0));
		
		components = new ArrayList<JComponent>();

		menuBar = new JMenuBar();
		
		JMenu userMenu = new JMenu("Usuario");
		components.add(userMenu);
		
		JMenuItem item1 = new JMenuItem("Jugada");
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Jugar");
			}
		});
		userMenu.add(item1);
		components.add(item1);
		JMenuItem item2 = new JMenuItem("Pago");
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Check");
			}
		});
		userMenu.add(item2);
		components.add(item2);
		JMenuItem item3 = new JMenuItem("Cierre");
		item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Balance");
			}
		});
		userMenu.add(item3);
		components.add(item3);
		JMenuItem item4 = new JMenuItem("Premios");
		item4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Loteria");
			}
		});
		userMenu.add(item4);
		components.add(item4);
		menuBar.add(userMenu);

		
		JMenu agenciaMenu = new JMenu("Agencia");
		components.add(agenciaMenu);
		
		JMenuItem item5 = new JMenuItem("Control");
		item5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Control");
			}
		});
		agenciaMenu.add(item5);
		components.add(item5);
		menuBar.add(agenciaMenu);
		
		
		JMenu logoutMenu = new JMenu("Logout");
		JMenuItem item6 = new JMenuItem("Logout");
		item6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuBar.setVisible(false);
				controller.actionMenu("Logout");
			}
		});
		logoutMenu.add(item6);
		menuBar.add(logoutMenu);
		
		menuBar.setVisible(false);
			
		this.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		this.setVisible(true);
		
//		this.pack();
	}
	
	public void reset(String[] authorities) {
		
		List<String> list = Arrays.asList(authorities);
		boolean isUserOrAdmin = list.contains("ROLE_USER")&&!list.contains("ROLE_MANAGER")||list.contains("ROLE_ADMIN");
		boolean isManagerOrAdmin = list.contains("ROLE_MANAGER")||list.contains("ROLE_ADMIN");
		
		components.get(0).setEnabled(isUserOrAdmin);
		components.get(1).setEnabled(isUserOrAdmin);
		components.get(2).setEnabled(isUserOrAdmin);
		components.get(3).setEnabled(isUserOrAdmin);
		components.get(4).setEnabled(isUserOrAdmin);
		components.get(5).setEnabled(isManagerOrAdmin);
		components.get(6).setEnabled(isManagerOrAdmin);
		menuBar.setVisible(true);
	}

}
