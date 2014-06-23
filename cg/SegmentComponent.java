package cg;

import java.awt.Graphics;

/**
 * The segment will have an origin indicating the location of the tail of the
 * segment. The xdir and ydir give magnitudes in the x and y directions. The
 * homogeneous array stores the homogeneous coordinates of the segment as
 * <W,X,Y>.
 */
public class SegmentComponent extends AbstractGeometry implements Segment {

	private static int getDx(Segment s) {
		return s.getTail().getX() - s.getHead().getX();
	}

	private static int getDy(Segment s) {
		return s.getTail().getY() - s.getHead().getY();
	}

	private static long[] getHomogeneous(Segment s) {
		long[] homogeneous = new long[3];
		int x1 = s.getTail().getX();
		int x2 = s.getHead().getX();
		int y1 = s.getTail().getY();
		int y2 = s.getHead().getY();
		homogeneous[0] = (x1 * y2) - (y1 * x2);
		homogeneous[1] = y1 - y2;
		homogeneous[2] = x2 - x1;
		return homogeneous;
	}

	private Point tail, head;

	SegmentComponent(int x1, int y1, int x2, int y2) {
		this.tail = new PointComponent(x1, y1);
		this.head = new PointComponent(x2, y2);

	}

	SegmentComponent(Point v1, Point v2) {
		this(v1.getX(), v1.getY(), v2.getX(), v2.getY());
	}

	@Override
	public Segment add(Segment v) {
		int x = head.getX() + getDx(v);
		int y = head.getY() + getDy(v);
		return new SegmentComponent(tail.getX(), tail.getY(), x, y);
	}

	@Override
	public Point findIntersection(Segment v) {
		long[] homogeneous = getHomogeneous(this);
		long[] vHomogeneous = getHomogeneous(v);
		double x0 = homogeneous[1] * vHomogeneous[2] - homogeneous[2]
				* vHomogeneous[1];
		double x1 = -homogeneous[0] * vHomogeneous[2] + homogeneous[2]
				* vHomogeneous[0];
		double x2 = homogeneous[0] * vHomogeneous[1] - homogeneous[1]
				* vHomogeneous[0];
		return new PointComponent((int) (x1 / x0), (int) (x2 / x0));
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
	public void paintComponent(Graphics g) {
		if (isInvisible()) {
			return;
		}
		g.setColor(super.getColor());
		g.drawLine(tail.getX(), tail.getY(), head.getX(), head.getY());
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
