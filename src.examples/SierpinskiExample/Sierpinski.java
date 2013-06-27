package SierpinskiExample;

/**
 * Brandon Autrey
 * COP 3330-0001
 * 
 * This program is a visualizer of Sierpinski's Triangle using colors choosen from the user and from a random function.
 * In the random function, a new color is generated for each level up to the max of 10 levels.
 * There are 5 buttons which the user can customize their button choice.  The color displayed on the button is what the user has choosen that color to be.
 * If a level depth greater than 6 is called, the colors loop back into the original color.
 * 
 */

/***********************************************************************
 * This program is supposed to serve as an example of how to use GUI's. 
 ***********************************************************************/

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Sierpinski implements ActionListener {

	private JFrame frame;
	//private String[] colornames = {"Black", "Blue", "Cyan", "Dark Gray", "Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"};
	//private Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
	private static Color col1, col2, col3, col4, col5, col6, col7, col8, col9, col10; 
	private static Color colors[];
	private JCheckBox randomBox;
	//private JComboBox color1, color2, color3, color4, color5;
	private JLabel label1, label2, label3, label4, label5, depth;
	private JButton drawButton, chooser1, chooser2, chooser3, chooser4, chooser5;
	private JTextField setDepth;
	private Canvas triangles;
	private Graphics g;

	private JPanel MainPanel, RPanel, panel1, panel2, panel3, panel4, panel5, panelText;
	private Box box;
	
	private int depthVal = 0;
	
	
	public Sierpinski() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setTitle("Sierpinski Triangle Visualizer");
		frame.setResizable(false); //so the user can't mess up how the program looks
		
		triangles = new Canvas(); //used for drawing the Sierpinski triangle
		triangles.setBackground(Color.BLACK);
		triangles.setSize(512, 512);
		
		depth = new JLabel("Recursive Depth: ");
		setDepth = new JTextField("5", 4);
		depthVal = 5; //default value for depth
		setDepth.addActionListener(this);
		panelText = new JPanel(); //JPanel defaults to flowlayout
		panelText.add(depth);
		panelText.add(setDepth);
		
		randomBox = new JCheckBox("Randomize colors at each level!");
		randomBox.addItemListener(new CheckBoxListener());
		
		drawButton = new JButton("Draw!!");
		drawButton.setPreferredSize(new Dimension(300, 50));
		drawButton.addActionListener(this);
		
		label1 = new JLabel("Color 1 ");
		label2 = new JLabel("Color 2 ");
		label3 = new JLabel("Color 3 ");
		label4 = new JLabel("Color 4 ");
		label5 = new JLabel("Color 5 ");
		
		/*color1 = new JComboBox(colornames);
		color2 = new JComboBox(colornames);
		color3 = new JComboBox(colornames);
		color4 = new JComboBox(colornames);
		color5 = new JComboBox(colornames);*/
		
		col1 = col2 = col3 = col4 = col5 = Color.WHITE; //default values for initial 5 colors
		
		chooser1 = new JButton("");
		chooser1.setPreferredSize(new Dimension(150, 25));
		chooser1.addActionListener(this);
		chooser1.setBackground(col1);
		//chooser1.setRolloverIcon(new ImageIcon());
		
		chooser2 = new JButton("");
		chooser2.setPreferredSize(new Dimension(150, 25));
		chooser2.addActionListener(this);
		chooser2.setBackground(col2);
		
		chooser3 = new JButton("");
		chooser3.setPreferredSize(new Dimension(150, 25));
		chooser3.addActionListener(this);
		chooser3.setBackground(col3);
		
		chooser4 = new JButton("");
		chooser4.setPreferredSize(new Dimension(150, 25));
		chooser4.addActionListener(this);
		chooser4.setBackground(col4);
		
		chooser5 = new JButton("");
		chooser5.setPreferredSize(new Dimension(150, 25));
		chooser5.addActionListener(this);
		chooser5.setBackground(col5);
		
		//groups the labels and buttons together
		panel1 = new JPanel();
		panel1.add(label1);
		panel1.add(chooser1);
		
		panel2 = new JPanel();
		panel2.add(label2);
		panel2.add(chooser2);
		
		panel3 = new JPanel();
		panel3.add(label3);
		panel3.add(chooser3);
		
		panel4 = new JPanel();
		panel4.add(label4);
		panel4.add(chooser4);
		
		panel5 = new JPanel();
		panel5.add(label5);
		panel5.add(chooser5);
		
		//groups the 5 panels into a box
		box = new Box(BoxLayout.Y_AXIS);
		box.add(panel1);
		box.add(panel2);
		box.add(panel3);
		box.add(panel4);
		box.add(panel5);
		
		//GridBagLayout is useful because it centers my buttons and aligns them vertically, for my case using it was relatively simple
		RPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		RPanel.add(panelText, c);
		c.gridy = 1;
		RPanel.add(box, c);
		c.gridy = 2;
		RPanel.add(randomBox, c);
		c.gridy = 3;
		RPanel.add(drawButton, c);
		RPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); //empty border around right area
		
		//top layer is BorderLayout, used for separating the button area and the canvas area
		MainPanel = new JPanel();
		MainPanel.add(triangles, BorderLayout.CENTER);
		MainPanel.add(RPanel, BorderLayout.EAST);	
		MainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); //creates space around window
		
		frame.add(MainPanel); //main panel holds the borderlayout and is the content pane for my program
		frame.pack();
		frame.setLocationRelativeTo(null); //sets windows location to center of screen
		g = triangles.getGraphics();
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == drawButton) {
			//sets the values for the color array to be passed into the draw function, these values change a lot so its critical it is here right before draw is called
			colors = new Color[]{col1, col2, col3, col4, col5, col6, col7, col8, col9, col10}; 
			draw(depthVal, 0, 0, 512, depthVal);
		}
		
		if(e.getSource() == setDepth) {
			
			try {
				String string = e.getActionCommand(); //gets string value in text field
				depthVal = Integer.parseInt(string); //converts string to integer
				//maximum value of 10 is allowed for the depth
				if(depthVal > 20) {
					depthVal = 10;
					JOptionPane.showMessageDialog(null, String.format("Depth value entered is bigger than 10; Depth set to %d", depthVal));
				}
				/*else
					JOptionPane.showMessageDialog(null, String.format("Depth = %d", depthVal));*/
			}  //these catch executes whenever letters are present in the string
			catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Please only enter numbers into the field");
				depthVal = 0;
			}
		}
		
		//when the color buttons are clicked it called the JColorChooser dialog pane to return a new color to be stored
		if(e.getSource() == chooser1) {
			col1 = JColorChooser.showDialog(null, "Pick a new color", col1);
			//if cancel is pressed in the dialog pane, then null is returned, this sets the default value to color when that happens
			if(col1 == null) 
				col1 = Color.WHITE;
			
			setButtonColor(col1, chooser1);
		}
		
		if(e.getSource() == chooser2) {
			col2 = JColorChooser.showDialog(null, "Pick a new color", col2);
			if(col2 == null) 
				col2 = Color.WHITE;
			
			setButtonColor(col2, chooser2);
		}
		
		if(e.getSource() == chooser3) {
			col3 = JColorChooser.showDialog(null, "Pick a new color", col3);
			if(col3 == null) 
				col3 = Color.WHITE;
			
			setButtonColor(col3, chooser3);
		}
		
		if(e.getSource() == chooser4) {
			col4 = JColorChooser.showDialog(null, "Pick a new color", col4);
			if(col4 == null) 
				col4 = Color.WHITE;
			
			setButtonColor(col4, chooser4);
		}
		
		if(e.getSource() == chooser5) {
			col5 = JColorChooser.showDialog(null, "Pick a new color", col5);
			if(col5 == null) 
				col5 = Color.WHITE;
			
			setButtonColor(col5, chooser5);
		}
	}
	
	private class CheckBoxListener implements ItemListener {
		
		public void itemStateChanged(ItemEvent event) {
									
			if(randomBox.isSelected()) {
				//disables original buttons 
				chooser1.setEnabled(false);
				chooser2.setEnabled(false);
				chooser3.setEnabled(false);
				chooser4.setEnabled(false);
				chooser5.setEnabled(false);
				
				//5 different random colors and sets it to the button background
				col1 = setButtonColor(getRandColor(), chooser1);
				col2 = setButtonColor(getRandColor(), chooser2);
				col3 = setButtonColor(getRandColor(), chooser3);
				col4 = setButtonColor(getRandColor(), chooser4);
				col5 = setButtonColor(getRandColor(), chooser5);
				//5 new random colors so it doesn't repeat the original 5
				col6 = Color.getHSBColor((float)Math.random(), 1.0f, 1.0f);
				col7 = Color.getHSBColor((float)Math.random(), 1.0f, 1.0f);
				col8 = Color.getHSBColor((float)Math.random(), 1.0f, 1.0f);
				col9 = Color.getHSBColor((float)Math.random(), 1.0f, 1.0f);
				col10 = Color.getHSBColor((float)Math.random(), 1.0f, 1.0f);
				
			}
			else {
				//re-enables buttons
				chooser1.setEnabled(true);
				chooser2.setEnabled(true);
				chooser3.setEnabled(true);
				chooser4.setEnabled(true);
				chooser5.setEnabled(true);
			}				
		}
	}
	
	//generates a random color using HSB
	private Color getRandColor() {
		return Color.getHSBColor((float)Math.random(), 1.0f, 1.0f);
	}
	
	private Color setButtonColor(Color color, JButton button) {
		button.setBackground(color);
		return color;
	}
	
	
	//initial call is draw(d, 0, 0, 512)
	void draw(int d, int x, int y, int S, int i) {
		if(d == 0) return;
		
		
		//last color in array needs to be the first to display
		//when random box is selected use up the 10 random colors
		if(randomBox.isSelected()) {
			g.setColor(colors[i - d]);
		}
		
		else {
			//keeps color value within 0-4 and then resets d and i
			if((i - d) >= 5) { //when i is high and d is low
				//resets i so the value i - d is within the array.  EX: 6 - 1 = 5 which is out of bounds
				i = i - 5;
				g.setColor(colors[i - d]);
				i = i + 5;
			}
			else 
				g.setColor(colors[i - d]);
		}
		
		//the 3 points for which to draw the triangle based on the box
		int[] xPoints = {x + S/2, x, x + S};
		int[] yPoints = {y, y + S, y + S};
		
		g.fillPolygon(xPoints, yPoints, 3);
		
		//recursive calls to reset the top left corner and size of box
		draw(d - 1, x + S/4, y, S/2, i);
		draw(d - 1, x, y + S/2, S/2, i);
		draw(d - 1, x + S/2, y + S/2, S/2, i);
	}
	
	//calls constructor to set up the GUI and action events
	public static void main(String[] args) {
		new SierpinskiExample.Sierpinski();
	}

}
