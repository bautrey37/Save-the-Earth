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
 * Displays the Credits Menu.
 * 
 * @author Earth's Defenders
 */
public class Credits extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private JButton ret;
	private JLabel name1, name2, name3, name4, name5, name6, name7, name8;
	private Font font = new Font("Xolonium", Font.PLAIN, 35);
	private Font retFont = new Font("Xolonium", Font.PLAIN, 30);
	
	private BufferedImage background;
	
	/**
	 * Sets up the Credit screen
	 * @param container - used to switch between the GUI screens. 
	 */
	public Credits(JPanel container) {
		this.container = container;
		
		//Return Button
		ret = new JButton("Return");
		ret.setFont(retFont);
		ret.setPreferredSize(new Dimension(200,50));
		ret.addActionListener(this);
		
		// spacing
		name1 = new JLabel(" ");
		name1.setFont(font);
		name2 = new JLabel(" ");
		name2.setFont(font);
		name3 = new JLabel(" ");
		name3.setFont(font);
		name4 = new JLabel(" ");
		name4.setFont(font);
		name5 = new JLabel(" ");
		name5.setFont(font);
		name6 = new JLabel(" ");
		name6.setFont(font);
		name7 = new JLabel(" ");
		name7.setFont(font);
		name8 = new JLabel(" ");
		name8.setFont(font);
		
		//Groups into GridBagLayout
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		this.add(name1, c);
		c.gridy++;
		this.add(name2, c);
		c.gridy++;
		this.add(name3, c);
		c.gridy++;
		this.add(name4, c);
		c.gridy++;
		this.add(name5, c);
		c.gridy++;
		this.add(name6, c);
		c.gridy++;
		this.add(name7, c);
		c.gridy++;
		this.add(name8, c);
		c.gridy++;
		c.insets = new Insets(40, 0, 20, 0);
		this.add(ret, c);
		
		this.setBackground(Color.BLUE);
		
		//try to get image from file
		try {
			background = ImageIO.read(this.getClass().getClassLoader().getResource("STE-Credits.jpg")); 
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
	 * When return button is clicked, go to TitleMenu
	 * @param e - button event
	 */
	public void actionPerformed(ActionEvent e) {
		CardLayout cl = (CardLayout)container.getLayout();
		cl.show(container, "title");
		
	}

}
