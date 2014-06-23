package cg;

import java.awt.Color;
import java.awt.Graphics;

public class PointComponent extends AbstractGeometry implements Point {
	private int x;
	private int y;

	PointComponent(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public Point add(Point p1) {
		return new PointComponent(x + p1.getX(), y + p1.getY());
	}

	@Override
	public int compareTo(Point p) {
		return (x < p.getX()) ? -1 : (x > p.getX()) ? 1 : (y < p.getY()) ? -1
				: (y > p.getY()) ? 1 : 0;
	}

	@Override
	public double distanceSquared(Point v) {
		double x = Math.pow(this.x - v.getX(), 2);
		double y = Math.pow(this.y - v.getY(), 2);
		return x + y;
	}

	@Override
	public Point div(double i) {
		return new PointComponent((int) (x / i), (int) (y / i));
	}

	@Override
	public int dot(Point v) {
		return x * v.getX() + y * v.getY();
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
	public Point mult(double i) {
		return new PointComponent((int) (x * i), (int) (y * i));
	}

	@Override
	public void paintComponent(Graphics g) {
		if (isInvisible())
			return;
		g.setColor(super.getColor());
		int size = (GeometryManager.getSize() > 2) ? GeometryManager.getSize()
				: 2;
		g.fillOval(x - size, y - size, 2 * size, 2 * size);
		g.setColor((super.getColor() == null || super.getColor() == Color.BLACK) ? Color.LIGHT_GRAY
				: Color.BLACK);
		g.drawOval(x - size, y - size, 2 * size, 2 * size);
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
	public Point sub(Point p1) {
		return new PointComponent(x - p1.getX(), y - p1.getY());
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
