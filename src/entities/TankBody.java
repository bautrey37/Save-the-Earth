package entities;

import java.io.File;

import javax.imageio.ImageIO;


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
			image = ImageIO.read( new File( "Tank-Body-1.png" ) );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}  //  End of TankBody constructor.

	
}  //  End of TankBody class.