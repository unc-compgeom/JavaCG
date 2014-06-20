package cg;

public interface Segment extends Drawable {

	public Segment add(Segment v);

	// public double dot(Segment v);

	public Vertex getHead();

	public double getHomogeneous(int index);

	public Vertex getTail();

	public int getDX();

	public int getDY();

	public Vertex intersection(Segment v);

	public double length();

	public double lengthSquared();

	public Segment tailReflection();

	public Segment perpendicular();

	public void translate(Vertex v);

//	public Segment unitVector();

	public void update(int x1, int y1, int x2, int y2);

	public void update(Vertex v1, Vertex v2);
	
	public Segment setVisible(boolean visible);

}
