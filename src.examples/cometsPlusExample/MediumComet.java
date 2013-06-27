package cometsPlusExample;
import java.util.*;

/**
 * Objects of this class represent medium-sized comets.
 * 
 * @author Thomas James O'Neill
 */
public class MediumComet extends Comet
{
	/**
	 * Creates a new medium comet.  All medium comets have a radius of 30 pixels.
	 * 
	 * @param xPos - The location of the comet in the x direction
	 * @param yPos - The location of the comet in the y direction
	 * @param xVel - The velocity of the comet in the x direction
	 * @param yVel - The velocity of the comet in the y direction
	 */
	public MediumComet( double xPos, double yPos, double xVel, double yVel )
	{
		super( xPos, yPos, xVel, yVel, 30);
	}
	
	/**
	 * Splits this comet into pieces.
	 * 
	 * @return A vector containing three small comets with the same
	 *         location as this comet, but randomly chosen velocities.
	 */
	public Vector<Comet> explode()
	{
		Vector<Comet> v = new Vector<Comet>();
		
		double xVel = 0.0;
		double yVel = 0.0;
		double vel = 0.0;
		
		while( v.size() < 3 )
		{
			do{
				xVel = Math.random() * 10.0 - 5.0;
				yVel = Math.random() * 10.0 - 5.0;
				vel = Math.sqrt( xVel*xVel + yVel*yVel );
			}while( vel < 3.5 || vel > 4.5 );
			
			SmallComet sComet = new SmallComet( this.getXPosition(), this.getYPosition(), xVel, yVel );
			v.add( sComet );
		}
		return v;
	}
} // End of MediumComet class
