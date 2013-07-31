package com.github.cop4331sum13.gui;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Displays the series of backstory images.
 */
public class BackStory extends JPanel implements MouseListener{
	private static final long serialVersionUID = 1L;
	private JPanel container;
	
	private String[] images = {"BackStory/BackStory1.jpg","BackStory/BackStory2.jpg","BackStory/BackStory3.jpg","BackStory/BackStory4.jpg","BackStory/BackStory5.jpg"};
	private int currentImage;
	
	private BufferedImage background[];
	
	public BackStory(JPanel container) {
		this.container = container;
		this.addMouseListener(this);
		currentImage = 0;
		
		background = new BufferedImage[5];
		
		//try to get image from file
		try {
			for(int i = 0; i < 5; i++)
				background[i] = ImageIO.read(this.getClass().getClassLoader().getResource(images[i])); 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This overrides the paintComponent method in JPanel.  It is implicitly called in this class.
	 * @param g - Graphics object
	 */
	@Override
	protected void paintComponent(Graphics g) {
		if (currentImage < images.length){
			super.paintComponent(g); //calls paintComponent in JPanel
			
			g.drawImage(background[currentImage], 0, 0, null);
		}
		else{
			currentImage = 0;
			CardLayout cl = (CardLayout)container.getLayout();
			cl.show(container, "title");
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		++currentImage;
		paintComponent(getGraphics());
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
