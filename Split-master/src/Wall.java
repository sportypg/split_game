import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall implements Collidable{

	private int x, y, width, length;
	private Rectangle boundingRect;
	
	public Wall(int x, int y, int width, int length){
		this.x = x;
		this.y = y;
		this.width = width;
		this.length = length;
		this.boundingRect = new Rectangle(x, y, width, length);
	}	
	public Rectangle getBoundingRect() {
		return boundingRect;
	}
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
}
	public int getWidth() {
		return width;
	}
	public int getLength() {
		return length;
	}

	public void draw(Graphics g){
		g.fillRect(x,  y,  width,  length);
	}
	public void collided(Ball b) {
		if(getBoundingRect().intersects(b.getBoundingRect())) {
			b.bounce();
		}
	}
	public boolean inside(int x,int y){
		if(x>=this.x&&x<this.x+width&&y>=this.y&&y<this.y+length)
			return true;
		return false;
	}
}
