package cometsPlusExample;
import java.util.*;

/**
 * This class is the root of all comet objects.
 * 
 * @author Thomas James O'Neill
 */
public abstract class Comet extends SpaceObject
{
	/**
	 * Creates a new comet in the specified place, with the specified velocity and size
	 * 
	 * @param xPos - The location of the comet in the x direction
	 * @param yPos - The location of the comet in the y direction
	 * @param xVel - The velocity of the comet in the x direction
	 * @param yVel - The velocity of the comet in the y direction
	 * @param radius - The distance from the center of the comet to its outer edge
	 */
	public Comet( double xPos, double yPos, double xVel, double yVel, double radius )
	{
		super( xPos, yPos, xVel, yVel, radius);
	}
	
	/**
	 * This indicates to the comet that it has been shot and should break into pieces.
	 * 
	 * @return A (possibly empty) vector of the comets spawned by the destruction
	 *         of this comet, or null if the comet refuses to die yet
	 */
	public abstract Vector<Comet> explode();
	
} // End of Comet class