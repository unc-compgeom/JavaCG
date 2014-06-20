package cg;


public interface Vector extends Drawable {

	public Vertex getTail();

	public Vertex getHead();

	public int getXdir();

	public int getYdir();

	public Vector add(Vector v);

	public void translate(Vertex v);

	public double length();

	public double lengthSquared();

	public Vector unitVector();

	public double dotProduct(Vector v);

	public double getHomogeneous(int index);

	public Vertex intersection(Vector v);

	public Vector perpendicular();

	public Vector originReflection();

	public void update(Vertex v1, Vertex v2);

	public void update(int x1, int y1, int x2, int y2);

}
