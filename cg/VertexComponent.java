package cg;

import java.awt.Color;
import java.awt.Graphics;

public class VertexComponent extends AbstractGeometry implements Vertex {
	private int x;
	private int y;

	protected VertexComponent(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public Vertex add(Vertex p1) {
		return new VertexComponent(x + p1.getX(), y + p1.getY());
	}

	@Override
	public Vertex clone() {
		// TODO Auto-generated method stub
		return new VertexComponent(x, y);
	}

	@Override
	public int compareTo(Vertex p) {
		return (x < p.getX()) ? -1 : (x > p.getX()) ? 1 : (y < p.getY()) ? -1
				: (y > p.getY()) ? 1 : 0;
	}

	@Override
	public Vertex div(double i) {
		return new VertexComponent((int) (x / i), (int) (y / i));
	}

	@Override
	public int dot(Vertex v) {
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
	public Vertex mult(double i) {
		return new VertexComponent((int) (x * i), (int) (y * i));
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(super.getColor());
		int size = (GeometryManager.getSize() > 2) ? GeometryManager.getSize()
				: 2;
		g.fillOval(x - size, y - size, 2 * size, 2 * size);
		g.setColor((super.getColor() == null || super.getColor() == Color.BLACK) ? Color.DARK_GRAY
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
	public Vertex sub(Vertex p1) {
		return new VertexComponent(x - p1.getX(), y - p1.getY());
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public double distanceSquared(Vertex v) {
		double x = Math.pow(this.x - v.getX(), 2);
		double y = Math.pow(this.y - v.getY(), 2);
		return x + y;
	}
}
