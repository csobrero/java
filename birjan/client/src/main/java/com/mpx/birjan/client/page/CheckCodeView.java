package com.mpx.birjan.client.page;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.client.BirjanClient;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Repository
public class CheckCodeView extends JPanel {

	private static final long serialVersionUID = -5632793035915116152L;

	@Autowired
	private BirjanClient controller;

	private JTextField textCode;

	private JTable table;

	public CheckCodeView() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		Box horizontalBox = Box.createHorizontalBox();
		add(horizontalBox);

		Component horizontalStrut_3 = Box.createHorizontalStrut(200);
		horizontalBox.add(horizontalStrut_3);

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
				Object[][] dataVector = controller.retrieveByCode(textCode.getText());
				if(dataVector!=null){
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					for (Object[] data : dataVector) {
						model.addRow(data);											
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
		verticalBox_2.add(scrollPane);
		scrollPane.setViewportBorder(null);

		table = new JTable();
		scrollPane.setViewportView(table);

		Component verticalStrut_2 = Box.createVerticalStrut(80);
		verticalBox_2.add(verticalStrut_2);

		Component horizontalStrut_4 = Box.createHorizontalStrut(200);
		horizontalBox.add(horizontalStrut_4);
		buildJTable(createModel(true));
	}

	private void buildJTable(TableModel model) {
		table.setFont(new Font("Tahoma", Font.PLAIN, 24));
		table.setRowHeight(40);
		table.setModel(model);

		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);

	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private DefaultTableModel createModel(boolean empty) {
		return new DefaultTableModel(new String[][] {}, new String[] { "#",
				"Apuesta", "Numero", "Posicion" }) {

			public Class getColumnClass(int columnIndex) {
				return String.class;
			}

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

}
