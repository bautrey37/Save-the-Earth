package com.github.cop4331sum13.gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameCompleted extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private BufferedImage background;
	
	public GameCompleted(JPanel container){
		this.container = container;
		
		try {
			background = ImageIO.read(new File("res/Congratulations.jpg"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}


	public void actionPerformed(ActionEvent e) {
		
	}

}
