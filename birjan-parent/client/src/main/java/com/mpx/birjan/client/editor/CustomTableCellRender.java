package com.mpx.birjan.client.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableCellRender extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	DecimalFormat df = new DecimalFormat("0.#");

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if (table.getName().equals("BALANCE")) {
			if (value != null && column > 1) {
				setText("$" + df.format(value));
				setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
				if (column == 9 && (Float) value < 0) {
					c.setForeground(Color.RED);
				}
			}
			if (row == table.getRowCount()-1) {
				c.setFont(getFont().deriveFont(Font.BOLD)); 
			}
		}

		// c.setFont(/* special font */);
		// c.setForeground(/* special foreground color */);
		// c.setBackground(/* special background color */);

		return c;
	}
}
