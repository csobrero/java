package com.mpx.birjan.client.page;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CheckView extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public CheckView() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		
		Box verticalBox = Box.createVerticalBox();
		centerPanel.add(verticalBox);
		
		JScrollPane scrollPane = new JScrollPane();
		verticalBox.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "Apuesta", "Numero", "Posicion"
			}
		));
		scrollPane.setViewportView(table);
		
		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);

	}

}
