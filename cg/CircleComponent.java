package cg;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CircleComponent extends AbstractGeometry implements Circle {
	private Vertex origin;
	private int radiusSquared;

	protected CircleComponent(int x, int y, int radiusSquared) {
		this.origin = new VertexComponent(x, y);
		this.radiusSquared = radiusSquared;
	}

	/**
	 * Create a Circle object given points that lie on its circumference.
	 * 
	 * @param vertices
	 *            points that lie on the circle's circumference.
	 * 
	 */
	protected CircleComponent(VertexSet vertices) {
		origin = new VertexComponent(0, 0);
		radiusSquared = -1;
		if (vertices.size() == 1) {
			origin = vertices.get(0);
			radiusSquared = 0;
		} else if (vertices.size() == 2) {
			// we have a diameter
			Vertex a = vertices.get(0);
			Vertex b = vertices.get(1);
			int dX = a.getX() - b.getX();
			int dY = a.getY() - b.getY();
			long dXSq = dX * dX;
			long dYSq = dY * dY;
			// radiusSquared = (sqrt(dx^2 + dy^2)/2)^2 = (dx^2 + dy^2)/4
			radiusSquared = (int) (dXSq + dYSq) / 4;
			origin = new VertexComponent(a.getX() - dX / 2, a.getY() - dY / 2);
		} else if (vertices.size() > 2) {
			Vertex p = vertices.get(0), q = vertices.get(1), r = vertices
					.get(2);
			long px = p.getX(), py = p.getY();
			long qx = q.getX(), qy = q.getY();
			long rx = r.getX(), ry = r.getY();

			int det = (int) ((px - qx) * (py - ry) - (py - qy) * (px - rx));
			int x = (int) (p.add(q).div(2).dot(p.sub(q)) * (py - ry) - (py - qy)
					* (p.add(r).div(2).dot(p.sub(r))))
					/ det;
			int y = (int) ((px - qx) * p.add(r).div(2).dot(p.sub(r)) - p.add(q)
					.div(2).dot(p.sub(q))
					* (px - rx))
					/ det;
			origin = new VertexComponent(x, y);
			radiusSquared = (p.sub(origin).dot(p.sub(origin)));
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(getColor());
		int radius = (int) Math.sqrt(getRadiusSquared());
		int x = getOrigin().getX();
		int y = getOrigin().getY();
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(GeometryManager.getSize()));
		g2D.drawOval(x - radius, y - radius, radius * 2, radius * 2);
		g2D.setStroke(new BasicStroke());
	}

	@Override
	public int getRadiusSquared() {
		return radiusSquared;
	}

	@Override
	public Vertex getOrigin() {
		return origin;
	}

	@Override
	public String toString() {
		return "Circle: o = " + origin + " r^2 = " + radiusSquared;
	}
}
