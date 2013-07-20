package com.github.cop4331sum13.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.cop4331sum13.Game;

public class Pause extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private JLabel pause;
	private JButton resume, title;
	
	private Font font = new Font("Xolonium", Font.PLAIN, 30);
	private Font Pfont = new Font("Xolonium", Font.PLAIN, 80);
	
	public Pause(JPanel container) {
		this.container = container;
		
		pause = new JLabel("Pause");
		pause.setFont(Pfont);
		
		resume = new JButton("Resume Game");
		setJButtonSettings(resume, font, 350, 70);
		
		title = new JButton("Main Menu");
		setJButtonSettings(title, font, 350, 70);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		//sets the external padding
		c.insets = new Insets(20, 0, 20, 0); //Insets(top, left, bottom, right) 
		this.add(pause, c);
		c.gridy++;
		this.add(resume, c);
		c.gridy++;
		this.add(title, c);
		
		this.setBackground(new Color(50,50,50,100)); //partially transparent, will display over the game
		this.setOpaque(true);
		this.addKeyListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == title) {
			CardLayout lm = (CardLayout)container.getLayout();
			lm.show(container, "title");
		}
		
		if(e.getSource() == resume) {
			CardLayout lm = (CardLayout)container.getLayout();
			lm.show(container, "game");
			
			for(Component comp : container.getComponents()) {
				//  If the component is of instance type "Game", call the runGame() method on this component.
				if(comp instanceof Game)  {
					((Game)comp).start();
					break;
				}
			}
			
		}
	}
	
	private void setJButtonSettings(JButton button, Font font, int width, int height) {
		button.addActionListener(this); //allows it to be interactive
		button.setFocusPainted(false); //gets rid of small border inside button
		button.setFont(font); //sets font
		button.setPreferredSize(new Dimension(width, height)); //sets size
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		System.out.println("Key Pressed");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
