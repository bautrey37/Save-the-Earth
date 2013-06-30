package enemiesTest;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Enemy {
	private double x, y, xVel, yVel; // location and velocity
	private BufferedImage image, erase; // holds the visual image for an enemy

	// Basic constructor
	public Enemy(double x, double y) {
		this.x = x;
		this.y = y;
		xVel = Math.random() * 10 - 5;
		yVel = Math.random() * 10 - 5;

		try {
			image = ImageIO.read(new File("placeholder.png"));
			erase = ImageIO.read(new File("placeholdererase.png"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Movement
	public void move(Graphics g) {
		g.drawImage(erase, (int) x, (int) y, null); // erase current coordinates

		// update coordinates
		x += xVel;
		y += yVel;

		g.drawImage(image, (int) x, (int) y, null); // draw new coordinates
	}
}
