package cop4331;

public class TitleMenu extends GuiMenu {

	private static final int START_ID = 00;
	private static final int CONTROLS_ID = 01;
	private static final int CREDITS_ID = 02;
	private static final int QUIT_ID = 03;
	
	private final int gameWidth;
	private final int gameHeight;
	
	public TitleMenu(int gameWidth, int gameHeight) {
		super();
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
	}

	
	
}
