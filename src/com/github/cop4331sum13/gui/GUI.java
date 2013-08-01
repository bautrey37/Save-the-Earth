package com.github.cop4331sum13.gui;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.cop4331sum13.Game;
import com.github.cop4331sum13.sound.SoundManager;

/**
 * Sets up GUI properties, including initializes all the menu's to be displayed.
 * 
 * @author Earth's Defenders
 */
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
	private JPanel panelContainer, credits, control, pause, backstory, endGame;
	private JComponent game, title;
	
	private CardLayout cl;
	
	public Font xoloniumFont;
	
	/**
	 * Creates the main window for all the sub menus and game to be displayed in.
	 */
	public GUI() {
		window = new JFrame(gamename);
		
		try {
			//creates a new custom font from file
			xoloniumFont = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("Xolonium-Regular.otf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			//registers font within java, can be used anywhere now
			ge.registerFont(xoloniumFont);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		
		//contains all the menu cards
		panelContainer = new JPanel(); 
		cl = new CardLayout();
		panelContainer.setLayout(cl);
		
		//contains the panels below as cards
		title = new TitleMenu(panelContainer);
		credits = new Credits(panelContainer);
		game = new Game(panelContainer);
		control = new Controls(panelContainer);
		pause = new Pause(panelContainer);
		backstory = new BackStory(panelContainer);
		endGame = new EndGame(panelContainer);
		
		//these are the cards
		panelContainer.add(title, "title"); 
		panelContainer.add(credits, "credits");
		panelContainer.add(game, "game");
		panelContainer.add(control, "controls");
		panelContainer.add(pause, "pause");
		panelContainer.add(backstory, "story");
		panelContainer.add(endGame, "end");
		
		cl.show(panelContainer, "title");  //title screen is first to show when launching
		SoundManager.playTitleSoundtrack();
		
		title.addMouseListener((MouseListener) title);
		
		window.setContentPane(panelContainer);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(gameWidth, gameHeight);
		window.setResizable(false);
		window.setLocationRelativeTo(null); //sets windows location to center of screen
		window.setVisible(true);
	}
	
	
}
