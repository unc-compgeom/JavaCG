package cg;

import java.awt.Color;
import java.awt.Graphics;

public class VertexComponent extends AbstractGeometry implements Vertex {
	private int x;
	private int y;
	private HalfEdge incident;

	public VertexComponent(int x, int y, HalfEdge incident) {
		this.x = x;
		this.y = y;
		this.incident = incident;
	}

	public VertexComponent(int x, int y) {
		this(x, y, null);
	}

	@Override
	public int compareTo(Vertex p) {
		return (x < p.getX()) ? -1 : (x > p.getX()) ? 1 : (y < p.getY()) ? -1
				: (y > p.getY()) ? 1 : 0;
	}

	public Vertex add(Vertex p1) {
		return new VertexComponent(x + p1.getX(), y + p1.getY());
	}

	public Vertex sub(Vertex p1) {
		return new VertexComponent(x - p1.getX(), y - p1.getY());
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
	public HalfEdge getIncident() {
		return incident;
	}

	@Override
	public void setIncident(HalfEdge incident) {
		this.incident = incident;
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
