package entities;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends Projectile {

	public Bullet(double xPos, double yPos, double xVel, double yVel) {
		super(xPos, yPos, xVel, yVel);
		
		try {
			image = ImageIO.read(new File(""));
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void 

}
