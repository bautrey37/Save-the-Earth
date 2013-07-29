package com.github.cop4331sum13.gui;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameCompleted extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private BufferedImage background;
	
	public GameCompleted(JPanel container){
		this.container = container;
		this.addMouseListener(this);
		
		try {
			background = ImageIO.read(new File("res/Congratulations.jpg"));
		} catch(Exception e) {
			e.printStackTrace();
		}
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
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
}
