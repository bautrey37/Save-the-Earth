package gameMain;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;



public class Game extends JComponent implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	private JPanel container;
	
	private Graphics g;
	
	private boolean running;
	
	/**
	 * Defines the values used in determining enemy count and enemy type in levels 1 - 10.	
	 * This variable is static so that it can be defined only once and used throughout the entire program's running time.
	 */
	private static int[] levelInfo;
	
	private BufferedImage background;
		
	/**
	 * Constructs a new Game JPanel for which to run the level that the user is playing on.
	 * 
	 * @param container - used to switch to other cards  
	 */
	public Game(JPanel container)
	{
		this.container = container;
		
		addKeyListener(this); //adds keylistener to this class
		this.setFocusable(true);
		
		g = getGraphics(); //gets graphics object of current class and stores it in g
		try {
			background = ImageIO.read(new File("res/STE-Background-1.jpg"));
			System.out.println("Passed");
			//g.drawImage(background, 0, 0, null); //causes error
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Add in code that configures level 1 - 10 variables, possibly by reading a file.
		 * Formatting will be decided at a later time.
		 * 
		 * @author Thomas J. O'Neill
		 * @date July 5, 2013, at 12:27 PM
		 */
		
		levelInfo = new int[10];
		
	} 
	
	/*
	 * Overrides paint method in JPanel
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this);
		
	}
	
	/*
	 * Overrides update method in JPanel
	 * @see javax.swing.JComponent#update(java.awt.Graphics)
	 */
	@Override
	public void update(Graphics g) {
		
	}
	/*
	 * 
	 * @see javax.swing.JPanel#updateUI()
	 */
	@Override
	public void updateUI() {
		
	}
	
	
	/**
	 * Runs the game for the user with certain aspects defined by the 
	 * @param dif
	 */
	public void start(int dif)
	{
		running = true;
		//start running game, display should already be set up
		
	}
	
	public void stop() {
		running = false;
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
		
//		if(background != null) {
//			g.drawImage(background, 0, 0, null);
//		} else {
//			System.out.println("background was null");
//		}
		
		//paintBackground(g);
	}  
	
	
	
	/**
	 * This method handles painting the background image at the start of the level and in between frames.
	 * This method is not an override
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


	//For some reason you must click the screen first before it can register key events.  
	//Maybe the focus is not on the canvas initially.
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
