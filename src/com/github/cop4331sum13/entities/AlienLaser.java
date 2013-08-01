package com.github.cop4331sum13.entities;

import javax.imageio.ImageIO;

/**
 * This class creates a laser fired by an alien ship and manages each laser shot
 * as a separate object.
 * 
 * @author Earth's Defenders
 */
public class AlienLaser extends Alien
{
	/**
	 * Tracks the laser's "life" span since spawning time.
	 */
	private int laserLife;
	
	
	/**
	 * Constructs a new "laser" that was "fired" by an alien ship.
	 */
	public AlienLaser(double xPos, double yPos, double xVel, double yVel, double angleToTank)
	{
		super( xPos, yPos, xVel, yVel );
		
		
		//  Set proper starting position
		this.xPosition = Math.sin( angleToTank ) + xPos;
		this.yPosition = Math.cos( angleToTank ) + yPos;
		
		this.xVelocity = Math.sin( angleToTank ) * 11.0;
		this.yVelocity = Math.cos( angleToTank ) * 11.0;
		
		
		
		//  Obtain image file for this alien laser.
		try
		{
			image = ImageIO.read(this.getClass().getClassLoader().getResource("Alien-Laser.png"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		this.health = 1;
		this.laserLife = 95;
		
	}
	
	
	/**
	 * Decrements the current life of this laser object.
	 */
	public void decreaseLife()
	{
		laserLife -= 1;
	}
	
	
	/**
	 * Returns the current life of this laser object.
	 * 
	 * @return the int representation of current object life.
	 */
	public int getLife()
	{
		return laserLife;
	}
	
	
	
}