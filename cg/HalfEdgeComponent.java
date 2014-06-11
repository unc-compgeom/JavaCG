package cg;

import java.awt.Graphics;

public class HalfEdgeComponent extends AbstractGeometry implements HalfEdge {
	private Vertex origin;
	private HalfEdge twin, next, previous;
	private Face incidentFace;

	public HalfEdgeComponent(Vertex origin, HalfEdge twin, HalfEdge next, HalfEdge previous,
			Face incidentFace) {
		super();
		this.origin = origin;
		this.twin = twin;
		this.next = next;
		this.previous = previous;
		this.incidentFace = incidentFace;
	}

	/**
	 * Constructs an edge and it's twin edge from the <code>origin</code> and
	 * <code>destination</code> vertices.
	 * 
	 * @param origin
	 * @param destination
	 * @param next
	 * @param previous
	 * @param indicentFace
	 */
	public HalfEdgeComponent(Vertex origin, Vertex destination, HalfEdge next,
			HalfEdge previous, Face indicentFace) {
		super();
		this.origin = origin;
		this.twin = new HalfEdgeComponent(destination, this, previous.getTwin(),
				next.getTwin(), incidentFace);
		this.next = next;
		this.previous = previous;
		this.incidentFace = indicentFace;
	}

	public HalfEdgeComponent() {
		super();
		this.origin = null;
		this.twin = null;
		this.next = null;
		this.previous = null;
		this.incidentFace = null;
	}

	@Override
	public Vertex getOrigin() {
		return this.origin;
	}

	@Override
	public void setOrigin(Vertex origin) {
		this.origin = origin;
		notifyObservers();
	}

	@Override
	public HalfEdge getTwin() {
		return twin;
	}

	@Override
	public void setTwin(HalfEdge twin) {
		this.twin = twin;
		notifyObservers();
	}

	@Override
	public Face getIncidentFace() {
		return incidentFace;
	}

	@Override
	public void setIncidentFace(Face incidentFace) {
		this.incidentFace = incidentFace;
		notifyObservers();
	}

	@Override
	public HalfEdge getNext() {
		return next;
	}

	@Override
	public void setNext(HalfEdge next) {
		this.next = next;
		notifyObservers();
	}

	@Override
	public HalfEdge getPrevious() {
		return previous;
	}

	@Override
	public void setPrevious(HalfEdge previous) {
		this.previous = previous;
		notifyObservers();
	}

	@Override
	public void paintComponent(Graphics g) {
		// get color
		super.paintComponent(g);
		// draw line
		g.drawLine(origin.getX(), origin.getY(), twin.getOrigin().getX(), twin
				.getOrigin().getY());
		// draw endpoints
		origin.paintComponent(g);
		twin.getOrigin().paintComponent(g);
	}
}
