package com.github.cop4331sum13;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.github.cop4331sum13.entities.*;
import com.github.cop4331sum13.gui.GUI;

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
	
	public static int difficulty;
	public static int planetHealth;

	private boolean accelLeft, accelRight;
	
	
	// controls whether the game shakes
	private int shaking;
	

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
	private boolean needCooldown;
	
	/**
	 * Manages the objects for displaying meteors
	 */
	private Vector<Meteor> meteors;
	
	/**
	 * Manages the objects for displaying alien ships
	 */
	private Vector<Alien> aliens;
	private Vector<AlienLaser> lasers;

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
		
		Game.difficulty = dif;
		
		if( dif == 1 )
		{
			Game.planetHealth = 30;
		}
		else if( dif == 2 )
		{
			Game.planetHealth = 20;
		}
		else  //  dif == 3
		{
			Game.planetHealth = 10;
		}
		
		
		
		// Create user's tank
		tank = new Tank[2];
		tank[0] = new TankBody(getWidth() * 0.5, getHeight() * 0.9, 0, 0);
		tank[0].setTankSpeedLimit(10);
		tank[1] = new TankCannon( getWidth() * 0.5, getHeight() * 0.9, 0, 0);
		
		shells = new Vector<TankShell>();
		aliens = new Vector<Alien>();
		meteors = new Vector<Meteor>();
		lasers = new Vector<AlienLaser>();
		needCooldown = false;
		
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
		double chance = 0;
		System.out.println("run was called");
		while (running) {
			//System.out.println("Iteration #" + iteration);
			iteration++;

			// Make an enemy
			if ((chance = 1000 * Math.random()) > 980) {
				// Meteors
				if (chance > 990)
					meteors.add(new Meteor((int)(Math.random() * 800.0), 0, 0 ,0 ));
				
				// Aliens
				aliens.add( Alien.spawnAlien( (int)(Math.random() * 800.0), 0, 0 ,0 ) );
			}
			
			long time = System.currentTimeMillis();

			this.paintComponent(this.getGraphics());
			// this.paintEntities( this.getGraphics() );

			this.moveEntities();
			this.processHits();
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

		// Draw meteors
		for (Meteor e : meteors) {
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
		for (Alien e : aliens) {
			if (null != e) {
				offg.drawImage(e.getImage(), (int)(e.getX() - e.getImage().getWidth() / 2),
						             (int)(e.getY() - e.getImage().getHeight() / 2), null);
			}
		}
		for (AlienLaser e : lasers) {
			if (null != e) {
				offg.drawImage(e.getImage(), (int)(e.getX() - e.getImage().getWidth() / 2),
						             (int)(e.getY() - e.getImage().getHeight() / 2), null);
			}
		}
		
		
		
		//  Get cannon angle and image.
		double num = ((TankCannon)tank[1]).getAngle() / Math.PI * 180.0;
		//System.out.println((int)num);
		BufferedImage temp = ((TankCannon)tank[1]).cannon[ (int) num - 90];
		
		//  Draw appropriate cannon image.
		offg.drawImage(temp, (int) tank[0].getX() - temp.getWidth() / 2,
				(int) tank[0].getY() - temp.getHeight() / 2, this);
		
		//  Draw tank body.
		offg.drawImage(tank[0].getImage(), (int) tank[0].getX() - tank[0].getImage().getWidth() / 2,
				(int) tank[0].getY() - tank[0].getImage().getHeight() / 2, this);
		
		// Handle shaking
		int xOffset = (int) ((shaking > 0)? ((Math.random() < .5) ? -1:1) * shaking * Math.random():0);
		int yOffset = (int) ((shaking > 0)? ((Math.random() < .5) ? -1:1) * shaking * Math.random():0);

//		if (xOffset != 0 && yOffset != 0)
//		System.out.printf("%d %d\n", xOffset, yOffset);
		
		// Make off screen image visible
		g.drawImage(offScreen, xOffset, yOffset, this);
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
		for (Meteor e : meteors) {
			if (e != null){
				shaking += e.autoAccelerate();
				e.move();
			}
		}
		if (shaking > 0)
			shaking--;
		
		
		//  Move Aliens
		for ( Alien e : aliens )
		{
			if ( e != null && e instanceof LargeAlien)
			{
				((LargeAlien)e).updateAngleToTank( (int)tank[0].getX(), (int)tank[0].getY() );
				((LargeAlien)e).autoAccelerate();
				e.move();
				((LargeAlien)e).fireLasers( lasers );
			}
			
			if (e != null && e instanceof SmallAlien)
			{
				((SmallAlien)e).autoAccelerate((int)tank[0].getX(), (int)tank[0].getY() );
				e.move();
			}
		}
		
		
		
		//  Move all shells on screen.
		for( TankShell e : shells)
		{
			if( e != null )
			{
				e.decreaseLife();
				e.move();
			}
		}
		
		
		for ( AlienLaser e : lasers )
		{
			if( e!= null )
			{
				e.decreaseLife();
				e.move();
			}
		}
	}
	
	
	public void processHits()
	{
		//  First, check for hits
		/*
		 * 
		 * First handle collisions between TankShell to Alien/Meteor objects
		 * 
		 * 
		 * 
		 * Then, handle collisions between AlienLaser and TankBody
		 * 
		 * 
		 * Finally, check "collisions" between Meteor/SmallAlien and planet (y-coor) 
		 * 
		 */
		
		//  This for loop handles collisions between the tank shells and all aliens and meteors
		for( int index = shells.size() - 1; index >= 0; index-- )
		{
			for( Alien e : aliens )
			{
				if( e != null )
				{
					if( shells.get( index ).getX() > e.getX() - e.getImage().getWidth() / 2 && 
						shells.get( index ).getX() < e.getX() + e.getImage().getWidth() / 2 &&
						shells.get( index ).getY() > e.getY() - e.getImage().getWidth() / 2 &&
						shells.get( index ).getY() < e.getY() + e.getImage().getWidth() / 2 )
					{
						shells.get( index ).setHealth( 0 );
						e.setHealth( e.getHealth() - 1 );
					}
				}
			}
			for( Meteor e : meteors )
			{
				if( e!= null )
				{
					if( Math.sqrt( Math.pow( shells.get( index ).getX() - e.getX(), 2.0 ) +
							       Math.pow( shells.get( index ).getY() - e.getY(), 2.0 ) )
							       <= e.getImage().getWidth() / 2 )
					{
						shells.get( index ).setHealth( 0 );
						e.setHealth( e.getHealth() - 1 );
					}
				}
			}
			
			
		}  //  Finished checking for user hits on enemy entities.
		
		
		//  This for loop checks for collisions between small aliens, meteors, and lasers to the user tank.
		for( Alien e : aliens )
		{
			if( e != null )
			{
				if( e instanceof Alien && 
					e.getX() >= tank[0].getX() - tank[0].getImage().getWidth() / 2 &&
					e.getX() <= tank[0].getX() + tank[0].getImage().getWidth() / 2  &&
					e.getY() >= tank[0].getY() - tank[0].getImage().getHeight() / 4 &&
					e.getY() <= tank[0].getY() + tank[0].getImage().getHeight() / 2 )
				{
					e.setHealth( 0 );
					tank[0].setHealth( tank[0].getHealth() - 2 );
				}
			}
		}
		for( Meteor e : meteors )
		{
			if( e.getX() >= tank[0].getX() - tank[0].getImage().getWidth() / 2 - 25 &&
				e.getX() <= tank[0].getX() + tank[0].getImage().getWidth() / 2 + 25 &&
				e.getY() >= tank[0].getY() - tank[0].getImage().getHeight() / 4 - 25 &&
				e.getY() <= tank[0].getY() + tank[0].getImage().getHeight() / 2 + 25 )
			{
				e.setHealth( 0 );
				tank[0].setHealth( tank[0].getHealth() - 5 );
			}
		}
		for( AlienLaser e : lasers )
		{
			if( e.getX() >= tank[0].getX() - tank[0].getImage().getWidth() / 3 &&
				e.getX() <= tank[0].getX() + tank[0].getImage().getWidth() / 3  &&
				e.getY() >= tank[0].getY() &&//- tank[0].getImage().getHeight() / 5 &&
				e.getY() <= tank[0].getY() + tank[0].getImage().getHeight() / 3 )
			{
				e.setHealth( 0 );
				tank[0].setHealth( tank[0].getHealth() - 1 );
			}
		}
		
		//  This for loop checks for "collisions" between meteors and the planet.
		for( int index = meteors.size() - 1; index >= 0; index-- )
		{
			if( meteors.get( index ).getY() >= this.getHeight() )
			{
				Game.planetHealth -= 3;
				meteors.get( index ).setHealth( 0 );
			}
		}
		
		
		//  Lastly, handle deletion of dead aliens, destroyed meteors, dead tank, and old shells/lasers
		for( int index = aliens.size() - 1; index >= 0; index-- )
		{
			if( aliens.get( index ).getHealth() <= 0 || aliens.get( index ).getY() >= this.getHeight() + 50 )
			{
				aliens.remove( index );
			}
		}  //  Finished deleting dead aliens.
		
		
		for( int index = meteors.size() - 1; index >= 0; index-- )
		{
			if( meteors.get( index ).getHealth() <= 0 || meteors.get( index ).getY() >= this.getHeight() + 100 )
			{
				meteors.remove( index );
			}
		}
		
		for( int index = shells.size() - 1; index >= 0; index-- )
		{
			if( shells.get( index ).getLife() <= 0 || shells.get( index ).getHealth() <= 0 )
			{
				shells.remove( index );
			}
		}
		for( int index = lasers.size() - 1; index >= 0; index-- )
		{
			if( lasers.get( index ).getLife() <= 0 || lasers.get( index ).getHealth() <= 0 )
			{
				lasers.remove( index );
			}
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
			//System.out.println("A");
		}
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			//System.out.println("D");
			accelRight = true;
		}
		if (key == KeyEvent.VK_SPACE && !needCooldown)
		{
			shells.add( new TankShell(tank[0].getX(), tank[0].getY(), 0, 0, ((TankCannon)tank[1]).getAngle() ) );
			needCooldown = true;
		}
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
		if (key == KeyEvent.VK_SPACE){
			needCooldown = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
