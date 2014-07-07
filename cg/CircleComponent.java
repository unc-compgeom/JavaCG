package cg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

public class CircleComponent extends AbstractGeometry implements Circle {
	private Point origin;
	private int radiusSquared;

	protected CircleComponent(int x, int y, int radiusSquared) {
		origin = new PointComponent(x, y);
		this.radiusSquared = radiusSquared;
	}

	/**
	 * Create a Circle object given points that lie on its circumference.
	 * 
	 * @param points
	 *            points that lie on the circle's circumference.
	 * 
	 */
	protected CircleComponent(List<Point> points) {
		origin = new PointComponent(0, 0);
		radiusSquared = -1;
		if (points.size() == 1) {
			origin = points.get(0);
			radiusSquared = 0;
		} else if (points.size() == 2) {
			// we have a diameter
			Point a = points.get(0);
			Point b = points.get(1);
			int dX = a.getX() - b.getX();
			int dY = a.getY() - b.getY();
			long dXSq = dX * dX;
			long dYSq = dY * dY;
			radiusSquared = (int) (dXSq + dYSq) / 4;
			origin = new PointComponent(a.getX() - dX / 2, a.getY() - dY / 2);
		} else if (points.size() > 2) {
			Point p = points.get(0), q = points.get(1), r = points.get(2);
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
			origin = new PointComponent(x, y);
			radiusSquared = (p.sub(origin).dot(p.sub(origin)));
		}
	}

	public CircleComponent(Circle c) {
		origin = c.getOrigin();
		radiusSquared = c.getRadiusSquared();
	}

	@Override
	public void paint(Graphics g) {
		if (isInvisible()) {
			return;
		}
		g.setColor(super.getColor());
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
	public Point getOrigin() {
		return origin;
	}

	@Override
	public String toString() {
		return "Circle: o = " + origin + " r^2 = " + radiusSquared;
	}

	@Override
	public void setColor(Color c) {
		super.setColor(c);
		notifyObservers();
	}
}
