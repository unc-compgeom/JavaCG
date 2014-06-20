package cg;

public interface Segment extends Drawable {

	public Segment add(Segment v);

	// public double dot(Segment v);

	public Point getHead();

	public double getHomogeneous(int index);

	public Point getTail();

	public int getDX();

	public int getDY();

	public Point intersection(Segment v);

	public double length();

	public double lengthSquared();

	public Segment tailReflection();

	public Segment perpendicular();

	public void translate(Point v);

	// public Segment unitVector();

	public void update(int x1, int y1, int x2, int y2);

	public void update(Point v1, Point v2);

}
