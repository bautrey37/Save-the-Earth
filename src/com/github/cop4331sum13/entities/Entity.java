/**
 * This is the parent/super class for all entities of the game, including the tank with tank aspects, shells fired,
 * and all enemies that will appear throughout the game.  This class contains the common aspects of all entities.
 */
package com.github.cop4331sum13.entities;

import java.awt.image.BufferedImage;


/**
 * This is the parent/super class for all entities of the game, including the tank with tank aspects, shells fired,
 * and all enemies that will appear throughout the game.  This class contains the common aspects of all entities.
 * 
 * @author Thomas J. O'Neill
 *
 */
public abstract class Entity
{
	/**
	 * Holds the image file for this Entity.
	 */
	protected BufferedImage image;
	
	/**
	 * The x coordinate of this object within the window.
	 */
	protected double xPosition;
	
	/**
	 * The y coordinate of this object within the window.
	 */
	protected double yPosition;
	
	/**
	 * The x velocity of this object as it moves within the window.
	 */
	protected double xVelocity;
	
	/**
	 * The y velocity of this object as it moves within the window.
	 */
	protected double yVelocity;
	
	//  Keep track of game window size.
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	
	protected int health;
	
	
	/**
	 * Constructs a new Entity object within given coordinates and initial speeds.
	 * 
	 * @param xPos - x coordinate to spawn Entity with.
	 * @param yPos - y coordinate to spawn Entity with.
	 * @param xVel - x velocity to spawn Entity with.
	 * @param yVel - y velocity to spawn Entity with.
	 */
	public Entity( double xPos, double yPos, double xVel, double yVel )
	{
		//  Initialize Entity with values provided
		xPosition = xPos;
		yPosition = yPos;
		xVelocity = xVel;
		yVelocity = yVel;
	}
	
	
	
	/**
	 * This method handles the movement of this entity.
	 */
	public void move()
	{
		//  NOTE:  This method does NOT need to be overridden.  Variations of an "acceleration" method will be added
		//				to all relevant child classes.
		
		
		//  Update position of Entity based on current location and speed variables.
		//  For x coordinate, ensure screen wrapping.  Do this by adding WIDTH to maintain position value, then
		//		that value modulus WIDTH again to get current position within screen.
		xPosition = ( xPosition + xVelocity + WIDTH ) % WIDTH;
		
		
		//  For y coordinate, DO NOT ensure screen wrapping:
		//		* Enemies will be spawned off screen
		//		* Program must be able to track when enemies "descend too low" to account for enemies "crashing" into planet.
		yPosition = yPosition + yVelocity;
		
		
	}  //  End of move() method.
	
	
	
	/**
	 * Retrieves the xPosition of this Entity object.
	 * 
	 * @return - x coordinate in the window
	 */
	public double getX()
	{
		return xPosition;
		
		
	}  //  End of getX() method.
	
	
	
	/**
	 * Retrieves the yPosition of this Entity object.
	 * 
	 * @return - y coordinate in the window
	 */
	public double getY()
	{
		return yPosition;
		
		
	}  //  End of getY() method.
	
	
	
	/**
	 * Sets the xPosition of this Entity object to a new position.
	 * 
	 * @param xPos - new x coordinate for this Entity
	 */
	public void setX( double xPos )
	{
		this.xPosition = xPos;
		
		
	}  //  End of setX() method.
	
	
	
	/**
	 * Sets the yPosition of this Entity object to a new position.
	 * 
	 * @param yPos - new y coordinate for this Entity
	 */
	public void setY( double yPos )
	{
		this.yPosition = yPos;
		
		
	}  //  End of setY() method.
	
	
	
	/**
	 * Retrieves the image for this Entity when necessary.
	 * 
	 * @param
	 */
	public BufferedImage getImage()
	{
		return image;
		
		
	}  //  End of getImage() method.
	
	
	
	public int getHealth()
	{
		return health;
	}
	
	public void setHealth( int health )
	{
		this.health = health;
	}
	
}  //  End of Entity class.
