package cg;

public interface Vertex extends Comparable<Vertex>, Cloneable, Drawable {

	public void setX(int x);

	public void setY(int y);

	public int getX();

	public int getY();

	public HalfEdge getIncident();

	public void setIncident(HalfEdge incident);

	public Vertex add(Vertex v);

	public Vertex sub(Vertex v);

	public Vertex div(double i);

	public Vertex mult(double i);

	public int dot(Vertex v);

	public Vertex clone();

}
