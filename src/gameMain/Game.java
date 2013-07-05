package gameMain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Game extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JPanel container;
	
	private Canvas levelArea;
	
	/**
	 * Defines the values used in determining enemy count and enemy type in levels 1 - 10.
	 * This variable is static so that it can be defined only once and used throughout the entire program's running time.
	 */
	private static int[] levelInfo;
	
	
	private Image background;
	
	
	
	/**
	 * Constructs a new Game JPanel for which to run the level that the user is playing on.
	 * 
	 * @param container - ???
	 */
	public Game(JPanel container)
	{
		this.container = container;
		
		
		
		/*
		 * Add in code that configures level 1 - 10 variables, possibly by reading a file.
		 * Formatting will be decided at a later time.
		 * 
		 * @author Thomas J. O'Neill
		 * @date July 5, 2013, at 12:27 PM
		 */
		
		levelInfo = new int[10];
		
	}  //  End of Game constructor.
	
	
	
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
		// Set up display.
		this.setSize( 800, 600 );
		this.setBackground( Color.WHITE );
		
		
		this.setLayout(new FlowLayout());
		
		
		
//		
//		this.setLayout(new FlowLayout());
//		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		//this.setResizable(false);
//		this.setSize(WIDTH, HEIGHT);
//		this.setBackground(Color.WHITE);
//		
//		
		try {
			background = ImageIO.read(new File("STE-Background-1.jpg"));
			System.out.println("Passed");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//paintBackground( levelArea.getGraphics() );
	}  //  End of setupGame() method.
	
	
	
	/**
	 * This method handles painting the background image at the start of the level and in between frames.
	 */
	public void paintBackground( Graphics g)
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
	
	
	
}
