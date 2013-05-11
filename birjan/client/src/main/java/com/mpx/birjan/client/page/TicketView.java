package com.mpx.birjan.client.page;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import javax.swing.table.JTableHeader;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.core.Rule;

@Repository
public class TicketView extends AbstractView {

	private static final long serialVersionUID = 4334436586243521165L;

	private JTable lotteryTable;
	
	private JLabel lblTotal;
	
	private Float totalAmount = 0f;
	
	DecimalFormat formatter = new DecimalFormat("#.##");
	
	public static String[][] loteriesMap = {
			{ "NACIONAL_PRIMERA", "NACIONAL_MATUTINA", "NACIONAL_VESPERTINA",
					"NACIONAL_NOCTURNA" },
			{ "PROVINCIA_PRIMERA", "PROVINCIA_MATUTINA",
					"PROVINCIA_VESPERTINA", "PROVINCIA_NOCTURNA" } };
	
	

	private Map<String, Object[][]> selected;

	public TicketView() {
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

		JLabel lblLoteria = new JLabel("Lot");
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

		lotteryTable = new JTable();
		buildLoteryTable();
		JScrollPane jScrollPane = new JScrollPane(lotteryTable);
		jScrollPane.setPreferredSize(new Dimension(150, 100));
		vb_2.add(jScrollPane);

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
		buildJTable();
		mainPanel.setViewportView(table);
		
		Box verticalBox = Box.createVerticalBox();
		hb.add(verticalBox);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(180);
		verticalBox.add(horizontalStrut_2);
		
		Component verticalStrut = Box.createVerticalStrut(100);
		verticalBox.add(verticalStrut);
		
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);
		
		lblTotal = new JLabel("TOTAL: $ 0.00");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 20));
		horizontalBox.add(lblTotal);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_1);

		Component vs_1 = Box.createVerticalStrut(20);
		vb.add(vs_1);

		Box hb_1 = Box.createHorizontalBox();
		vb.add(hb_1);

		btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnDone.isEnabled())
					btnDone.setEnabled(false);// prevent double click.
				controller.play();
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

	public void reset() {
		comboBox.setModel(new DefaultComboBoxModel(getdays()));
		comboBox.setSelectedIndex(0);
		init();

	}

	private void init() {
		String day = comboBox.getSelectedItem().toString().split(" ")[2];
		lotteryTable.setModel((createLoteryModel(controller.retrieveAvailability(day))));
		btnClear.setEnabled(false);
		btnDone.setEnabled(false);
		table.setModel((createModel(false)));
		table.changeSelection(0, 1, false, false);
		table.requestFocusInWindow();
		modifyModels();
	}

	private void buildJTable() {
		table.setFont(new Font("Tahoma", Font.PLAIN, 24));
		table.setRowHeight(40);
		table.setCellSelectionEnabled(true);

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

		table.getTableHeader().setReorderingAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private DefaultTableModel createModel(boolean empty) {
		return new DefaultTableModel((empty) ? new Object[][] {}
				: new Object[][] { { 1, "", null } }, new String[] { "Ubicacion", "Numero", "Importe" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, Float.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { true, true, true };

			public boolean isCellEditable(int row, int column) {
				return true;
//				return columnEditables[column]
//						&& row != (this.getRowCount() - 1);
			}
		};
	}

	private void buildLoteryTable() {
		lotteryTable.setCellSelectionEnabled(true);

		lotteryTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = lotteryTable.rowAtPoint(e.getPoint());
				int col = lotteryTable.columnAtPoint(e.getPoint());
				if (col == 0) {
					DefaultTableModel model = (DefaultTableModel) lotteryTable
							.getModel();
					List<List<Object>> data = model.getDataVector();
					List<Object> list = data.get(row);
					boolean set = true;
					for (int i = 1; set && i < list.size(); i++) {
						if (model.isCellEditable(row, i))
							set &= (Boolean) list.get(i);
					}
					for (int i = 1; i < list.size(); i++) {
						if (model.isCellEditable(row, i))
							list.set(i, !set);
					}
					model.fireTableDataChanged();
				}
			}
		});

		final JTableHeader tableHeader = lotteryTable.getTableHeader();
		tableHeader.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int idx = tableHeader.columnAtPoint(e.getPoint());
				DefaultTableModel model = (DefaultTableModel) lotteryTable.getModel();
				List<List<Object>> data = model.getDataVector();
				for (int j = (idx != 0) ? idx : 1; j < data.get(0).size(); j++) {
					boolean set = true;
					for (int i = 0; set && i < data.size(); i++) {
						if (model.isCellEditable(i, j))
							set &= (Boolean) data.get(i).get(j);
					}
					for (int i = 0; i < data.size(); i++) {
						if (model.isCellEditable(i, j))
							data.get(i).set(j, !set);
					}
					if (idx != 0)
						break;
				}
				model.fireTableDataChanged();
			}
		});

		lotteryTable.getTableHeader().setReorderingAllowed(false);
		lotteryTable.setRowSelectionAllowed(false);
		lotteryTable.setColumnSelectionAllowed(false);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private DefaultTableModel createLoteryModel(final Object[][] availability) {

		Object[][] model = new Object[availability.length][5];
		for (int i = 0; i < model.length; i++)
			for (int j = 0; j < 5; j++)
				model[i][j] = (j == 0) ? availability[i][j] : false;

		return new DefaultTableModel(model, new String[] { "Loteria", "P", "M",
				"V", "N" }) {
			Class[] columnTypes = new Class[] { String.class, Boolean.class,
					Boolean.class, Boolean.class, Boolean.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column) {
				return (column == 0) ? false
						: (Boolean) availability[row][column];
			}
		};
	}

	private void modifyModels() {
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);

//		table.getColumnModel().getColumn(1)
//				.setCellRenderer(new DefaultTableCellRenderer() {
//					@Override
//					protected void setValue(Object value) {
//						if (value != null) {
//							if (Pattern.matches("\\d{1,4}", value.toString())) {
//								String str = "xxx" + value.toString();
//								setText(str.substring(str.length() - 4,
//										str.length()));
//							} else
//								setText("");
//						}
//					}
//				});
		
		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int row = e.getFirstRow();
					int column = e.getColumn();
					DefaultTableModel model = (DefaultTableModel) e.getSource();

					Object value = model.getValueAt(row, column);
					if (column==1 && Pattern.matches("\\d{1,4}", value.toString())) {
							String str = "xxx" + value.toString();
							model.setValueAt(str.substring(str.length() - 4,str.length()),row,column);
					}
					
					if (row == (model.getRowCount() - 1)
							&& model.getValueAt(row, 0) != null
							&& !model.getValueAt(row, 1).toString().equals("")
							&& model.getValueAt(row, 2) != null) {
						model.addRow(new Object[] { 20, "", null });
					}
					if(column == 2) {
						updateTotal();
					}
					
				}
			}
		});

		lotteryTable.getColumnModel().getColumn(0).setMaxWidth(100);
		lotteryTable.getColumnModel().getColumn(1).setMaxWidth(20);
		lotteryTable.getColumnModel().getColumn(2).setMaxWidth(20);
		lotteryTable.getColumnModel().getColumn(3).setMaxWidth(20);
		lotteryTable.getColumnModel().getColumn(4).setMaxWidth(20);

		lotteryTable.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				updateTotal();
			}
		});
	}
	
	public Object[][] getData(){
		List<List<Object>> vector = getTableModel().getDataVector();
		Object[][] data = new Object[vector.size()-1][];
		int size = (vector.get(vector.size()-1).get(2)==null) ? vector
				.size() - 1 : vector.size();
		for (int i = 0; i < size; i++) {
			data[i] = vector.get(i).toArray(new Object[]{});
		}
		return data;
	}
	
	public List<String> selectedLotteries(){
		@SuppressWarnings("unchecked")
		List<List<Object>> lottery = ((DefaultTableModel) lotteryTable.getModel())
				.getDataVector();
		List<String> loteries = new ArrayList<String>();
		for (int i = 0; i < lottery.size(); i++)
			for (int j = 1; j < lottery.get(0).size(); j++)
				if((Boolean)lottery.get(i).get(j))
					loteries.add(loteriesMap[i][j-1]);
		return loteries;
	}

	private void updateTotal() {
		DefaultTableModel model = ((DefaultTableModel) table.getModel());
		int count = selectedLotteries().size();
		Float total = 0f;
		if (model.getRowCount() > 1 && count > 0) {
			for (int i = 0; i < model.getRowCount() - 1; i++)
				total += (Float) model.getValueAt(i, 2);
			total *=count;
		}
		lblTotal.setText(String.format("TOTAL: $ %.2f", total));
		btnDone.setEnabled(model.getRowCount() > 1 && count > 0);
	}

	public JTable getLotteryTable() {
		return lotteryTable;
	}

}