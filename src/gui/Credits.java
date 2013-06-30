package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Credits extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private Box names;
	private JButton ret;
	private JLabel name1, name2, name3, name4, name5;
	private Font font = new Font("Arial", Font.BOLD, 35);
	private Font retFont = new Font("Arial", Font.BOLD, 35);
	
	public Credits(JPanel container) {
		this.container = container;
		
		ret = new JButton("Return");
		ret.setFont(retFont);
		ret.setPreferredSize(new Dimension(200,50));
		ret.addActionListener(this);
		ret.setBackground(Color.CYAN);
		
		name1 = new JLabel("Thomas O'Niel");
		name1.setFont(font);
		name2 = new JLabel("Brandon Autrey");
		name2.setFont(font);
		name3 = new JLabel("Matthew Dworkin");
		name3.setFont(font);
		name4 = new JLabel("Stephen	Maguire");
		name4.setFont(font);
		name5 = new JLabel("Jared Wasserman");
		name5.setFont(font);
		
		names = new Box(BoxLayout.Y_AXIS);
		names.add(name1);
		names.add(name2);
		names.add(name3);
		names.add(name4);
		names.add(name5);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		this.add(names, c);
		c.gridy = 1;
		this.add(ret, c);
		
		this.setBackground(Color.PINK);
	}
	
	public void actionPerformed(ActionEvent e) {
		CardLayout cl = (CardLayout)container.getLayout();
		cl.show(container, "title");
		
	}

}
