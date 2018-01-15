import java.awt.Graphics;
import java.awt.Rectangle;

public class HorizontalDivider extends Divider {

	private boolean leftHit = false;
	private boolean rightHit = false;

	public HorizontalDivider(int x, int y, Polygon poly, Ball b, SplitGameMap gameMap) {
		this.gm = gameMap;
		this.ball = b;
		this.map = poly;
		this.location = new Vector(x, y);
		this.length = 0;
		this.boundingRect = new Rectangle(x, y, length, DIMS);
	}

	@Override
	public void collided(Ball b) {
		if (getBoundingRect().intersects(b.getBoundingRect())) {
			if (!stopGrowing) {
				b.setAlive(false);
			}
		}
	}

	@Override
	protected void draw(Graphics g) {
		this.updateRect();
		g.fillRect(location.getX(), location.getY(), length, DIMS);
	}

	protected void updateRect() {
		this.boundingRect = new Rectangle(location.getX(), location.getY(), length, DIMS);
	}

	@Override
	protected void grow() {
		leftHit = !map.inside(this.location.getX(), this.location.getY());
		rightHit = !map.inside(this.location.getX() + length, this.location.getY());
		if(!leftHit && !rightHit) {
			location.setX(location.getX() - SPEED);
			length += SPEED * 2;
		}
		else if(leftHit && !rightHit) {
			length += SPEED;
		}
		else if(rightHit && !leftHit) {
			location.setX(location.getX() - SPEED);
			length += SPEED;
		}
		if(rightHit && leftHit) {
			if(!stopGrowing) {
				dividerSplit();
			}
			stopGrowing = true;
		}
	}

	@Override
	protected void dividerSplit() {
		Polygon newPolygon = null;

		newPolygon = map.split(this.location.getX(), this.location.getY(), this.location.getX() + length, this.location.getY(), this.ball.getX(), this.ball.getY(), this, null);
		leftHit = false;
		rightHit = false;
		if(newPolygon != null) {
			gm.newSplit(newPolygon);
		}
	}
}
