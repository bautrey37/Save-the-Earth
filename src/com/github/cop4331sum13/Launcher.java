package com.github.cop4331sum13;

import javax.swing.SwingUtilities;

import com.github.cop4331sum13.gui.GUI;

public class Launcher {

	private static GUI gui;
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				gui = new GUI();
			}
			
		});	
	}

	public void runGame() {
		
	}
}
