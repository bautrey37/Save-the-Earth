package com.github.cop4331sum13.entities;

import javax.imageio.ImageIO;

import com.github.cop4331sum13.Game;


/**
 * This class creates and manages the body of the Tank that the user controls.
 * 
 * @author Thomas J. O'Neill
 */
public class TankBody extends Tank
{
	
	
	/**
	 * Constructs the tank body that the user sees in the window.
	 * 
	 * @param xPos - x coordinate to spawn TankBody with.
	 * @param yPos - y coordinate to spawn TankBody with.
	 * @param xVel - x velocity to spawn TankBody with.
	 * @param yVel - y velocity to spawn TankBody with.
	 */
	public TankBody(double xPos, double yPos, double xVel, double yVel)
	{
		//  Call parent class constructor.
		super(xPos, yPos, xVel, yVel);
		
		
		//  Obtain image file for this tank body.
		try
		{
			image = ImageIO.read(this.getClass().getClassLoader().getResource("Tank-Body.png"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		if( Game.difficulty == 1 )
		{
			this.health = 50;
		}
		else if( Game.difficulty == 2 )
		{
			this.health = 30;
		}
		else //  Game.difficulty == 3
		{
			this.health = 15;
		}
		
		
	}  
	
}  
