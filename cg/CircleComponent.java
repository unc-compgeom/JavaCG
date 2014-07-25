package cg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.List;

public class CircleComponent extends AbstractGeometry implements Circle {
	private Point topLeft;
	private final List<Point> points;
	private double diameter;

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
		points.forEach(GeometryManager::destroy);
		// do computations for drawing
		topLeft = new PointComponent(0, 0);
		diameter = -1;
		if (points.size() == 1) {
			topLeft = points.get(0);
			diameter = 0;
		} else if (points.size() == 2) {
			// we have a diameter
			Point a = points.get(0);
			Point b = points.get(1);
			float dX = a.getX() - b.getX();
			float dY = a.getY() - b.getY();
			double dXSq = dX * dX;
			double dYSq = dY * dY;
			double radius = Math.sqrt((dXSq + dYSq) / 4);
			diameter = radius*2;
			topLeft = new PointComponent((float) (a.getX() - (dX / 2) - radius), (float) (a.getY() - dY/2-radius));
		} else if (points.size() > 2) {
			Point p = points.get(0), q = points.get(1), r = points.get(2);
			double px = p.getX(), py = p.getY();
			double qx = q.getX(), qy = q.getY();
			double rx = r.getX(), ry = r.getY();

			double det = (px - qx) * (py - ry) - (py - qy) * (px - rx);
			float x = (float) ((p.plus(q).div(2).dot(p.sub(q)) * (py - ry) - (py - qy)
					* p.plus(r).div(2).dot(p.sub(r))) / det);
			float y = (float) (((px - qx) * p.plus(r).div(2).dot(p.sub(r)) - p
					.plus(q).div(2).dot(p.sub(q))
					* (px - rx)) / det);
			Point origin = new PointComponent(x, y); // tmp topLeft
			double radius = Math.sqrt(p.sub(origin).dot(p.sub(origin)));
			diameter = radius*2;
			topLeft = new PointComponent((float)(x-radius), (float)(y-radius));
		}
	}

	@Override
	public List<Point> getPoints() {
		return points;
	}

	@Override
	public void paint(GraphicsContext gc) {
		if (isInvisible()) {
			return;
		}
		// push old values onto the "stack"
		Paint oldFill = gc.getFill();
		Paint oldStroke = gc.getStroke();
		double oldSize = gc.getLineWidth();
		Color c = super.getColor();
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
