package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Controls extends JPanel implements ActionListener {

	private JPanel container;
	private JButton ret;
	private JLabel title, con1, con2, con3;
	
	private Font head = new Font("Arial", Font.BOLD, 50);
	private Font text = new Font("Arial", Font.BOLD, 30);
	
	public Controls(JPanel container) {
		this.container = container;
		
		ret = new JButton("Return");
		ret.setFont(text);
		ret.setPreferredSize(new Dimension(200,100));
		ret.setLocation(100, 200);
		ret.addActionListener(this);
		ret.setBackground(Color.YELLOW);
				
		title = new JLabel("Controls");
		title.setFont(head);
		
		con1 = new JLabel("To move, use the arrow keys");
		con1.setFont(text);
		con2 = new JLabel("To fire tank, press space");
		con2.setFont(text);
		con3 = new JLabel("To aim tank, use mouse");
		con3.setFont(text);
		
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
		this.add(ret, c);
		
		this.setBackground(Color.MAGENTA);
	}
	
	public void actionPerformed(ActionEvent e) {
		CardLayout cl = (CardLayout)container.getLayout();
		cl.show(container, "title");
		
	}

}
