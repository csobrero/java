package com.mpx.birjan.client.page;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.mpx.birjan.client.BirjanClient;

public abstract class ReseteableView extends JPanel {

	private static final long serialVersionUID = 7874438889892623958L;

	@Autowired
	protected BirjanClient controller;

	public abstract void reset();

}
