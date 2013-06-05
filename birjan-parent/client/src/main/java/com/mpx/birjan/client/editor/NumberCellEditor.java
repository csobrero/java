package com.mpx.birjan.client.editor;

import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class NumberCellEditor extends DefaultCellEditor {

	private static final long serialVersionUID = -5880293738247654675L;

	private static final Border red = new LineBorder(Color.red);

	public NumberCellEditor() {
		super(new JTextField());
		((JTextField) getComponent()).setHorizontalAlignment(JTextField.CENTER);
	}

	@Override
	public boolean stopCellEditing() {
		if (this.getCellEditorValue() != null) {
			return super.stopCellEditing();
		}
		((JTextField) getComponent()).setBorder(red);
		return false;

	}

	@Override
	public Object getCellEditorValue() {
		Object value = super.getCellEditorValue();
		if (value != null)
			if (Pattern.matches("\\d{4}", value.toString())) {
				return value;
			}
		return null;
	}
}
