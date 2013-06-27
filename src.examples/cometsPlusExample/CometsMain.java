package cometsPlusExample;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;

// This class is primarily responsible for organizing the game of Comets
public class CometsMain implements KeyListener
{
	public boolean godmode = true;  // false for being able to get blown up on impact with a comet; true to be invincible!
	
	
	
	//  The following 4 booleans affect only the shots fired
	public boolean bulletMania = false;  // false for single press, single shot; true for when frustrated with all of the comets
	public boolean autoFire = false;
	public int shotAge = 250;  // shot age for bulletMania
	public int shotDensity = 1;  // shot strength for bulletMania
	
	
	//  If false, enemy per wave count is always equal to maxComets.
	//  If true,  enemy per wave count increments with each wave.
	public boolean cometsON = false;
	
	
	int maxComets = 3;  // Starting amount of comets
	
	
	// Caution!!!  Following option WILL make movement VERY hard to control!
	public boolean advancedMovement = false;  // false for simple movement; true for space environment movement (but still with capped speeds and turn rates)
	
	
	
	
	public boolean increaseOmega = false;
	public boolean decreaseOmega = false;
	
	
	
	// GUI Data
	private JFrame frame; // The window itself
	private Canvas playArea;  // The area where the game takes place
	
	private final int playWidth = 800; // The width of the play area (in pixels)
	private final int playHeight = 500; // The height of the play area (in pixels)
	
	// Game Data
	private Ship ship; // The ship in play
	private Vector<Shot> shots; // The shots fired by the player
	private Vector<Comet> comets; // The comets floating around
	
	private boolean shipDead; // Whether or not the ship has been blown up
	private long shipTimeOfDeath; // The time at which the ship blew up
	
	// Keyboard data
	// Indicates whether the player is currently holding the accelerate, decelerate, turn
	// left, or turn right buttons, respectively
	private boolean accelerateHeld = false;
	private boolean decelerateHeld = false;
	//private boolean turnLeftHeld = false;
	//private boolean turnRightHeld = false;
	
	// Indicates whether the player struck the fire key
	private boolean firing = false;
	private int shotReady = -1;
	
	// Used for determining whether or not to display thruster graphic
	private int displayThrust = 0;
	
	// Set up the game and play!
	public CometsMain()
	{
		// Get everything set up
		configureGUI();
		configureGameData();
		
		// Display the window so play can begin
		frame.setVisible(true);
		
		// Start the gameplay
		playGame();
	}
	
	// Set up the initial positions of all space objects
	private void configureGameData()
	{
		// Configure the play area size
		SpaceObject.playfieldWidth = playWidth;
		SpaceObject.playfieldHeight = playHeight;
		
		// Create the ship
		ship = new Ship(playWidth/2, playHeight/2, 0, 0);
		
		// Create the shot vector (initially, there shouldn't be any shots on the screen)
		shots = new Vector<Shot>();
		
		// Create a set of initial comets with random spawn points and speeds
		// Will not spawn comets on top of ship
		comets = new Vector<Comet>();
		
		for( int index = 0; index < maxComets; index++ )
		{
			double xpos = 0.0;
			double ypos = 0.0;
			double xvel = 0.0;
			double yvel = 0.0;
			
			double d = 50;
			double speed = 0;
			while( d < 100 || speed > 1.0 || speed < .5)
			{
				xpos = Math.random() * playWidth;
				ypos = Math.random() * playHeight;
				d = Math.sqrt( (xpos - ship.getXPosition()) * (xpos - ship.getXPosition()) + 
						       (ypos - ship.getYPosition()) * (ypos - ship.getYPosition()) );
				
				xvel = Math.random() * 2.0 - 1.0;
				yvel = Math.random() * 2.0 - 1.0;
				speed = Math.sqrt( xvel*xvel + yvel*yvel );
			}
			
			comets.add(new LargeComet(xpos, ypos, xvel, yvel));
		}
	}
	
	// Set up the game window
	private void configureGUI()
	{
		// Create the window object
		frame = new JFrame("Comets");
		frame.setSize(playWidth+20, playHeight+35);
		frame.setResizable(false);
		
		// The program should end when the window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set the window's layout manager
		frame.setLayout(new FlowLayout());
		
		// Create the play area
		playArea = new Canvas();
		playArea.setSize(playWidth, playHeight);
		playArea.setBackground(Color.BLACK);
		playArea.setFocusable(false);
		frame.add(playArea);
		
		// Make the frame listen to keystrokes
		frame.addKeyListener(this);
	}
	
	// The main game loop. This method coordinates everything that happens in
	// the game
	private void playGame()
	{
		while(true)
		{
			// Measure the current time in an effort to keep up a consistent
			// frame rate
			long time = System.currentTimeMillis();
			
			// If the ship has been dead for more than 3 seconds, revive it
			if(shipDead && shipTimeOfDeath + 3000 < time)
			{
				shipDead = false;
				ship = new Ship(playWidth/2, playHeight/2, 0, 0);
			}
			
			// Process game events, move all the objects floating around,
			// and update the display
			if(!shipDead)
				handleKeyEntries();
			handleCollisions();
			moveSpaceObjects();
			
			// Sleep until it's time to draw the next frame 
			// (i.e. 32 ms after this frame started processing)
			try
			{
				long delay = Math.max(0, 32-(System.currentTimeMillis()-time));
				
				Thread.sleep(delay);
			}
			catch(InterruptedException e)
			{
			}
			
		}
	}

	// Deal with objects hitting each other
	private void handleCollisions()
	{
		// Anything that is destroyed should be erased, so get ready
		// to erase stuff
		Graphics g = playArea.getGraphics();
		g.setColor(Color.BLACK);
		
		// Deal with shots blowing up comets
		for(int i = 0; i < shots.size(); i++)
		{
			Shot s = shots.elementAt(i);
			for(int j = 0; j < comets.size(); j++)
			{
				Comet c = comets.elementAt(j);
				
				// If a shot has hit a comet, destroy both the shot and comet
				if(s.overlapping(c))
				{
					// Erase the bullet
					shots.remove(i);
					i--;
					this.drawSpaceObject(g, s);
					
					// If the comet was actually destroyed, replace the comet
					// with the new comets it spawned (if any)
					Vector<Comet> newComets = c.explode();
					if(newComets != null)
					{
						this.drawSpaceObject(g, c);
						comets.remove(j);
						j--;
						comets.addAll(newComets);		
					}
					break;
				}
			}
		}
		
		// Deal with comets blowing up the ship
		if(!shipDead)
		{
			for(Comet c : comets)
			{
				// If the ship hit a comet, kill the ship and mark down the time 
				if(c.overlapping(ship) && !godmode)
				{
					shipTimeOfDeath = System.currentTimeMillis();
					shipDead = true;
					drawShip(g, ship);
				}
			}
		}
		
		if(comets.size() == 0)
		{if(cometsON)maxComets+=2;
			for( int index = 0; index < maxComets; index++ )
			{
				double xpos = 0.0;
				double ypos = 0.0;
				double xvel = 0.0;
				double yvel = 0.0;
				
				double d = 50;
				double speed = 0;
				while( d < 100 || speed > 1.5 || speed < .5)
				{
					xpos = Math.random() * playWidth;
					ypos = Math.random() * playHeight;
					d = Math.sqrt( (xpos - ship.getXPosition()) * (xpos - ship.getXPosition()) + 
							       (ypos - ship.getYPosition()) * (ypos - ship.getYPosition()) );
					
					xvel = Math.random() * 2.0 - 1.0;
					yvel = Math.random() * 2.0 - 1.0;
					speed = Math.sqrt( xvel*xvel + yvel*yvel );
				}
				
				comets.add(new LargeComet(xpos, ypos, xvel, yvel));
			}
		}
	}
	
	// Check which keys have been pressed and respond accordingly
	private void handleKeyEntries()
	{
		// Ship movement keys
		if(accelerateHeld)
			ship.accelerate();
		if(decelerateHeld)
		{
			ship.decelerate();
		}
		
		// Shooting the cannon
		if(!bulletMania && (firing && shotReady == 0 && shots.size() < 10) )
		{
			firing = false;
			shots.add(ship.fire( 11 ));
		}
		if(bulletMania)
		{
			if( firing  )
			{for(int index = 0; index < shotDensity; index++ )
				{shots.add(ship.fire( 5 ));
				shots.add(ship.fire( 10 ));
				shots.add(ship.fire( 15 ));
				shots.add(ship.fire( 20 ));
				shots.add(ship.fire( 25 ));
				shots.add(ship.fire( 30 ));
				}if(!autoFire)firing = false;
			}
		}
	}
	
	// Deal with moving all the objects that are floating around
	private void moveSpaceObjects()
	{
		Graphics g = playArea.getGraphics();
		
		// Handle the movements of all objects in the field
		if(!shipDead)
			updateShip(g);
		updateShots(g);
		updateComets(g);		
	}
	
	// Move all comets and draw them to the screen
	private void updateComets(Graphics g)
	{
		for(Comet c : comets)
		{
			// Erase the comet at its old position
			g.setColor(Color.BLACK);
			drawSpaceObject(g, c);
			
			// Move the comet to its new position
			c.move();
			
			// Draw it at its new position
			g.setColor(Color.CYAN);
			drawSpaceObject(g, c);
			
		}
	}
	
	// Move all shots and draw them to the screen
	private void updateShots(Graphics g)
	{
		
		for(int i = 0; i < shots.size(); i++)
		{
			Shot s = shots.elementAt(i);
			
			// Erase the shot at its old position
			g.setColor(Color.BLACK);
			drawSpaceObject(g, s);
			
			// Move the shot to its new position
			s.move();
			
			// Remove the shot if it's too old
			if(!bulletMania && s.getAge() > 50 || bulletMania && s.getAge() > shotAge)
			{
				shots.remove(i);
				i--;
			}
			// Otherwise, draw it at its new position
			else
			{
				g.setColor(Color.RED);
				drawSpaceObject(g, s);
			}		
		}
	}
	
	// Draws the space object s to the the specified graphics context
	private void drawSpaceObject(Graphics g, SpaceObject s)
	{
		// Figure out where the object should be drawn
		int radius = (int)s.getRadius();
		int xCenter = (int)s.getXPosition();
		int yCenter = (int)s.getYPosition();
		
		// Draw the object
		g.drawOval(xCenter - radius, yCenter - radius, radius*2, radius*2);
	}
	
	// Moves the ship and draws it at its new position
	private void updateShip(Graphics g)
	{
		// Erase the ship at its old position
		g.setColor(Color.BLACK);
		drawShip(g, ship);

		// Ship rotation must be handled between erasing the ship at its old position
		// and drawing it at its new position so that artifacts aren't left on the screen
		if(increaseOmega && !advancedMovement) ship.rotateLeft();
		if(decreaseOmega && !advancedMovement) ship.rotateRight();
		
		
		if(increaseOmega && advancedMovement) ship.increaseOmega();
		if(decreaseOmega && advancedMovement) ship.decreaseOmega();
		
		ship.move();
		
		// Draw the ship at its new position
		g.setColor(Color.WHITE);
		drawShip(g, ship);
	}
	
	// Draws this ship s to the specified graphics context
	private void drawShip(Graphics g, Ship s)
	{
		// Figure out where the ship should be drawn
		int radius = (int)s.getRadius();
		int xCenter = (int)s.getXPosition();
		int yCenter = (int)s.getYPosition();
		
		
		// Draw the ship body, but as a triangle
		int forwardPointX = (int)(radius * 1.0 * Math.sin(s.getAngle()));
		int forwardPointY = (int)(radius * 1.0 * Math.cos(s.getAngle()));
		int backLeftX = (int)(radius * 1.3 * Math.sin(s.getAngle()-.7));
		int backLeftY = (int)(radius * 1.3 * Math.cos(s.getAngle()-.7));
		int backRightX = (int)(radius * 1.3 * Math.sin(s.getAngle()+.7));
		int backRightY = (int)(radius * 1.3 * Math.cos(s.getAngle()+.7));
		g.drawLine(xCenter - backLeftX, yCenter - backLeftY, xCenter - backRightX, yCenter - backRightY);
		g.drawLine(xCenter - backLeftX, yCenter - backLeftY, xCenter + forwardPointX, yCenter + forwardPointY);
		g.drawLine(xCenter - backRightX, yCenter - backRightY, xCenter + forwardPointX, yCenter + forwardPointY);
		
		
		if(accelerateHeld && displayThrust <= 1 || g.getColor() == Color.black )
		{
			if(g.getColor() != Color.black)
				g.setColor(Color.RED);
			
			backLeftX = (int)(radius * 1.5 * Math.sin(s.getAngle()-.22));
			backLeftY = (int)(radius * 1.5 * Math.cos(s.getAngle()-.22));
			backRightX = (int)(radius * 1.5 * Math.sin(s.getAngle()+.22));
			backRightY = (int)(radius * 1.5 * Math.cos(s.getAngle()+.22));
			forwardPointX = (int)(radius * 2.5 * Math.sin(s.getAngle()));
			forwardPointY = (int)(radius * 2.5 * Math.cos(s.getAngle()));
			g.drawLine(xCenter - backLeftX, yCenter - backLeftY, xCenter - backRightX, yCenter - backRightY);
			g.drawLine(xCenter - backLeftX, yCenter - backLeftY, xCenter - forwardPointX, yCenter - forwardPointY);
			g.drawLine(xCenter - backRightX, yCenter - backRightY, xCenter - forwardPointX, yCenter - forwardPointY);
			
			
		}
		if(increaseOmega && displayThrust <= 1 || g.getColor() == Color.black )
		{
			if(g.getColor() != Color.black)
				g.setColor(Color.RED);
			
			backLeftX = (int)(radius * 1.2 * Math.sin(s.getAngle()-.24));
			backLeftY = (int)(radius * 1.2 * Math.cos(s.getAngle()-.24));
			backRightX = (int)(radius * 1.3 * Math.sin(s.getAngle()-.14));
			backRightY = (int)(radius * 1.3 * Math.cos(s.getAngle()-.14));
			forwardPointX = (int)(radius * 1.7 * Math.sin(s.getAngle()-.67));
			forwardPointY = (int)(radius * 1.7 * Math.cos(s.getAngle()-.67));
			g.drawLine(xCenter + backLeftX, yCenter + backLeftY, xCenter + backRightX, yCenter + backRightY);
			g.drawLine(xCenter + backLeftX, yCenter + backLeftY, xCenter + forwardPointX, yCenter + forwardPointY);
			g.drawLine(xCenter + backRightX, yCenter + backRightY, xCenter + forwardPointX, yCenter + forwardPointY);
			backLeftX = (int)(radius * 1.2 * Math.sin(s.getAngle()-.29+Math.PI*0.9));
			backLeftY = (int)(radius * 1.2 * Math.cos(s.getAngle()-.29+Math.PI*0.9));
			backRightX = (int)(radius * 1.3 * Math.sin(s.getAngle()-.19+Math.PI*0.9));
			backRightY = (int)(radius * 1.3 * Math.cos(s.getAngle()-.19+Math.PI*0.9));
			forwardPointX = (int)(radius * 1.7 * Math.sin(s.getAngle()-.52+Math.PI*0.9));
			forwardPointY = (int)(radius * 1.7 * Math.cos(s.getAngle()-.52+Math.PI*0.9));
			g.drawLine(xCenter + backLeftX, yCenter + backLeftY, xCenter + backRightX, yCenter + backRightY);
			g.drawLine(xCenter + backLeftX, yCenter + backLeftY, xCenter + forwardPointX, yCenter + forwardPointY);
			g.drawLine(xCenter + backRightX, yCenter + backRightY, xCenter + forwardPointX, yCenter + forwardPointY);
			
			
		}
		if(decreaseOmega && displayThrust <= 1 || g.getColor() == Color.black )
		{
			if(g.getColor() != Color.black)
				g.setColor(Color.RED);
			
			backLeftX = (int)(radius * 1.2 * Math.sin(s.getAngle()+.24));
			backLeftY = (int)(radius * 1.2 * Math.cos(s.getAngle()+.24));
			backRightX = (int)(radius * 1.3 * Math.sin(s.getAngle()+.14));
			backRightY = (int)(radius * 1.3 * Math.cos(s.getAngle()+.14));
			forwardPointX = (int)(radius * 1.7 * Math.sin(s.getAngle()+.67));
			forwardPointY = (int)(radius * 1.7 * Math.cos(s.getAngle()+.67));
			g.drawLine(xCenter + backLeftX, yCenter + backLeftY, xCenter + backRightX, yCenter + backRightY);
			g.drawLine(xCenter + backLeftX, yCenter + backLeftY, xCenter + forwardPointX, yCenter + forwardPointY);
			g.drawLine(xCenter + backRightX, yCenter + backRightY, xCenter + forwardPointX, yCenter + forwardPointY);
			backLeftX = (int)(radius * 1.2 * Math.sin(s.getAngle()+.29-Math.PI*0.9));
			backLeftY = (int)(radius * 1.2 * Math.cos(s.getAngle()+.29-Math.PI*0.9));
			backRightX = (int)(radius * 1.3 * Math.sin(s.getAngle()+.19-Math.PI*0.9));
			backRightY = (int)(radius * 1.3 * Math.cos(s.getAngle()+.19-Math.PI*0.9));
			forwardPointX = (int)(radius * 1.7 * Math.sin(s.getAngle()+.52-Math.PI*0.9));
			forwardPointY = (int)(radius * 1.7 * Math.cos(s.getAngle()+.52-Math.PI*0.9));
			g.drawLine(xCenter + backLeftX, yCenter + backLeftY, xCenter + backRightX, yCenter + backRightY);
			g.drawLine(xCenter + backLeftX, yCenter + backLeftY, xCenter + forwardPointX, yCenter + forwardPointY);
			g.drawLine(xCenter + backRightX, yCenter + backRightY, xCenter + forwardPointX, yCenter + forwardPointY);
			
			
		}
		if(decelerateHeld && displayThrust <=1 || g.getColor() == Color.black){
			if(g.getColor() != Color.black)
				g.setColor(Color.RED);
			
			backLeftX = (int)(radius * 1.2 * Math.sin(s.getAngle()-.24));
			backLeftY = (int)(radius * 1.2 * Math.cos(s.getAngle()-.24));
			backRightX = (int)(radius * 1.3 * Math.sin(s.getAngle()-.14));
			backRightY = (int)(radius * 1.3 * Math.cos(s.getAngle()-.14));
			forwardPointX = (int)(radius * 1.7 * Math.sin(s.getAngle()-.17));
			forwardPointY = (int)(radius * 1.7 * Math.cos(s.getAngle()-.17));
			g.drawLine(xCenter + backLeftX, yCenter + backLeftY, xCenter + backRightX, yCenter + backRightY);
			g.drawLine(xCenter + backLeftX, yCenter + backLeftY, xCenter + forwardPointX, yCenter + forwardPointY);
			g.drawLine(xCenter + backRightX, yCenter + backRightY, xCenter + forwardPointX, yCenter + forwardPointY);
			
			backLeftX = (int)(radius * 1.2 * Math.sin(s.getAngle()+.24));
			backLeftY = (int)(radius * 1.2 * Math.cos(s.getAngle()+.24));
			backRightX = (int)(radius * 1.3 * Math.sin(s.getAngle()+.14));
			backRightY = (int)(radius * 1.3 * Math.cos(s.getAngle()+.14));
			forwardPointX = (int)(radius * 1.7 * Math.sin(s.getAngle()+.17));
			forwardPointY = (int)(radius * 1.7 * Math.cos(s.getAngle()+.17));
			g.drawLine(xCenter + backLeftX, yCenter + backLeftY, xCenter + backRightX, yCenter + backRightY);
			g.drawLine(xCenter + backLeftX, yCenter + backLeftY, xCenter + forwardPointX, yCenter + forwardPointY);
			g.drawLine(xCenter + backRightX, yCenter + backRightY, xCenter + forwardPointX, yCenter + forwardPointY);
		}int x = (int)(Math.random() + 2);
		if(displayThrust > x)
		{
			displayThrust = 0;
		}
		displayThrust++;
	}
		
	// Deals with keyboard keys being pressed
	public void keyPressed(KeyEvent key)
	{
		// Mark down which important keys have been pressed
		if(key.getKeyCode() == KeyEvent.VK_UP)
			this.accelerateHeld = true;
		if(key.getKeyCode() == KeyEvent.VK_LEFT)
		{
			//this.turnLeftHeld = true;
			this.increaseOmega = true;
		}
			
		if(key.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			//this.turnRightHeld = true;
			this.decreaseOmega = true;
		}
			
		if(key.getKeyCode() == KeyEvent.VK_SPACE)
		{
			this.firing = true;
			
			if(this.shotReady <= 1)
				this.shotReady++;
		}
			
		
		if(key.getKeyCode() == KeyEvent.VK_DOWN)
			this.decelerateHeld = true;
	}

	// Deals with keyboard keys being released
	public void keyReleased(KeyEvent key)
	{
		// Mark down which important keys are no longer being pressed
		if(key.getKeyCode() == KeyEvent.VK_UP)
			this.accelerateHeld = false;
		if(key.getKeyCode() == KeyEvent.VK_LEFT)
			{/*this.turnLeftHeld = false;*/this.increaseOmega = false;}
		if(key.getKeyCode() == KeyEvent.VK_RIGHT)
			{/*this.turnRightHeld = false;*/this.decreaseOmega = false;}
		
		if(key.getKeyCode() == KeyEvent.VK_DOWN)
			this.decelerateHeld = false;
		
		if(key.getKeyCode() == KeyEvent.VK_SPACE)
			this.shotReady = -1;
	}

	// This method is not actually used, but is required by the KeyListener interface
	public void keyTyped(KeyEvent arg0)
	{
	}
	
	
	public static void main(String[] args)
	{
		// A GUI program begins by creating an instance of the GUI
		// object. The program is event driven from that point on.
		new CometsMain();
	}

}