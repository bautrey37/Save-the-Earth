package gui;

import gameMain.Game;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {

	//window properties
	private final static String gamename = "Save the Earth";
	public static final int gameWidth = 800;
	public static final int gameHeight = gameWidth * 3/4;
	
	private JFrame window;
	private JPanel panelContainer, title, credits, upgrades, game, control;
	
	private CardLayout cl;
	
	public GUI() {
		window = new JFrame(gamename);
		
		panelContainer = new JPanel(); //contains the panels below as cards
		title = new TitleMenu(panelContainer);
		credits = new Credits(panelContainer);
		upgrades = new Upgrade(panelContainer);
		game = new Game(panelContainer);
		control = new Controls(panelContainer);
		
		cl = new CardLayout();
		panelContainer.setLayout(cl);
		panelContainer.add(title, "title"); //these are the cards
		panelContainer.add(credits, "credits");
		panelContainer.add(upgrades, "upgrades");
		panelContainer.add(game, "game");
		panelContainer.add(control, "controls");
		
		cl.show(panelContainer, "title");  //title screen is first to show when launching
		
		window.setContentPane(panelContainer);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(gameWidth, gameHeight);
		window.setResizable(false);
		window.setLocationRelativeTo(null); //sets windows location to center of screen
		window.setVisible(true);
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
