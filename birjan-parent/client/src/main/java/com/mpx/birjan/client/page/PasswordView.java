package com.mpx.birjan.client.page;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordView extends ReseteableView {

	private static final long serialVersionUID = -3938082696927332283L;
	
	private JTextField userTextField;
	private JTextField passwordTextFiled;

	private JComboBox comboBox;

	private JComboBox comboBox1;

	private JComboBox comboBox2;

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
		
		JPanel panel_3 = new JPanel();
		verticalBox.add(panel_3);
		FlowLayout fl_panel_3 = new FlowLayout(FlowLayout.CENTER, 5, 5);
		fl_panel_3.setAlignOnBaseline(true);
		panel_3.setLayout(fl_panel_3);
		
		comboBox = new JComboBox();
		panel_3.add(comboBox);
		
		comboBox1 = new JComboBox();
		panel_3.add(comboBox1);
		
		comboBox2 = new JComboBox();
		panel_3.add(comboBox2);
		
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
				DateTime date = controller.getServerDateTime();
				if (date != null) {
					String[] day = comboBox.getSelectedItem().toString().split("  ")[1].split("/");
					date = new DateTime(date.getYear(), Integer.parseInt(day[1]),
							Integer.parseInt(day[0]), comboBox1.getSelectedIndex(), comboBox2.getSelectedIndex(), 0, 0);
				}
				
				controller.login(userTextField.getText(),passwordTextFiled.getText(), date);			
			}
		});
	}

	public void reset() {

		userTextField.setText("u1");
		
		comboBox.setVisible(false);
		comboBox1.setVisible(false);
		comboBox2.setVisible(false);
		
		if(controller.getServerDateTime()!=null){
			DateTime date = new DateTime();
			comboBox.setModel(new DefaultComboBoxModel(getdays(date)));
			comboBox.setSelectedIndex(15);
			String[] hours = new String[24];
			String[] minutes = new String[60];
			for (int i = 0; i < 60; i++) {
				if(i<24)
					hours[i]=i+"";
				minutes[i]=i+"";
			}
			comboBox1.setModel(new DefaultComboBoxModel(hours));
			comboBox1.setSelectedIndex(date.getHourOfDay());
			comboBox2.setModel(new DefaultComboBoxModel(minutes));
			comboBox2.setSelectedIndex(date.getMinuteOfHour());
			comboBox.setVisible(true);
			comboBox1.setVisible(true);
			comboBox2.setVisible(true);
		}
		
	}
	
	private String[] getdays(final DateTime date) {
		String[] days = new String[30];
		Locale locale = new Locale("es");
		DateTime dt = date.minusDays(15);
		for (int i = 0; i < days.length; i++) {
			days[i] = dt.toString("EEEE", locale).toUpperCase() + "  "
					+ dt.getDayOfMonth() + "/" + dt.getMonthOfYear();
			dt = dt.plusDays(1);
		}
		return days;
	}

}
