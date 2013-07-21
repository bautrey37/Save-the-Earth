/**
 * 
 */
package com.github.cop4331sum13.entities;

import java.io.File;

import javax.imageio.ImageIO;

/**
 * @author Thomas J. O'Neill
 */
public class AlienLaser extends Alien
{
	
	
	
	/**
	 * 
	 */
	public AlienLaser(double xPos, double yPos, double xVel, double yVel, double angleToTank)
	{
		super( xPos, yPos, xVel, yVel );
		// TODO Auto-generated constructor stub
		
		
		this.xPosition = Math.sin( angleToTank ) + xPos;
		this.yPosition = Math.cos( angleToTank ) + yPos;
		
		this.xVelocity = Math.sin( angleToTank ) * 11.0;
		this.yVelocity = Math.cos( angleToTank ) * 11.0;
		
		
		
		//  Obtain image file for this tank shell.
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