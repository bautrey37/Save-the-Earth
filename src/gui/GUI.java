package gui;

import gameMain.Game;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {

	private final static String gamename = "Save the Earth";
	public static final int gameWidth = 800;
	public static final int gameHeight = gameWidth * 3/4;
	
	private JFrame frame;
	private JPanel panelContainer;
	private TitleMenu title;
	private Credits credits;
	private Upgrade upgrades;
	private Game game;
	
	private CardLayout cl;
	
	public GUI() {
		frame = new JFrame(gamename);
		
		panelContainer = new JPanel(); //contains the panels below as cards
		title = new TitleMenu(panelContainer);
		credits = new Credits(panelContainer);
		upgrades = new Upgrade(panelContainer);
		game = new Game(panelContainer);
		
		cl = new CardLayout();
		panelContainer.setLayout(cl);
		panelContainer.add(title, "title"); //these are the cards
		panelContainer.add(credits, "credits");
		panelContainer.add(upgrades, "upgrades");
		panelContainer.add(game, "game");
		
		cl.show(panelContainer, "title");  //title screen is first to show when launching
		
		frame.setContentPane(panelContainer);
		//frame.add(panelContainer);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(gameWidth, gameHeight);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); //sets windows location to center of screen
		frame.setVisible(true);
	}
	
	public int getWidth() {
		return gameWidth;
	}
	
	public int getHeight() {
		return gameHeight;
	}
	
	public void exit() {
		System.exit(0);
	}
}
