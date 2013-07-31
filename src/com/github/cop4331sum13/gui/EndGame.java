package com.github.cop4331sum13.gui;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.github.cop4331sum13.sound.SoundManager;

/**
 * Displays the victory  or defeat screen.
 */
public class EndGame extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private Image background;
	
	public EndGame(JPanel container) {
		this.container = container;
		this.addMouseListener(this);
	}
	
	public void setWinScreen(boolean i) {
		this.requestFocusInWindow();
		//backgrounds are loaded in according to winnin condition to issue right screen is displayed
		if(i) {
			try {
				background = ImageIO.read(this.getClass().getClassLoader().getResource("Congratulations.jpg"));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		else{
			try {
				background = ImageIO.read(this.getClass().getClassLoader().getResource("GameOver.jpg"));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		paintComponent(this.getGraphics());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		CardLayout cl = (CardLayout)container.getLayout();
		cl.show(container, "title");
		SoundManager.stopLevelSoundtrack();
		SoundManager.playTitleSoundtrack();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
}
