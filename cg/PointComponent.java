package cg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import util.ColorSpecial;


public class PointComponent extends AbstractGeometry implements Point {
	private float x;
	private float y;

	PointComponent(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	PointComponent(Point p) {
		super();
		x = p.getX();
		y = p.getY();
	}

	@Override
	public int compareTo(Point p) {
		// lexicographical comparison
		return x < p.getX() ? -1 : x > p.getX() ? 1 : y < p.getY() ? -1 : y > p
				.getY() ? 1 : 0;
	}

	@Override
	public double distanceSquared(Point v) {
		double x = Math.pow(this.x - v.getX(), 2);
		double y = Math.pow(this.y - v.getY(), 2);
		return x + y;
	}

	@Override
	public Point div(float i) {
		return new PointComponent(x / i, y / i);
	}

	@Override
	public double dot(Point v) {
		return x * v.getX() + y * v.getY();
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public Point mult(float i) {
		return new PointComponent(x * i, y * i);
	}

	@Override
	public void paint(GraphicsContext gc) {
		if (isInvisible()) {
			return;
		}
		// push old values onto the "stack"
		Paint oldFill = gc.getFill();
		Paint oldStroke = gc.getStroke();
		//double oldSize = gc.getLineWidth(); // we don't need this for points
		Color c = super.getColor();
		if (c != null) {
			gc.setFill(c);
			gc.setStroke(c.darker());
		}
		gc.setLineWidth(1);

		// draw the point
		if (gc.getFill() == null || gc.getFill() == Color.BLACK) {
			gc.setStroke(ColorSpecial.LIGHT_SLATE_GRAY);
		}
		int size = GeometryManager.getSize() > 2 ? GeometryManager.getSize()
				: 2;
		gc.fillOval((x - size), (y - size), 2 * size, 2 * size);
		gc.strokeOval((x - size), (y - size), 2 * size, 2 * size);

		// restore gc from the "stack"
		gc.setFill(oldFill);
		gc.setStroke(oldStroke);
		//gc.setLineWidth(oldSize);
	}

	@Override
	public Point plus(Point p1) {
		return new PointComponent(x + p1.getX(), y + p1.getY());
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public Point sub(Point p1) {
		return new PointComponent(x - p1.getX(), y - p1.getY());
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
