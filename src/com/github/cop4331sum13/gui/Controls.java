package com.github.cop4331sum13.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Displays the Control Menu.
 * 
 * @author Earth's Defenders
 */
public class Controls extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private JButton ret;
	private JLabel title, con1, con2, con3, con4, con5, con6, con7;
	
	private Font head = new Font("Xolonium", Font.PLAIN, 50);
	private Font text = new Font("Xolonium", Font.PLAIN, 30);
	
	private BufferedImage background;
	
	/**
	 * Sets up Control screen.
	 * @param container
	 */
	public Controls(JPanel container) {
		this.container = container;
		
		//set up return button
		ret = new JButton("Return");
		ret.setFont(text);
		ret.setPreferredSize(new Dimension(200,50));
		ret.addActionListener(this);
				
		title = new JLabel(" ");
		title.setFont(head);
		
		//sets spacing for return button
		con1 = new JLabel(" ");
		con1.setFont(text);
		con2 = new JLabel(" ");
		con2.setFont(text);
		con3 = new JLabel(" ");
		con3.setFont(text);
		con4 = new JLabel(" ");
		con4.setFont(text);
		con5 = new JLabel(" ");
		con5.setFont(text);
		con6 = new JLabel(" ");
		con6.setFont(text);
		con7 = new JLabel(" ");
		con7.setFont(text);
		
		// aligns the layout
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		this.add(title, c);
		c.gridy = 1;
		this.add(con1, c);
		c.gridy = 2;
		this.add(con2, c);
		c.gridy = 3;
		this.add(con3, c);
		c.gridy = 4;
		this.add(con4, c);
		c.gridy = 5;
		this.add(con5, c);
		c.gridy = 6;
		this.add(con6, c);
		c.gridy = 7;
		this.add(con7, c);
		c.gridy = 8;
		c.insets = new Insets(40, 0, 0, 0);
		this.add(ret, c);
		
		this.setBackground(Color.GREEN);
		
		//try to get image from file
		try {
			background = ImageIO.read(this.getClass().getClassLoader().getResource("STE-Controls.jpg")); 
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
		super.paintComponent(g); //calls paintComponent in JPanel
		g.drawImage(background, 0, 0, null);
		
	}
	
	/**
	 * When button is pressed, switch to the title screen
	 * @param e - button action event
	 */
	public void actionPerformed(ActionEvent e) {
		CardLayout cl = (CardLayout)container.getLayout();
		cl.show(container, "title");
		
	}

}
