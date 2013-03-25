package com.mpx.birjan.client.page;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TicketPage extends JFrame {

	private static final long serialVersionUID = 1900172587979828855L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicketPage frame = new TicketPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TicketPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
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

		JPanel centerPanel = new Ticket();
		contentPane.add(centerPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

	}

}
