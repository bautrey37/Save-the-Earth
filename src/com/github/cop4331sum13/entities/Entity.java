package com.github.cop4331sum13.entities;

import java.awt.image.BufferedImage;


/**
 * This is the parent/super class for all entities of the game, including the tank with tank aspects, shells fired,
 * and all enemies that will appear throughout the game.  This class contains the common aspects of all entities.
 * 
 * @author Earth's Defenders
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
	protected static final int WIDTH = 800;
	protected static final int HEIGHT = 600;
	
	/**
	 * Represents the current health of this entity object.
	 */
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
		//  Update position of Entity based on current location and speed variables.
		//  For x coordinate, ensure screen wrapping.  Do this by adding WIDTH to maintain position value, then
		//		that value modulus WIDTH again to get current position within screen.
		xPosition = ( xPosition + xVelocity + WIDTH ) % WIDTH;
		
		
		//  For y coordinate, DO NOT ensure screen wrapping:
		//		* Enemies will be spawned off screen
		//		* Program must be able to track when enemies "descend too low" to account for enemies "crashing" into planet.
		yPosition = yPosition + yVelocity;
		
		
	}
	
	
	
	/**
	 * Retrieves the xPosition of this Entity object.
	 * 
	 * @return - x coordinate in the window
	 */
	public double getX()
	{
		return xPosition;
		
		
	}
	
	
	
	/**
	 * Retrieves the yPosition of this Entity object.
	 * 
	 * @return - y coordinate in the window
	 */
	public double getY()
	{
		return yPosition;
		
		
	}
	
	
	
	/**
	 * Sets the xPosition of this Entity object to a new position.
	 * 
	 * @param xPos - new x coordinate for this Entity
	 */
	public void setX( double xPos )
	{
		this.xPosition = xPos;
		
		
	}
	
	
	
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
	 * @return image file representing this entity.
	 */
	public BufferedImage getImage()
	{
		return image;
		
		
	}  //  End of getImage() method.
	
	
	/**
	 * Returns the current health of this entity object.
	 * 
	 * @return int representation of health.
	 */
	public int getHealth()
	{
		return health;
	}
	
	
	/**
	 * Sets the health of this object to a provided value.
	 * 
	 * @param health - value to set entity health to.
	 */
	public void setHealth( int health )
	{
		this.health = health;
	}
	
}
