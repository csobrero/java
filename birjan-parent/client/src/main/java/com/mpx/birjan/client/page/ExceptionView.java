package com.mpx.birjan.client.page;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.springframework.stereotype.Repository;

@Repository
public class ExceptionView extends ReseteableView {

	private static final long serialVersionUID = 1L;

	private JTextArea textArea;

	private JScrollPane jScrollPane;

	public ExceptionView() {
		this.setSize(800, 400);
		setLayout(new BorderLayout(0, 0));

		Box horizontalBox = Box.createHorizontalBox();
		add(horizontalBox, BorderLayout.CENTER);

		Box verticalBox = Box.createVerticalBox();
		horizontalBox.add(verticalBox);

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut);

		JPanel panel = new JPanel();
		verticalBox.add(panel);
		textArea = new JTextArea();
		textArea.setEditable(false);
		jScrollPane = new JScrollPane(textArea);
		jScrollPane.setPreferredSize(new Dimension(700, 400));
		panel.add(jScrollPane);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_1);
	}

	public void show(Throwable t) {
		StringWriter errors = new StringWriter();
		t.printStackTrace(new PrintWriter(errors));
		textArea.setText(errors.toString());
		textArea.setCaretPosition(0);
	}

	@Override
	public void reset() {
	}

}
