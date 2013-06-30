package enemiesTest;

import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class Window {
	// Canvas and window
	JFrame window;
	static Canvas canvas;
	Graphics g;

	// Size of screen
	private final int WIDTH = 900;
	private final int HEIGHT = 500;

	// Enemies
	static Enemy[] enemies = new Enemy[10];

	public static void main(String[] args) throws Exception {
		new Window();

		// make enemies
		Random r = new Random();
		for (int i = 0; i < enemies.length; ++i) {
			enemies[i] = new Enemy(Math.abs(r.nextInt()) % canvas.getWidth(), Math.abs(r.nextInt())
					% canvas.getHeight());
		}

		while (true) {
			for (Enemy e : enemies) {
				e.move(canvas.getGraphics());
			}

			Thread.sleep(100);
		}
	}

	// GUI Stuff (just for testing enemies, nothing special)
	public Window() {
		// Set up window
		window = new JFrame("Enemies");
		window.setLayout(new FlowLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setSize(WIDTH, HEIGHT);
		window.setBackground(Color.WHITE);
		window.setLocation(200, 100);

		// Set up canvas
		canvas = new Canvas();
		canvas.setBackground(Color.WHITE);
		canvas.setSize(window.getWidth() - 100, window.getHeight() - 100);
		window.add(canvas);

		// Set visible
		window.setVisible(true);
		window.pack();

		// Set up characters
		g = canvas.getGraphics();
	}
}
