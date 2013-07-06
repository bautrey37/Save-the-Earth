package enemiesTest;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class BufferedCanvas extends Canvas {
	protected static Enemy[] enemies;
	Image background;

	@SuppressWarnings("static-access")
	public BufferedCanvas(Enemy[] enemies) {
		super();
		this.enemies = enemies;
		try {
			background = ImageIO.read(new File("res/STE-Background-1.jpg"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Graphics g) {
		Graphics offg;
		Image offScreen = null;

		// Initialize off screen image
		offScreen = createImage(getWidth(), getHeight());
		offg = offScreen.getGraphics();

		// Clear old image
		paint(offg);
		offg.drawImage(background, 0, 0, null);

		// Draw new enemies
		for (Enemy e : enemies) {
			offg.drawImage(e.getSprite(), (int) (e.getX() - e.getSprite().getWidth() / 2), (int) (e.getY() - e
					.getSprite().getHeight() / 2), null);
		}

		// Make off screen image visible
		g.drawImage(offScreen, 0, 0, this);
	}
}
