package gameMain;


import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Enemy {
	private double x, y, xVel, yVel; // location and velocity
	private BufferedImage image; // holds the visual image for an enemy
	private double halfWidth, halfHeight;

	// Basic constructor
	public Enemy(int width) {
		xVel = Math.random() - .45;
		yVel = Math.random() - .45;

		try {
			image = ImageIO.read(new File("Large-Alien-Ship.png"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		halfWidth = image.getWidth() / 2;
		halfHeight = image.getHeight() / 2;

		x = halfWidth + 1 + (1000 * Math.random()) % (width - halfWidth - 1);
		y = halfHeight;
	}

	// Movement
	public void move(int width, int height) {
		// update coordinates
		x += xVel;
		y += yVel;

		if ((x + halfWidth) > width || (x - halfWidth) < 0) {
			xVel *= -1;
		}
		if ((y + halfHeight) > height || (y - halfHeight) < 0) {
			yVel *= -1;
		}
	}

	// Getters
	public BufferedImage getSprite() {
		return image;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
