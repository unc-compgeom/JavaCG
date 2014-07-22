package cg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


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
		isReady = true;
	}

	@Override
	public synchronized int compareTo(Point p) {
		// lexicographical comparison
		return x < p.getX() ? -1 : x > p.getX() ? 1 : y < p.getY() ? -1 : y > p
				.getY() ? 1 : 0;
	}

	@Override
	public synchronized double distanceSquared(Point v) {
		double x = Math.pow(this.x - v.getX(), 2);
		double y = Math.pow(this.y - v.getY(), 2);
		return x + y;
	}

	@Override
	public synchronized Point div(float i) {
		return new PointComponent(x / i, y / i);
	}

	@Override
	public synchronized double dot(Point v) {
		return x * v.getX() + y * v.getY();
	}

	@Override
	public synchronized float getX() {
		return x;
	}

	@Override
	public synchronized float getY() {
		return y;
	}

	@Override
	public synchronized Point mult(float i) {
		return new PointComponent(x * i, y * i);
	}

	@Override
	public synchronized Point plus(Point p1) {
		return new PointComponent(x + p1.getX(), y + p1.getY());
	}

	@Override
	public synchronized void setX(float x) {
		isReady = false;
		this.x = x;
		isReady = true;
		notifyObservers(this);
		synchronized(this){notifyAll();}
	}

	@Override
	public synchronized void setY(float y) {
		isReady = false;
		this.y = y;
		isReady = true;
		notifyObservers(this);
		synchronized(this){notifyAll();}
	}

	@Override
	public synchronized Point sub(Point p1) {
		return new PointComponent(x - p1.getX(), y - p1.getY());
	}

	@Override
	public synchronized String toString() {
		return "(" + x + ", " + y + ")";
	}
}
