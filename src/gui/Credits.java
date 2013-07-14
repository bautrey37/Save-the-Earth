package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Credits extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private JButton ret;
	private JLabel name1, name2, name3, name4, name5;
	private Font font = new Font("Basica v.2012", Font.PLAIN, 35);
	private Font retFont = new Font("Basica v.2012", Font.PLAIN, 30);
	
	public Credits(JPanel container) {
		this.container = container;
		
		//Return Button
		ret = new JButton("Return");
		ret.setFont(retFont);
		ret.setPreferredSize(new Dimension(200,100));
		ret.addActionListener(this);
		ret.setBackground(Color.CYAN);
		
		//Labels
		name1 = new JLabel("Thomas O'Neill");
		name1.setFont(font);
		name2 = new JLabel("Brandon Autrey");
		name2.setFont(font);
		name3 = new JLabel("Matthew Dworkin");
		name3.setFont(font);
		name4 = new JLabel("Stephen	 Maguire");
		name4.setFont(font);
		name5 = new JLabel("Jared Wasserman");
		name5.setFont(font);
		
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
		c.insets = new Insets(40, 0, 20, 0);
		this.add(ret, c);
		
		this.setBackground(Color.BLUE);
	}
	
	public void actionPerformed(ActionEvent e) {
		CardLayout cl = (CardLayout)container.getLayout();
		cl.show(container, "title");
		
	}

}
