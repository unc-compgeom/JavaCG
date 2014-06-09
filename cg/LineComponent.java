package cg;

import java.awt.Graphics;

public class LineComponent extends AbstractGeometry implements Line {
	private Point p1;
	private Point p2;

	public LineComponent(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public Point getP1() {
		return this.p1;
	}

	@Override
	public Point getP2() {
		return this.p2;
	}

	@Override
	public void setP1(Point p1) {
		this.p1 = p1;
		notifyObservers();
	}

	@Override
	public void setP2(Point p2) {
		this.p2 = p2;
		notifyObservers();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		p1.paintComponent(g);
		p2.paintComponent(g);
	}
}
