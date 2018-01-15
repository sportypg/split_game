import java.awt.Graphics;
import java.awt.Rectangle;

public class VerticalDivider extends Divider {

	boolean topHit = false;
	boolean bottomHit = false;

	public VerticalDivider(int x, int y, Polygon poly, Ball b, SplitGameMap gameMap) {
		this.gm = gameMap;
		this.ball = b;
		map = poly;
		location = new Vector(x, y);
		length = 0;
		this.boundingRect = new Rectangle(x, y, DIMS, length);
	}
	@Override
	public void collided(Ball b) {
		if(getBoundingRect().intersects(b.getBoundingRect())) {
			if(!stopGrowing) {
				b.setAlive(false);
			}
		}
	}
	@Override
	protected void grow() {
		topHit = !map.inside(this.location.getX(), this.location.getY());
		bottomHit = !map.inside(this.location.getX(), this.location.getY() + length);
		if(!topHit && !bottomHit) {
			location.setY(location.getY() - SPEED);
			length += SPEED * 2;
		}
		else if(topHit && !bottomHit) {
			length += SPEED;
		}
		else if(bottomHit && !topHit) {
			location.setY(location.getY() - SPEED);
			length += SPEED;
		}
		if(topHit && bottomHit) {
			if(!stopGrowing) {
				dividerSplit();
			}
			stopGrowing = true;
		}
	}
	@Override
	protected void draw(Graphics g) {
		this.updateRect();
		g.fillRect(location.getX(), location.getY(), DIMS, length);

	}
	@Override
	protected void updateRect() {
		this.boundingRect = new Rectangle(location.getX(), location.getY(), DIMS, length);
	}
	@Override
	protected void dividerSplit() {
		Polygon newPolygon = null;

		newPolygon = map.split(this.location.getX(), this.location.getY(), this.location.getX(), this.location.getY() + length, this.ball.getX(), this.ball.getY(), this, null);
		if(newPolygon != null) {
			gm.newSplit(newPolygon);
		}
		topHit = false;
		bottomHit = false;
	}
}
