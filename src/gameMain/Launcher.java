package gameMain;

import javax.swing.SwingUtilities;

import gui.GUI;

public class Launcher {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUI();
			}
			
		});	
	}

	public void runGame() {
		//Game startGame = new Game();
	}
}
