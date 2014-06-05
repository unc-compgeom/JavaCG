package cg;

import java.awt.Color;
import java.awt.Graphics;

public class PointComponent extends Draw implements Point {
	private int x;
	private int y;

	public PointComponent(int x, int y) {
		this.x = x;
		this.y = y;
		
	}

	public PointComponent(Point p1) {
		this(p1.getX(), p1.getY());
	}

	public PointComponent() {
		this(0, 0);
	}

	@Override
	public void setPoint(Point p1) {
		setPoint(p1.getX(), p1.getY());
		notifyObservers();
	}

	@Override
	public void setPoint(int x, int y) {
		this.x = x;
		this.y = y;
		notifyObservers();
	}

	@Override
	public int compareTo(Point p) {
		return Math.abs(x - p.getX()) + Math.abs(y - p.getY());
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
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillOval(x - 1, y - 1, 3, 3);
		g.setColor(Color.BLACK);
		g.drawOval(x - 1, y - 1, 3, 3);
	}
}
