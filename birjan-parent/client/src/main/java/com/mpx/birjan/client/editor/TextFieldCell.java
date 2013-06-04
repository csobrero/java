package com.mpx.birjan.client.editor;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.CellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

public class TextFieldCell extends JTextField {
	private static final long serialVersionUID = 1L;

	public TextFieldCell(JTable cellTable) {
		super(); // calling parent constructor
		final JTable table = cellTable; // this one is required to get cell
										// editor and stop editing

		this.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				System.out.println("zarasa");
			}

			// this function successfully provides cell editing stop
			// on cell losts focus (but another cell doesn't gain focus)
			public void focusLost(FocusEvent e) {
				System.out.println("zarasa");
//				CellEditor cellEditor = table.getCellEditor();
//				if (cellEditor != null)
//					if (cellEditor.getCellEditorValue() != null)
//						cellEditor.stopCellEditing();
//					else
//						cellEditor.cancelCellEditing();
			}
		});
	}
}
