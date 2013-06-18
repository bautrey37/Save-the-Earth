package cometsPlusExample;
import java.util.*;

/**
 * Objects of this class represent small comets.
 * 
 * @author Thomas James O'Neill
 */
public class SmallComet extends Comet
{
	/**
	 * Creates a new small comet.  All small comets have a radius of 20 pixels.
	 * 
	 * @param xPos - The location of the comet in the x direction
	 * @param yPos - The location of the comet in the y direction
	 * @param xVel - The velocity of the comet in the x direction
	 * @param yVel - The velocity of the comet in the y direction
	 */
	public SmallComet( double xPos, double yPos, double xVel, double yVel )
	{
		super( xPos, yPos, xVel, yVel, 20);
	}
	
	/**
	 * Being the smallest-sized comet, the small comet does NOT spawn any other comets when exploded.
	 * 
	 * @return an empty vector.
	 */
	public Vector<Comet> explode()
	{
		Vector<Comet> v = new Vector<Comet>();
		return v;
	}
} // End of SmallComet class
