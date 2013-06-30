package gui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Pause implements ActionListener {

	private JPanel container;
	private JLabel pause;
	private JButton resume, title;
	
	public Pause(JPanel container) {
		this.container = container;
		
		pause = new JLabel("Pause");
		
		resume = new JButton("Resume");
		
		title = new JButton("Main Menu");
		title.addActionListener(this);
		//add buttons 
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == title) {
			CardLayout lm = (CardLayout)container.getLayout();
			lm.show(container, "title");
		}
	}
}
