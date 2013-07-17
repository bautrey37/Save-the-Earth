package gameMain;

import gui.GUI;

import javax.swing.SwingUtilities;

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
