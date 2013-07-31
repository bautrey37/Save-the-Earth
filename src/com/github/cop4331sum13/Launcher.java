package com.github.cop4331sum13;

import javax.swing.SwingUtilities;

import com.github.cop4331sum13.gui.GUI;

/**
 * This is the class that contains the main method and launches the game by called GUI class.
 * The GUI runs in its own thread.
 */
public class Launcher {
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUI();
			}
			
		});	
	}
}
