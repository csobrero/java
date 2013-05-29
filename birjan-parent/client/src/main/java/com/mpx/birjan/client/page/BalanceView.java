package com.mpx.birjan.client.page;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.client.BirjanClient;
import com.mpx.birjan.common.BalanceDTO;

@Repository
public class BalanceView extends JPanel {

	private static final long serialVersionUID = 4334436586243521165L;

	@Autowired
	protected BirjanClient controller;

	protected JTable table;

	protected JComboBox comboBox, comboBox_3;

	protected JButton btnDone, btnClear, btnExport, btnValidate;
	
	DecimalFormat df = new DecimalFormat("0.##");
	
	protected boolean development = false;
	
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

		JPanel panel = new JPanel();
		hb_2.add(panel);

		Box vb_2 = Box.createVerticalBox();
		panel.add(vb_2);

		JLabel lblLoteria = new JLabel("Balance");
		lblLoteria.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLoteria.setFont(new Font("Tahoma", Font.BOLD, 18));
		vb_2.add(lblLoteria);

		Component vs_6 = Box.createVerticalStrut(20);
		vb_2.add(vs_6);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				init();
			}
		});
		vb_2.add(comboBox);

		Component vs_4 = Box.createVerticalStrut(10);
		vb_2.add(vs_4);

		Component hs = Box.createHorizontalStrut(120);
		vb_2.add(hs);

		Component vs_2 = Box.createVerticalStrut(20);
		vb_1.add(vs_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setLayout(new ScrollPaneLayout());
		hb.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		Component horizontalStrut = Box.createHorizontalStrut(50);
		hb.add(horizontalStrut);

		Component vs_1 = Box.createVerticalStrut(20);
		vb.add(vs_1);

		Box hb_1 = Box.createHorizontalBox();
		vb.add(hb_1);

		btnDone = new JButton("Balance");
		btnDone.addActionListener(new ActionListener() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tableModel = getTableModel();
				BalanceDTO[] balance = controller.balance();
				if(balance!=null){
					for (BalanceDTO balanceDTO : balance) {
						Vector rowData = new Vector();
						DateTime dt = new DateTime(balanceDTO.getDate());
						rowData.add(dt.getDayOfMonth()+"/"+dt.getMonthOfYear());
						rowData.add(balanceDTO.getState().name());
						rowData.add("$"+df.format(balanceDTO.getCash()));
						rowData.add("$"+df.format(balanceDTO.getPayments()));
						rowData.add("$"+df.format(balanceDTO.getIncome()));
						rowData.add("$"+df.format(balanceDTO.getCommission()));
						float cashBalance = balanceDTO.getCash()-balanceDTO.getPayments()+balanceDTO.getIncome()
								-balanceDTO.getCommission();
						rowData.add("$"+df.format(cashBalance));
						rowData.add("$"+df.format(balanceDTO.getPrizes()));
						rowData.add("$"+df.format(cashBalance-balanceDTO.getPrizes()));
						tableModel.addRow(rowData);
					}
					btnDone.setEnabled(false);
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
				comboBox.setSelectedIndex(0);
				reset();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 10));
		hb_1.add(btnClear);
		
		Component hs_1 = Box.createHorizontalStrut(20);
		hb_1.add(hs_1);
		
		btnValidate = new JButton("Validar");
		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnValidate.isEnabled())
					btnValidate.setEnabled(false);// prevent double click.
				controller.validateDraw();
			}
		});
		btnValidate.setVisible(false);
		hb_1.add(btnValidate);
	}

	private String[] getdays() {
		String[] days = new String[3];
		Locale locale = new Locale("es");
		DateTime dt = new DateTime(new Date());
		for (int i = 0; i < days.length; i++) {
			if (dt.getDayOfWeek() == DateTimeConstants.SUNDAY)
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
	
	public void init() {
		buildJTable(createModel());
		btnClear.setEnabled(true);
		btnDone.setEnabled(true);
		table.setEnabled(false);
	}

	private void buildJTable(TableModel model) {
		table.setModel(model);

		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMaxWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setMaxWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setMaxWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setMaxWidth(150);

		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setMaxWidth(150);
		table.getColumnModel().getColumn(5).setPreferredWidth(150);
		table.getColumnModel().getColumn(5).setMaxWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(6).setMaxWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setMaxWidth(150);
		table.getColumnModel().getColumn(8).setPreferredWidth(150);
		table.getColumnModel().getColumn(8).setMaxWidth(150);

		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setEnabled(false);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private DefaultTableModel createModel() {
		return new DefaultTableModel(null, new String[] { "Fecha", "Estado", "Caja", 
				"Pagos", "Recaudacion", "Comision",	"Saldo", "Premios", "Balance" }) {

			public Class getColumnClass(int columnIndex) {
				return String.class;
			}

			boolean[] columnEditables = new boolean[] { false, true, false,
					true };

			public boolean isCellEditable(int row, int column) {
				return false;//columnEditables[column];
			}
			
		};
	}
	
	public DefaultTableModel getTableModel() {
		return (DefaultTableModel)table.getModel();
	}

	public String getDay() {
		return comboBox.getSelectedItem().toString().split(" ")[2];
	}
}
