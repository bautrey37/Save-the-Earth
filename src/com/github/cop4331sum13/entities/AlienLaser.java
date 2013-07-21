/**
 * 
 */
package com.github.cop4331sum13.entities;

import java.io.File;

import javax.imageio.ImageIO;

/**
 * This class creates a laser fired by an alien ship and manages each laser shot
 * as a separate object.
 * 
 * @author Thomas J. O'Neill
 */
public class AlienLaser extends Alien
{
	
	
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
			image = ImageIO.read( new File( "res/Alien-Laser.png" ) );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}  //  End of AlienLaser() constructor.
	
	
	
}  //  End of AlienLaser class.