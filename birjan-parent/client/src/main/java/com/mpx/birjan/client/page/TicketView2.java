package com.mpx.birjan.client.page;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.springframework.stereotype.Repository;

@Repository
public class TicketView2 extends JPanel {

	private static final long serialVersionUID = 4334436586243521165L;
	
	private Boolean[][] availability;
	
	private JTable table;

	public TicketView2() {
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


		table = new JTable();
		table.setCellSelectionEnabled(true);
		JScrollPane jScrollPane = new JScrollPane(table);
		jScrollPane.setPreferredSize(new Dimension(150, 100));
		panel.add(jScrollPane);

		Component hs_4 = Box.createHorizontalStrut(20);
		hb_2.add(hs_4);

		Component vs_2 = Box.createVerticalStrut(20);
		vb_1.add(vs_2);

		Component hs_1 = Box.createHorizontalStrut(215);
		hb.add(hs_1);

		Component vs_1 = Box.createVerticalStrut(20);
		vb.add(vs_1);

		Box hb_1 = Box.createHorizontalBox();
		vb.add(hb_1);

		reset();
	}

	public void reset() {
		buildJTable(createModel());
		availability = new Boolean[][]{{false,false,false,false,false},{false,true,false,false,false}};
	}

	private void buildJTable(TableModel model) {
		table.setModel(model);

		// table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(20);
		table.getColumnModel().getColumn(2).setMaxWidth(20);
		table.getColumnModel().getColumn(3).setMaxWidth(20);
		table.getColumnModel().getColumn(4).setMaxWidth(20);

		table.getTableHeader().setReorderingAllowed(false);
		// table.setRowSelectionAllowed(false);
		// table.setColumnSelectionAllowed(false);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private DefaultTableModel createModel() {
		return new DefaultTableModel(new Object[][] {
				{ "NACIONAL", false, false, false, false },
				{ "PROVINCIA", false, false, false, false } }, new String[] {
				"Loteria", "P", "M", "V", "N" }) {
			Class[] columnTypes = new Class[] { String.class, Boolean.class,
					Boolean.class, Boolean.class, Boolean.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column) {
				return (row==0)?false:availability[row][column];
			}
		};
	}

	public void setAvailability(Boolean[][] availability) {
		this.availability = availability;
	}
}
