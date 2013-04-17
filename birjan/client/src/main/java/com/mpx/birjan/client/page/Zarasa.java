package com.mpx.birjan.client.page;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Zarasa {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Zarasa window = new Zarasa();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Zarasa() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		TicketView2 view2 = new TicketView2();
		frame.add(view2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
