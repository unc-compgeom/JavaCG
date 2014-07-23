package cg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * The segment will have an origin indicating the location of the tail of the
 * segment. The xdir and ydir give magnitudes in the x and y directions. The
 * homogeneous array stores the homogeneous coordinates of the segment as
 * <W,X,Y>.
 */
public class SegmentComponent extends AbstractGeometry implements Segment {

	private static float getDx(Segment s) {
		return s.getTail().getX() - s.getHead().getX();
	}

	private static float getDy(Segment s) {
		return s.getTail().getY() - s.getHead().getY();
	}

	private static double[] getHomogeneous(Segment s) {
		double[] homogeneous = new double[3];
		float x1 = s.getTail().getX();
		float x2 = s.getHead().getX();
		float y1 = s.getTail().getY();
		float y2 = s.getHead().getY();
		homogeneous[0] = x1 * y2 - y1 * x2;
		homogeneous[1] = y1 - y2;
		homogeneous[2] = x2 - x1;
		return homogeneous;
	}

	private Point tail, head;

	SegmentComponent(float x1, float y1, float x2, float y2) {
		tail = new PointComponent(x1, y1);
		head = new PointComponent(x2, y2);

	}

	SegmentComponent(Point v1, Point v2) {
		this(v1.getX(), v1.getY(), v2.getX(), v2.getY());
	}

	@Override
	public Segment add(Segment v) {
		float x = head.getX() + getDx(v);
		float y = head.getY() + getDy(v);
		return new SegmentComponent(tail.getX(), tail.getY(), x, y);
	}

	@Override
	public Point findIntersection(Segment v) {
		double[] homogeneous = getHomogeneous(this);
		double[] vHomogeneous = getHomogeneous(v);
		double x0 = homogeneous[1] * vHomogeneous[2] - homogeneous[2]
				* vHomogeneous[1];
		double x1 = -homogeneous[0] * vHomogeneous[2] + homogeneous[2]
				* vHomogeneous[0];
		double x2 = homogeneous[0] * vHomogeneous[1] - homogeneous[1]
				* vHomogeneous[0];
		// TODO remove this cast down and preserve precision
		return new PointComponent((float) (x1 / x0), (float) (x2 / x0));

	}

	@Override
	public Segment findPerpendicular() {
		return new SegmentComponent(tail.getX(), tail.getY(), -getDy(this)
				+ tail.getX(), getDx(this) + tail.getY());
	}

	@Override
	public Point getHead() {
		return head;
	}

	@Override
	public double getLength() {
		return Math.hypot(getDx(this), getDy(this));
	}

	@Override
	public double getLengthSquared() {
		return Math.pow(getDx(this), 2) + Math.pow(getDy(this), 2);
	}

	@Override
	public Point getTail() {
		return tail;
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

		gc.strokeLine(head.getX(), head.getY(), tail.getX(), tail.getY());
		head.paint(gc);
		tail.paint(gc);

		// restore gc from the "stack"
		gc.setFill(oldFill);
		gc.setStroke(oldStroke);
		gc.setLineWidth(oldSize);
	}

	@Override
	public void setHead(Point head) {
		this.head = head;
		notifyObservers();

	}

	@Override
	public void setInvisible(boolean invisible) {
		super.setInvisible(invisible);
		tail.setInvisible(invisible);
		head.setInvisible(invisible);
	}

	@Override
	public void setTail(Point tail) {
		this.tail = tail;
		notifyObservers();

	}

	@Override
	public Segment tailReflection() {
		return new SegmentComponent(tail.getX(), tail.getY(), tail.getX()
				- getDx(this), tail.getY() - getDy(this));
	}

	@Override
	public void translate(Point v) {
		head.setX(head.getX() - tail.getX() + v.getX());
		head.setY(head.getY() - tail.getY() + v.getY());
		tail.setX(v.getX());
		tail.setY(v.getY());
		notifyObservers();
	}
}
