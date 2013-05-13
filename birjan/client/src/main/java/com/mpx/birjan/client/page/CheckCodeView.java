package com.mpx.birjan.client.page;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.client.BirjanClient;
import com.mpx.birjan.common.Jugada;

import javax.swing.JLabel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Repository
public class CheckCodeView extends JPanel {

	private static final long serialVersionUID = -5632793035915116152L;

	@Autowired
	private BirjanClient controller;

	private JTextField textCode;

	private JTable table;
	
	private JTable lotteryTable;
	
	private JLabel label;
	
	private List<JLabel> winLotteries;

	public CheckCodeView() {
		this.setSize(800, 400);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		Box horizontalBox = Box.createHorizontalBox();
		add(horizontalBox);
		
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
		buildLoteryTable();
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
				Jugada jugada = controller.retrieveByCode(textCode.getText());
				if(jugada.getData()!=null){
					
					label.setText("Fecha: " + jugada.getDay());
					
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					for (Object[] data : jugada.getData()) {
						model.addRow(data);											
					}
					
					Set<String> lotteryNames = jugada.getLotteries().keySet();
					String[][] loteriesMap = TicketView.loteriesMap;
					for (int i = 0; i < loteriesMap.length; i++) {
						for (int j = 0; j < loteriesMap[i].length; j++) {
							lotteryTable.getModel().setValueAt(lotteryNames.contains(loteriesMap[i][j]), i, j + 1);
						}
					}
					((DefaultTableModel)lotteryTable.getModel()).fireTableDataChanged();
				}
			}
		});
		horizontalBox_1.add(btnSearch);

		Component horizontalStrut_2 = Box.createHorizontalStrut(100);
		horizontalBox_1.add(horizontalStrut_2);

		Component verticalStrut_1 = Box.createVerticalStrut(10);
		verticalBox_2.add(verticalStrut_1);

		JScrollPane scrollPane = new JScrollPane();
		verticalBox_2.add(scrollPane);
		scrollPane.setViewportBorder(null);

		table = new JTable();
		scrollPane.setViewportView(table);

		Component verticalStrut_2 = Box.createVerticalStrut(80);
		verticalBox_2.add(verticalStrut_2);
		
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
		
		Font f = new Font("Tahoma", Font.BOLD, 20);
		
		JPanel panel_1 = new JPanel();
		horizontalBox_3.add(panel_1);
		panel_1.setVisible(true);
		
		Box verticalBox_4 = Box.createVerticalBox();
		panel_1.add(verticalBox_4);
		
		JLabel lPagar = new JLabel("Pagar: ");
		verticalBox_4.add(lPagar);
		
		List<JLabel> list = new ArrayList<JLabel>();
		for (int i = 0; i < 10; i++) {
			JLabel jLabel = new JLabel("--");
			list.add(jLabel);
			verticalBox_4.add(jLabel);
			jLabel.setVisible(false);
		}
		
		Component verticalStrut_7 = Box.createVerticalStrut(10);
		verticalBox_4.add(verticalStrut_7);
		
		JLabel label_1 = new JLabel("TOTAL: $ 0.00");
		verticalBox_4.add(label_1);
		label_1.setFont(f);
		
		Component horizontalStrut_9 = Box.createHorizontalStrut(20);
		horizontalBox_3.add(horizontalStrut_9);
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

}
