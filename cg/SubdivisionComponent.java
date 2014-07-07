package cg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import predicates.Predicate;
import util.DuplicatePointException;
import util.MalformedTriangulationException;

/**
 * This class can be used to represent a Delaunay Triangulation. It uses the
 * {@link QuadEdge} data structure to maintain edge data.
 * 
 * @author Vance Miller
 * 
 */
public class SubdivisionComponent extends AbstractGeometry implements
		Subdivision {
	private Edge startingEdge;
	private final List<Edge> edges;

	public SubdivisionComponent(Point a, Point b, Point c) {
		Edge ea = QuadEdge.makeEdge();
		ea.setOrig(a);
		ea.setDest(b);

		Edge eb = QuadEdge.makeEdge();
		QuadEdge.splice(ea.sym(), eb);
		eb.setOrig(b);
		eb.setDest(c);

		Edge ec = QuadEdge.makeEdge();
		QuadEdge.splice(eb.sym(), ec);
		ec.setOrig(c);
		ec.setDest(a);

		QuadEdge.splice(ec.sym(), ea);
		this.startingEdge = ea;
		edges = new ArrayList<Edge>();
		ea.setInvisible(true);
		eb.setInvisible(true);
		ec.setInvisible(true);
		edges.add(ea);
		edges.add(eb);
		edges.add(ec);
		notifyObservers();
	}

	@Override
	public void insertSite(Point p) throws DuplicatePointException,
			MalformedTriangulationException {
		Edge e = locate(p);
		if (Predicate.onEdge(p, e)) {
			e = e.oPrev();
			QuadEdge.deleteEdge(e.oNext());
			notifyObservers();
		}
		// connect the new point to the vertices of the containing triangle
		Edge base = QuadEdge.makeEdge();
		base.setOrig(e.orig());
		base.setDest(GeometryManager.newPoint(p));
		QuadEdge.splice(base, e);

		this.startingEdge = base;
		// for drawing:
		edges.add(base);
		base.setColor(getColor());
		// add edges
		do {

			base = QuadEdge.connect(e, base.sym());

			e = base.oPrev();
			// for drawing:
			edges.add(base);
			base.setColor(getColor());
			if (base.isInvisible())
				base.setInvisible(true);
		} while (e.lNext() != startingEdge);
		// examine suspect edges and ensure that the Delaunay condition is
		// satisfied
		do {
			Edge t = e.oPrev();
			if (Predicate.rightOf(t.dest(), e)
					&& Predicate.isPointInCircle(p, e.orig(), t.dest(),
							e.dest())) {
				QuadEdge.swap(e);
				notifyObservers();
				e = e.oPrev();
			} else if (e.oNext() == startingEdge) {
				return;
			} else {
				e = e.oNext().lPrev();
			}
		} while (true);
	}

	@Override
	public Edge locate(Point q) throws DuplicatePointException,
			MalformedTriangulationException {
		Edge e = startingEdge;
		int guard = 2 * edges.size();
		if (!Predicate.rightOrAhead(e.dest(), e.orig(), q)) {
			e = e.sym();
		}
		Point p = e.orig();
		if (p == q) {
			throw new DuplicatePointException(q);
		}
		// invariant: e intersects pq with e.dest() on, right, or ahead of pq.
		do {
			if (q == e.dest()) {
				// duplicate point
				throw new DuplicatePointException(q);
			} else if (!Predicate.leftOrAhead(q, e.orig(), e.dest())) {
				// q is on an edge or inside a triangle edge
				return e.sym();
			} else if (Predicate.rightOrAhead(e.oNext().dest(), p, q)) {
				e = e.oNext();
			} else {
				e = e.lNext().sym();
			}
		} while (guard-- > 0);
		throw new MalformedTriangulationException();
	}

	@Override
	public void paint(Graphics g) {
		if (isInvisible())
			return;
		for (Edge e : edges) {
			e.paint(g);
		}
	}

	@Override
	public void setColor(Color c) {
		super.setColor(c);
		for (Edge e : edges) {
			e.setColor(c);
		}
	}
}