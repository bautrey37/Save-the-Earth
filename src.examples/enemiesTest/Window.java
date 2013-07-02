package enemiesTest;

import java.awt.*;

import javax.swing.*;

public class Window {
	// Canvas and window
	JFrame window;
	static BufferedCanvas canvas;
	Graphics g;

	// Size of screen
	private final int WIDTH = 800;
	private final int HEIGHT = WIDTH * 3 / 4;

	// Enemies
	protected static Enemy[] enemies = new Enemy[5];

	public static void main(String[] args) throws Exception {
		new Window();

		// make enemies
		for (int i = 0; i < enemies.length; ++i) {
			enemies[i] = new Enemy(canvas.getWidth());
		}

		while (true) {
			for (Enemy e : enemies) {
				e.move(canvas.getWidth(), canvas.getHeight());
			}
			canvas.update(canvas.getGraphics());
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

		// Set up canvas
		canvas = new BufferedCanvas(enemies);
		canvas.setBackground(Color.WHITE);
		canvas.setSize(window.getWidth(), window.getHeight());
		window.add(canvas);

		// Set visible
		window.setVisible(true);
		window.pack();
	}
}
