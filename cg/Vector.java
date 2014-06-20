package cg;

public interface Vector extends Drawable {

	public Vector add(Vector v);

	public double dot(Vector v);

	public Vertex getHead();

	public double getHomogeneous(int index);

	public Vertex getTail();

	public int getXdir();

	public int getYdir();

	public Vertex intersection(Vector v);

	public double length();

	public double lengthSquared();

	public Vector originReflection();

	public Vector perpendicular();

	public void translate(Vertex v);

	public Vector unitVector();

	public void update(int x1, int y1, int x2, int y2);

	public void update(Vertex v1, Vertex v2);

}
