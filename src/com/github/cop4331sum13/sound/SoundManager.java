/**
 * 
 */
package com.github.cop4331sum13.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class handles reading in and playing the sound effects for the game that
 * the user is playing.
 * 
 * @author Thomas J. O'Neill
 */
public class SoundManager
{
	/**
	 * Holds the sound clip for when the cannon fires.
	 */
	private static Clip tankFire;
	
	/**
	 * Holds the sound clip for when an explosion occurs.
	 */
	private static Clip explosion;
	
	
	/**
	 * Initializes all sound variables with the sound files to use during the game.
	 * 
	 * (NOTE:  May not be necessary if file is to be read in everytime the sound is played.)
	 */
	public static void configureSounds()
	{
		try {
			tankFire = AudioSystem.getClip();
			AudioInputStream tankFireTemp;
			tankFireTemp = AudioSystem.getAudioInputStream( new File("res/sound/cannon.wav") );
			tankFire.open( tankFireTemp );
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * Read in and play the sound for firing the tank cannon.
	 */
	public static void playTankFire()
	{
		//  Obtain sound file.
		try {
			//  Initialize clip object.
			tankFire = AudioSystem.getClip();
			
			//  Create and initialize streaming object with sound file.
			AudioInputStream tankFireTemp = AudioSystem.getAudioInputStream( new File("res/sound/cannon.wav") );
			
			//  Add sound file to the player.
			tankFire.open( tankFireTemp );
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
		
		//  Play sound file.
		tankFire.start();
	}
	
	
	
	/**
	 * Read in and play the sound for explosions on screen.
	 */
	public static void playExplosion()
	{
		//  Obtain sound file.
		try {
			//  Initialize clip object.
			explosion = AudioSystem.getClip();
			
			//  Create and initialize streaming object with sound file.
			AudioInputStream explosionTemp = AudioSystem.getAudioInputStream( new File("res/sound/explosion.wav") );
			
			//  Add sound file to the player.
			explosion.open( explosionTemp );
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
		
		//  Play sound file.
		explosion.start();
	}
	
}
