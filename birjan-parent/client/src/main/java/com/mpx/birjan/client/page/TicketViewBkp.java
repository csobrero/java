package com.mpx.birjan.client.page;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Locale;
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
import javax.swing.table.TableModel;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.stereotype.Repository;

@Repository
public class TicketViewBkp extends AbstractView {
	
	private static final long serialVersionUID = 4334436586243521165L;
	
	public TicketViewBkp() {
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
		;

		JLabel lblLoteria = new JLabel("Lot");
		lblLoteria.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLoteria.setFont(new Font("Tahoma", Font.BOLD, 18));
		vb_2.add(lblLoteria);
		
		Component vs_6 = Box.createVerticalStrut(20);
		vb_2.add(vs_6);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		vb_2.add(comboBox);

		Component vs_4 = Box.createVerticalStrut(10);
		vb_2.add(vs_4);

		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String day = comboBox.getSelectedItem().toString().split(" ")[2];
				String selected = comboBox_1.getSelectedItem().toString();
				comboBox_2.setModel(new DefaultComboBoxModel(controller.populateCombo("ticket", selected, day)));
				comboBox_2.setEnabled(true);
				comboBox_2.requestFocusInWindow();
			}
		});
		vb_2.add(comboBox_1);

		Component vs_5 = Box.createVerticalStrut(10);
		vb_2.add(vs_5);

		comboBox_2 = new JComboBox();
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildJTable(createModel(false));
				table.changeSelection(0, 1, false, false);
				table.requestFocusInWindow();
				btnClear.setEnabled(true);
			}
		});
		vb_2.add(comboBox_2);

		Component hs = Box.createHorizontalStrut(120);
		vb_2.add(hs);

		Component hs_4 = Box.createHorizontalStrut(20);
		hb_2.add(hs_4);

		Component vs_2 = Box.createVerticalStrut(20);
		vb_1.add(vs_2);

		JScrollPane mainPanel = new JScrollPane();
		mainPanel.setViewportBorder(null);
		mainPanel.setLayout(new ScrollPaneLayout());
		hb.add(mainPanel);

		table = new JTable();
		table.setCellSelectionEnabled(true);
		mainPanel.setViewportView(table);

		Component hs_1 = Box.createHorizontalStrut(215);
		hb.add(hs_1);

		Component vs_1 = Box.createVerticalStrut(20);
		vb.add(vs_1);

		Box hb_1 = Box.createHorizontalBox();
		vb.add(hb_1);

		btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnDone.isEnabled())
					btnDone.setEnabled(false);//prevent double click.
//					controller.printHash();
			}
		});
		btnDone.setFont(new Font("Tahoma", Font.BOLD, 11));
		hb_1.add(btnDone);

		Component hs_2 = Box.createHorizontalStrut(20);
		hb_1.add(hs_2);

		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 10));
		hb_1.add(btnClear);
	}

	private String[] getdays() {
		String[] days = new String[6];
		Locale locale = new Locale("es");
		DateTime dt = new DateTime(new Date());
		for (int i = 0; i < days.length; i++) {
			if (!development && dt.getDayOfWeek() == DateTimeConstants.SUNDAY)
				dt = dt.plusDays(1);
			days[i] = dt.toString("EEEE", locale).toUpperCase() + "  "
					+ dt.getDayOfMonth();
			dt = dt.plusDays(1);
		}
		return days;
	}

	public void reset(){
		buildJTable(createModel(true));
		comboBox.setModel(new DefaultComboBoxModel(getdays()));
		comboBox.setSelectedIndex(0);
		String day = comboBox.getSelectedItem().toString().split(" ")[2];
		comboBox_1.setModel(new DefaultComboBoxModel(controller.populateCombo("ticket", "LOTERIA", day)));
		comboBox_1.requestFocusInWindow();
		comboBox_2.setModel(new DefaultComboBoxModel());
		comboBox_2.setEnabled(false);
		btnClear.setEnabled(false);
		btnDone.setEnabled(false);
	}
	
	private void buildJTable(TableModel model) {
		table.setFont(new Font("Tahoma", Font.PLAIN, 24));
		table.setRowHeight(40);
		table.setModel(model);

		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setMaxWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setMaxWidth(100);

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
		
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					if (btnDone.isEnabled())
						btnDone.doClick();
					break;
				case KeyEvent.VK_ESCAPE:
						reset();
					break;
				}

			}
		});

		table.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int row = e.getFirstRow();
					int column = e.getColumn();
					DefaultTableModel model = (DefaultTableModel) e.getSource();

					if (row == (model.getRowCount() - 2)
							&& model.getValueAt(row, 3)!=null
							&& !model.getValueAt(row, 2).toString().equals("")
							&& model.getValueAt(row, 1)!=null) {
						model.setValueAt(String.valueOf(row + 2), row + 1, 0);
						model.addRow(new Object[] { "...", null, "", null });
						table.changeSelection((row + 1), 1, false, false);
						table.requestFocus();
						btnDone.setEnabled(true);
					}
				}
			}
		});

		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private DefaultTableModel createModel(boolean empty) {
		return new DefaultTableModel((empty)?new Object[][] {}:new Object[][] {
				{ "1", null, "", null }, { "...", null, "", null }},
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
