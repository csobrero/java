package com.mpx.birjan.client.page;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.StringUtils;
import java.awt.Font;
import javax.swing.JButton;

public class TicketPage extends JFrame {

	private static final long serialVersionUID = 1900172587979828855L;

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicketPage frame = new TicketPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({"serial", "unchecked", "rawtypes" })
	public TicketPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		JMenu filemenu = new JMenu("Archivo");
		JMenu helpmenu = new JMenu("Ayuda");
		JMenuItem fileItem1 = new JMenuItem("Ticket");
		JMenuItem helpItem1 = new JMenuItem("Acerca");
		filemenu.add(fileItem1);
		helpmenu.add(helpItem1);
		menuBar.add(filemenu);
		menuBar.add(helpmenu);
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
		
		Box verticalBox = Box.createVerticalBox();
		centerPanel.add(verticalBox);
		
		Component verticalStrut = Box.createVerticalStrut(15);
		verticalBox.add(verticalStrut);
		
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		
		Component horizontalStrut = Box.createHorizontalStrut(40);
		horizontalBox.add(horizontalStrut);
		
		JScrollPane mainPanel = new JScrollPane();
		mainPanel.setViewportBorder(null);
		mainPanel.setLayout(new ScrollPaneLayout());
		horizontalBox.add(mainPanel);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 24));
		table.setRowHeight(40);
		// table.setPreferredScrollableViewportSize(new Dimension(20, 20));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"1", null, null, null},
				{"...", null, "", null},
			},
			new String[] {
				"#", "Apuesta", "Numero", "Posicion"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Float.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column] && row!=(this.getRowCount()-1);
			}
		});
		
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
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		table.getColumnModel().getColumn(2)
				.setCellRenderer(new DefaultTableCellRenderer() {

					@Override
					protected void setValue(Object value) {
						if (value != null) {
							if (Pattern.matches("\\d{1,4}", value.toString())) {
								String str = "xxx" + value.toString();
								setText(str.substring(str.length() - 4, str.length()));
							} else
								setText("");
						}
					}
				});
		
//		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
//		table.setDefaultRenderer(String.class, centerRenderer);
//		table.setDefaultRenderer(Integer.class, centerRenderer);
		
		table.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int row = e.getFirstRow();
					int column = e.getColumn();
					DefaultTableModel model = (DefaultTableModel) e.getSource();
//					String columnName = model.getColumnName(column);
//					Object data = model.getValueAt(row, column);
					
					if (row == (model.getRowCount() - 2) && column == (model.getColumnCount() - 1)) {
						model.setValueAt(String.valueOf(row+2), row+1, 0);
						model.addRow(new Object[] { "...", null, "", null });
					}
				}
			}
		});
		
//		table.updateUI();
		mainPanel.setViewportView(table);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(40);
		horizontalBox.add(horizontalStrut_1);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_1);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);
		
		JButton btnDone = new JButton("Done");
		btnDone.setFont(new Font("Tahoma", Font.BOLD, 11));
		horizontalBox_1.add(btnDone);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut_2);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 10));
		horizontalBox_1.add(btnClear);



		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

	}
}
