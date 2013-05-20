package com.mpx.birjan.client.page;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class Fruta extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public Fruta() {
		this.setSize(200, 200);
		
		
		table = new JTable();
		table.setModel(buildModel());
		table.getColumnModel().getColumn(1).setMaxWidth(20);
		table.getColumnModel().getColumn(2).setMaxWidth(20);
		table.getColumnModel().getColumn(3).setMaxWidth(20);
		table.getColumnModel().getColumn(4).setMaxWidth(20);
//		add(table);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
	}

	private DefaultTableModel buildModel() {
		return new DefaultTableModel(new Object[][] {
				{ "Nacional", false, false, false, false },
				{ "Provincia", false, false, false, false } }, new String[] {
				"Loteria", "P", "M", "V", "N" }) {
			Class[] columnTypes = new Class[] { String.class, Boolean.class,
					Boolean.class, Boolean.class, Boolean.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, true, true,
					true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
	}

}
