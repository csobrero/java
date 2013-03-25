package com.mpx.birjan.client.page;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.regex.Pattern;

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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Ticket extends JPanel {

	private JTable table;

	/**
	 * Create the panel.
	 */
	public Ticket() {

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		Box verticalBox = Box.createVerticalBox();
		this.add(verticalBox);

		Component verticalStrut = Box.createVerticalStrut(15);
		verticalBox.add(verticalStrut);

		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);

		Box verticalBox_1 = Box.createVerticalBox();
		horizontalBox.add(verticalBox_1);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		verticalBox_1.add(verticalStrut_3);

		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox_1.add(horizontalBox_2);

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut_3);

		JPanel panel = new JPanel();
		horizontalBox_2.add(panel);

		Box verticalBox_2 = Box.createVerticalBox();
		panel.add(verticalBox_2);
		;

		JLabel lblLoteria = new JLabel("Lot");
		lblLoteria.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLoteria.setFont(new Font("Tahoma", Font.BOLD, 18));
		verticalBox_2.add(lblLoteria);

		Component verticalStrut_4 = Box.createVerticalStrut(10);
		verticalBox_2.add(verticalStrut_4);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "NACIONAL",
				"PROVINCIA" }));
		verticalBox_2.add(comboBox_1);

		Component verticalStrut_5 = Box.createVerticalStrut(10);
		verticalBox_2.add(verticalStrut_5);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "PRIMERA",
				"MATUTINA", "VESPERTINA", "NOCTURNA" }));
		verticalBox_2.add(comboBox);

		Component horizontalStrut = Box.createHorizontalStrut(120);
		verticalBox_2.add(horizontalStrut);

		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut_4);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalBox_1.add(verticalStrut_2);

		JScrollPane mainPanel = new JScrollPane();
		mainPanel.setViewportBorder(null);
		mainPanel.setLayout(new ScrollPaneLayout());
		horizontalBox.add(mainPanel);

		table = new JTable();
		buildJTable();
		mainPanel.setViewportView(table);

		Component horizontalStrut_1 = Box.createHorizontalStrut(215);
		horizontalBox.add(horizontalStrut_1);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_1);

		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);

		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Vector dataVector = ((DefaultTableModel) table.getModel())
						.getDataVector();

				System.out.println("call service");
			}
		});
		btnDone.setFont(new Font("Tahoma", Font.BOLD, 11));
		horizontalBox_1.add(btnDone);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut_2);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildJTable();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 10));
		horizontalBox_1.add(btnClear);
	}

	private void buildJTable() {
		table.setFont(new Font("Tahoma", Font.PLAIN, 24));
		table.setRowHeight(40);
		// table.setPreferredScrollableViewportSize(new Dimension(20, 20));
		table.setModel(createModel());

		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(0).setMinWidth(5);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setMinWidth(50);
		table.getColumnModel().getColumn(2).setMaxWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setMinWidth(50);
		table.getColumnModel().getColumn(3).setMaxWidth(100);

		// DefaultTableCellRenderer centerRenderer = new
		// DefaultTableCellRenderer();
		// centerRenderer.setHorizontalAlignment( JLabel.CENTER );

		table.getColumnModel().getColumn(2)
				.setCellRenderer(new DefaultTableCellRenderer() {

					@Override
					protected void setValue(Object value) {
						if (value != null) {
							if (Pattern.matches("\\d{1,4}", value.toString())) {
								String str = "xxx" + value.toString();
								setText(str.substring(str.length() - 4,
										str.length()));
							} else
								setText("");
						}
					}
				});

		// DefaultTableCellRenderer centerRenderer = new
		// DefaultTableCellRenderer();
		// centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		// table.setDefaultRenderer(String.class, centerRenderer);
		// table.setDefaultRenderer(Integer.class, centerRenderer);

		table.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int row = e.getFirstRow();
					int column = e.getColumn();
					DefaultTableModel model = (DefaultTableModel) e.getSource();
					// String columnName = model.getColumnName(column);
					// Object data = model.getValueAt(row, column);

					if (row == (model.getRowCount() - 2)
							&& column == (model.getColumnCount() - 1)) {
						model.setValueAt(String.valueOf(row + 2), row + 1, 0);
						model.addRow(new Object[] { "...", null, "", null });
					}
				}
			}
		});

		// table.updateUI();
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private DefaultTableModel createModel() {
		return new DefaultTableModel(new Object[][] {
				{ "1", null, null, null }, { "...", null, "", null }, },
				new String[] { "#", "Apuesta", "Numero", "Posicion" }) {
			Class[] columnTypes = new Class[] { String.class, Float.class,
					String.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, true, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column]
						&& row != (this.getRowCount() - 1);
			}
		};
	}
}
