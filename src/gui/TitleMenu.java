package gui;


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TitleMenu extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private static final int START_ID = 0;
	private static final int CONTROLS_ID = 1;
	private static final int CREDITS_ID = 2;
	private static final int QUIT_ID = 3;
	
	private List<JButton> buttons = new ArrayList<JButton>();  
	
	private JPanel container;
	private JButton test;
	
	
	public TitleMenu(JPanel container) {	
		this.container = container;
		
		test = new JButton("New Game");
		test.setFont(new Font("Arial", Font.BOLD, 18));
		test.setPreferredSize(new Dimension(200,50));
		test.addActionListener(this);
		test.setBackground(Color.BLUE);
		
		add(test);
		setBackground(Color.GREEN);
	}	
	
	public void addButton(int id, int x, int y, int w, int h) {
		
	}

	public void actionPerformed(ActionEvent e) {
		//actions for frame
		if(e.getSource() == test) {
			System.out.println("test button was pressed");
			//CardLayout cl = (CardLayout)container.getLayout();
			//cl.show(container, "game");
		}
	}
		
}
