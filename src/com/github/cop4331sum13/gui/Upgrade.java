package com.github.cop4331sum13.gui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Upgrade extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private JButton cont;
	
	public Upgrade(JPanel container) {
		this.container = container;
		
		cont = new JButton("Continue");
	}
}
