package gui;


import gameMain.Game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

public class TitleMenu extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	//private List<JButton> buttons = new ArrayList<JButton>();  
	
	private JPanel container, difficultyPane, bottomPane, top, buttons;
	private JButton newGame, quit, credits, controls, easy, medium, hard;
	private Font font = new Font("Bodoni MT", Font.BOLD, 30);
	private Font difFont = new Font("Bodomu MT", Font.BOLD, 20);
	
	private Graphics g;
	private BufferedImage background;
	
	private int difficulty;
	
	public TitleMenu(JPanel container) {
		//container contains the CardLayout.  Used to switch to other panels.
		this.container = container;
		
		//Buttons
		newGame = new JButton("New Game");
		newGame.setFont(font);
		newGame.setPreferredSize(new Dimension(200,50));
		newGame.addActionListener(this);
		
		controls = new JButton("Controls");
		controls.setFont(font);
		controls.setPreferredSize(new Dimension(200,50));
		controls.addActionListener(this);
		
		credits = new JButton("Credits");
		credits.setFont(font);
		credits.setPreferredSize(new Dimension(200,50));
		credits.addActionListener(this);
		
		quit = new JButton("Quit");
		quit.setFont(font);
		quit.setPreferredSize(new Dimension(200,50));
		quit.addActionListener(this);
		
		easy = new JButton("Easy");
		easy.setBackground(Color.GREEN);
		easy.setFont(difFont);
		
		medium = new JButton("Medium");
		medium.setBackground(Color.YELLOW);
		medium.setFont(difFont);
		
		hard = new JButton("Hard");
		hard.setBackground(Color.RED);
		hard.setFont(difFont);
		
		//Grouping Panels
		difficultyPane = new JPanel();
		difficultyPane.add(easy);
		difficultyPane.add(medium);
		difficultyPane.add(hard);
		difficultyPane.setBackground(new Color(255,255,255,0)); //set transparent
		
		bottomPane = new JPanel();
		bottomPane.add(controls);
		bottomPane.add(credits);
		bottomPane.add(quit);
		bottomPane.setBackground(new Color(255,255,255,0)); //set transparent
		
		top = new JPanel();
		top.setMaximumSize(new Dimension(GUI.gameWidth, GUI.gameHeight/4));
		top.setMinimumSize(new Dimension(GUI.gameWidth, GUI.gameHeight/4));
		top.setBackground(Color.BLUE);
		
		//Groups the grouping panels into a GridBagLayout
		buttons = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		//c.weighty = 2.0;
		buttons.add(newGame, c);
		c.ipady = 50;
		c.gridy++;
		buttons.add(difficultyPane, c);
		//c.ipady = 100;
		c.gridy++;
		buttons.add(bottomPane, c);
		buttons.setBackground(new Color(255,255,255,0));
		
		//adds to top level JPanel
		this.setLayout(new BorderLayout());
		this.setBackground(Color.GRAY);
		this.add(top,BorderLayout.NORTH);
		this.add(buttons, BorderLayout.SOUTH);
		
		//try to get image from file
//		try {
//			background = ImageIO.read(new File("Save-the-Earth.png"));
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		paintComponent(g);
	}	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}

	public void actionPerformed(ActionEvent e) {
		//actions for frame
		if(e.getSource() == newGame) {
			CardLayout cl = (CardLayout)container.getLayout();
			cl.show(container, "game");
			
			
			/*
			 * Next, obtain "game", the specific component in question, in order to begin the game.
			 */
			for( Component comp : container.getComponents() )
			{
				//  If the component is of instance type "Game", call the runGame() method on this component.
				if( comp instanceof Game )
				{
					( (Game) comp ).runGame( difficulty );
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
			credits.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		}
		if(e.getSource() == quit) {
			System.exit(0);
		}
		if(e.getSource() == easy) {
			difficulty = 1;
			easy.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		}
		if(e.getSource() == medium) {
			difficulty = 2;
		}
		if(e.getSource() == hard) {
			difficulty = 3;
		}
	}
		
}
