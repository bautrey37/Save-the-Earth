/**
 * 
 */
package com.github.cop4331sum13.entities;

import java.util.Vector;

import javax.imageio.ImageIO;

import com.github.cop4331sum13.Game;

/**
 * Represents a large alien entity that the user will fight mid-game.
 * 
 * @author Earth's Defenders
 */
public class LargeAlien extends Alien
{
	private double systemTimeForAccel;
	
	private double systemTimeForFire;
	
	private static double timeToWaitForAccel = 50;
	
	private double timeToWaitForFire;
	
	
	
	/**
	 * Construct a new Alien object that the user sees on screen.
	 */
	public LargeAlien(double xPos, double yPos, double xVel, double yVel)
	{
		super( xPos, yPos, xVel, yVel );
		systemTimeForAccel = System.currentTimeMillis();
		systemTimeForFire = System.currentTimeMillis();
		timeToWaitForFire = Math.random() * 2000.0 + 1000.0;
		
		
		//  Obtain image file for this large alien.
		try
		{
			image = ImageIO.read(this.getClass().getClassLoader().getResource("Large-Alien-Ship.png"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		//  Set unit's health based on difficulty
		if( Game.difficulty == 1 )
		{
			this.health = 2;
		}
		else if( Game.difficulty == 2)
		{
			this.health = 3;
		}
		else  //  Game.difficulty == 3
		{
			this.health = 3;
		}
		
		
		
	}
	
	
	
	/**
	 * This method updates the ship's movement direction and speed.
	 */
	public void autoAccelerate()
	{
		if( !(System.currentTimeMillis() - systemTimeForAccel > LargeAlien.timeToWaitForAccel) )
		{
			return;
		}
		else
		{
			systemTimeForAccel = System.currentTimeMillis();
		}
		if( this.yPosition > 50.0 && this.yPosition < 150 )
		{
			double tempAngle = Math.random() * Math.PI * 2.0;
			this.yVelocity += ( Math.cos( tempAngle ) * 0.2 ) % 3.0;
			this.xVelocity += ( Math.sin( tempAngle ) * 0.2 ) % 3.0;
		}
		else if( this.yPosition <= 50.0 )
		{
			double tempAngle = Math.random() * Math.PI * 1.0 - Math.PI * 0.5;
			this.yVelocity += ( Math.cos( tempAngle ) * 0.2 ) % 3.0;
			this.xVelocity += ( Math.sin( tempAngle ) * 0.2 ) % 3.0;
			
		}
		else//( this.yPosition >= 150 )
		{
			double tempAngle = Math.random() * Math.PI * 1.0 + Math.PI * 0.5;
			this.yVelocity += ( Math.cos( tempAngle ) * 0.2 ) % 3.0;
			this.xVelocity += ( Math.sin( tempAngle ) * 0.2 ) % 3.0;
		}
		
	}
	
	
	/**
	 * This method fires two shots at the tanks current position.  These shots will be 
	 * "fire and forget" shots, meaning that they are not homing and will simply move
	 * on a straight path
	 * 
	 * @param lasers - the vector list managing all the lasers that have been spawned thus far.
	 * @param tankX - x coordinate of user tank.
	 * @param tankY - y coordinate of user tank.
	 * 
	 * @return true if a new pair of lasers were added, false if no lasers were added.
	 */
	public boolean fireLasers( Vector<AlienLaser> lasers, int tankX, int tankY )
	{
		//  Check if enough time has passed prior to firing a new volley of lasers.
		if( !(System.currentTimeMillis() - systemTimeForAccel > LargeAlien.timeToWaitForAccel) )
		
		if( !(System.currentTimeMillis() - systemTimeForFire > timeToWaitForFire ) )
		{
			return false;
		}
		else
		{
			systemTimeForFire = System.currentTimeMillis();
			timeToWaitForFire = Math.random() * 2000.0 + 1000.0;
		}
		this.updateAngleToTank( tankX - 30, tankY);
		lasers.add( new AlienLaser( this.xPosition + 50, this.yPosition + 15, 0, 0, this.angleToTank) );
		this.updateAngleToTank( tankX + 30, tankY);
		lasers.add( new AlienLaser( this.xPosition - 50, this.yPosition + 15, 0, 0, this.angleToTank) );
		this.updateAngleToTank( tankX, tankY);
		
		return true;
	}
	
	
}
