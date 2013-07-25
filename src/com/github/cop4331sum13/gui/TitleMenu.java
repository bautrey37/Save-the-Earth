package com.github.cop4331sum13.gui;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.github.cop4331sum13.Game;

/**
 * TBA
 *
 */

public class TitleMenu extends JPanel implements ActionListener, MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	private List<JButton> difButtons = new ArrayList<JButton>(3);  
	private int difficulty;
	
	private JPanel container, difficultyPane, bottomPane, buttons;
	private JButton newGame, quit, credits, controls, easy, medium, hard;
	private Font font = new Font("Xolonium", Font.PLAIN, 25);
	private Font difFont = new Font("Xolonium", Font.PLAIN, 20);
	
	//private Graphics g;
	private BufferedImage logo, background;
	
	public TitleMenu(JPanel container) {
		//container contains the CardLayout.  Used to switch to other panels.
		this.container = container;
		
		//sets default difficulty level to easy.
		this.difficulty = 1;
		
		//Buttons
		newGame = new JButton("Begin Mission");
		setJButtonSettings(newGame, font, 250, 50);
		
		controls = new JButton("Controls");
		setJButtonSettings(controls, font, 200, 50);
		
		credits = new JButton("Credits");
		setJButtonSettings(credits, font, 180, 50);
				
		quit = new JButton("Quit");
		setJButtonSettings(quit, font, 150, 50);
		
		easy = new JButton("Easy");
		setJButtonSettings(easy, difFont, 100, 40);
		easy.setBackground(Color.GREEN);
		
		medium = new JButton("Medium");
		setJButtonSettings(medium, difFont, 120, 40);
		medium.setBackground(Color.YELLOW);
		
		hard = new JButton("Hard");
		setJButtonSettings(hard, difFont, 100, 40);
		hard.setBackground(Color.RED);
		
		//add difficulty level buttons to arraylist
		difButtons.add(easy);
		difButtons.add(medium);
		difButtons.add(hard);
		
		//Grouping Panels
		difficultyPane = new JPanel();
		//FlowLayout(alignment, horizontal gap, vertical gap)
		difficultyPane.setLayout(new FlowLayout(0, 20, 10));
		difficultyPane.add(easy);
		difficultyPane.add(medium);
		difficultyPane.add(hard);
		difficultyPane.setBackground(new Color(255,255,255,0)); //set transparent
		
		bottomPane = new JPanel();
		bottomPane.setLayout(new FlowLayout(0, 20, 0));
		bottomPane.add(controls);
		bottomPane.add(credits);
		bottomPane.add(quit);
		bottomPane.setBackground(new Color(255,255,255,0)); //set transparent
		
		//Groups the grouping panels into a GridBagLayout
		buttons = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		buttons.add(newGame, c);
		c.ipady = 20;
		c.gridy++;
		buttons.add(difficultyPane, c);
		c.ipady = 50;
		c.gridy++;
		buttons.add(bottomPane, c);
		buttons.setBackground(new Color(255,255,255,0));
		
		//adds to top level JPanel
		this.setLayout(new BorderLayout());
		this.setBackground(Color.LIGHT_GRAY);
		this.add(buttons, BorderLayout.SOUTH);
		
		//try to get image from file
		try {
			//URL image = TitleMenu.class.getResource("/res/Save-the-Earth.png");
			logo = ImageIO.read(new File("res/Save-the-Earth.png"));
			background = ImageIO.read(new File("res/STE-Title.jpg")); 
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
		//calculated logo in scale and center of screen based of the height, 350
		g.drawImage(logo, 64, 0, 672, 350, null);
		
	}

	public void actionPerformed(ActionEvent e) {
		//actions for frame
		if(e.getSource() == newGame) {
			CardLayout cl = (CardLayout)container.getLayout();
			cl.show(container, "game"); //shows the game
			
			/*
			 * Next, obtain "game", the specific component in question, in order to begin the game.
			 */
			for( Component comp : container.getComponents() )
			{
				//  If the component is of instance type "Game", call the runGame() method on this component.
				if( comp instanceof Game )
				{
					((Game)comp).init(difficulty);
					break;
				}
			}
		}
		if(e.getSource() == controls) {
			CardLayout cl = (CardLayout)container.getLayout();
			cl.show(container, "controls");
		}
		if(e.getSource() == credits) {
			CardLayout cl = (CardLayout)container.getLayout();
			cl.show(container, "credits");
		}
		if(e.getSource() == quit) {
			System.exit(0);
		}
		if(e.getSource() == easy) {
			difficulty = 1;
			setActiveButton(easy);
		}
		if(e.getSource() == medium) {
			difficulty = 2;
			setActiveButton(medium);
		}
		if(e.getSource() == hard) {
			difficulty = 3;
			setActiveButton(hard);
		}
	}
		
	private void setActiveButton(JButton button) {
		//clear all borders
		for(JButton b: difButtons) {
			b.setBorder(BorderFactory.createEmptyBorder());
		}
		//set button selected to border
		button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
	}
	
	private void setJButtonSettings(JButton button, Font font, int width, int height) {
		button.addActionListener(this); //allows it to be interactive
		button.setFocusPainted(false); //gets rid of small border inside button
		button.setFont(font);
		button.setPreferredSize(new Dimension(width, height)); //sets size
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int mouseX = MouseInfo.getPointerInfo().getLocation().x - GUI.window.getX();
		int mouseY = MouseInfo.getPointerInfo().getLocation().y - GUI.window.getY();
		
		// x = 170 to 630
		// y = 64 to 340
		
		if (170 <= mouseX && mouseX <= 630 && 64 <= mouseY && mouseY <= 340){
			CardLayout cl = (CardLayout)container.getLayout();
			cl.show(container, "story");
		}
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
