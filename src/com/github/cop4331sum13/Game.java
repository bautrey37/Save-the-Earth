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
 * This is the main class that runs the in-game level.  All necessary procedures are handled by this class only.
 */
public class Game extends JComponent implements KeyListener, Runnable
{
	/**
	 * This value is necessary for managing separate threads properly. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Contains the entire gui that is used by the program.
	 */
	private JPanel container;
	
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
	
	/**
	 * Used to manage the health of the planet that the user is defending and is declared as static as there is only
	 * one planet to defend during game play.
	 */
	public static int planetHealth;
	
	/**
	 * Used to manage movement of the tank based on user input.
	 */
	private boolean accelLeft, accelRight;
	
	/**
	 * Controls whether or not the game screen shakes.
	 */
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
		levelInfo = new int[10];
		
		//  Enables keyboard input.
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
		
		//  Set the planet health based on difficulty level.
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
		
		
		// Create user's tank and cannon
		tank = new Tank[2];
		tank[0] = new TankBody(getWidth() * 0.5, getHeight() * 0.9, 0, 0);
		tank[0].setTankSpeedLimit(10);
		tank[1] = new TankCannon( getWidth() * 0.5, getHeight() * 0.9, 0, 0);
		
		//  Initialize all lists for managing on-screen entities.
		shells = new Vector<TankShell>();
		aliens = new Vector<Alien>();
		meteors = new Vector<Meteor>();
		lasers = new Vector<AlienLaser>();
		
		start();
		
		
	}  //  End of init() method.
	
	
	
	/**
	 * Starts the thread for running the game.  Keeps existing game data (for when resuming from "pause").
	 */
	public void start()
	{
		//  Set/reset values for starting new game and resuming from "pause".
		accelLeft = false;
		accelRight = false;
		running = true;
		needCooldown = false;
		
		this.requestFocusInWindow();
		
		//  Create a new thread to enable concurrent running of multiple methods.
		game = new Thread(this);
		game.setPriority(Thread.MAX_PRIORITY);
		game.start();
		
		
	}  //  End of start() method.
	
	
	
	/**
	 * Stops the game from running, essentially ending the thread.
	 */
	public void stop()
	{
		//  Setting running to false stops all processing for the level.
		running = false;
		
	}  //  End of stop() method.
	
	
	
	/**
	 * Runs the game in its own thread.
	 */
	@Override
	public void run()
	{
		//  Used to determine which type of enemy to spawn into the game next.
		double chance = 0;
		
		//  This method handles the game, calling all the necessary functions to keep things moving on screen.
		while (running)
		{
			// Make an enemy
			if ((chance = 1000 * Math.random()) > 980) {
				// Meteors
				if (chance > 990)
					meteors.add(new Meteor((int)(Math.random() * 800.0), 0, 0 ,0 ));
				
				// Aliens
				aliens.add( Alien.spawnAlien( (int)(Math.random() * 800.0), 0, 0 ,0 ) );
			}
			
			
			//  Get system time in preparation for sleeping until the next frame.
			long time = System.currentTimeMillis();
			
			
			//  Draw background and all entities onto the screen.
			this.paintComponent(this.getGraphics());
			
			//  Move all entities across the screen.
			this.moveEntities();
			
			//  Check for hits between tank, planet, and enemies.
			this.processHits();
			
			//  Enable a sleep timer.  This forces the game to run at approximately 30 frames per second.
			try
			{
				long delay = Math.max(0, 32 - (System.currentTimeMillis() - time));
				Thread.sleep(delay);
			}
			catch (InterruptedException e) {
			}
			
			
		}  //  End of while loop.
		
		
	}  //  End of run() method.
	
	
	
	/**
	 * This method handles drawing all game entities and the game background onto the screen for one frame at a time.
	 * 
	 * @param g - the Graphics object that handles what is currently seen by the user.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		//  Call super class method.
		super.paintComponent(g);
		
		
		//  Declare variable for buffering image.
		Graphics offg;
		Image offScreen = null;
		
		
		// Initialize off screen image
		offScreen = createImage(getWidth(), getHeight());
		offg = offScreen.getGraphics();
		
		
		// Reset screen by re-drawing background image in preparation for this frame.
		offg.drawImage(background, 0, 0, this);
		
		
		// Draw meteors
		for (Meteor e : meteors)
		{
			if (null != e)
			{
				offg.drawImage(e.getImage(), (int)(e.getX() - e.getImage().getWidth() / 2),
						                     (int)(e.getY() - e.getImage().getHeight() / 2), null);
			}
		}

		
		//  Draw all shells on screen.
		for (TankShell e : shells)
		{
			if (null != e)
			{
				offg.drawImage(e.getImage(), (int)(e.getX() - e.getImage().getWidth() / 2),
						                     (int)(e.getY() - e.getImage().getHeight() / 2), null);
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
		
		// Handle shaking during meteor impact.
		int xOffset = (int) ((shaking > 0)? ((Math.random() < .5) ? -1:1) * shaking * Math.random():0);
		int yOffset = (int) ((shaking > 0)? ((Math.random() < .5) ? -1:1) * shaking * Math.random():0);
		
		
		// Make off screen image visible
		g.drawImage(offScreen, xOffset, yOffset, this);
		
		
	}  //  End of paintComponent() method.
	
	
	
	/**
	 * Handles moving all entities across the screen.
	 */
	public synchronized void moveEntities()
	{
		// Set tank velocity to appropriate leve.
		if (accelLeft)
			tank[0].accelerateLeft();
		if (accelRight)
			tank[0].accelerateRight();
		if (!accelLeft && !accelRight)
			tank[0].lowerTankSpeed();
		
		
		//  Move tank.
		tank[0].move();
		
		
		//  Move all shells on screen.
		for( TankShell e : shells)
		{
			e.move();
		}
		
		
		//  Obtain current mouse position, then rotate cannon.
		double mouseX = MouseInfo.getPointerInfo().getLocation().getX() - GUI.window.getX();
		double mouseY = MouseInfo.getPointerInfo().getLocation().getY() - GUI.window.getY();
		((TankCannon)tank[1]).updateAngleMouse( (int) mouseX, (int) mouseY, (int) tank[0].getX(), (int) tank[0].getY() );
		
		
		//  Meteors across the screen.
		for (Meteor e : meteors) {
			if (e != null){
				shaking += e.autoAccelerate();
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
				((LargeAlien)e).fireLasers( lasers );
			}
			//  If SmallAlien, update acceleration towards tank.
			if (e != null && e instanceof SmallAlien)
			{
				((SmallAlien)e).autoAccelerate((int)tank[0].getX(), (int)tank[0].getY() );
				e.move();
			}
		}
		
		
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
			if( meteors.get( index ).getY() >= this.getHeight() )
			{
				Game.planetHealth -= 3;
				meteors.get( index ).setHealth( 0 );
			}
		}
		
		
		//  Lastly, handle deletion of dead aliens, destroyed meteors, dead tank, and old shells/lasers
		//  This for loop handles deletion of dead aliens.
		for( int index = aliens.size() - 1; index >= 0; index-- )
		{
			if( aliens.get( index ).getHealth() <= 0 || aliens.get( index ).getY() >= this.getHeight() + 50 )
			{
				aliens.remove( index );
			}
		}
		//  This for loop handles deletion of destroyed meteors.
		for( int index = meteors.size() - 1; index >= 0; index-- )
		{
			if( meteors.get( index ).getHealth() <= 0 || meteors.get( index ).getY() >= this.getHeight() + 100 )
			{
				meteors.remove( index );
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
	 * Handles when the user presses down a key on the keyboard.
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		//  Obtain info for which key was pressed.
		int key = e.getKeyCode();
		
		
		//  If user pressed escape key, pause the game and switch to the pause screen.
		if (key == KeyEvent.VK_ESCAPE) {
			stop();
			CardLayout cl = (CardLayout) container.getLayout();
			cl.show(container, "pause");
		}
		
		
		//  If user pressed "A" or left arrow, set for moving tank to the left.
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
		{
			accelLeft = true;
		}
		
		
		//  If user pressed "D" or right arrow, set for moving tank to the right.
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
		{
			accelRight = true;
		}
		
		
		//  If user pressed space bar, create a new tank shell and prevent key from automatically repeating.
		if (key == KeyEvent.VK_SPACE && !needCooldown)
		{
			shells.add( new TankShell(tank[0].getX(), tank[0].getY(), 0, 0, ((TankCannon)tank[1]).getAngle() ) );
			needCooldown = true;
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
		
		
		//  If user released "A" or left arrow, set for stopping tank acceleration to the left.
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
		{
			accelLeft = false;
		}
		
		
		//  If user released "D" or right arrow, set for stopping tank acceleration to the right.
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
		{
			accelRight = false;
		}
		
		
		//  If user released space bar, allow user to fire another shell when space bar is pressed again.
		if (key == KeyEvent.VK_SPACE)
		{
			needCooldown = false;
		}
		
		
	}  //  End of keyReleased() method.
	
	
	
	/**
	 * Handles when a key is pressed and released.  Method is here as KeyListener requires this method, but it
	 * is empty as it is not necessary for the game to function.
	 */
	@Override
	public void keyTyped(KeyEvent e)
	{
		//  Place holder method.
		
		
	}  //  End of keyTyped() method.
	
	
	
}  //  End of Game class.
