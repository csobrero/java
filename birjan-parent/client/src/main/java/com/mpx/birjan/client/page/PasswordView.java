package com.mpx.birjan.client.page;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.client.BirjanClient;

@Repository
public class PasswordView extends JPanel {

	private static final long serialVersionUID = -3938082696927332283L;
	
	@Autowired
	protected BirjanClient controller;
	
	private JTextField userTextField;
	private JTextField passwordTextFiled;

	/**
	 * Initialize the contents of the frame.
	 */
	public PasswordView() {
		this.setSize(800, 400);
		setLayout(new BorderLayout(0, 0));
		
		Box horizontalBox = Box.createHorizontalBox();
		add(horizontalBox, BorderLayout.CENTER);
		
		Box verticalBox = Box.createVerticalBox();
		horizontalBox.add(verticalBox);
		
		Component verticalStrut = Box.createVerticalStrut(100);
		verticalBox.add(verticalStrut);
		
		JPanel panel = new JPanel();
		verticalBox.add(panel);
		FlowLayout fl_panel = new FlowLayout(FlowLayout.CENTER, 5, 5);
		fl_panel.setAlignOnBaseline(true);
		panel.setLayout(fl_panel);
		
		JLabel lblNewLabel = new JLabel("Usuario: ");
		panel.add(lblNewLabel);
		
		Component horizontalStrut = Box.createHorizontalStrut(30);
		panel.add(horizontalStrut);
		
		userTextField = new JTextField();
		panel.add(userTextField);
		userTextField.setColumns(15);
		
		JPanel panel_1 = new JPanel();
		panel_1.setAlignmentY(Component.TOP_ALIGNMENT);
		verticalBox.add(panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("Password: ");
		panel_1.add(lblNewLabel_1);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_1);
		
		passwordTextFiled = new JTextField();
		panel_1.add(passwordTextFiled);
		passwordTextFiled.setColumns(15);
		
		JPanel panel_2 = new JPanel();
		verticalBox.add(panel_2);
		
		JButton btnNewButton = new JButton("LogIn");
		panel_2.add(btnNewButton);
		
		Component verticalStrut_1 = Box.createVerticalStrut(200);
		verticalBox.add(verticalStrut_1);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.login(userTextField.getText(),passwordTextFiled.getText());			
			}
		});
	}

	public void reset() {
		userTextField.setText("xris");
		passwordTextFiled.setText("xris");
	}

}
