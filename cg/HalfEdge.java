package cg;


public interface HalfEdge extends Drawable, Comparable<HalfEdge> {

	public Vertex getOrigin();

	public void setOrigin(Vertex origin);

	public HalfEdge getTwin();

	public void setTwin(HalfEdge twin);

	public Face getIncidentFace();

	public void setIncidentFace(Face incidentFace);

	public HalfEdge getNext();

	public void setNext(HalfEdge next);

	public HalfEdge getPrevious();

	public void setPrevious(HalfEdge previous);

}
