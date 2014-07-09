package cg;

import java.awt.Color;
import java.awt.Graphics;

public class PointComponent extends AbstractGeometry implements Point {
	private float x;
	private float y;

	PointComponent(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	PointComponent(Point p) {
		super();
		this.x = p.getX();
		this.y = p.getY();
	}

	@Override
	public Point add(Point p1) {
		return new PointComponent(x + p1.getX(), y + p1.getY());
	}

	@Override
	public int compareTo(Point p) {
		// lexicographical comparison
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
	public Point div(float i) {
		return new PointComponent(x / i, y / i);
	}

	@Override
	public double dot(Point v) {
		return x * v.getX() + y * v.getY();
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public Point mult(float i) {
		return new PointComponent(x * i, y * i);
	}

	@Override
	public void paint(Graphics g) {
		if (isInvisible())
			return;
		Color oldG = g.getColor(), c = super.getColor();
		if (c != null) {
			g.setColor(c);
		}
		int size = (GeometryManager.getSize() > 2) ? GeometryManager.getSize()
				: 2;
		g.fillOval((int) (x - size), (int) (y - size), 2 * size, 2 * size);
		g.setColor((c == null || c == Color.BLACK) ? Color.LIGHT_GRAY
				: Color.BLACK);
		g.drawOval((int) (x - size), (int) (y - size), 2 * size, 2 * size);
		g.setColor(oldG);
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
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
