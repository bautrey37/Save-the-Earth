package cometsPlusExample;

/**
 * Objects of this class represent the bullets fired by the player's ship.
 * They keep track of their age so that old shots can be removed from play.
 * 
 * @author Thomas James O'Neill
 */
public class Shot extends SpaceObject
{
	/**
	 * Keeps track of the age of this shot.
	 */
	private	int age;
	
	/**
	 * Creates a new Shot.  Shots have radius 3.  New shots have age 0.
	 */
	public Shot( double xPos, double yPos, double xVel, double yVel )
	{
		super( xPos, yPos, xVel, yVel, 3);
		age = 0;
	}
	
	/**
	 * Returns the age of this shot, i.e. the number of times that it has had move() called on it.
	 * 
	 * @return the age of the shot
	 */
	public int getAge()
	{
		return age;
	}
	
	/**
	 * In addition to moving the shot, the age of the shot is incremented by one.
	 */
	public void move()
	{
		super.move();
		age++;
	}
} // End of Shot class