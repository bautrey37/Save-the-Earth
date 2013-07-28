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
 * This class handles reading in and playing the sound effects for the game.
 * 
 * @author Thomas J. O'Neill
 */
public class SoundManager
{
	/**
	 * Manages the sound stream to play.
	 */
	private static Clip soundClip;
	
	
	
	/**
	 * Initializes all sound variables with the sound files to use during the game.
	 * 
	 * (NOTE:  May not be necessary if file is to be read in every time the sound is played.)
	 */
	public static void configureSounds()
	{
		//  Place holder method.  Use if it can be found such that a sound file can be
		//  read in once and only once, but they played back as many times as needed.
	}
	
	
	
	/**
	 * This method takes in the name of the sound clip that is desired to be play,
	 * imports the file itself, and then plays that sound clip through the user's 
	 * speakers, if available.  Method does not override volume levels and, instead,
	 * uses the current volume level set by the OS of the user's computer.
	 * 
	 * @param fileName - name of sound clip to read in and play.
	 */
	private static void playSoundClip( String fileName )
	{
		//  Obtain sound file.
		try {
			//  Initialize clip object.
			soundClip = AudioSystem.getClip();
			
			//  Create and initialize streaming object with sound file.
			String inFile = "res/sound/" + fileName + ".wav";
			AudioInputStream soundClipTemp = AudioSystem.getAudioInputStream( new File( inFile ) );
			
			//  Add sound file to the player.
			soundClip.open( soundClipTemp );
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
		
		//  Play sound file.
		soundClip.start();
		
		
	}
	
	
	
	/**
	 * Play the sound for firing the tank cannon.
	 */
	public static void playTankFire()
	{
		playSoundClip( "cannon" );
		
		
	}
	
	
	
	/**
	 * Play the sound for explosions on screen.
	 */
	public static void playExplosion()
	{
		playSoundClip( "explosion" );
		
		
	}
	
	
	
	/**
	 * Play the sound for firing the alien lasers.
	 */
	public static void playLaser()
	{
		playSoundClip( "laser" );
		
		
	}
	
	
	
	/**
	 * Play the sound for kamikaze strike from small aliens.
	 */
	public static void playKamikaze()
	{
		playSoundClip( "kamikaze" );
		
		
	}
	
	
	
}
