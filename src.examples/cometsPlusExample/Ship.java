package cometsPlusExample;

import cometsPlusExample.Shot;
import cometsPlusExample.SpaceObject;

/**
 * 
 * @author Thomas James O'Neill
 */
public class Ship extends SpaceObject
{
	/**
	 * The angle representing the direction the ship is currently facing.
	 */
	protected double angle = 0.0;
	double angularVelocity;
	/**
	 * Creates a new Ship.  Each ship has a radius of 10.
	 * 
	 * @param xPos - The location of the ship in the x direction
	 * @param yPos - The location of the ship in the y direction
	 * @param xVel - The velocity of the ship in the x direction
	 * @param yVel - The velocity of the ship in the y direction
	 */
	public Ship( double xPos, double yPos, double xVel, double yVel )
	{
		super( xPos, yPos, xVel, yVel, 10.0);
		angle = Math.PI;
	}
	
	/**
	 * Increases the speed of the ship by a slight amount in the direction
	 * that it is facing. If this causes the ship to be moving faster than
	 * 10 pixels per frame, then the speed is scaled down to 10 pixels per frame.
	 */
	public void accelerate()
	{
		xVelocity += .7;// * Math.sin( angle );
		//yVelocity += .1 * Math.cos( angle );
		
		double vel = Math.sqrt( xVelocity*xVelocity );//+ yVelocity*yVelocity );
		
		if( vel > 10 )
		{
			xVelocity *= 10.0 / vel;
			//yVelocity *= 10.0 / vel;
		}
	}
	
	/**
	 * Produces a new shot originating from the center of the ship. The shot's
	 * velocity is equal to three pixels per frame in the direction that
	 * the ship is pointing added to the ship's current velocity.
	 * 
	 * @return the new shot
	 */
	public Shot fire( int speed )
	{
		double xVel = 30.0*Math.sin( angle );
		double yVel = 30.0*Math.cos( angle );
		
		Shot s = new Shot( this.getXPosition(), this.getYPosition(), xVel   , yVel );
		
		return s;
	}
	
	/**
	 * Returns the direction that the ship is facing.
	 * 
	 * @return direction ship is facing
	 */
	public double getAngle()
	{
		return angle;
	}
	
	/**
	 * Slightly rotates the ship counter-clockwise.
	 */
	public void rotateLeft()
	{
		angle += .1;
	}
	
	/**
	 * Slightly rotates the ship clockwise.
	 */
	public void rotateRight()
	{
		angle -= .1;
	}
	
	public void increaseOmega() { if( angularVelocity < Math.PI*.075 ) angularVelocity += Math.PI*.001; }
	public void decreaseOmega() { if( angularVelocity > -Math.PI*.075 ) angularVelocity -= Math.PI*.001; }
    
	public void move() {super.move();angle += angularVelocity;}
	
	
	/**
	 * Increases the speed of the ship by a slight amount in the direction opposite
	 * that it is facing. If this causes the ship to be moving faster than
	 * 10 pixels per frame, then the speed is scaled down to 10 pixels per frame.
	 */
	public void decelerate()
	{
		xVelocity -= .7;// * Math.sin( angle );
		//yVelocity -= .1 * Math.cos( angle );
		
		double vel = Math.sqrt( xVelocity*xVelocity );//+ yVelocity*yVelocity );
		
		if( vel > 10 )
		{
			xVelocity *= 10.0 / vel;
			//yVelocity *= 10.0 / vel;
		}
	}
	
	
	public void lowerSpeed()
	{
		if( xVelocity > 0 )
		{
			if( xVelocity >= 1.0 )
			{
				xVelocity -= 1.0;
			}
			else
			{
				xVelocity = 0;
			}
		}
		else if( xVelocity < 0 )
		{
			if( xVelocity <= -1.0 )
			{
				xVelocity += 1.0;
			}
			else
			{
				xVelocity = 0;
			}
		}
	}
} // End of Ship class
