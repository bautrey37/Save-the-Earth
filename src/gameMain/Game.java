package gameMain;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import entities.*;
import gui.GUI;

/**
 * This is the main class that runs the game.
 * 
 */

public class Game extends JComponent implements KeyListener, Runnable {

	private static final long serialVersionUID = 1L;

	private JPanel container;
	private BufferedImage background;
	private Thread game;

	private volatile boolean running; // volatile makes it thread safe
	private int iteration;

	private boolean accelLeft, accelRight;

	private Enemy[] enemies;
	private int numEnemies = 0;
	

	/**
	 * Defines the values used in determining enemy count and enemy type in levels 1 - 10. This variable is static so
	 * that it can be defined only once and used throughout the entire program's running time.
	 */
	private static int[] levelInfo;

	/**
	 * Manages the objects for displaying the user's tank.
	 */
	private Tank[] tank;
	
	/**
	 * Manages the objects for displaying and tracking shots fired by user.
	 */
	private volatile Vector<TankShell> shells;

	/**
	 * Constructs a new Game JPanel for which to run the level that the user is playing on.
	 * 
	 * @param container
	 *            - used to switch to other cards
	 */
	public Game(JPanel container) {
		this.container = container;

		try {
			background = ImageIO.read(new File("res/STE-Background-1.jpg"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Add in code that configures level 1 - 10 variables, possibly by reading a file. Formatting will be decided at
		 * a later time.
		 */
		levelInfo = new int[10];

		this.addKeyListener(this);
	}

	/**
	 * This method configures the initial game settings, including setting up the playing field, creating the initial
	 * enemies, and spawning the user's tank.
	 * 
	 * @param dif - difficulty of game
	 */
	public void init(int dif) {
		//clear previous game data
		
		// Create user's tank
		tank = new Tank[2];
		tank[0] = new TankBody(getWidth() * 0.5, getHeight() * 0.9, 0, 0);
		tank[0].setTankSpeedLimit(10);
		tank[1] = new TankCannon( getWidth() * 0.5, getHeight() * 0.9, 0, 0);
		
		shells = new Vector<TankShell>();
		
		enemies = new Enemy[50];
		
		start();
	}

	/**
	 * Starts the thread for running the game.  Keeps existing game data
	 */
	public void start() {
		accelLeft = false;
		accelRight = false;
		running = true;
		this.requestFocusInWindow();
		
		game = new Thread(this);
		game.setPriority(Thread.MAX_PRIORITY);
		game.start();
	}

	/**
	 * Stops the game from running, essentially ending the thread
	 */
	public void stop() {
		running = false;
	}

	/**
	 * Runs the game in its own thread.
	 */
	@Override
	public void run() {
		iteration = 0;
		System.out.println("run was called");
		while (running) {
			//System.out.println("Iteration #" + iteration);
			iteration++;

			// Make an enemy
			if (1000 * Math.random() > 980) {
				enemies[numEnemies] = new Enemy(getWidth());
				numEnemies = (numEnemies + 1) % enemies.length;
			}
			
			long time = System.currentTimeMillis();

			this.paintComponent(this.getGraphics());
			// this.paintEntities( this.getGraphics() );

			this.moveEntities();
			// System.out.println( tank[0].getX() );

			try {
				long delay = Math.max(0, 32 - (System.currentTimeMillis() - time));
				Thread.sleep(delay);
			}
			catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		/*
		 * g.drawImage(background, 0, 0, this); g.drawImage( tank[0].getImage(), (int)tank[0].getX() -
		 * tank[0].getImage().getWidth() / 2, (int)tank[0].getY() - tank[0].getImage().getHeight() / 2, this );
		 */

		Graphics offg;
		Image offScreen = null;

		// Initialize off screen image
		offScreen = createImage(getWidth(), getHeight());
		offg = offScreen.getGraphics();

		// Clear old image
		// paintComponent(offg);
		offg.drawImage(background, 0, 0, this);

		// Draw new enemies
		for (Enemy e : enemies) {
			if (null != e) {
				offg.drawImage(e.getImage(), (int)(e.getX() - e.getImage().getWidth() / 2),
						             (int)(e.getY() - e.getImage().getHeight() / 2), null);
			}
		}

		
		//  Draw all shells on screen.
		for (TankShell e : shells) {
			if (null != e) {
				offg.drawImage(e.getImage(), (int)(e.getX() - e.getImage().getWidth() / 2),
						             (int)(e.getY() - e.getImage().getHeight() / 2), null);
			}
		}
		
		
		
		
		//  Get cannon angle and image.
		double num = ((TankCannon)tank[1]).getAngle() / Math.PI * 180.0;
		System.out.println((int)num);
		BufferedImage temp = ((TankCannon)tank[1]).cannon[ (int) num - 90];
		
		//  Draw appropriate cannon image.
		offg.drawImage(temp, (int) tank[0].getX() - temp.getWidth() / 2,
				(int) tank[0].getY() - temp.getHeight() / 2, this);
		
		//  Draw tank body.
		offg.drawImage(tank[0].getImage(), (int) tank[0].getX() - tank[0].getImage().getWidth() / 2,
				(int) tank[0].getY() - tank[0].getImage().getHeight() / 2, this);
		
		
		// Make off screen image visible
		g.drawImage(offScreen, 0, 0, this);
	}

	
	/**
	 * Handles moving all entities across the screen.
	 */
	public synchronized void moveEntities() {
		// Handle tank movement.
		if (accelLeft)
			tank[0].accelerateLeft();
		if (accelRight)
			tank[0].accelerateRight();
		if (!accelLeft && !accelRight)
			tank[0].lowerTankSpeed();

		tank[0].move();
		
		//  Move all shells on screen.
		for( TankShell s : shells)
			s.move();
		
		//  Handle tank cannon rotation
		double mouseX = MouseInfo.getPointerInfo().getLocation().getX() - GUI.window.getX();
		double mouseY = MouseInfo.getPointerInfo().getLocation().getY() - GUI.window.getY();
		((TankCannon)tank[1]).updateAngleMouse( (int) mouseX, (int) mouseY, (int) tank[0].getX(), (int) tank[0].getY() );
		
		// Move Enemies
		for (Enemy e : enemies) {
			if (e != null)
				e.move();
		}

	}


	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// System.out.println("Key pressed");

		if (key == KeyEvent.VK_ESCAPE) {
			stop();
			CardLayout cl = (CardLayout) container.getLayout();
			cl.show(container, "pause");
		}

		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			accelLeft = true;
			System.out.println("A");
		}
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			System.out.println("D");
			accelRight = true;
		}
		if (key == KeyEvent.VK_SPACE)
			shells.add( new TankShell(tank[0].getX(), tank[0].getY(), 0, 0, ((TankCannon)tank[1]).getAngle() ) );
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			accelLeft = false;
		}
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			accelRight = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
