package com.github.cop4331sum13.entities;

import java.io.File;

import javax.imageio.ImageIO;

import com.github.cop4331sum13.gui.GUI;

public class Meteor extends Entity {

	private static final double gravConst = 0.6;

	private boolean grounded;

	public Meteor(double xPos, double yPos, double xVel, double yVel) {
		super(xPos, yPos, xVel, yVel);
		xVelocity = ((Math.random() < .5) ? -1.0 : 1.0) * 2 * Math.random();
		yVelocity = 5 * Math.random() + 1;
		grounded = false;

		try {
			image = ImageIO.read(new File("res/Meteor.png"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public int autoAccelerate() {
		if (!grounded) {
			if (yPosition > GUI.gameHeight - 60){
				grounded = true;
				return 20; 
			}
		}
		return 0;
	}

}
