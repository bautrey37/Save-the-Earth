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
			image = ImageIO.read(new File("res/Large-Alien-Ship.png"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		xPosition = 1000 * Math.random() % (screenWidth - image.getWidth());
		yPosition = 0;
	}

	@Override
	public void move() {
		super.move();

		if (xPosition + image.getWidth() > screenWidth || xPosition < 0) {
			xVelocity *= -1;
		}
	}

	// // Movement
	// public void move(int width, int height) {
	// // update coordinates
	// xPosition += xVelocity;
	// yPosition += yVelocity;
	//
	// if (xPosition + image.getWidth() > width || xPosition < 0) {
	// xVelocity *= -1;
	// }
	// if (yPosition + image.getHeight() > height || yPosition < 0) {
	// yVelocity *= -1;
	// }
	// }

}
