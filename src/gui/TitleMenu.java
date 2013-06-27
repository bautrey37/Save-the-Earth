package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TitleMenu extends GUI implements ActionListener {

	private static final int START_ID = 0;
	private static final int CONTROLS_ID = 1;
	private static final int CREDITS_ID = 2;
	private static final int QUIT_ID = 3;
	
	private List<JButton> buttons = new ArrayList<JButton>();  
	
	private JButton test;
	
	private JFrame title;
	
	public TitleMenu(JFrame title) {
		this.title = title;
		
		test = new JButton("New Game");
		test.setFont(new Font("Arial", Font.BOLD, 18));
		test.setPreferredSize(new Dimension(100,50));
		test.addActionListener(this);
		test.setBackground(Color.BLUE);
		
		title.add(test);
		title.setVisible(true);
	}	
	
	public void addButton(int id, int x, int y, int w, int h) {
		
	}

	public void actionPerformed(ActionEvent e) {
		//actions for frame
	}
		
}
