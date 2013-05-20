package com.mpx.birjan.client.page;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.stereotype.Repository;

@Repository
public class BalanceView extends AbstractView {

	private static final long serialVersionUID = -937229003775095821L;

	public final String[] states = { null, "WINNER", "LOSER", "PAID" };

	private JTextField textCode;

	public BalanceView() {

		this.setSize(800, 400);

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		Box vb = Box.createVerticalBox();
		this.add(vb);

		Component vs = Box.createVerticalStrut(15);
		vb.add(vs);

		Box hb = Box.createHorizontalBox();
		vb.add(hb);

		Box vb_1 = Box.createVerticalBox();
		hb.add(vb_1);

		Component vs_3 = Box.createVerticalStrut(20);
		vb_1.add(vs_3);

		Box hb_2 = Box.createHorizontalBox();
		vb_1.add(hb_2);

		Component hs_3 = Box.createHorizontalStrut(20);
		hb_2.add(hs_3);

		JPanel panel = new JPanel();
		hb_2.add(panel);

		Box vb_2 = Box.createVerticalBox();
		panel.add(vb_2);

		JLabel lblLoteria = new JLabel("Lot");
		lblLoteria.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLoteria.setFont(new Font("Tahoma", Font.BOLD, 18));
		vb_2.add(lblLoteria);

		Component vs_6 = Box.createVerticalStrut(20);
		vb_2.add(vs_6);
		
		textCode = new JTextField();
		textCode.setColumns(15);
		vb_2.add(textCode);
		
		vb_2.add(Box.createVerticalStrut(10));

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.retriveBalance();
			}
		});
		vb_2.add(comboBox);

		Component hs = Box.createHorizontalStrut(120);
		vb_2.add(hs);

		Component hs_4 = Box.createHorizontalStrut(20);
		hb_2.add(hs_4);

		Component vs_2 = Box.createVerticalStrut(20);
		vb_1.add(vs_2);

		Box verticalBox = Box.createVerticalBox();
		hb.add(verticalBox);

		JPanel panel_1 = new JPanel();
		verticalBox.add(panel_1);
		
		Box verticalBox_1 = Box.createVerticalBox();
		panel_1.add(verticalBox_1);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox_1.add(verticalStrut);

		Component horizontalStrut = Box.createHorizontalStrut(500);
		verticalBox.add(horizontalStrut);

		Component vs_1 = Box.createVerticalStrut(20);
		vb.add(vs_1);

		Box hb_1 = Box.createHorizontalBox();
		vb.add(hb_1);

		btnDone = new JButton("Balance");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDone.setFont(new Font("Tahoma", Font.BOLD, 11));
		hb_1.add(btnDone);

		Component hs_2 = Box.createHorizontalStrut(20);
		hb_1.add(hs_2);

		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox.setSelectedIndex(0);
				reset();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 10));
		hb_1.add(btnClear);
	}

	private String[] getdays() {
		String[] days = new String[20];
		Locale locale = new Locale("es");
		DateTime dt = new DateTime(new Date());
		for (int i = 0; i < days.length; i++) {
			if (!development && dt.getDayOfWeek() == DateTimeConstants.SUNDAY)
				dt = dt.minusDays(1);
			days[i] = dt.toString("EEEE", locale).toUpperCase() + "  "
					+ dt.getDayOfMonth();
			dt = dt.minusDays(1);
		}
		return days;
	}

	public void reset() {
		comboBox.setModel(new DefaultComboBoxModel(getdays()));
		comboBox.setSelectedIndex(0);
		init();
	}

	private void init() {
//		String day = comboBox.getSelectedItem().toString().split(" ")[2];
		textCode.setText(controller.getUser());
		btnClear.setEnabled(true);
		btnDone.setEnabled(true);
//		btnDone.setRequestFocusEnabled(true);
	}

	public JTextField getTextCode() {
		return textCode;
	}
}
