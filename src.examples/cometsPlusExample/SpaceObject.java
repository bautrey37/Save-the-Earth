package cometsPlusExample;

/**
 * This class is the root of the hierarchy of objects that float in space.
 * All of the game objects in Comets derive from this class. 
 * 
 * @author Thomas James O'Neill
 */
public abstract class SpaceObject
{
	/**
	 * The x coordinate of the object in the space created
	 */
	protected double xPosition;
	
	/**
	 * The y coordinate of the object in the space created
	 */
	protected double yPosition;
	
	/**
	 * The distance from the center of the object to its edge
	 */
	private double radius;
	
	/**
	 * The size of the play area in the y direction
	 */
	public static double playfieldHeight;
	
	/**
	 * The size of the play area in the x direction
	 */
	public static double playfieldWidth;
	
	/**
	 * The rate at which the object is moving in the x direction
	 */
	protected double xVelocity;
	
	/**
	 * The rate at which the object is moving in the y direction 
	 */
	protected double yVelocity;
	
	
	
	/**
	 * Creates a space object in the specified place, with the specified speed, and the specified size.
	 * 
	 * @param xPos - The location of the object in the x direction
     * @param yPos - The location of the object in the y direction
     * @param xVel - The velocity of the object in the x direction
     * @param yVel - The velocity of the object in the y direction
     * @param radius - The distance from the center of the object to its outer edge
	 */
	public SpaceObject( double xPos, double yPos, double xVel, double yVel, double radius )
	{
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.xVelocity = xVel;
		this.yVelocity = yVel;
		this.radius = radius;
	}
	
	
	
	/**
	 * Returns the size of the object.
	 * 
	 * @return the object's radius
	 */
	public double getRadius()
	{
		return radius;
	}
	
	
	
	/**
	 * Returns the position of the object in the x direction
	 * 
	 * @returns the object's x position
	 */
	public double getXPosition()
	{
		return xPosition;
	}
	
	
	
	/**
	 * Returns the position of the object in the y direction
	 * 
	 * @returns the object's y position
	 */
	public double getYPosition()
	{
		return yPosition;
	}
	
	
	
	/**
	 * Updates the position of the object based on its velocity,
	 * and wraps it around to the other side of the play field
	 * if it goes off the edge.
	 */
	public void move()
	{
		xPosition = ( xPosition + xVelocity + playfieldWidth ) % playfieldWidth;
		yPosition = ( yPosition + yVelocity + playfieldHeight ) % playfieldHeight;
	}
	
	
	
	/**
	 * Determines whether this object is overlapping with rhs.
	 * 
	 * @param rhs - The other object that we're comparing this one against
	 * 
	 * @returns true if the objects overlap and false otherwise
	 */
	public boolean overlapping( SpaceObject rhs )
	{
		/* 
		 * Overlapping will be determined by comparing the distance
		 * between the centers of the two objects to the sum of their radii.  The distance will be found
		 * using the familiar formula:
		 *  d = sqrt( (x2 - x1)^2 + (y2 - y1)^2 )
		 * If d is less than the sum of the radius of this and the radius of rhs,
		 * then this and rhs are overlapping.
		 */
		
		
		double x1 = this.getXPosition();
		double y1 = this.getYPosition();
		double r1 = this.getRadius();
		
		double x2 = rhs.getXPosition();
		double y2 = rhs.getYPosition();
		double r2 = rhs.getRadius();
		
		double d = Math.sqrt( (x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1) );
		
		if( d < r1 + r2 )
		{
			return true;
		}
		else
		{
			return false;
		}
	} // End of overlapping() method
} // End of SpaceObject class