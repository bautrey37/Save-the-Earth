/**
 * 
 */
package com.github.cop4331sum13.animation;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class handles creating objects for displaying the images of an explosion animation.
 * The explosion animation created by this class is used for dying aliens, meteors, and when
 * the tank is destroyed.
 * 
 * @author Thomas J. O'Neill
 */
public class Explosion
{
	/**
	 * Contains all images used in the sequence of all explosion animations.
	 */
	private static BufferedImage[] sequence;
	
	/**
	 * Central x coordinate of where this explosion is taking place.
	 */
	private int xPosition;
	
	/**
	 * Central y coordinate of where this explosion is taking place.
	 */
	private int yPosition;
	
	/**
	 * The current frame to display of the explosion.
	 */
	private int frameNum;
	
	/**
	 * Constructs a new explosion object.
	 * 
	 * @param xPos - the x coordinate of this explosion.
	 * @param yPos - the y coordinate of this explosion.
	 */
	public Explosion( double xPos, double yPos)
	{
		this.xPosition = (int)xPos;
		this.yPosition = (int)yPos;
		
		this.frameNum = 0;
	}
	
	
	/**
	 * Return the x coordinate of this object in the plane.
	 * 
	 * @return x coordinate.
	 */
	public int getX()
	{
		return this.xPosition;
	}
	
	
	/**
	 * Return the y coordinate of this object in the plane.
	 * 
	 * @return y coordinate.
	 */
	public int getY()
	{
		return this.yPosition;
	}
	
	
	/**
	 * Obtains the current frame that this explosion object is currently displaying.
	 * 
	 * @return the frame number.
	 */
	public int getFrameNumber()
	{
		return this.frameNum;
	}
	
	
	/**
	 * Increments the frame number.  Used right after a frame is drawn.
	 */
	public void increaseFrameNumber()
	{
		this.frameNum += 1;
	}
	
	
	/**
	 * Returns the image for the frame.
	 * 
	 * @param index - frame to get.
	 * @return image representing frame in question.
	 */
	public BufferedImage getFrameImage( int index )
	{
		return sequence[ index  ];
	}
	
	
	/**
	 * This method initializes the image array with all the images that make up
	 * the explosion animation.  This method MUST be called once prior to running the game.
	 */
	public static void setupSequence()
	{
		//  Initialize array.
		sequence = new BufferedImage[ 14 ];
		
		//  Read in all 14 images for each of the 14 frames of explosion animations.
		for( int index = 1; index <= 14; index++ )
		{
			try
			{
				String infile = "explosion/" + index + ".png";
				sequence[ index - 1 ] = ImageIO.read(Explosion.class.getClassLoader().getResource(infile));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
