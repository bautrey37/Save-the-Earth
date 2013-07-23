/**
 * 
 */
package com.github.cop4331sum13.entities;

import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.github.cop4331sum13.Game;

/**
 * 
 * 
 * 
 * @author Thomas J. O'Neill
 */
public class LargeAlien extends Alien
{
	private double systemTimeForAccel;
	
	private double systemTimeForFire;
	
	private static double timeToWaitForAccel = 50;
	private double timeToWaitForFire;
	/**
	 * 
	 */
	public LargeAlien(double xPos, double yPos, double xVel, double yVel)
	{
		super( xPos, yPos, xVel, yVel );
		systemTimeForAccel = System.currentTimeMillis();
		systemTimeForFire = System.currentTimeMillis();
		timeToWaitForFire = Math.random() * 2000.0 + 1000.0;
		
		//  Stuff goes here.
		
		//  Obtain image file for this tank shell.
		try
		{
			image = ImageIO.read( new File( "res/Large-Alien-Ship.png" ) );
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
			this.health = 4;
		}
		
		
		
	}  //  End of LargeAlien() constructor.
	
	
	
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
		
		/*
		this.xVelocity = Math.sin( angleToTank ) * 5.0;
		this.yVelocity = Math.cos( angleToTank ) * 5.0;
		*/
	}
	
	
	/**
	 * This method fires two shots at the tanks current position.  These shots will be 
	 * "fire and forget" shots, meaning that they are not homing and will simply move
	 * on a straight path
	 */
	public void fireLasers( Vector<AlienLaser> lasers )
	{
		
		
		if( !(System.currentTimeMillis() - systemTimeForAccel > LargeAlien.timeToWaitForAccel) )
		
		if( !(System.currentTimeMillis() - systemTimeForFire > timeToWaitForFire ) )
		{
			return;
		}
		else
		{
			systemTimeForFire = System.currentTimeMillis();
			timeToWaitForFire = Math.random() * 2000.0 + 1000.0;
		}
		lasers.add( new AlienLaser( this.xPosition + 50, this.yPosition + 15, 0, 0, this.angleToTank) );
		lasers.add( new AlienLaser( this.xPosition - 50, this.yPosition + 15, 0, 0, this.angleToTank) );
	}
	
	
}  //  End of LargeAlien class.
