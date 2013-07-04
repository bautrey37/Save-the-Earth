package entities;

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
	
	
	
	/**
	 * Constructs the cannon of the Tank that the user controls.
	 * 
	 * @param xPos - x coordinate to spawn TankCannon with.
	 * @param yPos - y coordinate to spawn TankCannon with.
	 * @param xVel - x velocity to spawn TankCannon with.
	 * @param yVel - y velocity to spawn TankCannon with.
	 */
	public TankCannon(double xPos, double yPos, double xVel, double yVel)
	{
		//  Call parent class constructor.
		super(xPos, yPos, xVel, yVel);
		
		
		//  Obtain image file for this tank body.
		try
		{
			image = ImageIO.read( new File("Tank-Cannon-1.png") );
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
	public void updateAngleMouse( int mouseX, int mouseY )
	{
		//  Obtain the mouse coordinates relative to the tank itself.
		double tempX = mouseX - this.getX();
		double tempY = mouseY - this.getY();
		
		
		//  Obtain the angle of inclination between the mouse and the tank.
		//  NOTE:  due to limits of arctan, this will ALWAYS be between 0 - 90 degrees.
		double angleTemp = Math.atan( Math.abs(tempY / tempX) );
		
		
		
		//**************************
		/* 
		 * 
		 * KEEP IN MIND FOR LATER:  DO WE ALLOW FULL 360 DEGREE ROTATION OR LIMIT IT, TO 180 DEGREES?
		 * 
		 * FOR NOW, METHOD ALLOWS FOR FULL 360 DEGREE ROTATION
		 * 
		 * 
		 * THIS IS IN ALL CAPS TO GRAB EVERYONE'S ATTENTION AND TO ENSURE RESOLUTION BY FINAL PRODUCT  :D
		 * 
		 * 
		 ****************************/
		
		
		
		
		//  Take the temporary angle variable and get the true angle of inclination (0 - 360 degrees).
		if(tempY == 0 && tempX == 0)
			this.angle = 0;
		if( tempY > 0 && tempX > 0)
			this.angle = Math.PI*.5 - angleTemp;
		if( tempY > 0 && tempX == 0)
			this.angle = 0.0;
		if( tempY > 0 && tempX < 0 )
			this.angle = Math.PI*1.5 + angleTemp;
		if(tempY == 0 && tempX < 0 )
			this.angle = Math.PI*1.5;
		if(tempY < 0 && tempX < 0)
			this.angle = Math.PI*1.5 - angleTemp;
		if(tempY < 0 && tempX == 0)
			this.angle = Math.PI;
		if(tempY < 0 && tempX > 0)
			this.angle = angleTemp + Math.PI*.5;
		
		
	}  //  End of updateAngle() method.
	
	
	
}  //  End of TankCannon class.
