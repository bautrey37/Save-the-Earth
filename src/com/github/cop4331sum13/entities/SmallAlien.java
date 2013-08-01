package com.github.cop4331sum13.entities;

import javax.imageio.ImageIO;

import com.github.cop4331sum13.Game;
import com.github.cop4331sum13.gui.GUI;

/**
 * Creates and manages the small aliens that appear and attack the user tank.
 * 
 * @author Earth's Defenders
 */

public class SmallAlien extends Alien {

	private static final int nonRotateHeight = GUI.gameHeight - 50;
	private static final double multiplier = 1.05;
	private static final double gravConst = 0.6;

	private double systemTimeForAccel;

	private static double timeToWaitForAccel = 50;

	private boolean grounded;

	/**
	 * Constructs a new small alien to place within the game.
	 */
	public SmallAlien(double xPos, double yPos, double xVel, double yVel) {
		super(xPos, yPos, xVel, yVel);
		grounded = false;

		try {
			image = ImageIO.read(this.getClass().getClassLoader().getResource("Small-Alien-Ship.png"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		//  Set unit's health based on difficulty
		if( Game.difficulty == 1 )
		{
			this.health = 1;
		}
		else if( Game.difficulty == 2)
		{
			this.health = 1;
		}
		else //  Game.difficulty == 3
		{
			this.health = 2;
		}
	}

	/**
	 * This method updates the ship's direction and speed.  Specifically, this
	 * method will cause the small ship to turn towards the tank.
	 * 
	 * @param tankX - x coordinate of the user tank.
	 * @param tankY - y coordinate of the user tank.
	 */
	public void autoAccelerate(int tankX, int tankY) {
		if (!grounded) {
			if (!(System.currentTimeMillis() - systemTimeForAccel > SmallAlien.timeToWaitForAccel)) {
				return;
			}
			else {
				systemTimeForAccel = System.currentTimeMillis();
			}

			// Check if rotation is possible
			if (yPosition < SmallAlien.nonRotateHeight)
				updateAngleToTank(tankX, tankY);

			// Accelerate
			xVelocity += Math.sin(angleToTank) * multiplier;
			yVelocity += Math.cos(angleToTank) * multiplier;

			// Check for grounded
			if (yPosition > GUI.gameHeight - 60) {
				grounded = true;

				yVelocity = -0.3 * yVelocity;
				xVelocity = 0.1 * xVelocity;
			}
		}
		else {
			yVelocity += gravConst;
		}
	}

}
