package com.github.cop4331sum13.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class handles reading in and playing the sound effects for the game.
 * 
 * @author Earth's Defenders
 */
public class SoundManager
{
	/**
	 * Manages the sound stream to play.
	 */
	private static Clip soundClip, levelSoundtrack, titleSoundtrack;
	
	/**
	 * Keeps track of song position when user pauses the game.
	 */
	private static int pausePosition;
	
	
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
			String inFile = "sound/" + fileName + ".wav";
			AudioInputStream soundClipTemp = AudioSystem.getAudioInputStream(SoundManager.class.getClassLoader().getResource(inFile));
			
			//  Add sound file to the player.
			soundClip.open( soundClipTemp );
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		
		//  Play sound file.
		soundClip.start();	
	}
	
	
	
	/**
	 * Plays the sound track for when the user is in-game.
	 * 
	 * @param fileName - name of level sound file.
	 */
	private static void playLevelSoundtrack( String fileName )
	{
		//  Obtain sound file.
		try {
			//  Initialize clip object.
			levelSoundtrack = AudioSystem.getClip();
			
			//  Create and initialize streaming object with sound file.
			String inFile = "sound/background/" + fileName + ".wav";
			AudioInputStream soundClipTemp = AudioSystem.getAudioInputStream(SoundManager.class.getClassLoader().getResource(inFile));
			
			//  Add sound file to the player.
			levelSoundtrack.open( soundClipTemp );
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		
		//  Play sound file.
		levelSoundtrack.start();		
	}
	
	
	
	/**
	 * Plays the sound track for when the user is on the main menu/controls/credits screens.
	 * 
	 * @param fileName - name of title sound file.
	 */
	private static void playTitleSoundtrack( String fileName )
	{
		//  Obtain sound file.
		try {
			//  Initialize clip object.
			titleSoundtrack = AudioSystem.getClip();
			
			//  Create and initialize streaming object with sound file.
			String inFile = "sound/background/" + fileName + ".wav";
			AudioInputStream soundClipTemp = AudioSystem.getAudioInputStream(SoundManager.class.getClassLoader().getResource(inFile));
			
			//  Add sound file to the player.
			titleSoundtrack.open( soundClipTemp );
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		
		//  Play sound file.
		titleSoundtrack.start();	
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
	
	
	/**
	 * Play the sound track for the in-game level.
	 */
	public static void playLevelSoundtrack()
	{
		playLevelSoundtrack( "Five Armies" );
	}
	
	
	/**
	 * Pause the sound track for the in-game level and record the position that was left from.
	 */
	public static void pauseLevelSoundtrack()
	{
		pausePosition = levelSoundtrack.getFramePosition();
		levelSoundtrack.stop();
	}
	
	
	/**
	 * Resume the sound track for the in-game level when the user
	 * clicks "resume" on the pause menu.
	 */
	public static void resumeLevelSoundtrack()
	{
		playLevelSoundtrack( "Five Armies" );
		levelSoundtrack.setFramePosition( pausePosition );
	}
	
	
	/**
	 * Completely stops playing the sound track for the in-game level.
	 */
	public static void stopLevelSoundtrack()
	{
		levelSoundtrack.stop();
	}
	
	
	/**
	 * Plays the sound track for the main menu and sets that sound track to loop.
	 */
	public static void playTitleSoundtrack()
	{
		playTitleSoundtrack( "Movement Proposition" );
		titleSoundtrack.loop( Clip.LOOP_CONTINUOUSLY );
	}
	
	
	/**
	 * Completely stops playing the sound track for the title screens.
	 */
	public static void stopTitleSoundtrack()
	{
		titleSoundtrack.stop();
	}
	
	
}
