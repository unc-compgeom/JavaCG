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

	private static float getDx(final Segment s) {
		return s.getTail().getX() - s.getHead().getX();
	}

	private static float getDy(final Segment s) {
		return s.getTail().getY() - s.getHead().getY();
	}

	private static double[] getHomogeneous(final Segment s) {
		final double[] homogeneous = new double[3];
		final float x1 = s.getTail().getX();
		final float x2 = s.getHead().getX();
		final float y1 = s.getTail().getY();
		final float y2 = s.getHead().getY();
		homogeneous[0] = x1 * y2 - y1 * x2;
		homogeneous[1] = y1 - y2;
		homogeneous[2] = x2 - x1;
		return homogeneous;
	}

	private Point tail, head;

	SegmentComponent(final float x1, final float y1, final float x2,
			final float y2) {
		tail = new PointComponent(x1, y1);
		head = new PointComponent(x2, y2);

	}

	SegmentComponent(final Point v1, final Point v2) {
		this(v1.getX(), v1.getY(), v2.getX(), v2.getY());
	}

	@Override
	public Segment add(final Segment v) {
		final float x = head.getX() + getDx(v);
		final float y = head.getY() + getDy(v);
		return new SegmentComponent(tail.getX(), tail.getY(), x, y);
	}

	@Override
	public Point findIntersection(final Segment v) {
		final double[] homogeneous = getHomogeneous(this);
		final double[] vHomogeneous = getHomogeneous(v);
		final double x0 = homogeneous[1] * vHomogeneous[2] - homogeneous[2]
				* vHomogeneous[1];
		final double x1 = -homogeneous[0] * vHomogeneous[2] + homogeneous[2]
				* vHomogeneous[0];
		final double x2 = homogeneous[0] * vHomogeneous[1] - homogeneous[1]
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

		gc.strokeLine(head.getX(), head.getY(), tail.getX(), tail.getY());
		head.paint(gc);
		tail.paint(gc);

		// restore gc from the "stack"
		gc.setFill(oldFill);
		gc.setStroke(oldStroke);
		gc.setLineWidth(oldSize);
	}

	@Override
	public void setHead(final Point head) {
		this.head = head;
		notifyObservers();

	}

	@Override
	public void setInvisible(final boolean invisible) {
		super.setInvisible(invisible);
		tail.setInvisible(invisible);
		head.setInvisible(invisible);
	}

	@Override
	public void setTail(final Point tail) {
		this.tail = tail;
		notifyObservers();

	}

	@Override
	public Segment tailReflection() {
		return new SegmentComponent(tail.getX(), tail.getY(), tail.getX()
				- getDx(this), tail.getY() - getDy(this));
	}

	@Override
	public void translate(final Point v) {
		head.setX(head.getX() - tail.getX() + v.getX());
		head.setY(head.getY() - tail.getY() + v.getY());
		tail.setX(v.getX());
		tail.setY(v.getY());
		notifyObservers();
	}
}
