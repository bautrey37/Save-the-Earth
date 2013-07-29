package com.github.cop4331sum13;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.github.cop4331sum13.animation.Explosion;
import com.github.cop4331sum13.entities.*;
import com.github.cop4331sum13.gui.GUI;
import com.github.cop4331sum13.gui.Pause;
import com.github.cop4331sum13.sound.SoundManager;

/**
 * This is the main class that runs the in-game level.  All necessary procedures are handled by this class only.
 */
public class Game extends JComponent implements KeyListener, Runnable, MouseListener
{
	private Vector<Explosion> explosions;
	private boolean GODMODE = true;
	
	private boolean aimWithMouse;
	private boolean aimWithKeyboard;
	private boolean rotateLeft;
	private boolean rotateRight;
	
	private BufferedImage mouseMode;
	private BufferedImage keyboardMode;
	
	private Image offScreen = null;
	
	
	/**
	 * This value is necessary for managing separate threads properly. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Contains the entire gui that is used by the program.
	 */
	private JPanel container;
	
	/**
	 * Font for game time
	 */
	private static Font font = new Font("Xolonium", Font.PLAIN, 25);
	
	/**
	 * Contains the background image used when playing the levels.
	 */
	private BufferedImage background;
	
	/**
	 * Used for starting a separate thread to allow multiple functions to run side by side,
	 * as is necessary for running the program properly.
	 */
	private Thread game;
	
	/**
	 * Determines when to keep playing the level and when to cut the level off.
	 */
	private volatile boolean running; // volatile makes it thread safe
	
	/**
	 * States the difficulty level that the user will be playing at and is declared as static as it should never exist
	 * as more than one value.
	 */
	public static int difficulty;
	
	private int maxPlanetHealth, planetHealth, maxTankHealth, tankHealth, xPos, yPos, width, height;
	
	/**
	 * Used to manage movement of the tank based on user input.
	 */
	private boolean accelLeft, accelRight;
	
	/**
	 * Controls whether or not the game screen shakes.
	 */
	private int shaking;
	
	/**
	 * Used to adjust the chance of enemies spawning per game tick
	 * It is stored as a number out of 1000 (for example, spawnRate = 10 means 10/1000 or 1% chance) 
	 */
	private double spawnRate;
	
	/**
	 * Used to control when enemies can spawn if there are none on the screen
	 */
	private int spawnCoolDown;
	private int spawnCoolDownConst; // varies by difficulty
	
	/**
	 * Holds the current game time
	 */
	public static Timer timer;
	private static int interval;
	
	/**
	 * Defines the values used in determining enemy count and enemy type in levels 1 - 10. This variable is static so
	 * that it can be defined only once and used throughout the entire program's running time.
	 */
	//private static int[] levelInfo;

	/**
	 * Manages the objects for displaying the user's tank.
	 */
	private Tank[] tank;
	
	/**
	 * Manages the objects for displaying and tracking shots fired by user.
	 */
	private volatile Vector<TankShell> shells;
	
	/**
	 * Used to determine if the user must release the space bar before another shell can be fired.
	 */
	private boolean needCooldown;
	
	/**
	 * Manages the objects for displaying meteors.
	 */
	private Vector<Meteor> meteors;
	
	/**
	 * Manages the objects for displaying alien ships.
	 */
	private Vector<Alien> aliens;
	
	/**
	 * Manages the objects for displaying the alien lasers that are fired.
	 */
	private Vector<AlienLaser> lasers;

	/**
	 * Constructs a new Game JPanel for which to run the level that the user is playing on.
	 * 
	 * @param container - used to switch to other cards
	 */
	public Game(JPanel container)
	{
		//  Assign the "cards" to container to enable switching to pause and main menu.
		this.container = container;
		this.addMouseListener(this);
		
		//  Obtain background image for the play level.
		try
		{
			background = ImageIO.read(new File("res/STE-Background-1.jpg"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		/*
		 * Add in code that configures level 1 - 10 variables, possibly by reading a file. Formatting will be decided at
		 * a later time.
		 */
		//levelInfo = new int[10];
		
		this.addKeyListener(this);
	}

	/**
	 * This method configures the initial game settings, including setting up the playing field, creating the initial
	 * enemies, and spawning the user's tank.  Additionally, this method functions to reset all relevant data for when
	 * the user returns to main menu and starts another new game.
	 * 
	 * @param dif - difficulty of game
	 */
	public void init( int dif )
	{
		//  Set the global game difficulty per user selection.
		Game.difficulty = dif;
		
		//  Set the planet health and spawn rate based on difficulty level.
		if( dif == 1 )
		{
			planetHealth = 50;
			spawnRate = 3;
			spawnCoolDownConst = 100;
		}
		else if( dif == 2 )
		{
			planetHealth = 30;
			spawnRate = 7;
			spawnCoolDownConst = 85;
		}
		else  //  dif == 3
		{
			planetHealth = 15;
			spawnRate = 11;
			spawnCoolDownConst = 70;
		}
		maxPlanetHealth = planetHealth;
		
		// Create user's tank and cannon
		tank = new Tank[2];
		tank[0] = new TankBody(getWidth() * 0.5, getHeight() * 0.9, 0, 0);
		tank[0].setTankSpeedLimit(10);
		tank[1] = new TankCannon( getWidth() * 0.5, getHeight() * 0.9, 0, 0);
		
		//max health is needed to draw health bar
		maxTankHealth = tank[0].getHealth();
		
		//  Initialize all lists for managing on-screen entities.
		shells = new Vector<TankShell>();
		aliens = new Vector<Alien>();
		meteors = new Vector<Meteor>();
		lasers = new Vector<AlienLaser>();
		explosions = new Vector<Explosion>();
		
		//  Configure animations.
		Explosion.setupSequence();
		
		
		//  Set default controls to mouse.
		aimWithMouse = true;
		aimWithKeyboard = false;
		
		// Initialize game time
		interval = 180; // (seconds)
		timer = new Timer();
		
		
		
		//  Obtain background image for the play level.
		try
		{
			mouseMode = ImageIO.read( new File( "res/Mouse.jpg" ) );
			keyboardMode = ImageIO.read( new File( "res/Keyboard.jpg" ) );
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		start();
	}
	
	
	
	/**
	 * Starts the thread for running the game.  Keeps existing game data (for when resuming from "pause").
	 */
	public void start()
	{
		//  Set/reset values for starting new game and resuming from "pause".
		accelLeft = false;
		accelRight = false;
		rotateLeft = false;
		rotateRight = false;
		running = true;
		needCooldown = false;
		
		// start/restart timer
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
					--interval;
			    }
		}, (long)1000, (long)1000);
		
		this.requestFocusInWindow();
		
		//  Create a new thread to enable concurrent running of multiple methods.
		game = new Thread(this);
		game.setPriority(Thread.MAX_PRIORITY);
		game.start();
		
		
	} 
	
	
	
	/**
	 * Stops the game from running, essentially ending the thread.
	 */
	public void stop()
	{
		//  Setting running to false stops all processing for the level.
		running = false;
		
	} 
	
	
	
	/**
	 * Runs the game in its own thread.
	 */
	@Override
	public void run()
	{
		
		//  This method handles the game, calling all the necessary functions to keep things moving on screen.
		while (running)
		{
			// Spawn enemies
			if (1000 * Math.random() > (1000 - spawnRate - 1) || 
					(aliens.size() == 0 && meteors.size() == 0 && spawnCoolDown <= 0)) {
				
				spawnCoolDown = spawnCoolDownConst;
				
				// Roll again for Meteors (.3 chance of spawning one)
				if (Math.random() < 0.3)
					meteors.add(new Meteor((int)(Math.random() * 800.0), 0, 0 ,0 ));
				
				// Aliens
				aliens.add( Alien.spawnAlien( (int)(Math.random() * 800.0), 0, 0 ,0 ) );
				if( aliens.get( aliens.size() - 1 ) instanceof SmallAlien)
				{
					SoundManager.playKamikaze();
				}
			}
			
			
			//  Get system time in preparation for sleeping until the next frame.
			long time = System.currentTimeMillis();
			
			
			//  Draw background and all entities onto the screen.
			this.paintComponent(this.getGraphics());
			
			//  Move all entities across the screen.
			this.moveEntities();
			
			//  Check for hits between tank, planet, and enemies.
			this.processHits();
			
			// Increment spawn rate
			this.spawnRate += .001;
			
			// Decrement the spawn cool down
			this.spawnCoolDown -= 1;
			
			//  Enable a sleep timer.  This forces the game to run at approximately 30 frames per second.
			try
			{
				long delay = Math.max(0, 32 - (System.currentTimeMillis() - time));
				Thread.sleep(delay);
			}
			catch (InterruptedException e) {
			}
				
		}
		
	}
	
	
	/**
	 * This method handles drawing all game entities and the game background onto the screen for one frame at a time.
	 * 
	 * @param g - the Graphics object that handles what is currently seen by the user.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		
		//  Declare variable for buffering image.
		Graphics offg;
		
		
		// Initialize off screen image
		offScreen = createImage(getWidth(), getHeight());
		offg = offScreen.getGraphics();
		
		
		// Reset screen by re-drawing background image in preparation for this frame.
		offg.drawImage(background, 0, 0, this);
		
		
		// Check for win
		if (interval < 0){
			if (!GODMODE) endGame(true);
		}
		
		
		// Draw planet health
		yPos = 350;
		xPos = 20;
		width = 25;
		height = 200;
		tankHealth = tank[0].getHealth();
		
		offg.setColor(Color.GREEN); //good health
		offg.fillRect(xPos, yPos, width, height); //by default draw the entire length of health bar
		offg.setColor(Color.RED); //missing health
		if(planetHealth < 0) {
			//you have died
			offg.fillRect(xPos, yPos, width, height);
			if(!GODMODE) endGame(false);
		}			
		else {
			offg.fillRect(xPos, yPos, width, height - height * planetHealth/maxPlanetHealth);
		}
		
		//  Draw health bar for tank
		yPos = (int) tank[0].getY() + 45;
		xPos = (int) tank[0].getX() - 42;
		width = 100;
		height = 10;
		tankHealth = tank[0].getHealth();
		//System.out.println("X: " + xPos + ", Y: " + yPos + ", tankHealth: " + tank[0].getHealth());
		
		offg.setColor(Color.GREEN); //good health
		offg.fillRect(xPos, yPos, width, height); //by default draw the entire length of health bar
		offg.setColor(Color.RED); //missing health
		if(tankHealth < 0) {
			//you have died
			offg.fillRect(xPos, yPos, width, height);
			if(!GODMODE) endGame(false);
		}			
		else {
			offg.fillRect(xPos + width * tankHealth/maxTankHealth, yPos, width - width * tankHealth/maxTankHealth, height);
		}
		
		
		// Draw meteors
		for (Meteor e : meteors)
		{
			if (null != e)
			{
				offg.drawImage(e.getImage(), (int)(e.getX() - e.getImage().getWidth() / 2),
						                     (int)(e.getY() - e.getImage().getHeight() / 2), null);
			}
		}
		
		
		
		//  "shells" must be synchronized before it is iterated.
		synchronized (shells)
		{
			//  Draw all shells on screen.
			for (TankShell e : shells)
			{
				if (null != e)
				{
					offg.drawImage(e.getImage(), (int)(e.getX() - e.getImage().getWidth() / 2),
		     									 (int)(e.getY() - e.getImage().getHeight() / 2), null);
				}
			}
		}
		
		
		
		
		//  Draw all aliens onto the screen.
		for (Alien e : aliens)
		{
			if (null != e)
			{
				offg.drawImage(e.getImage(), (int)(e.getX() - e.getImage().getWidth() / 2),
										     (int)(e.getY() - e.getImage().getHeight() / 2), null);
			}
		}
		
		
		//  Draw all alien laser shots onto the screen.
		for (AlienLaser e : lasers)
		{
			if (null != e)
			{
				offg.drawImage(e.getImage(), (int)(e.getX() - e.getImage().getWidth() / 2),
						                     (int)(e.getY() - e.getImage().getHeight() / 2), null);
			}
		}
		
		
		//  Get cannon angle and image.
		double num = ((TankCannon)tank[1]).getAngle() / Math.PI * 180.0;
		BufferedImage temp = ((TankCannon)tank[1]).cannon[ (int) num - 90];
		
		
		//  Draw appropriate cannon image to screen.
		offg.drawImage(temp, (int) tank[0].getX() - temp.getWidth() / 2,
							 (int) tank[0].getY() - temp.getHeight() / 2, this);
		
		
		//  Draw tank body.
		offg.drawImage(tank[0].getImage(), (int) tank[0].getX() - tank[0].getImage().getWidth() / 2,
										   (int) tank[0].getY() - tank[0].getImage().getHeight() / 2, this);
		
		
		
		
		//  Draw explosions.
		for( Explosion e : explosions )
		{
			if( e!= null )
			{
				if( e.getFrameNumber() == 0 )
				{
					//  Start playing sound at first frame of each explosion.
					SoundManager.playExplosion();
				}
				if( e.getFrameNumber() < 14 )
				{
					offg.drawImage(e.getFrameImage( e.getFrameNumber() ),
								   e.getX() - e.getFrameImage( e.getFrameNumber() ).getWidth() / 2,
								   e.getY() - e.getFrameImage( e.getFrameNumber() ).getHeight() / 2, this);
					e.increaseFrameNumber();
				}
			}
		}
		//  Remove old explosion objects.
		for( int index = explosions.size() - 1; index >= 0; index-- )
		{
			if( explosions.get( index ).getFrameNumber() >= 14 )
			{
				explosions.remove( index );
			}
		}
		
		
		// Draw game time on screen
		offg.setFont(font);
		offg.setColor(Color.GREEN);
		int tempSec = interval%60;
		offg.drawString(interval/60 + ":" + ((tempSec < 10) ? "0" + tempSec:tempSec), getWidth() - 76, 20);
		
		
		// Handle shaking during meteor impact.
		int xOffset = (int) ((shaking > 0)? ((Math.random() < .5) ? -1:1) * shaking * Math.random():0);
		int yOffset = (int) ((shaking > 0)? ((Math.random() < .5) ? -1:1) * shaking * Math.random():0);
		
		
		
		
		if( aimWithMouse )
		offg.drawImage( mouseMode, this.getWidth() - mouseMode.getWidth() + 5,
								   this.getHeight() - mouseMode.getHeight() + 5, this );
		
		if( aimWithKeyboard )
		offg.drawImage( keyboardMode, this.getWidth() - keyboardMode.getWidth() + 5,
				   this.getHeight() - keyboardMode.getHeight() + 5, this );
		
		
		
		
		
		// Make off screen image visible
		g.drawImage(offScreen, xOffset, yOffset, this);
		
		
	}

	
	/**
	 * Handles moving all entities across the screen.
	 */
	public synchronized void moveEntities()
	{
		// Set tank velocity to appropriate level.
		if (accelLeft)
			tank[0].accelerateLeft();
		if (accelRight)
			tank[0].accelerateRight();
		if (!accelLeft && !accelRight)
			tank[0].lowerTankSpeed();
		
		
		//  Move tank.
		tank[0].move();
		
		
		//  "shells" must be specifically synchronized before it is iterated.
		synchronized (shells)
		{
			//  Move all shells on screen.
			for( TankShell e : shells)
			{
				e.move();
			}
		}
		
		
		//  Obtain current mouse position, then rotate cannon.
		if( aimWithMouse == true )
		{
			double mouseX = MouseInfo.getPointerInfo().getLocation().getX() - GUI.window.getX();
			double mouseY = MouseInfo.getPointerInfo().getLocation().getY() - GUI.window.getY() - 25;
			((TankCannon)tank[1]).updateAngleMouse( (int) mouseX, (int) mouseY, (int) tank[0].getX(), (int) tank[0].getY() );
		}
		if( aimWithKeyboard == true )
		{
			if( rotateLeft )
				((TankCannon)tank[1]).updateAngleKeyboard( 1 );
			if( rotateRight )
				((TankCannon)tank[1]).updateAngleKeyboard( 0 );
		}
		
		
		
		
		//  Meteors across the screen.
		for (Meteor e : meteors) {
			if (e != null)
			{
				e.move();
			}
		}
		
		//  Update shaking value if necessary.
		if (shaking > 0)
		{
			shaking--;
		}
		
		
		//  Move Aliens.
		for ( Alien e : aliens )
		{
			//  If LargeAlien, update angle of inclination from alien to tank to aim lasers.
			if ( e != null && e instanceof LargeAlien)
			{
				((LargeAlien)e).updateAngleToTank( (int)tank[0].getX(), (int)tank[0].getY() );
				((LargeAlien)e).autoAccelerate();
				e.move();
				Boolean hasFired = ((LargeAlien)e).fireLasers( lasers, (int)tank[0].getX(), (int)tank[0].getY() );
				if( hasFired )
				{
					SoundManager.playLaser();
				}
			}
			//  If SmallAlien, update acceleration towards tank.
			if (e != null && e instanceof SmallAlien)
			{
				((SmallAlien)e).autoAccelerate((int)tank[0].getX(), (int)tank[0].getY() );
				e.move();
			}
		}
		
		
		//  "shells" must be synchronized before it is iterated.
		synchronized (shells)
		{
			//  Move all shells across the screen.
			for( TankShell e : shells)
			{
				if( e != null )
				{
					//  Shell has moved for one frame.
					e.decreaseLife();
					e.move();
				}
			}
		}
		
		//  Move all lasers across the screen.
		for ( AlienLaser e : lasers )
		{
			if( e!= null )
			{
				//  Laser has moved for one frame.
				e.decreaseLife();
				e.move();
			}
		}
		
		
	}  //  End of moveEntities() method.
	
	
	
	/**
	 * Process all collisions that occur during the game.  This includes strikes against all entities and
	 * the planet as well.
	 */
	public void processHits()
	{
		//  This for loop handles collisions between the tank shells and all aliens and meteors
		for( int index = shells.size() - 1; index >= 0; index-- )
		{
			//  Check for hit on any of the aliens that are alive.
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
			//  Check for hit on any of the meteors that are still up in the air.
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
		
		
		//  The next three for loops check for hits between small aliens, meteors, and lasers against the user tank.
		//  This for loop checks for hits between small aliens and the tank.
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
		//  This for loop checks for hits between meteors and the tank.
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
		//  This for loop checks for hits between lasers and the tank.
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
		
		
		//  This for loop checks if meteors have landed on the planet.
		for( int index = meteors.size() - 1; index >= 0; index-- )
		{
			if( meteors.get( index ).getY() >= this.getHeight() - meteors.get( index ).getImage().getHeight() * 0.4)
			{
				planetHealth -= 3;
				meteors.get( index ).setHealth( 0 );
			}
		}
		
		
		//  Lastly, handle deletion of dead aliens, destroyed meteors, dead tank, and old shells/lasers
		//  This for loop handles deletion of dead aliens.
		for( int index = aliens.size() - 1; index >= 0; index-- )
		{
			if( aliens.get( index ).getHealth() <= 0 || aliens.get( index ).getY() >= this.getHeight() + 5 )
			{
				explosions.add( new Explosion( aliens.get( index ).getX(), aliens.get( index ).getY() ) );
				aliens.remove( index );
			}
		}
		//  This for loop handles deletion of destroyed meteors.
		for( int index = meteors.size() - 1; index >= 0; index-- )
		{
			if( meteors.get( index ).getHealth() <= 0 || meteors.get( index ).getY() >= this.getHeight() + 100 )
			{
				explosions.add( new Explosion( meteors.get( index ).getX(), meteors.get( index ).getY() ) );
				meteors.remove( index );
				shaking += 20;
			}
		}
		//  This method handles deletion of old/used shells.
		for( int index = shells.size() - 1; index >= 0; index-- )
		{
			if( shells.get( index ).getLife() <= 0 || shells.get( index ).getHealth() <= 0 )
			{
				shells.remove( index );
			}
		}
		//  This method handles deletion of old/used lasers.
		for( int index = lasers.size() - 1; index >= 0; index-- )
		{
			if( lasers.get( index ).getLife() <= 0 || lasers.get( index ).getHealth() <= 0 )
			{
				lasers.remove( index );
			}
		}
		
		
	}  //  End of processHits() method.
	
	
	/**
	 * Stops game running thread.  Then displays the corresponding screen for lose or win.
	 * @param i - true for win, false for lose
	 */
	private void endGame(boolean i) {
		stop();
		timer.cancel();
		CardLayout cl = (CardLayout) container.getLayout();
		if(i)
			cl.show(container, "win");
		else
			cl.show(container, "lose");
	}
	
	
	/**
	 * Handles when the user presses down a key on the keyboard.
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		
		//  Obtain info for which key was pressed.
		int key = e.getKeyCode();
		
		//  Toggle aiming system.
		if (key == KeyEvent.VK_T)
		{
			if( aimWithMouse == true )
			{
				aimWithMouse = false;
				aimWithKeyboard = true;
			}
			else
			{
				aimWithMouse = true;
				aimWithKeyboard = false;
			}
		}
		
		
		//  If user pressed escape key, pause the game and switch to the pause screen.
		if (key == KeyEvent.VK_ESCAPE) {
			stop();
			timer.cancel();
		
			for( Component comp : container.getComponents() )
			{
				//  If the component is of instance type "Game", call the runGame() method on this component.
				if( comp instanceof Pause )
				{
					((Pause)comp).drawImage(offScreen);
					break;
				}
			}
			CardLayout cl = (CardLayout) container.getLayout();
			cl.show(container, "pause");
		}
		
		
		//  If user pressed "A", set for moving tank to the left.
		if (key == KeyEvent.VK_A)
		{
			accelLeft = true;
		}
		
		
		//  If user pressed "D", set for moving tank to the right.
		if (key == KeyEvent.VK_D)
		{
			accelRight = true;
		}
		
		
		
		
		if (aimWithKeyboard == true && key == KeyEvent.VK_LEFT)
		{
			rotateLeft = true;
		}
		
		if (aimWithKeyboard == true && key == KeyEvent.VK_RIGHT)
		{
			rotateRight = true;
		}
		
		
		
		
		//  If user pressed space bar, create a new tank shell and prevent key from automatically repeating.
		if ( aimWithKeyboard == true && (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP) && !needCooldown )
		{
			shells.add( new TankShell(tank[0].getX(), tank[0].getY(), 0, 0, ((TankCannon)tank[1]).getAngle() ) );
			needCooldown = true;
			SoundManager.playTankFire();
			
		}
		
		
	}  //  End of keyPressed() method.
	
	
	
	/**
	 * Handles when the user releases a key on the keyboard.
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
		//  Obtain info for which key was released.
		int key = e.getKeyCode();
		
		
		//  If user released "A", set for stopping tank acceleration to the left.
		if (key == KeyEvent.VK_A)
		{
			accelLeft = false;
		}
		
		
		//  If user released "D", set for stopping tank acceleration to the right.
		if (key == KeyEvent.VK_D)
		{
			accelRight = false;
		}
		
		if (aimWithKeyboard == true && key == KeyEvent.VK_LEFT)
		{
			rotateLeft = false;
		}
		
		if (aimWithKeyboard == true && key == KeyEvent.VK_RIGHT)
		{
			rotateRight = false;
		}
		
		//  If user released space bar, allow user to fire another shell when space bar is pressed again.
		if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP)
		{
			needCooldown = false;
		}
		
		
	}
	
	
	
	/**
	 * Handles when a key is pressed and released.  Method is here as KeyListener requires this method, but it
	 * is empty as it is not necessary for the game to function.
	 */
	@Override
	public void keyTyped(KeyEvent e)
	{}	
	
	
	
	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if( aimWithMouse == true && arg0.getButton() == MouseEvent.BUTTON1 )
		{
			shells.add( new TankShell(tank[0].getX(), tank[0].getY(), 0, 0, ((TankCannon)tank[1]).getAngle() ) );
			SoundManager.playTankFire();
		}
		
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	
	@Override
	public void mouseExited(MouseEvent arg0) {}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
