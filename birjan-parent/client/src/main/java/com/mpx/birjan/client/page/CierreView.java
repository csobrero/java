package com.mpx.birjan.client.page;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
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

import com.mpx.birjan.common.BalanceDTO;
import com.mpx.birjan.common.Status;

@Repository
public class CierreView extends AbstractView {

	private static final long serialVersionUID = -937229003775095821L;

	public final String[] states = { null, "WINNER", "LOSER", "PAID" };

	private JTextField textCode;

	private BalanceDTO balance;
	private JTextField cashField;
	private JTextField paymentsField;
	private JTextField cashBalanceField;
	private JTextField incomeField;
	private JTextField commissionField;
	private JTextField winnersField;
	private JTextField prizesField;
	private JTextField balanceField;
	
	DecimalFormat df = new DecimalFormat("0.##");

	private JLabel fechaLbl;

	public CierreView() {

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
				
			}
		});
		vb_2.add(comboBox);

		Component hs = Box.createHorizontalStrut(120);
		vb_2.add(hs);

		Component hs_4 = Box.createHorizontalStrut(20);
		hb_2.add(hs_4);

		Component vs_2 = Box.createVerticalStrut(20);
		vb_1.add(vs_2);
		
				JPanel panel_1 = new JPanel();
				hb.add(panel_1);

		Box verticalBox = Box.createVerticalBox();
		panel_1.add(verticalBox);
		
		Box verticalBox_1 = Box.createVerticalBox();
		verticalBox.add(verticalBox_1);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox_1.add(horizontalBox_1);
		
		fechaLbl = new JLabel("Fecha: ");
		fechaLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		horizontalBox_1.add(fechaLbl);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(300);
		horizontalBox_1.add(horizontalStrut_3);
		
		Component verticalStrut = Box.createVerticalStrut(5);
		verticalBox_1.add(verticalStrut);
		
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox_1.add(horizontalBox);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_4);
		
		JLabel lbl1 = new JLabel("Caja Inicial: ");
		horizontalBox.add(lbl1);
		
		cashField = new JTextField();
		cashField.setEditable(false);
		horizontalBox.add(cashField);
		cashField.setColumns(8);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_1);
		
		JLabel lbl2 = new JLabel("Pagos Realizados: ");
		horizontalBox.add(lbl2);
		
		paymentsField = new JTextField();
		paymentsField.setEditable(false);
		horizontalBox.add(paymentsField);
		paymentsField.setColumns(8);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_2);
		
		Component verticalStrut_2 = Box.createVerticalStrut(15);
		verticalBox_1.add(verticalStrut_2);
		
		Box verticalBox_3 = Box.createVerticalBox();
		verticalBox_1.add(verticalBox_3);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox_3.add(horizontalBox_2);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut);
		
		JLabel lbl3 = new JLabel("Recaudacion: ");
		horizontalBox_2.add(lbl3);
		
		incomeField = new JTextField();
		incomeField.setEditable(false);
		incomeField.setColumns(8);
		horizontalBox_2.add(incomeField);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut_5);
		
		JLabel lbl4 = new JLabel("Comision: ");
		horizontalBox_2.add(lbl4);
		
		commissionField = new JTextField();
		commissionField.setEditable(false);
		commissionField.setColumns(8);
		horizontalBox_2.add(commissionField);
		
		Component horizontalStrut_9 = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut_9);
		
		Component verticalStrut_1 = Box.createVerticalStrut(15);
		verticalBox_1.add(verticalStrut_1);
		
		Box verticalBox_2 = Box.createVerticalBox();
		verticalBox_1.add(verticalBox_2);
		
		Box horizontalBox_3 = Box.createHorizontalBox();
		verticalBox_2.add(horizontalBox_3);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		horizontalBox_3.add(horizontalStrut_6);
		
		JLabel lbl5 = new JLabel("Caja Saldo: ");
		horizontalBox_3.add(lbl5);
		
		cashBalanceField = new JTextField();
		cashBalanceField.setEditable(false);
		cashBalanceField.setColumns(8);
		horizontalBox_3.add(cashBalanceField);
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(20);
		horizontalBox_3.add(horizontalStrut_7);
		
		Component verticalStrut_6 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_6);
		
		Box verticalBox_4 = Box.createVerticalBox();
		verticalBox.add(verticalBox_4);
		
		Box horizontalBox_4 = Box.createHorizontalBox();
		verticalBox_4.add(horizontalBox_4);
		
		JLabel label = new JLabel("Proximo Cierre");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		horizontalBox_4.add(label);
		
		Component horizontalStrut_8 = Box.createHorizontalStrut(300);
		horizontalBox_4.add(horizontalStrut_8);
		
		Component verticalStrut_3 = Box.createVerticalStrut(5);
		verticalBox_4.add(verticalStrut_3);
		
		Box horizontalBox_5 = Box.createHorizontalBox();
		verticalBox_4.add(horizontalBox_5);
		
		Component horizontalStrut_10 = Box.createHorizontalStrut(20);
		horizontalBox_5.add(horizontalStrut_10);
		
		JLabel lbl6 = new JLabel("Ganadores: ");
		horizontalBox_5.add(lbl6);
		
		winnersField = new JTextField();
		winnersField.setEditable(false);
		winnersField.setColumns(8);
		horizontalBox_5.add(winnersField);
		
		Component horizontalStrut_11 = Box.createHorizontalStrut(20);
		horizontalBox_5.add(horizontalStrut_11);
		
		JLabel lbl7 = new JLabel("Premios a Pagar: ");
		horizontalBox_5.add(lbl7);
		
		prizesField = new JTextField();
		prizesField.setEditable(false);
		prizesField.setColumns(8);
		horizontalBox_5.add(prizesField);
		
		Component horizontalStrut_12 = Box.createHorizontalStrut(20);
		horizontalBox_5.add(horizontalStrut_12);
		
		Component verticalStrut_4 = Box.createVerticalStrut(15);
		verticalBox_4.add(verticalStrut_4);
		
		Box verticalBox_5 = Box.createVerticalBox();
		verticalBox_4.add(verticalBox_5);
		
		Box horizontalBox_6 = Box.createHorizontalBox();
		verticalBox_5.add(horizontalBox_6);
		
		Component horizontalStrut_13 = Box.createHorizontalStrut(20);
		horizontalBox_6.add(horizontalStrut_13);
		
		JLabel lbl8 = new JLabel("Balance:  ");
		lbl8.setFont(new Font("Tahoma", Font.BOLD, 11));
		horizontalBox_6.add(lbl8);
		
		balanceField = new JTextField();
		balanceField.setFont(new Font("Tahoma", Font.BOLD, 11));
		balanceField.setEditable(false);
		balanceField.setColumns(8);
		horizontalBox_6.add(balanceField);
		
		Component horizontalStrut_14 = Box.createHorizontalStrut(20);
		horizontalBox_6.add(horizontalStrut_14);
		
		Component verticalStrut_5 = Box.createVerticalStrut(15);
		verticalBox_4.add(verticalStrut_5);

		Component vs_1 = Box.createVerticalStrut(20);
		vb.add(vs_1);

		Box hb_1 = Box.createHorizontalBox();
		vb.add(hb_1);

		btnDone = new JButton("Balance");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean close = btnDone.getText().equals("Cierre");
				BalanceDTO balanceDTO = controller.performBalance(close);
				if(balanceDTO!=null){
					DateTime dt = new DateTime(balanceDTO.getDate());
					fechaLbl.setText("Fecha: "+dt.getDayOfMonth()+"/"+dt.getMonthOfYear());
					cashField.setText("$"+df.format(balanceDTO.getCash()));
					paymentsField.setText("$"+df.format(balanceDTO.getPayments()));
					incomeField.setText("$"+df.format(balanceDTO.getIncome()));
					commissionField.setText("$"+df.format(balanceDTO.getCommission()));
					float cashBalance = balanceDTO.getCash()-balanceDTO.getPayments()+balanceDTO.getIncome()
							-balanceDTO.getCommission();
					cashBalanceField.setText("$"+df.format(cashBalance));
					winnersField.setText(df.format(balanceDTO.getWinners()));
					prizesField.setText("$"+df.format(balanceDTO.getPrizes()));
					balanceField.setText("$"+df.format(cashBalance-balanceDTO.getPrizes()));
					if(balanceDTO.getState().equals(Status.CLOSE)){
						btnDone.setVisible(false);
					} else {
						btnDone.setText("Cierre");
					}
				}
			}
		});
		btnDone.setFont(new Font("Tahoma", Font.BOLD, 11));
		hb_1.add(btnDone);
		
		Component hs_2 = Box.createHorizontalStrut(20);
		hb_1.add(hs_2);

		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		textCode.setText(controller.getUser());
		fechaLbl.setText("Fecha: ");
		balanceField.setText("");
		prizesField.setText("");
		winnersField.setText("");
		cashBalanceField.setText("");
		commissionField.setText("");
		incomeField.setText("");
		paymentsField.setText("");
		cashField.setText("");
		btnDone.setText("Balance");
		btnDone.setVisible(true);
	}

	public JTextField getTextCode() {
		return textCode;
	}

	public void setBalance(BalanceDTO balance) {
		this.balance = balance;		
	}
}
