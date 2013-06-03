package com.mpx.birjan.client.page;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mpx.birjan.client.BirjanClient;
import com.mpx.birjan.client.TicketPrintable;

@Repository
public class PrintView extends JPanel {

	private static final long serialVersionUID = -6039872057061219542L;

	@Autowired
	private BirjanClient controller;

	private JLabel lblcCode;

	private Printable ticket;

	private JButton btnPrint;

	private JButton btnCancel;
	private JTextField textField;

	public PrintView() {

		setLayout(new BorderLayout(0, 0));

		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);

		Box verticalBox = Box.createVerticalBox();
		topPanel.add(verticalBox);

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_2);

		lblcCode = new JLabel();
		horizontalBox_2.add(lblcCode);
		lblcCode.setText("VERIFICACION:");
		lblcCode.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setEnabled(false);
		horizontalBox_2.add(horizontalStrut_1);
		
		textField = new JTextField();
		horizontalBox_2.add(textField);
		textField.setColumns(12);
		textField.setFont(new Font("Tahoma", Font.BOLD, 18));
		textField.setEditable(false);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_1);

		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		
		Box verticalBox_2 = Box.createVerticalBox();
		centerPanel.add(verticalBox_2);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox_2.add(horizontalBox_1);

		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);

		Box verticalBox_1 = Box.createVerticalBox();
		bottomPanel.add(verticalBox_1);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalBox_1.add(verticalStrut_2);

		Box horizontalBox = Box.createHorizontalBox();
		verticalBox_1.add(horizontalBox);

		btnPrint = new JButton("Imprimir");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print();
				controller.actionMenu("Jugada");
			}
		});
		horizontalBox.add(btnPrint);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);

		btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionMenu("Jugada");
			}
		});
		horizontalBox.add(btnCancel);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		verticalBox_1.add(verticalStrut_3);
	}

	public void setTicket(TicketPrintable ticket) {
		this.ticket = ticket;
		textField.setText(ticket.getHash());
		textField.requestFocusInWindow();
		btnPrint.setEnabled(true);

	}

	private void print() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		if (pj.printDialog()) {
			PageFormat pf = pj.defaultPage();
			Paper paper = pf.getPaper();
			double width = 2.28d * 72d;
			double height = 2.28d * 72d;
			double margin = 0.15d * 72d;
			paper.setSize(width, height);
			paper.setImageableArea(margin, margin, width - (margin * 2), height
					- (margin * 2));
			pf.setOrientation(PageFormat.LANDSCAPE);
			pf.setPaper(paper);
			// PageFormat validatePage = pj.validatePage(pf);

			Book pBook = new Book();
			pBook.append(ticket, pf);
			pj.setPageable(pBook);

			try {
				pj.print();
			} catch (PrinterException ex) {
				ex.printStackTrace();
			}
		}
	}

}