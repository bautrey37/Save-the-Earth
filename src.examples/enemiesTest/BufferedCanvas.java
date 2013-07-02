package enemiesTest;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

@SuppressWarnings("serial")
public class BufferedCanvas extends Canvas {
	protected static Enemy[] enemies;

	@SuppressWarnings("static-access")
	public BufferedCanvas(Enemy[] enemies) {
		super();
		this.enemies = enemies;
	}

	public void update(Graphics g) {
		Graphics offg;
		Image offScreen = null;

		// Initialize off screen image
		offScreen = createImage(getWidth(), getHeight());
		offg = offScreen.getGraphics();

		// Clear old image
		offg.setColor(getBackground());
		offg.fillRect(0, 0, getWidth(), getHeight());
		paint(offg);

		// Draw new enemies
		for (Enemy e : enemies) {
			offg.drawImage(e.getSprite(), (int) (e.getX() - e.getSprite().getWidth() / 2), (int) (e.getY() - e
					.getSprite().getHeight() / 2), null);
		}

		// Make off screen image visible
		g.drawImage(offScreen, 0, 0, this);
	}
}
