package entities;

import java.io.File;

import javax.imageio.ImageIO;

public class Enemy extends Entity {
	private int screenWidth;

	// Basic constructor
	public Enemy(int screenWidth) {
		super(0, 0, 0, 0);
		xVelocity = 10*Math.random() - 4.5;
		yVelocity = 5*Math.random() + 1;

		this.screenWidth = screenWidth;

		try {
			image = ImageIO.read(new File("res/Meteor.png"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		xPosition = (1000 * Math.random()) % (screenWidth - image.getWidth());
		yPosition = -1*image.getHeight();
	}
}
