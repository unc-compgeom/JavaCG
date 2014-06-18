package cg;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CircleComponent extends AbstractGeometry implements Circle {
	private Vertex origin;
	private int radius;

	public CircleComponent(int x, int y, int radius) {
		this.origin = new VertexComponent(x, y);
		this.radius = radius;
	}

	/**
	 * Create a Circle object given points that lie on its circumference.
	 * 
	 * @param vertices
	 *            points that lie on the circle's circumference.
	 * 
	 */
	public CircleComponent(VertexSet vertices) {
		origin = null;
		radius = -1;
		if (vertices.size() == 1) {
			origin = vertices.get(0);
			radius = 0;
		} else if (vertices.size() == 2) {

		} else if (vertices.size() > 2) {
			Vertex a = vertices.get(0), b = vertices.get(1), c = vertices
					.get(2);
			long x1 = a.getX(), y1 = a.getY();
			long x2 = b.getX(), y2 = b.getY();
			long x3 = c.getX(), y3 = c.getY();

			// these three points satisfy the equation (x' - x)^2 + (y' - y)^2 -
			// r^2 = 0 for this circle.

			int x = (int) ((-x3 * x3 + x1 * x1 - y3 * y3 + y1 * y1
					- (x2 * x2 + x1 * x1) / (y1 - y2) + (y2 * y2 - y1 * y1)
					/ (y1 - y2)) / (2 * (x1 - x3) - 2 * (x1 - x2) / (y1 - y2)));
			int y = (int) ((x2 * x2 + x1 * x1 - 2 * x * (x1 - x2) - y2 * y2 + y1
					* y1) / (2 * (y1 - y2)));
			origin = new VertexComponent(x, y);
			radius = (int) Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(getColor());
		int radius = getRadius();
		int x = getOrigin().getX();
		int y = getOrigin().getY();
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(getSize()));
		g2D.drawOval(x - radius / 2, y - radius / 2, radius, radius);
		g2D.setStroke(new BasicStroke());
	}

	@Override
	public int getRadius() {
		return radius;
	}

	@Override
	public Vertex getOrigin() {
		return origin;
	}
}
