package cg;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class HalfEdgeComponent extends AbstractGeometry implements HalfEdge {
	private Vertex origin;
	private HalfEdge twin, next, previous;
	private Face incidentFace;

	public HalfEdgeComponent(Vertex origin, HalfEdge twin, HalfEdge next,
			HalfEdge previous, Face incidentFace) {
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
		this.twin = new HalfEdgeComponent(destination, this,
				previous.getTwin(), next.getTwin(), incidentFace);
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
		g.setColor(super.getColor());
		// draw line
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(getSize()));
		g2D.drawLine(origin.getX(), origin.getY(), twin.getOrigin().getX(),
				twin.getOrigin().getY());
		g2D.setStroke(new BasicStroke());
		// draw endpoints
		origin.setSize(getSize());
		origin.setColor(super.getColor());
		origin.paintComponent(g);
		twin.setColor(super.getColor());
		twin.getOrigin().setSize(getSize());
		twin.getOrigin().paintComponent(g);
	}

	@Override
	public String toString() {
		return getOrigin().toString() + " -> "
				+ getTwin().getOrigin().toString();
	}

	@Override
	public int compareTo(HalfEdge o) {
		return origin.compareTo(o.getOrigin());
	}
}
