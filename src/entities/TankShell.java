package entities;

import java.io.File;

import javax.imageio.ImageIO;
/*
 * Please note:  All tank shells should be managed by one (1) Vector list in the runner class (Game).
 */


/**
 * This class creates an individual tank shell.
 * 
 * @author Thomas J. O'Neill
 */
public class TankShell extends Tank
{
	/**
	 * Determines the strength of this tank shell.  Can be modified by the Upgrade class.
	 */
	private static int shellStrength;
	
	
	
	
	/**
	 * Constructs a new tank shell every time the user "fires" the tank cannon.  Each shell is treated as
	 * a separate object.
	 * 
	 * @param xPos - x coordinate to spawn TankShell with.
	 * @param yPos - y coordinate to spawn TankShell with.
	 * @param xVel - x velocity to spawn TankShell with.
	 * @param yVel - y velocity to spawn TankShell with.
	 */
	public TankShell(double xPos, double yPos, double xVel, double yVel)
	{
		//  Call parent class constructor
		super(xPos, yPos, xVel, yVel);
		
		
		//  Obtain image file for this tank body.
		try
		{
			image = ImageIO.read( new File( "Tank-Shell-1.png" ) );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}  //  End of TankShell constructor.
	
	
	
	/**
	 * Returns the power level of this tank shell.  Used in determining how much "health" to take
	 * from specific enemies.
	 * 
	 * @return shellStrength - the power level of the tank shell.
	 */
	public int getShellStrength()
	{
		return shellStrength;
		
		
	}  //  End of getShellStrength() method.
	
	
	
	/**
	 * Modifies the power of all tank shell's strength.
	 * 
	 * NOTE:  MUST BE CALLED before the first level and set to default level. 
	 * 		  Difficulty modifier on first screen will handle this call.
	 * 
	 * @param sStrength - new strength level for all shells.
	 */
	public void setShellStrength( int sStrength )
	{
		shellStrength = sStrength;
		
		
	}  //  End of setShellStrength() method.
	
	
	
}  //  End of TankShell class.
