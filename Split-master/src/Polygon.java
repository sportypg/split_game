
import java.util.ArrayList;

public class Polygon {

	private ArrayList<Wall> walls = new ArrayList<Wall>();

	public Polygon() {

	}

	public int getMinX(int y) {
		int minX = Integer.MAX_VALUE;
		for (Wall wall : walls) {
			if (wall.getY() <= y && wall.getY() + wall.getLength() > y) {
				if (minX > wall.getX())
					minX = wall.getX();
			}
		}
		return minX;
	}

	public int getMinY(int x) {
		int minY = Integer.MAX_VALUE;
		for (Wall wall : walls) {
			if (wall.getX() <= x && wall.getX() + wall.getWidth() > x) {
				if (minY > wall.getY())
					minY = wall.getY();
			}
		}
		return minY;
	}

	public int getHeight() {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (Wall wall : walls) {
			if (wall.getY() < min) {
				min = wall.getY();
			}
			if (wall.getY() + 10 > max) {
				max = wall.getY() + 10;
			}
		}
		return max - min;
	}

	public int getWidth() {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (Wall wall : walls) {
			if (wall.getX() < min) {
				min = wall.getX();
			}
			if (wall.getX() + 10 > max) {
				max = wall.getX() + 10;
			}
		}
		return max - min;
	}

	public void add(Wall wall) {
		walls.add(wall);
	}

	public ArrayList<Wall> walls() {
		return walls;
	}

	public boolean inside(int x, int y) {
		boolean up = false;
		boolean down = false;
		boolean right = false;
		boolean left = false;
		for (Wall wall : walls) {
			if (x > wall.getX() && x < wall.getX() + wall.getWidth()) {
				if (!(y > wall.getY() && y < wall.getY() + wall.getLength())) {
					if (y < wall.getY()) {
						up = true;
					} else {
						down = true;
					}
				}
			} else if (y > wall.getY() && y < wall.getY() + wall.getLength()) {
				if (!(x > wall.getX() && x < wall.getX() + wall.getWidth())) {
					if (x < wall.getX()) {
						right = true;
					} else {
						left = true;
					}
				}
			}
		}
		if (up && down && right && left) {
			return true;
		} else {
			return false;
		}
	}

	public Polygon split(int x1, int y1, int x2, int y2, int ballX1, int ballX2, Divider d1, Divider d2) {
		Wall divider1 = null;
		Wall divider2 = null;
		if (d2 != null)
			divider2 = new Wall(d2.location.getX(), d2.location.getY(), d2.getLength(), d2.getDims());
		if (x1 == x2)
			divider1 = new Wall(d1.location.getX(), d1.location.getY(), d1.getDims(), d1.getLength());
		else if (y1 == y2)
			divider1 = new Wall(d1.location.getX(), d1.location.getY(), d1.getLength(), d1.getDims());
		Wall wall1 = null;
		Wall wall2 = null;
		Wall wall1a = null;
		Wall wall1b = null;
		Wall wall2a = null;
		Wall wall2b = null;
		Polygon p1 = new Polygon();
		Polygon p2 = new Polygon();
		for (Wall wall : walls) {
			if (wall.inside(x1, y1))
				wall1 = wall;
			else if (wall.inside(x2, y2)) {
				wall2 = wall;
			}
		}
		if (wall1 == null || wall2 == null) {
			return null;
		}
		if (wall1.getWidth() == 10) {
			wall1a = new Wall(wall1.getX(), wall1.getY(), 10, y1 - wall1.getY() + 10);
			wall1b = new Wall(wall1.getX(), y1, 10, wall1.getY() + wall1.getLength() - y1);
		} else if (wall1.getLength() == 10) {
			wall1a = new Wall(wall1.getX(), wall1.getY(), x1 - wall1.getX() + 10, 10);
			wall1b = new Wall(x1, wall1.getY(), wall1.getX() + wall1.getWidth() - x1, 10);

		}
		if (wall2.getWidth() == 10) {
			wall2a = new Wall(wall2.getX(), wall2.getY(), 10, y2 - wall2.getY() + 10);
			wall2b = new Wall(wall2.getX(), y2, 10, wall2.getY() + wall2.getLength() - y2);
		} else if (wall2.getLength() == 10) {
			wall2a = new Wall(wall2.getX(), wall2.getY(), x2 - wall2.getX() + 10, 10);
			wall2b = new Wall(x2, wall2.getY(), wall2.getX() + wall2.getWidth() - x2, 10);
		}
		if (x1 == x2) {
			for (Wall wall : walls) {
				if (wall.getX() != wall1.getX() || wall.getY() != wall1.getY() || wall.getLength() != wall1.getLength()
						|| wall.getWidth() != wall1.getWidth()) {
					if (wall.getX() != wall2.getX() || wall.getY() != wall2.getY()
							|| wall.getLength() != wall2.getLength() || wall.getWidth() != wall2.getWidth()) {
						if (wall.getX() <= x1) {
							p1.add(wall);
						} else {
							p2.add(wall);
						}
					}
				}
			}
			p1.add(wall1a);
			p1.add(wall2a);
			p2.add(wall1b);
			p2.add(wall2b);
			p1.add(divider1);
			p2.add(divider1);
			if (divider2 != null) {
				p1.add(divider2);
				p2.add(divider2);
			}
			if (p1.inside(ballX1 + Ball.radius / 2, ballX2 + Ball.radius / 2)) {
				return p1;
			} else if (p2.inside(ballX1 + Ball.radius / 2, ballX2 + Ball.radius / 2)) {
				return p2;
			} else {
				return null;
			}
		} else if (y1 == y2) {
			for (Wall wall : walls) {
				if (wall.getX() != wall1.getX() || wall.getY() != wall1.getY() || wall.getLength() != wall1.getLength()
						|| wall.getWidth() != wall1.getWidth()) {
					if (wall.getX() != wall2.getX() || wall.getY() != wall2.getY()
							|| wall.getLength() != wall2.getLength() || wall.getWidth() != wall2.getWidth()) {
						if (wall.getY() > y1) {
							p2.add(wall);
						} else {
							p1.add(wall);
						}
					}
				}
			}
			p1.add(wall1a);
			p1.add(wall2a);
			p2.add(wall1b);
			p2.add(wall2b);
			p1.add(divider1);
			p2.add(divider1);
			if (divider2 != null) {
				p1.add(divider2);
				p2.add(divider2);
			}
			if (p1.inside(ballX1 + Ball.radius / 2, ballX2 + Ball.radius / 2)) {
				return p1;
			} else if (p2.inside(ballX1 + Ball.radius / 2, ballX2 + Ball.radius / 2)) {
				return p2;
			} else {
				return null;
			}
		} else if ((x1 > x2 && y1 > y2) || (x2 > x1 && y2 > y1)) {

		} else {
		}
		return null;
	}

	public Polygon expand() {
		Polygon newPolygon = new Polygon();
		int newHeight = 0;
		int newWidth = 0;
		if (getWidth() / getHeight() <= SplitLauncher.W - 40 / SplitLauncher.H - 170) {
			newHeight = SplitLauncher.H - 170;
			newWidth = (int) (newHeight * getWidth() / getHeight());
			newPolygon.add(new Wall(SplitLauncher.W / 2 - newWidth / 2, 20, newWidth, 10));
			newPolygon.add(new Wall(SplitLauncher.W / 2 - newWidth / 2, 20, 10, newHeight));
			newPolygon.add(new Wall(SplitLauncher.W / 2 - newWidth / 2, 20 + newHeight - 10, newWidth, 10));
			newPolygon.add(new Wall(SplitLauncher.W / 2 + newWidth / 2 - 10, 20, 10, newHeight));
			return newPolygon;
		} else {
			newWidth = SplitLauncher.W - 40;
			newHeight = (int) (newWidth * getHeight() / getWidth());
			newPolygon.add(new Wall(20, (SplitLauncher.H - 120) / 2 - newHeight / 2, 10, newHeight));
			newPolygon.add(new Wall(20, (SplitLauncher.H - 120) / 2 - newHeight / 2, newWidth, 10));
			newPolygon.add(new Wall(20, (SplitLauncher.H - 120) / 2 + newHeight / 2 - 10, newWidth, 10));
			newPolygon.add(new Wall(20 + newWidth - 10, (SplitLauncher.H - 120) / 2 - newHeight / 2, 10, newHeight));
			return newPolygon;

		}
	}
}
