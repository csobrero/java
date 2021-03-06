package com.mpx.birjan.client.page;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.client.BirjanClient;
import com.mpx.birjan.client.editor.NumberCellEditor;
import com.mpx.birjan.util.Utils.Combos;

@Repository
public class PremiosView extends ReseteableView {

	private static final long serialVersionUID = 4334436586243521165L;

	@Autowired
	protected BirjanClient controller;

	protected JTable table;

	protected JComboBox comboBox, comboBox_1, comboBox_2;

	protected JButton btnDone, btnClear, btnValidate;

	public PremiosView() {

		this.setSize(800, 400);

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		Box vb = Box.createVerticalBox();
		this.add(vb);

		Component vs = Box.createVerticalStrut(15);
		vb.add(vs);

		Box hb = Box.createHorizontalBox();
		vb.add(hb);

		Box vb_1 = Box.createVerticalBox();
		hb.add(vb_1);

		Component vs_3 = Box.createVerticalStrut(20);
		vb_1.add(vs_3);

		Box hb_2 = Box.createHorizontalBox();
		vb_1.add(hb_2);

		Component hs_3 = Box.createHorizontalStrut(20);
		hb_2.add(hs_3);

		JPanel panel = new JPanel();
		hb_2.add(panel);

		Box vb_2 = Box.createVerticalBox();
		panel.add(vb_2);

		JLabel lblLoteria = new JLabel("Premio");
		lblLoteria.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLoteria.setFont(new Font("Tahoma", Font.BOLD, 18));
		vb_2.add(lblLoteria);

		Component vs_6 = Box.createVerticalStrut(20);
		vb_2.add(vs_6);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				init();
			}
		});
		vb_2.add(comboBox);

		Component vs_4 = Box.createVerticalStrut(10);
		vb_2.add(vs_4);

		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String day = Combos.getDay(comboBox.getSelectedItem().toString());
				String selected = comboBox_1.getSelectedItem().toString();
				String[] comboOptions = controller.populateCombo("draw", selected, day);
					if(comboOptions!=null){
					comboBox_2.setModel(new DefaultComboBoxModel(comboOptions));
					comboBox_2.setEnabled(true);
					comboBox_2.requestFocusInWindow();
				}
			}
		});
		vb_2.add(comboBox_1);

		Component vs_5 = Box.createVerticalStrut(10);
		vb_2.add(vs_5);

		comboBox_2 = new JComboBox();
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildJTable(createModel());
				controller.updateDraw(comboBox_1.getSelectedItem().toString(), comboBox_2.getSelectedItem().toString(),
						Combos.getDay(comboBox.getSelectedItem().toString()), true);
				table.changeSelection(0, 1, false, false);
				table.requestFocusInWindow();
				btnClear.setEnabled(true);
				btnDone.setEnabled(true);
				table.setEnabled(true);
			}
		});
		vb_2.add(comboBox_2);

		Component hs = Box.createHorizontalStrut(120);
		vb_2.add(hs);

		Component hs_4 = Box.createHorizontalStrut(20);
		hb_2.add(hs_4);

		Component vs_2 = Box.createVerticalStrut(20);
		vb_1.add(vs_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setLayout(new ScrollPaneLayout());
		hb.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		Component horizontalStrut = Box.createHorizontalStrut(120);
		hb.add(horizontalStrut);

		Component vs_1 = Box.createVerticalStrut(20);
		vb.add(vs_1);

		Box hb_1 = Box.createHorizontalBox();
		vb.add(hb_1);

		btnDone = new JButton("Update");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.updateDraw(comboBox_1.getSelectedItem().toString(), comboBox_2.getSelectedItem().toString(),
						Combos.getDay(comboBox.getSelectedItem().toString()), false);
			}
		});
		btnDone.setFont(new Font("Tahoma", Font.BOLD, 11));
		hb_1.add(btnDone);

		Component hs_2 = Box.createHorizontalStrut(20);
		hb_1.add(hs_2);

		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox.setSelectedIndex(0);
				reset();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 10));
		hb_1.add(btnClear);

		Component hs_1 = Box.createHorizontalStrut(20);
		hb_1.add(hs_1);

		btnValidate = new JButton("Validar");
		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.validateDraw(comboBox_1.getSelectedItem().toString(), comboBox_2.getSelectedItem()
						.toString(), Combos.getDay(comboBox.getSelectedItem().toString()));
			}
		});
		hb_1.add(btnValidate);
	}

	public void reset() {
		comboBox.setModel(new DefaultComboBoxModel(Combos.buildDays(3, false)));
		comboBox.setSelectedIndex(0);
	}

	public void init() {
		buildJTable(createModel());
		String day = Combos.getDay(comboBox.getSelectedItem().toString());
		comboBox_1.setModel(new DefaultComboBoxModel(controller.populateCombo("ticket", "LOTERIA", day)));
		comboBox_1.requestFocusInWindow();
		comboBox_2.setModel(new DefaultComboBoxModel());
		comboBox_2.setEnabled(false);
		btnValidate.setVisible(false);
		btnClear.setEnabled(false);
		btnDone.setEnabled(false);
		table.setEnabled(false);

	}

	public void updateModel(String[] data) {
		if (data != null) {
			boolean validate = true;
			if (data.length > 0) {
				btnDone.setEnabled(true);
				for (int i = 0; i < data.length; i++) {
					if (i < 10) {
						getTableModel().setValueAt(data[i], i, 1);
					} else {
						getTableModel().setValueAt(data[i], i - 10, 3);
					}
					validate &= !data[i].equals("");
				}
			}
			if (validate) {
				// controller.validateDraw();
				btnDone.setVisible(false);
				btnClear.setVisible(false);
				btnValidate.setVisible(true);
			}
		}
	}

	private void buildJTable(TableModel model) {
		table.setFont(new Font("Tahoma", Font.PLAIN, 24));
		table.setRowHeight(40);
		table.setModel(model);
		table.setName("PREMIOS");

		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setMaxWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(100);

		table.getColumnModel().getColumn(1).setCellEditor(new NumberCellEditor());
		table.getColumnModel().getColumn(3).setCellEditor(new NumberCellEditor());
		
		Object key = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).get(KeyStroke.getKeyStroke("ENTER"));
		final Action oldAction = table.getActionMap().get(key);
		table.getActionMap().put(key, new AbstractAction("wrap") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectionModel().getLeadSelectionIndex();
				int column = table.getColumnModel().getSelectionModel().getLeadSelectionIndex();
				if (row == table.getRowCount() - 1) {
					table.getSelectionModel().setLeadSelectionIndex(0);
					table.getColumnModel().getSelectionModel().setLeadSelectionIndex(column == 1 ? 3 : 1);
					return;
				}
				oldAction.actionPerformed(e);
			}
		});

		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setEnabled(false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private DefaultTableModel createModel() {
		return new DefaultTableModel(new Object[][] { { "1", "", "11", "" }, { "2", "", "12", "" },
				{ "3", "", "13", "" }, { "4", "", "14", "" }, { "5", "", "15", "" }, { "6", "", "16", "" },
				{ "7", "", "17", "" }, { "8", "", "18", "" }, { "9", "", "19", "" }, { "10", "", "20", "" } },
				new String[] { "#", "Numero", "#", "Numero" }) {

			public Class getColumnClass(int columnIndex) {
				return String.class;
			}

			boolean[] columnEditables = new boolean[] { false, true, false, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		};
	}

	public DefaultTableModel getTableModel() {
		return (DefaultTableModel) table.getModel();
	}
}
