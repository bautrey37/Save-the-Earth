package com.github.cop4331sum13.gui;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.cop4331sum13.Game;

public class GUI {
	
	//window properties
	private final static String gamename = "Save the Earth";
	public static final int gameWidth = 800;
	public static final int gameHeight = gameWidth * 3/4;
	
	/**
	 * Handles the entire graphics environment.  Only one JFrame is needed throughout the game
	 * and the window information is needed by other classes, so this object may be static.
	 */
	public static JFrame window;
	private JPanel panelContainer, credits, upgrades, control, pause, synopsis, gameLost, gameCompleted;
	private JComponent game, title;
	
	private CardLayout cl;
	
	public Font xoloniumFont;
	
	public GUI() {
		window = new JFrame(gamename);
		
		try {
			//creates a new custom font from file
			xoloniumFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/Xolonium-Regular.otf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			//registers font within java, can be used anywhere now
			ge.registerFont(xoloniumFont);
			
			/* // print out all available font names
			String []fontNames = ge.getAvailableFontFamilyNames();
			for(String str: fontNames) {
				System.out.println(str);
			}*/
		} catch (IOException e) {
			System.err.println("Cannot find file");
			e.printStackTrace();
		} catch (FontFormatException e) {
			System.err.println("Font formmating error happened");
			e.printStackTrace();
		}
		
		panelContainer = new JPanel(); //contains the panels below as cards
		title = new TitleMenu(panelContainer);
		credits = new Credits(panelContainer);
		upgrades = new Upgrade(panelContainer);
		game = new Game(panelContainer);
		control = new Controls(panelContainer);
		pause = new Pause(panelContainer);
		
		gameLost = new GameLost(panelContainer);
		gameCompleted = new GameCompleted(panelContainer);
		
		synopsis = new Synopsis(panelContainer);
		
		cl = new CardLayout();
		panelContainer.setLayout(cl);
		panelContainer.add(title, "title"); //these are the cards
		panelContainer.add(credits, "credits");
		panelContainer.add(upgrades, "upgrades");
		panelContainer.add(game, "game");
		panelContainer.add(control, "controls");
		panelContainer.add(pause, "pause");
		
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
	
	
}
