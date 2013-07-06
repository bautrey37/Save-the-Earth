package gameMain;

import gui.GUI;

import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;



public class Game extends Canvas implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	private JPanel container;
	
	private Canvas levelArea;
	private Graphics g;
	
	/**
	 * Defines the values used in determining enemy count and enemy type in levels 1 - 10.
	 * This variable is static so that it can be defined only once and used throughout the entire program's running time.
	 */
	private static int[] levelInfo;
	
	
	private Image background;
	
	
	
	/**
	 * Constructs a new Game JPanel for which to run the level that the user is playing on.
	 * 
	 * @param container - used to switch to other cards  
	 */
	public Game(JPanel container)
	{
		this.container = container;
		
		//levelArea.setSize(GUI.gameWidth, GUI.gameHeight);
		g = levelArea.getGraphics();
		
		/*
		 * Add in code that configures level 1 - 10 variables, possibly by reading a file.
		 * Formatting will be decided at a later time.
		 * 
		 * @author Thomas J. O'Neill
		 * @date July 5, 2013, at 12:27 PM
		 */
		
		levelInfo = new int[10];
		
	} 
	
	
	
	/**
	 * Runs the game for the user with certain aspects defined by the 
	 * @param dif
	 */
	public void runGame( int dif )
	{
		//  Run the initial commands to configure the display
		setupGame();
		
	}
	
	
	/**
	 * This method configures the initial game settings, including setting up the playing field, creating the initial
	 * enemies, and spawning the user's tank.
	 */
	public void setupGame()
	{
		// Set up display. (Display is already set up because of the GUI class)
		this.setBackground( Color.WHITE );
				
		try {
			background = ImageIO.read(new File("res/STE-Background-1.jpg"));
			System.out.println("Passed");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//paintBackground( levelArea.getGraphics() );
	}  
	
	
	
	/**
	 * This method handles painting the background image at the start of the level and in between frames.
	 */
	public void paintBackground(Graphics g)
	{
		
		Graphics offg = this.getGraphics();
		Image offScreen = null;
		
		// Initialize off screen image
		offScreen = createImage(getWidth(), getHeight());
		offg = offScreen.getGraphics();
		
		// Clear old image
		paint(offg);
		offg.drawImage(background, 0, 0, null);
		//playLevel();
		
		// Make off screen image visible
		g.drawImage(offScreen, 0, 0, this);
		
		
		
	}  //  End of paintBackground() method.
	
	public void playLevel()
	{
		
	}
	
	public void tick() {
		
	}
	
	public void render() {
		
	}


//these are not working...
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		System.out.println("Key pressed");
		
		if(key == KeyEvent.VK_ESCAPE) {
			System.out.println("Escape key got");
			
			CardLayout cl = (CardLayout)container.getLayout();
			cl.show(container, "pause");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	
	
}
