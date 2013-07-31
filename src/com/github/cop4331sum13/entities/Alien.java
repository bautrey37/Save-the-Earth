/**
 * 
 */
package com.github.cop4331sum13.entities;

/**
 * This class functions as the base for both small and large aliens that appear mid game
 * and try to attack the user's tank.
 * 
 * @author Thomas J. O'Neill
 */
public abstract class Alien extends Entity
{	
	
	/**
	 * This value tracks the angle of inclination from the alien ship midpoint to the
	 * user tank midpoint.  It is used for sending small ships straight at the tank and
	 * for large ships to aim their "lasers" at the tank
	 */
	
	protected double angleToTank;
	
	
	
	/**
	 * 
	 */
	public Alien(double xPos, double yPos, double xVel, double yVel)
	{
		super( xPos, yPos, xVel, yVel );
		
		
		//  Initialize class variables.
		angleToTank = 0.0;
		
	}  //  End of Alien() constructor.
	
	
	
	/**
	 * This method updates this Alien object's angle to be correct for the next frame.
	 * 
	 * @param
	 */
	public void updateAngleToTank( int tankX, int tankY )
	{
		//  Get temporary coordinates with this object as origin point.
		double tempX = tankX - this.xPosition;
		double tempY = tankY - this.yPosition;
		
		
		//  Obtain the angle of inclination between the mouse and the tank.
		//  NOTE:  due to limits of arctan, this will ALWAYS be between 0 - 90 degrees.
		double angleTemp = Math.atan( Math.abs(tempY / tempX) );
		
		
		//  Take the temporary angle variable and get the true angle of inclination (0 - 360 degrees).
		//  9 cases must be tested for based on unit circle:
		//      - 4 points of intersection on the x and y axis
		//	    - 4 measures of 90 degrees to cover 360 degree rotation
		//      - 1 point where the origin is
		if(tempY == 0 && tempX == 0)
			this.angleToTank = 0;
		if( tempY > 0 && tempX > 0)
			this.angleToTank = Math.PI*.5 - angleTemp;
		if( tempY > 0 && tempX == 0)
			this.angleToTank = 0.0;
		if( tempY > 0 && tempX < 0 )
			this.angleToTank = Math.PI*1.5 + angleTemp;
		if(tempY == 0 && tempX < 0 )
			this.angleToTank = Math.PI*1.5;
		if(tempY < 0 && tempX < 0)
			this.angleToTank = Math.PI*1.5 - angleTemp;
		if(tempY < 0 && tempX == 0)
			this.angleToTank = Math.PI;
		if(tempY < 0 && tempX > 0)
			this.angleToTank = angleTemp + Math.PI*.5;
		if(tempY == 0 && tempX > 0)
			this.angleToTank = Math.PI / 2.0;
		
		
		
	}
	
	
}
