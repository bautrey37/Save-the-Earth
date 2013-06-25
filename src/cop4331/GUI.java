package cop4331;

import javax.swing.JFrame;

public class GUI {

	private final static String gamename = "Save the Earth";
	
	private final int gameWidth = 800;
	private final int gameHeight = gameWidth * 3/4;
	
	private JFrame title, credit, game;
	
	public GUI() {
		title = new JFrame(gamename);
		title.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		title.setSize(gameWidth, gameHeight);
		title.setResizable(false);
		title.setLocationRelativeTo(null); //sets windows location to center of screen
		title.setVisible(true);
		
	}
}
