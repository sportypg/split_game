import java.awt.Graphics;
import java.awt.Rectangle;


public abstract class Divider implements Collidable {

	protected int length;
	protected Vector location;
	protected final int SPEED = 10;
	protected final int DIMS = 10;
	protected Rectangle boundingRect;
	protected boolean stopGrowing = false;
	protected Polygon map;
	protected Ball ball;
	protected SplitGameMap gm;

	protected Rectangle getBoundingRect() {
		return boundingRect;
	}
	protected void updatePolygon(Polygon newMap) {
		map = newMap;
	}
	protected int getLength() {
		return length;
	}
	protected abstract void dividerSplit();

	protected abstract void updateRect();

	protected abstract void grow();

	protected abstract void draw(Graphics g);

	protected int getDims() {
		return DIMS;
	}
}
