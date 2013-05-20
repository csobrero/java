package com.mpx.birjan.client.page;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerView extends JPanel {
	
	private JTextField textField;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public CustomerView() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Box verticalBox = Box.createVerticalBox();
		add(verticalBox);
		
		Component verticalStrut_4 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_4);
		
		JPanel panel = new JPanel();
		verticalBox.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		Box horizontalBox = Box.createHorizontalBox();
		panel.add(horizontalBox);
		
		JRadioButton rdbtnExistente = new JRadioButton("Existente");
		horizontalBox.add(rdbtnExistente);
		rdbtnExistente.setSelected(true);
		
		JRadioButton rdbtnNuevo = new JRadioButton("Nuevo");
		horizontalBox.add(rdbtnNuevo);
		
		Component verticalStrut = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut);
		
		JPanel panel_2 = new JPanel();
		verticalBox.add(panel_2);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		panel_2.add(horizontalBox_1);
		
		textField = new JTextField();
		textField.setColumns(20);
		textField.setPreferredSize(new Dimension(140, 20));
		horizontalBox_1.add(textField);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("1")){
					buildJTable();
					((DefaultTableModel)table.getModel()).addRow(new Object[] {Boolean.FALSE,null,null,null});
				}
			}
		});
		horizontalBox_1.add(btnBuscar);
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut_1);
		
		JSeparator separator = new JSeparator();
		verticalBox.add(separator);
		
		Component verticalStrut_3 = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut_3);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_2);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut_1);
		
		JScrollPane scrollPane = new JScrollPane();
		horizontalBox_2.add(scrollPane);
		scrollPane.setViewportBorder(null);
		scrollPane.setLayout(new ScrollPaneLayout());
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut_2);
		
		Component verticalStrut_2 = Box.createVerticalStrut(200);
		verticalBox.add(verticalStrut_2);
		buildJTable();

	}
	
	private void buildJTable() {
		table.setModel(createModel());
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(20);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial"})
	private DefaultTableModel createModel() {
		return new DefaultTableModel(
				new Object[][]{},
					new String[] {
						"#", "Nombre", "Apellido", "Celular"
					}
				) {
					Class[] columnTypes = new Class[] {
						Boolean.class, String.class, String.class, String.class
					};
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				};
	}

}
