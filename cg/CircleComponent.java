package cg;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CircleComponent extends AbstractGeometry implements Circle {
	private Point topLeft;
	private final List<Point> points;
	private double diameter;

	CircleComponent(final Circle c) {
		this(c.getPoints());
	}

	/**
	 * Create a Circle object given points that lie on its circumference.
	 *
	 * @param points
	 *            points that lie on the circle's circumference.
	 *
	 */
	CircleComponent(final List<Point> points) {
		this.points = points;
		points.forEach(GeometryManager::destroy);
		// do computations for drawing
		topLeft = new PointComponent(0, 0);
		diameter = -1;
		if (points.size() == 1) {
			topLeft = points.get(0);
			diameter = 0;
		} else if (points.size() == 2) {
			// we have a diameter
			final Point a = points.get(0);
			final Point b = points.get(1);
			final float dX = a.getX() - b.getX();
			final float dY = a.getY() - b.getY();
			final double dXSq = dX * dX;
			final double dYSq = dY * dY;
			final double radius = Math.sqrt((dXSq + dYSq) / 4);
			diameter = radius * 2;
			topLeft = new PointComponent((float) (a.getX() - dX / 2 - radius),
					(float) (a.getY() - dY / 2 - radius));
		} else if (points.size() > 2) {
			final Point p = points.get(0), q = points.get(1), r = points.get(2);
			final double px = p.getX(), py = p.getY();
			final double qx = q.getX(), qy = q.getY();
			final double rx = r.getX(), ry = r.getY();

			final double det = (px - qx) * (py - ry) - (py - qy) * (px - rx);
			final float x = (float) ((p.plus(q).div(2).dot(p.sub(q))
					* (py - ry) - (py - qy) * p.plus(r).div(2).dot(p.sub(r))) / det);
			final float y = (float) (((px - qx)
					* p.plus(r).div(2).dot(p.sub(r)) - p.plus(q).div(2)
					.dot(p.sub(q))
					* (px - rx)) / det);
			final Point origin = new PointComponent(x, y); // tmp topLeft
			final double radius = Math.sqrt(p.sub(origin).dot(p.sub(origin)));
			diameter = radius * 2;
			topLeft = new PointComponent((float) (x - radius),
					(float) (y - radius));
		}
	}

	@Override
	public List<Point> getPoints() {
		return points;
	}

	@Override
	public void paint(final GraphicsContext gc) {
		if (isInvisible()) {
			return;
		}
		// push old values onto the "stack"
		final Paint oldFill = gc.getFill();
		final Paint oldStroke = gc.getStroke();
		final double oldSize = gc.getLineWidth();
		final Color c = super.getColor();
		if (c != null) {
			gc.setFill(c);
			gc.setStroke(c.darker());
		}
		gc.setLineWidth(GeometryManager.getSize());
		// draw the circle
		gc.strokeOval(topLeft.getX(), topLeft.getY(), diameter, diameter);

		// restore gc from the "stack"
		gc.setFill(oldFill);
		gc.setStroke(oldStroke);
		gc.setLineWidth(oldSize);
	}

	@Override
	public String toString() {
		return "Circle: o = " + topLeft + " r = " + diameter;
	}
}
