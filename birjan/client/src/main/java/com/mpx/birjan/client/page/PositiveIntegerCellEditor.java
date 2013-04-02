package com.mpx.birjan.client.page;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class PositiveIntegerCellEditor extends DefaultCellEditor {

    private static final Border red = new LineBorder(Color.red);
    private static final Border black = new LineBorder(Color.black);
    private JTextField textField;

    public PositiveIntegerCellEditor(JTextField textField) {
        super(textField);
        this.textField = textField;
        this.textField.setHorizontalAlignment(JTextField.RIGHT);
    }

    @Override
    public boolean stopCellEditing() {
        try {
            Float v = Float.parseFloat(textField.getText());
            if (v < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            textField.setBorder(red);
            return false;
        }
        return super.stopCellEditing();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table,
        Object value, boolean isSelected, int row, int column) {
        textField.setBorder(black);
        return super.getTableCellEditorComponent(
            table, value, isSelected, row, column);
    }
}
