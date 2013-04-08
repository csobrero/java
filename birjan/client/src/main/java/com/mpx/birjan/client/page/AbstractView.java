package com.mpx.birjan.client.page;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Autowired;

import com.mpx.birjan.client.BirjanClient;


public abstract class AbstractView extends JPanel {

	private static final long serialVersionUID = 4226833723449941921L;

	@Autowired
	protected BirjanClient controller;

	protected JTable table;

	protected JComboBox comboBox, comboBox_1, comboBox_2;

	protected JButton btnDone, btnClear, btnExport, btnValidate;;
	
	protected boolean development = false;
	
	public abstract void reset();
	
	public DefaultTableModel getTableModel() {
		return (DefaultTableModel)table.getModel();
	}
	
	public JComboBox getComboBox_1() {
		return comboBox_1;
	}

	public JComboBox getComboBox_2() {
		return comboBox_2;
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	public void setDevelopment(boolean development) {
		this.development = development;
	}
}
