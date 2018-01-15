
import java.awt.Graphics;
import java.util.ArrayList;

public class SplitGameMap extends GameMap {

	private Ball ball;
	private Vector dimensions;
	private Polygon polygon = new Polygon();
	private ArrayList<Divider> dividers = new ArrayList<Divider>();
	private int cushion = 10;
	private boolean ready = true;
	private boolean localReady = true;
	private final int STARTWIDTH;
	private final int STARTHEIGHT;
	private boolean clearing = false;

	public SplitGameMap(Vector dims) {
		dimensions = dims;
		createBall();
		createWalls();
		STARTHEIGHT = polygon.getHeight();
		STARTWIDTH = polygon.getWidth();
	}
	public int getStartWidth() {
		return STARTWIDTH;
	}
	public int getStartHeight() {
		return STARTHEIGHT;
	}
	public int getWidth() {
		return dimensions.getX();
	}
	public int getHeight() {
		return dimensions.getY();
	}
	private void createWalls() {
		polygon.add(new Wall(cushion * 2, cushion * 2, cushion, dimensions.getY() - 150 - 2 * cushion));
		polygon.add(new Wall(cushion * 2, dimensions.getY() - 150 - cushion, dimensions.getX() - 4 * cushion, cushion));
		polygon.add(new Wall(cushion * 2, cushion * 2, dimensions.getX() - 4 * cushion, cushion));
		polygon.add(new Wall(dimensions.getX() - cushion * 3, cushion * 2, cushion, dimensions.getY() - 150 - 2 * cushion));
	}
	private void updateAllPolygons() {
		ball.updatePolygon(polygon);
		for (Divider div : dividers) {
			div.updatePolygon(polygon);
		}
	}
	public Ball getBall() {
		return ball;
	}
	public void ready() {
		ready = true;
	}
	@Override
	public void tick() {
		ball.move();
		for (Wall w : polygon.walls()) {
			w.collided(ball);
		}
		if(clearing) {
			clearDividers();
			clearing = false;
		}
		for (Divider div : dividers) {
			div.grow();
			div.collided(ball);
		}
	}
	public void expandPolygon() {
		polygon = polygon.expand();
		updateAllPolygons();
	}
	@Override
	public void draw(Graphics g) {
		if (ball.getAlive()) {
			ball.draw(g);
			for (Divider div : dividers) {
				div.draw(g);
			}
			for (Wall w : polygon.walls()) {
				w.draw(g);
			}
		} 
		else {
			gameOver();
		}
	}
	private void createBall() {
		final int ballRadius = 100;
		int x = (int) (Math.random()*(dimensions.getX()-cushion*6-100))+cushion*3;
		int y = (int) (Math.random()*(dimensions.getY()-150-cushion-cushion*3-100))+cushion*3;
		ball = new Ball(x, y, ballRadius, polygon);
	}
	public void addDivider(Divider div) {
		if (ready && localReady) {
			clearDividers();
			dividers.add(div);
			localReady = false;
		}
	}
	
	public void clearDividers() {
		dividers.clear();
	}
	public Polygon getPolygon() {
		return polygon;
	}
	public void newSplit(Polygon newPolygon) {
		localReady = true;
		polygon = newPolygon;
		clearing = true;
		updateAllPolygons();
	}
	
	public void newExpansion(Polygon newExpanded) {
		polygon = newExpanded;
		updateAllPolygons();
		System.out.println(this.dimensions.getX() + " " +  this.dimensions.getY());
		ball  = new Ball(this.dimensions.getX() / 2, this.dimensions.getY() / 2, ball.getSquareParams() * 2, polygon);
	}
	public boolean getGameOver() {
		return gameOver;
	}
}
