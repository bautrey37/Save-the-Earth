package com.github.cop4331sum13.entities;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


/**
 * This class creates the tank cannon of the tank that the user controls midgame.
 * 
 * @author Thomas J. O'Neill
 */
public class TankCannon extends Tank
{
	/**
	 * Keeps track of the angle of inclination of the cannon.
	 * 
	 * This values is defined in terms of radians.  For example, 1 = pi; therefore, 2 = pi or one full rotation.
	 */
	private double angle;
	public BufferedImage[] cannon;
	
	
	/**
	 * Constructs the cannon of the Tank that the user controls.
	 * 
	 * @param xPos - x coordinate to spawn TankCannon with.
	 * @param yPos - y coordinate to spawn TankCannon with.
	 * @param xVel - x velocity to spawn TankCannon with.
	 * @param yVel - y velocity to spawn TankCannon with.
	 */
	public TankCannon(double xPos, double yPos, double xVel, double yVel )
	{
		//  Call parent class constructor.
		super(xPos, yPos, xVel, yVel);
		
		//  Initialize
		angle = Math.PI;
		
		
		//  Initialize array of images.
		cannon = new BufferedImage[181];
		
		//  Read in 181 images for all appropriate degrees of inclination for cannon.
		for( int i = 90; i < 271; i++ )
		{
			try
			{
				cannon[i - 90] = ImageIO.read( new File("res/cannon/" + ((Integer)i).toString() + ".png" ) );
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
			
		}
		
	}  //  End of TankCannon constructor.
	
	
	
	/**
	 * Retrieves the angle of the tank cannon.  This angle is used for positioning the graphing
	 * as well as determining x- and y-velocities of shells fired.
	 * 
	 * @return angle - the angle of inclination of the cannon.
	 */
	public double getAngle()
	{
		return angle;
		
		
	}  //  End of getAngle() method.
	
	
	
	/**
	 * Updates the angle of the tank cannon based on the current mouse position if mouse option is enabled.
	 * 
	 * @param mouseX - the x coordinate of the mouse relevant to the game window.
	 * @param mouseY - the y coordinate of the mouse relevant to the game window.
	 */
	public void updateAngleMouse( int mouseX, int mouseY, int cannonX, int cannonY )
	{
		//  Obtain the mouse coordinates relative to the tank itself.
		double tempX = mouseX - cannonX;
		double tempY = mouseY - cannonY;
		
		
		//  Obtain the angle of inclination between the mouse and the tank.
		//  NOTE:  due to limits of arctan, this will ALWAYS be between 0 - 90 degrees.
		double angleTemp = Math.atan( Math.abs(tempY / tempX) );
		
		
		
		//  Take the temporary angle variable and get the true angle of inclination (0 - 360 degrees).
		if(tempY == 0 && tempX == 0)
			this.angle = Math.PI / 2.0;//0;
		if( tempY > 0 && tempX > 0)
			this.angle = Math.PI / 2.0;//Math.PI*.5 - angleTemp;
		if( tempY > 0 && tempX == 0)
			this.angle = Math.PI / 2.0;//0.0;
		if( tempY > 0 && tempX < 0 )
			this.angle = Math.PI * 1.5;//Math.PI*1.5 + angleTemp;
		if(tempY == 0 && tempX < 0 )
			this.angle = Math.PI*1.5;
		if(tempY < 0 && tempX < 0)
			this.angle = Math.PI*1.5 - angleTemp;
		if(tempY < 0 && tempX == 0)
			this.angle = Math.PI;
		if(tempY < 0 && tempX > 0)
			this.angle = angleTemp + Math.PI*.5;
		
		
		if(tempY == 0 && tempX > 0)
			this.angle = Math.PI / 2.0;
		
		
		
	}  //  End of updateAngle() method.
	
	
	
}  //  End of TankCannon class.
