package com.mpx.birjan.client.page;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.client.BirjanClient;
import com.mpx.birjan.common.Payment;
import com.mpx.birjan.common.Status;
import com.mpx.birjan.common.Ticket;

@Repository
public class CheckCodeView extends JPanel {

	private static final long serialVersionUID = -5632793035915116152L;

	@Autowired
	private BirjanClient controller;
	
	private JButton btnPay, btnClear;

	private JTextField textCode;

	private JTable table;
	
	private JTable lotteryTable;
	
	private JLabel label;
	
	private JLabel lblTotal;
	
	private List<JLabel> winLotteries;
	
	private static Map<String, String> map;
	
	static{	
		map = new HashMap<String, String>();
		map.put("NACIONAL_PRIMERA", "Nac. Primera");
		map.put("NACIONAL_MATUTINA", "Nac. Matutina");
		map.put("NACIONAL_VESPERTINA", "Nac. Vespertina");
		map.put("NACIONAL_NOCTURNA", "Nac. Nocturna");
		map.put("PROVINCIA_PRIMERA", "Prov. Primera");
		map.put("PROVINCIA_MATUTINA", "Prov. Matutina");
		map.put("PROVINCIA_VESPERTINA", "Prov. Vespertina");
		map.put("PROVINCIA_NOCTURNA", "Prov. Nocturna");
	}

	public CheckCodeView() {
		this.setSize(800, 400);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Box verticalBox_5 = Box.createVerticalBox();
		add(verticalBox_5);

		Box horizontalBox = Box.createHorizontalBox();
		verticalBox_5.add(horizontalBox);
		
		Box verticalBox = Box.createVerticalBox();
		horizontalBox.add(verticalBox);
		
		Component verticalStrut_5 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_5);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_2);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut_5);
		
		JPanel panel = new JPanel();
		horizontalBox_2.add(panel);
		
		Box verticalBox_1 = Box.createVerticalBox();
		panel.add(verticalBox_1);
		
		label = new JLabel("Fecha: ");
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		label.setAlignmentX(0.5f);
		verticalBox_1.add(label);
		
		Component verticalStrut_4 = Box.createVerticalStrut(10);
		verticalBox_1.add(verticalStrut_4);
		
		lotteryTable = new JTable();
		JScrollPane scrollPane_1 = new JScrollPane(lotteryTable);
		scrollPane_1.setPreferredSize(new Dimension(150, 100));
		verticalBox_1.add(scrollPane_1);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(120);
		verticalBox_1.add(horizontalStrut_6);
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut_7);
		
		Component verticalStrut_6 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_6);

		Box verticalBox_2 = Box.createVerticalBox();
		horizontalBox.add(verticalBox_2);

		Component verticalStrut = Box.createVerticalStrut(10);
		verticalBox_2.add(verticalStrut);

		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox_2.add(horizontalBox_1);

		Component horizontalStrut = Box.createHorizontalStrut(100);
		horizontalBox_1.add(horizontalStrut);

		textCode = new JTextField();
		textCode.setToolTipText("cliente");
		horizontalBox_1.add(textCode);
		textCode.setColumns(15);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut_1);

		JButton btnSearch = new JButton("Buscar");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				init();
				Ticket ticket = controller.retrieveByCode(textCode.getText());
				if (ticket.getData() != null) {

					label.setText("Fecha: " + ticket.getDay());

					DefaultTableModel model = (DefaultTableModel) table.getModel();
					for (Object[] data : ticket.getData()) {
						model.addRow(data);
					}
					
					@SuppressWarnings("unchecked")
					Collection<String> lotteryNames = CollectionUtils.collect(ticket.getPayments(), new Transformer() {
						public Object transform(Object input) {
							return ((Payment)input).getLottery().name();
						}
					});
					
					String[][] loteriesMap = TicketView.loteriesMap;
					for (int i = 0; i < loteriesMap.length; i++) {
						for (int j = 0; j < loteriesMap[i].length; j++) {
							lotteryTable.getModel().setValueAt(
									lotteryNames.contains(loteriesMap[i][j]),
									i, j + 1);
						}
					}
					((DefaultTableModel) lotteryTable.getModel())
							.fireTableDataChanged();

					if(paymentSummary(ticket)>0){
//						lblTotal.setText(String.format("TOTAL: $ %.2f", total));
//						lblTotal.setVisible(true);
						btnPay.setVisible(true);
					}
				}
			}
		});
		horizontalBox_1.add(btnSearch);

		Component horizontalStrut_2 = Box.createHorizontalStrut(100);
		horizontalBox_1.add(horizontalStrut_2);

		Component verticalStrut_1 = Box.createVerticalStrut(10);
		verticalBox_2.add(verticalStrut_1);

		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setPreferredSize(new Dimension(400, 200));
		verticalBox_2.add(scrollPane);
		scrollPane.setViewportBorder(null);

		table = new JTable();
		scrollPane.setViewportView(table);

		Box verticalBox_3 = Box.createVerticalBox();
		horizontalBox.add(verticalBox_3);

		Component horizontalStrut_3 = Box.createHorizontalStrut(180);
		verticalBox_3.add(horizontalStrut_3);

		Component verticalStrut_3 = Box.createVerticalStrut(100);
		verticalBox_3.add(verticalStrut_3);

		Box horizontalBox_3 = Box.createHorizontalBox();
		verticalBox_3.add(horizontalBox_3);

		Component horizontalStrut_8 = Box.createHorizontalStrut(20);
		horizontalBox_3.add(horizontalStrut_8);

		JPanel panel_1 = new JPanel();
		horizontalBox_3.add(panel_1);
		panel_1.setVisible(true);

		Box verticalBox_4 = Box.createVerticalBox();
		panel_1.add(verticalBox_4);

		JLabel lPagar = new JLabel("Aciertos: ");
		verticalBox_4.add(lPagar);

		Component verticalStrut_7 = Box.createVerticalStrut(10);
		verticalBox_4.add(verticalStrut_7);

		winLotteries = new ArrayList<JLabel>();
		for (int i = 0; i < 10; i++) {
			JLabel jLabel = new JLabel();
			winLotteries.add(jLabel);
			verticalBox_4.add(jLabel);
		}
		
		lblTotal = new JLabel();
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 20));
		verticalBox_4.add(lblTotal);
		

		Component horizontalStrut_9 = Box.createHorizontalStrut(20);
		horizontalBox_3.add(horizontalStrut_9);
		
		Component verticalStrut_8 = Box.createVerticalStrut(20);
		verticalBox_5.add(verticalStrut_8);
		
		Box hb_1 = Box.createHorizontalBox();
		verticalBox_5.add(hb_1);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		hb_1.add(horizontalStrut_4);
		
		btnPay = new JButton("Pagar");
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnPay.isVisible()) {
					btnPay.setVisible(false);
					Ticket ticket = controller.pay(textCode.getText());
					lblTotal.setText(String.format("PAGAR: $ %.2f", paymentSummary(ticket)));
					lblTotal.setVisible(true);
				}
			}
		});
		btnPay.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPay.setVisible(false);
		hb_1.add(btnPay);

		Component hs_2 = Box.createHorizontalStrut(20);
		hb_1.add(hs_2);

		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textCode.setText(null);
				init();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 10));
		hb_1.add(btnClear);

		init();
	}

	public void reset() {
		init();
	}

	private void init() {
		for (JLabel jLabel : winLotteries)
			jLabel.setVisible(false);
		lblTotal.setVisible(false);
		buildJTable(createModel());
		buildLoteryTable();
	}
	
	private void buildJTable(TableModel model) {
		table.setFont(new Font("Tahoma", Font.PLAIN, 24));
		table.setRowHeight(40);
		table.setModel(model);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);

	}
	
	private void buildLoteryTable() {
		lotteryTable.setCellSelectionEnabled(false);
		lotteryTable.getTableHeader().setReorderingAllowed(false);
		lotteryTable.setRowSelectionAllowed(false);
		lotteryTable.setColumnSelectionAllowed(false);
		
		lotteryTable.setModel(createLoteryModel(new Object[][]{{"Nacional",false,false,false,false},{"Provincia",false,false,false,false}}));
		
		lotteryTable.getColumnModel().getColumn(0).setMaxWidth(100);
		lotteryTable.getColumnModel().getColumn(1).setMaxWidth(20);
		lotteryTable.getColumnModel().getColumn(2).setMaxWidth(20);
		lotteryTable.getColumnModel().getColumn(3).setMaxWidth(20);
		lotteryTable.getColumnModel().getColumn(4).setMaxWidth(20);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private DefaultTableModel createModel() {
		return new DefaultTableModel(new String[][] {}, new String[] { "Ubicacion", "Numero", "Importe" }) {

			public Class getColumnClass(int columnIndex) {
				return String.class;
			}

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private DefaultTableModel createLoteryModel(Object[][] model) {

		return new DefaultTableModel(model, new String[] { "Loteria", "P", "M",
				"V", "N" }) {
			Class[] columnTypes = new Class[] { String.class, Boolean.class,
					Boolean.class, Boolean.class, Boolean.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}
	
	private float paymentSummary(Ticket ticket) {
		float total = 0f;
		int i = 0;
		for (Payment payment : ticket.getPayments()) {
			if (payment.getAmount() != null) {
				String s = ": PAGADO";
				if (payment.getStatus().equals(Status.WINNER)) {
					total += payment.getAmount();
					s = String.format(": $ %.2f", payment.getAmount());
				}
				JLabel jLabel = winLotteries.get(i++);
				jLabel.setText(map.get(payment.getLottery().name())
						+ s);

				jLabel.setVisible(true);
			}
		}
		return total;
	}

}
