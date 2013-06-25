package cop4331;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class TitleMenu extends GUI implements ActionListener {

	private static final int START_ID = 00;
	private static final int CONTROLS_ID = 01;
	private static final int CREDITS_ID = 02;
	private static final int QUIT_ID = 03;
	
	private final int gameWidth;
	private final int gameHeight;
	
	private JFrame title;
	
	public TitleMenu(int gameWidth, int gameHeight) {
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
	}	
	
	public void setupTitleFrame() {
		title = new JFrame();
		title.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		title.setVisible(true);
		title.setResizable(false);
		title.setSize(gameWidth, gameHeight);
	}

	public void actionPerformed(ActionEvent e) {
		//actions for frame
	}
		
}
