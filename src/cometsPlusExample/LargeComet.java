package cometsPlusExample;
import java.util.*;

/**
 * Objects of this class represent large comets.
 * 
 * @author Thomas James O'Neill
 */
public class LargeComet extends Comet
{
	/**
	 * Creates a new large comet.  All large comets have a radius of 40 pixels.
	 * 
	 * @param xPos - The location of the comet in the x direction
	 * @param yPos - The location of the comet in the y direction
	 * @param xVel - The velocity of the comet in the x direction
	 * @param yVel - The velocity of the comet in the y direction
	 */
	public LargeComet( double xPos, double yPos, double xVel, double yVel )
	{
		super( xPos, yPos, xVel, yVel, 40);
	}
	
	/**
	 * Splits this comet into pieces.
	 * 
	 * @return A vector containing two medium comets with the same
	 *         location as this comet, but randomly chosen velocities.
	 */
	public Vector<Comet> explode()
	{
		Vector<Comet> v = new Vector<Comet>();
		
		double xVel = 0.0;
		double yVel = 0.0;
		double vel = 0.0;
		
		while( v.size() < 2 )
		{
			do{
				xVel = Math.random() * 10.0 - 5.0;
				yVel = Math.random() * 10.0 - 5.0;
				vel = Math.sqrt( xVel*xVel + yVel*yVel );
			}while( vel < 2.0 || vel > 3.0 );
			
			MediumComet mComet = new MediumComet( this.getXPosition(), this.getYPosition(), xVel, yVel );
			v.add( mComet );
		}
		return v;
	}
} // End of LargeComet class
