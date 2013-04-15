package com.mpx.birjan.client.page;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PasswordView {

	private JFrame frame;
	private final JTextField fieldUser = new JTextField();
	private final JPasswordField fieldPassword = new JPasswordField();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
					PasswordView window = new PasswordView();
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
	public PasswordView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUser = new JLabel("User");
		lblUser.setBounds(105, 109, 22, 14);
		frame.getContentPane().add(lblUser);
		fieldUser.setBounds(133, 106, 182, 20);
		
		
		frame.getContentPane().add(fieldUser);
		fieldUser.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(81, 135, 46, 14);
		frame.getContentPane().add(lblPassword);
		fieldPassword.setBounds(133, 132, 182, 20);
		
		
		frame.getContentPane().add(fieldPassword);
		
		JButton btnLogIn = new JButton("LogIn");
		btnLogIn.setBounds(133, 158, 182, 23);
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fieldUser.getText().equalsIgnoreCase("1")&&fieldPassword.getText().equalsIgnoreCase("1")){
//					TicketPage ticketPage = new TicketPage();
//					ticketPage.setVisible(true);
				}
				fieldUser.setText(null);
				fieldPassword.setText(null);
			}
		});
		frame.getContentPane().add(btnLogIn);
	}

}
