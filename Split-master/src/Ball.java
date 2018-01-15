
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;

public class Ball {

	private int x, y;
	static int radius;
	private int params;
	private Image img;
	private int a = getRandomXMovement();
	private int b = getRandomYMovement();
	private Rectangle boundingRect;
	private boolean alive = true;
	private Polygon map;
	private final int SPEED = 7;

	public Ball(int x, int y, int radius, Polygon poly) {
		map = poly;
		this.setX(x);
		this.setY(y);
		getImg();
		this.radius = radius;
		params = radius / 2;
		boundingRect = new Rectangle(x, y, params, params);
	}
	private void getImg() {
		URL iconUrl = this.getClass().getResource("ball.png");
		Toolkit tk = Toolkit.getDefaultToolkit();
		img = tk.getImage(iconUrl);
	}
	public void setSpeed(int level) {
		if (a > -7 && a < 7) {
			if (a < 0) {
				a = 0 - SPEED - 1 * level;
			}
			if (a > 0) {
				a = 0 + SPEED + 1 * level;
			}
		}
		if (b > -7 && b < 7) {
			if (b < 0) {
				b = 0 - SPEED - 1 * level;
			}
			if (b > 0) {
				b = 0 + SPEED + 1 * level;
			}
		}
	}
	public int getSquareParams() {
		return params;
	}
	public void updatePolygon(Polygon newMap) {
		map = newMap;
	}
	private void updateRect() {
		this.boundingRect = new Rectangle(x, y, params, params);
	}
	public Rectangle getBoundingRect() {
		return boundingRect;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setA() {
		a = getRandomXMovement();
	}
	public void setB() {
		b = getRandomYMovement();
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setAlive(boolean al) {
		alive = al;
	}
	public boolean getAlive() {
		return alive;
	}
	public void draw(Graphics g) {
		if (alive) {
			this.updateRect();
			g.drawImage(img, x, y, params, params, null);
		}
	}
	public void bounce() {
		ArrayList<Wall> wallList = map.walls();

		for (int i = 0; i < wallList.size(); i++) {
			if (wallList.get(i).getWidth() == 10) {
				if (this.boundingRect.getMaxX() > wallList.get(i).getX()
						&& this.boundingRect.getMaxX() < (wallList.get(i).getX() + wallList.get(i).getWidth())) {// right
					// wall
					if (a > 0)
						a *= -1;
					x -= 10;
					updateRect();
				}
				if ((this.boundingRect.getMinX() > wallList.get(i).getX())
						&& (this.boundingRect.getMinX() < wallList.get(i).getWidth() + wallList.get(i).getX())) { // left
					// wall
					if (a < 0)
						a *= -1;
					x += 10;
					updateRect();

				}
			}
			if (wallList.get(i).getLength() == 10) {
				if ((this.boundingRect.getMaxY() > wallList.get(i).getY())
						&& (this.boundingRect.getMaxY() < wallList.get(i).getLength() + wallList.get(i).getY())) { // bottom
					// wall
					if (b > 0)
						b *= -1;
					y -= 10;
					updateRect();

				}
				if (this.boundingRect.getMinY() > wallList.get(i).getY()
						&& this.boundingRect.getMinY() < wallList.get(i).getY() + wallList.get(i).getLength()) {// top
					// wall
					if (b < 0)
						b *= -1;
					y += 10;
					updateRect();

				}
			}
		}
	}
	public int getRandomXMovement() {
		int x = (int) (Math.random() * 6) + 3;
		int g = (int) (Math.random() * 2);
		if(g%2==0){
			x*=-1;
		}
		return x;
	}
	public int getRandomYMovement() {
		int y = (int) (Math.random() * 6) + 3;
		int g = (int) (Math.random() * 2);
		if(g%2==0){
			y*=-1;
		}
		return y;
	}
	public void move() {
		this.x += a;
		this.y += b;
	}

}