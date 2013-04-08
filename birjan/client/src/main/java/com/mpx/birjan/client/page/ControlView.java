package com.mpx.birjan.client.page;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.client.BirjanClient;
import com.mpx.birjan.service.impl.BirjanUtils;

@Repository
public class ControlView extends AbstractView {

	private static final long serialVersionUID = 4334436586243521165L;

	private Workbook workbook;

	public ControlView() {

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
		comboBox.setModel(new DefaultComboBoxModel(getdays()));
		comboBox.setSelectedIndex(0);
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
				comboBox_2.setModel(new DefaultComboBoxModel(controller
						.populateCombo("draw", selected, day)));
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
				controller.retriveGames();
				if(workbook!=null){
					btnClear.setEnabled(true);
					btnExport.setEnabled(true);					
				}
			}
		});
		vb_2.add(comboBox_2);

		Component hs = Box.createHorizontalStrut(120);
		vb_2.add(hs);

		Component hs_4 = Box.createHorizontalStrut(20);
		hb_2.add(hs_4);

		Component vs_2 = Box.createVerticalStrut(20);
		vb_1.add(vs_2);

		Box verticalBox = Box.createVerticalBox();
		hb.add(verticalBox);

		JPanel panel_1 = new JPanel();
		verticalBox.add(panel_1);
		
		Box verticalBox_1 = Box.createVerticalBox();
		panel_1.add(verticalBox_1);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox_1.add(verticalStrut);
		
		JButton btnNewButton = new JButton("New button");
		verticalBox_1.add(btnNewButton);

		Component horizontalStrut = Box.createHorizontalStrut(500);
		verticalBox.add(horizontalStrut);

		Component vs_1 = Box.createVerticalStrut(20);
		vb.add(vs_1);

		Box hb_1 = Box.createHorizontalBox();
		vb.add(hb_1);

		btnExport = new JButton("Exportar");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnExport.isEnabled())
					btnExport.setEnabled(false);// prevent double click.
				JFileChooser jfc = new JFileChooser(System
						.getProperty("user.home"));
				jfc.setSelectedFile(new File(buildFileName()));
				int returnVal = jfc.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					FileOutputStream fileOut = null;
					try {
						fileOut = new FileOutputStream(jfc.getSelectedFile());
						workbook.write(fileOut);
						fileOut.flush();
						btnExport.setEnabled(true);
					} catch (IOException ex) {
						ex.printStackTrace();
					} finally{
						try {
							fileOut.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

				} else {
					System.out.println("Save command cancelled by user. ");
				}
			}

			private String buildFileName() {
				String lottery = getComboBox_1().getSelectedItem().toString();
				String variant = getComboBox_2().getSelectedItem().toString();
				DateTime date = BirjanUtils.getDate(getComboBox().getSelectedItem().toString());
				DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
				return lottery+"_"+variant+"_"+fmt.print(date)+".xls";
			}
		});
		btnExport.setFont(new Font("Tahoma", Font.BOLD, 11));
		hb_1.add(btnExport);

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
	}

	private String[] getdays() {
		String[] days = new String[3];
		Locale locale = new Locale("es");
		DateTime dt = new DateTime(new Date());
		for (int i = 0; i < days.length; i++) {
			if (!development && dt.getDayOfWeek() == DateTimeConstants.SUNDAY)
				dt = dt.minusDays(1);
			days[i] = dt.toString("EEEE", locale).toUpperCase() + "  "
					+ dt.getDayOfMonth();
			dt = dt.minusDays(1);
		}
		return days;
	}

	public void reset() {
		String day = comboBox.getSelectedItem().toString().split(" ")[2];
		comboBox_1.setModel(new DefaultComboBoxModel(controller.populateCombo(
				"ticket", "LOTERIA", day)));
		comboBox_1.requestFocusInWindow();
		comboBox_2.setModel(new DefaultComboBoxModel());
		comboBox_2.setEnabled(false);
		btnClear.setEnabled(false);
		btnExport.setEnabled(false);

	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
		
	}
}
