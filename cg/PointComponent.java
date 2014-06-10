package cg;

import java.awt.Color;
import java.awt.Graphics;

public class PointComponent extends AbstractGeometry implements Point {
	private int x;
	private int y;

	public PointComponent(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Point p) {
		return (x < p.getX()) ? -1 : (x > p.getX()) ? 1 : (y < p.getY()) ? -1
				: (y > p.getY()) ? 1 : 0;
	}

	public Point add(Point p1) {
		return new PointComponent(x + p1.getX(), y + p1.getY());
	}

	public Point sub(Point p1) {
		return new PointComponent(x - p1.getX(), y - p1.getY());
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillOval(x - 1, y - 1, 3, 3);
		g.setColor(Color.BLACK);
		g.drawOval(x - 1, y - 1, 3, 3);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
