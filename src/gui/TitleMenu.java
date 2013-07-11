package gui;


import gameMain.Game;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * TBA
 *
 */

public class TitleMenu extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	//private List<JButton> buttons = new ArrayList<JButton>();  
	
	private JPanel container, difficultyPane, bottomPane, buttons;
	private JButton newGame, quit, credits, controls, easy, medium, hard;
	private Font font = new Font("Bodoni MT", Font.BOLD, 30);
	private Font difFont = new Font("Bodomu MT", Font.BOLD, 20);
	
	//private Graphics g;
	private BufferedImage logo, background;
	
	private int difficulty;
	
	public TitleMenu(JPanel container) {
		//container contains the CardLayout.  Used to switch to other panels.
		this.container = container;
		
		//Buttons
		newGame = new JButton("New Game");
		setJButtonSettings(newGame, font, 200, 50);
		
		controls = new JButton("Controls");
		setJButtonSettings(controls, font, 150, 50);
		
		credits = new JButton("Credits");
		setJButtonSettings(credits, font, 150, 50);
				
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
			logo = ImageIO.read(new File("res/Save-the-Earth.png"));
			background = ImageIO.read(new File("res/STE-Title.jpg")); //this image looks silly as title image lol
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
			//if()
			easy.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		}
		if(e.getSource() == medium) {
			difficulty = 2;
		}
		if(e.getSource() == hard) {
			difficulty = 3;
		}
	}
		
	private void setJButtonSettings(JButton button, Font font, int width, int height) {
		button.addActionListener(this); //allows it to be interactive
		button.setFocusPainted(false); //gets rid of small border inside button
		button.setFont(font);
		button.setPreferredSize(new Dimension(width, height)); //sets size
	}
}
