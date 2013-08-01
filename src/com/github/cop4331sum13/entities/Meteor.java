package com.github.cop4331sum13.entities;

import javax.imageio.ImageIO;

import com.github.cop4331sum13.Game;


/**
 * Manages a meteor that the user sees on the screen.
 * 
 * @author Earth's Defenders
 */
public class Meteor extends Entity {


	/**
	 * Constructs a new meteor object for displaying on the screen. 
	 */
	public Meteor(double xPos, double yPos, double xVel, double yVel) {
		super(xPos, yPos, xVel, yVel);
		xVelocity = ((Math.random() < .5) ? -1.0 : 1.0) * 2 * Math.random();
		yVelocity = 5 * Math.random() + 1;

		try {
			image = ImageIO.read(this.getClass().getClassLoader().getResource("Meteor.png"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		//  Set unit's health based on difficulty
		if( Game.difficulty == 1 )
		{
			this.health = 4;
		}
		else if( Game.difficulty == 2)
		{
			this.health = 5;
		}
		else  //  Game.difficulty == 3
		{
			this.health = 6;
		}
		
		
	}
	
	
}
