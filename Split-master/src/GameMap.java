import java.awt.Graphics;
import java.awt.Image;

public abstract class GameMap {
	
	Image backgroundImage;
	protected boolean gameOver;
	
	public GameMap() {
		super();
	}
	public abstract void tick();
	public abstract void draw(Graphics g);
	public void startGame() {
		gameOver = false;
	}
	public void gameOver() {
		gameOver = true;
	}
}
