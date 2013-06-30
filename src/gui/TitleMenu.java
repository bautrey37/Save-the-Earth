package gui;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TitleMenu extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	//private List<JButton> buttons = new ArrayList<JButton>();  
	
	private JPanel container, difficultyPane, bottomPane;
	private JButton newGame, quit, credits, controls, easy, medium, hard;
	private Font font = new Font("Arial", Font.BOLD, 30);
	private Font difFont = new Font("Arial", Font.BOLD, 20);
	
	private int difficulty;
	
	public TitleMenu(JPanel container) {	
		this.container = container;
		
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
		
		difficultyPane = new JPanel();
		difficultyPane.add(easy);
		difficultyPane.add(medium);
		difficultyPane.add(hard);
		
		bottomPane = new JPanel();
		bottomPane.add(controls);
		bottomPane.add(credits);
		bottomPane.add(quit);

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		this.add(newGame, c);
		c.gridy = 1;
		this.add(difficultyPane, c);
		c.gridy = 2;
		this.add(bottomPane, c);
		
		this.setBackground(Color.GRAY);
	}	
	
//	public void addButton(int id, int x, int y, int w, int h) {
//	
//	}

	public void actionPerformed(ActionEvent e) {
		//actions for frame
		if(e.getSource() == newGame) {
			CardLayout cl = (CardLayout)container.getLayout();
			cl.show(container, "game");
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
		}
		if(e.getSource() == medium) {
			difficulty = 2;
		}
		if(e.getSource() == hard) {
			difficulty = 3;
		}
	}
		
}
