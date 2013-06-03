package com.mpx.birjan.client.page;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

	private Timer timer;

	private JLabel dateLbl;

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
				controller.actionMenu("Jugada");
			}
		});
		userMenu.add(item1);
		components.add(item1);
		JMenuItem item2 = new JMenuItem("Pago");
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Pago");
			}
		});
		userMenu.add(item2);
		components.add(item2);
		JMenuItem item3 = new JMenuItem("Cierre");
		item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Cierre");
			}
		});
		userMenu.add(item3);
		components.add(item3);
		JMenuItem item4 = new JMenuItem("Premios");
		item4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Premios");
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
		JMenuItem item6 = new JMenuItem("Balance");
		item6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Balance");
			}
		});
		agenciaMenu.add(item6);
		components.add(item6);
		menuBar.add(agenciaMenu);

		JMenu logoutMenu = new JMenu("Logout");
		JMenuItem item = new JMenuItem("Logout");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuBar.setVisible(false);
				controller.actionMenu("Logout");
			}
		});
		logoutMenu.add(item);
		menuBar.add(logoutMenu);

		menuBar.add(Box.createHorizontalGlue());
		dateLbl = new JLabel();
		dateLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuBar.setVisible(false);
				controller.actionMenu("ChangeTime");
			}
		});
		menuBar.add(dateLbl);

		Component hs = Box.createHorizontalStrut(5);
		menuBar.add(hs);

		menuBar.setVisible(false);

		this.getContentPane().add(menuBar, BorderLayout.NORTH);

		this.setVisible(true);

	}

	public void reset() {
		components.get(0).setEnabled(controller.isUser());
		components.get(1).setEnabled(controller.isUser() && !controller.isManager());
		components.get(2).setEnabled(controller.isUser() && !controller.isManager());
		components.get(3).setEnabled(controller.isUser());
		components.get(4).setEnabled(controller.isUser() && !controller.isManager());
		components.get(5).setEnabled(controller.isManager());
		components.get(6).setEnabled(controller.isManager());
		components.get(7).setEnabled(controller.isManager());

		timer = new Timer();
		timer.schedule(new UpdateClock(), 0, 60000);

		menuBar.setVisible(true);
	}

	public void closeUser() {
		components.get(0).setEnabled(controller.isManager());
	}

	private class UpdateClock extends TimerTask {

		final DateTimeFormatter fmt = DateTimeFormat.forPattern("EEEE dd/MM HH:mm");

		@Override
		public void run() {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					dateLbl.setText(fmt.print(new DateTime()));
				}
			});
		}
	}

}
