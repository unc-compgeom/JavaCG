package cg;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The segment will have an origin indicating the location of the tail of the
 * segment. The xdir and ydir give magnitudes in the x and y directions. The
 * homogeneous array stores the homogeneous coordinates of the segment as
 * <W,X,Y>.
 */
public class SegmentComponent extends AbstractGeometry implements Segment {

	int dX;
	int dY;
	private Point head;
	private final double[] homogeneous = { 0, 0, 0 };
	private Point tail;

	protected SegmentComponent(int x1, int y1, int x2, int y2) {
		tail = new PointComponent(x1, y1);
		head = new PointComponent(x2, y2);
		dX = x2 - x1;
		dY = y2 - y1;
		homogeneous[0] = (x1 * y2) - (y1 * x2);
		homogeneous[1] = y1 - y2;
		homogeneous[2] = x2 - x1;
	}

	protected SegmentComponent(Point v1, Point v2) {
		this(v1.getX(), v1.getY(), v2.getX(), v2.getY());
	}

	@Override
	public Segment add(Segment v) {
		int x = head.getX() + v.getDX();
		int y = head.getY() + v.getDY();
		return new SegmentComponent(tail.getX(), tail.getY(), x, y);
	}

	// @Override
	// public double dot(Segment v) {
	// return (this.dX * v.getDY()) - (this.dY * v.getDX());
	// }

	@Override
	public int getDX() {
		return dX;
	}

	@Override
	public int getDY() {
		return dY;
	}

	@Override
	public Point getHead() {
		return head;
	}

	@Override
	public double getHomogeneous(int index) {
		while (index < 0) {
			index = index + 3;
		}
		return homogeneous[index % 3];
	}

	@Override
	public Point getTail() {
		return tail;
	}

	@Override
	public Point intersection(Segment v) {
		double x0 = homogeneous[1] * v.getHomogeneous(2) - homogeneous[2]
				* v.getHomogeneous(1);
		double x1 = -homogeneous[0] * v.getHomogeneous(2) + homogeneous[2]
				* v.getHomogeneous(0);
		double x2 = homogeneous[0] * v.getHomogeneous(1) - homogeneous[1]
				* v.getHomogeneous(0);
		return new PointComponent((int) (x1 / x0), (int) (x2 / x0));
	}

	@Override
	public double length() {
		return Math.hypot(dX, dY);
	}

	@Override
	public double lengthSquared() {
		return Math.pow(dX, 2) + Math.pow(dY, 2);
	}

	@Override
	public void paintComponent(Graphics g) {
		if (isInvisible()) {
			return;
		}
		g.setColor(super.getColor());
		g.setColor(getColor());
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(GeometryManager.getSize()));
		g2D.drawLine(tail.getX(), tail.getY(), head.getX(), head.getY());
		g2D.setStroke(new BasicStroke());
	}

	@Override
	public Segment perpendicular() {
		return new SegmentComponent(tail.getX(), tail.getY(),
				-dY + tail.getX(), dX + tail.getY());
	}

	@Override
	public Segment tailReflection() {
		return new SegmentComponent(tail.getX(), tail.getY(), tail.getX() - dX,
				tail.getY() - dY);
	}

	@Override
	public void translate(Point v) {
		head.setX(head.getX() - tail.getX() + v.getX());
		head.setY(head.getY() - tail.getY() + v.getY());
		tail.setX(v.getX());
		tail.setY(v.getY());
		homogeneous[0] = (tail.getX() * head.getY())
				- (tail.getY() * head.getX());
		notifyObservers();
	}

	// @Override
	// public Segment unitVector() {
	// return new SegmentComponent(0, 0, (int) (dX / this.length()),
	// (int) (dY / this.length()));
	// }

	@Override
	public void update(int x1, int y1, int x2, int y2) {
		tail = new PointComponent(x1, y1);
		head = new PointComponent(x2, y2);
		dX = x2 - x1;
		dY = y2 - y1;
		homogeneous[0] = (x1 * y2) - (y1 * x2);
		homogeneous[1] = y1 - y2;
		homogeneous[2] = x2 - x1;
		notifyObservers();
	}

	@Override
	public void update(Point v1, Point v2) {
		update(v1.getX(), v1.getY(), v2.getX(), v2.getY());
	}
}
