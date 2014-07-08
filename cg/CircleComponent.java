package cg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

public class CircleComponent extends AbstractGeometry implements Circle {
	private Point origin;
	private final List<Point> points;
	private double radiusSquared;

	CircleComponent(Circle c) {
		this(c.getPoints());
	}

	/**
	 * Create a Circle object given points that lie on its circumference.
	 * 
	 * @param points
	 *            points that lie on the circle's circumference.
	 * 
	 */
	CircleComponent(List<Point> points) {
		this.points = points;
		// do computations for drawing
		origin = new PointComponent(0, 0);
		radiusSquared = -1;
		if (points.size() == 1) {
			origin = points.get(0);
			radiusSquared = 0;
		} else if (points.size() == 2) {
			// we have a diameter
			Point a = points.get(0);
			Point b = points.get(1);
			float dX = a.getX() - b.getX();
			float dY = a.getY() - b.getY();
			double dXSq = dX * dX;
			double dYSq = dY * dY;
			radiusSquared = (dXSq + dYSq) / 4;
			origin = new PointComponent(a.getX() - dX / 2, a.getY() - dY / 2);
		} else if (points.size() > 2) {
			Point p = points.get(0), q = points.get(1), r = points.get(2);
			double px = p.getX(), py = p.getY();
			double qx = q.getX(), qy = q.getY();
			double rx = r.getX(), ry = r.getY();

			double det = (px - qx) * (py - ry) - (py - qy) * (px - rx);
			float x = (float) ((p.add(q).div(2).dot(p.sub(q)) * (py - ry) - (py - qy)
					* (p.add(r).div(2).dot(p.sub(r)))) / det);
			float y = (float) (((px - qx) * p.add(r).div(2).dot(p.sub(r)) - p
					.add(q).div(2).dot(p.sub(q))
					* (px - rx)) / det);
			origin = new PointComponent(x, y);
			radiusSquared = (p.sub(origin).dot(p.sub(origin)));
		}
	}

	@Override
	public List<Point> getPoints() {
		return points;
	}

	@Override
	public void paint(Graphics g) {
		if (isInvisible()) {
			return;
		}
		g.setColor(super.getColor());
		int radius = (int) Math.sqrt(radiusSquared);
		double x = origin.getX();
		double y = origin.getY();
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(GeometryManager.getSize()));
		g2D.drawOval((int) (x - radius), (int) (y - radius), radius * 2,
				radius * 2);
		g2D.setStroke(new BasicStroke());
		for (Point p : points) {
			Color old = p.getColor();
			p.setColor(getColor());
			p.paint(g);
			p.setColor(old);
		}
	}

	@Override
	public String toString() {
		return "Circle: o = " + origin + " r^2 = " + radiusSquared;
	}
}
