package com.github.cop4331sum13.entities;

import java.io.File;

import javax.imageio.ImageIO;

import com.github.cop4331sum13.gui.GUI;

/**
 * 
 * @author Jared
 *
 */

public class SmallAlien extends Alien{
	
	private static final int nonRotateHeight = GUI.gameHeight - 50;
	private static final double multiplier = 1.0;
	
	private double systemTimeForAccel;
	
	private static double timeToWaitForAccel = 50;
	
	
	public SmallAlien(double xPos, double yPos, double xVel, double yVel){
		super(xPos, yPos, xVel, yVel);
		alive = true;
		
		try
		{
			image = ImageIO.read( new File( "res/Small-Alien-Ship.png" ) );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * This method updates the ship's movement direction and speed.
	 */
	public void autoAccelerate(int tankX, int tankY){
		if( !(System.currentTimeMillis() - systemTimeForAccel > SmallAlien.timeToWaitForAccel) )
		{
			return;
		}
		else
		{
			systemTimeForAccel = System.currentTimeMillis();
		}
		
		
		// Check if rotation is possible
		if (yPosition < SmallAlien.nonRotateHeight)
			updateAngleToTank(tankX, tankY);
		
		// Accelerate
		xVelocity += Math.sin( angleToTank ) * multiplier;
		yVelocity += Math.cos( angleToTank ) * multiplier;
	}
	
	
}
