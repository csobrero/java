package com.mpx.birjan.client.page;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.service.impl.BirjanWebService;
import java.awt.BorderLayout;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Font;

@Repository
public class PrintTicketPanel extends JPanel {
	
	private static final long serialVersionUID = -6039872057061219542L;
	
	@Autowired
	private BirjanWebService webService;

	private String hash;
	
	public PrintTicketPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		
		Box verticalBox = Box.createVerticalBox();
		topPanel.add(verticalBox);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut);
		
		JLabel lblcConfirmationCode = new JLabel();
		lblcConfirmationCode.setText("VALIDADOR:  "+hash);
		lblcConfirmationCode.setFont(new Font("Tahoma", Font.BOLD, 18));
		verticalBox.add(lblcConfirmationCode);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_1);
		
		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
//		init();
	}

	public void setHash(String hash) {
		this.hash  = hash;
	}
	
//	@PostConstruct
//	public void init() {
////		if(webService==null)
////			return;
//	}
	
}
