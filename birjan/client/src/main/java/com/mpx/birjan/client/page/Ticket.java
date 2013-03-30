package com.mpx.birjan.client.page;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.service.impl.BirjanWebService;

@Repository
public class Ticket extends JPanel {
	
	private static final long serialVersionUID = 4334436586243521165L;

	@Autowired
	private BirjanWebService webService;
	
	@Autowired
	private PrintTicketPanel centerPanel;

	private JTable table;

	private JComboBox comboBox_1;

	private JComboBox comboBox_2;

	private JButton btnClear;

	private JButton btnDone;
	
	public Ticket() {
		init();
	}
	
	@PostConstruct
	public void init() {
		if(webService==null)
			return;

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

		Component vs_4 = Box.createVerticalStrut(10);
		vb_2.add(vs_4);

		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = ((String)comboBox_1.getSelectedItem());
				comboBox_2.setModel(new DefaultComboBoxModel(getCombo(selected)));
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
				String selected = ((String)comboBox_2.getSelectedItem());
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
				String hash = done();
				centerPanel.setHash(hash);
				centerPanel.validate();
				Container container = Ticket.this.getParent();
				container.remove(Ticket.this);
				container.add(centerPanel, BorderLayout.CENTER);
				container.validate();
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
		
		reset();
	}

	public void reset(){
		buildJTable(createModel(true));
		comboBox_1.setModel(new DefaultComboBoxModel(getCombo("LOTERIA")));
		comboBox_1.requestFocusInWindow();
		comboBox_2.setModel(new DefaultComboBoxModel());
		comboBox_2.setEnabled(false);
		btnClear.setEnabled(false);
		btnDone.setEnabled(false);
	}
	
	private String done() {
		List<List<Object>> dataVector = ((DefaultTableModel) table
				.getModel()).getDataVector();
		Float[] betAmount = new Float[20];
		String[] numbers = new String[20];
		for (List<Object> obj : dataVector) {
			if (obj.get(1) != null && obj.get(2) != null && obj.get(3) != null) {
				betAmount[(Integer) obj.get(3)] = (Float) obj.get(1);
				numbers[(Integer) obj.get(3)] = (String) obj.get(2);
			}
		}

		String lottery = comboBox_1.getSelectedItem().toString();
		String variant = comboBox_2.getSelectedItem().toString();

		String hash = webService.createGame(lottery + "_" + variant,
				betAmount, numbers, null);
		
		reset();
		
		return hash;
	}
	
	private String[] getCombo(String comboName) {
		if(webService!=null)
			return webService.getComboOptions(comboName);
		return new String[]{""};
	}

	
	private void buildJTable(TableModel model) {
		table.setFont(new Font("Tahoma", Font.PLAIN, 24));
		table.setRowHeight(40);
		// table.setPreferredScrollableViewportSize(new Dimension(20, 20));
		table.setModel(model);

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
		
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					if (btnDone.isEnabled())
						done();
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
					// String columnName = model.getColumnName(column);
					// Object data = model.getValueAt(row, column);

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

		// table.updateUI();
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
