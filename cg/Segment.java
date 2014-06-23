package cg;

public interface Segment extends Drawable {

	public Segment add(Segment v);

	public Point findIntersection(Segment v);

	public Segment findPerpendicular();

	public int getDx();

	public int getDy();

	public Point getHead();

	public double getLength();

	public double getLengthSquared();

	public Point getTail();

	public void setHead(Point head);

	public void setTail(Point tail);

	public Segment tailReflection();

	public void translate(Point v);

}
