package cg;

import util.CGObservable;

public interface Vertex extends Comparable<Vertex>, Cloneable, CGObservable {

	public void setX(int x);

	public void setY(int y);

	public int getX();

	public int getY();

	public HalfEdge getIncident();

	public void setIncident(HalfEdge incident);

	public Vertex add(Vertex p1);

	public Vertex sub(Vertex p1);

	public Vertex clone();
	
	public double distanceSquared(Vertex v);

}
