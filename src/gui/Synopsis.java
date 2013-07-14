package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JPanel;

/**
 * Will display the backstory for the game in the GUI.
 *
 */

public class Synopsis extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Scanner fin; 
	private int counter;
	private String str;
	
	private JPanel container; //used to switch to other panels
	
	public Synopsis(JPanel container) {
		this.container = container;
		
		try {	
			fin = new Scanner(new File("res/backstory.txt"));
			printBackstory();
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(fin != null) fin.close();
		}
	}
	
	private void printBackstory() {
		
		while(fin.hasNext()) {
			str = fin.next();
			counter++;
			System.out.print(str + " ");
			
			//prints new line after so many lines
			if(counter == 10) {
				System.out.println();
				counter = 0;
			}
		}
		
		System.out.println();
	}
}
