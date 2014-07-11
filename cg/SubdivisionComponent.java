package cg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import predicates.Predicate;
import util.CG;
import util.ColorSpecial;
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

	public SubdivisionComponent() {
		// TODO find a triangle large enough to encompass <tt>points</tt>
		int scale = 16364;
		Point a = new PointComponent(-1 * scale - 1, 2 * scale);
		Point b = new PointComponent(-1 * scale, -1 * scale);
		Point c = new PointComponent(2 * scale, -1 * scale);

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
		// add edges
		do {
			base = QuadEdge.connect(e, base.sym());
			e = base.oPrev();
			// for drawing:
			edges.add(base);
			if (base.isInvisible()) {
				base.setInvisible(true);
			}
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
		} while (true);
	}

	@Override
	public void paint(Graphics g) {
		if (isInvisible())
			return;
		Color oldG = g.getColor(), c = super.getColor();
		if (c != null) {
			g.setColor(c);
		}
		for (Edge e : edges) {
			e.paint(g);
			Color o = g.getColor();
			g.setColor(ColorSpecial.AZURE);
			try {
				e.rot().paint(g);
			} catch (NullPointerException ex) {

			}
			g.setColor(o);
		}
		g.setColor(oldG);
	}

	@Override
	public void getDual() {
		// iterate over each triangle
		Edge e = startingEdge;
		fixTriangle(e.lNext(), e.lNext().lNext(), e.oNext());
		e.setVisited();
	}

	private void traverse(Edge start) {
		Edge e = start;
		do {
			if (isWall(e) || isWall(e.sym())) {
				e = e.lNext();
			} else {
				e = e.oNext();
			}
		} while (e != start);
	}

	private boolean isWall(Edge e) {
		return e.dest().compareTo(e.orig()) < 0
				&& e.orig().compareTo(e.lNext().dest()) < 0;
	}

	private void fixTriangle(Edge e1, Edge e2, Edge e3) {
		Point center = getCenter(e1, e2, e3);
		System.out
				.println("e1.rot() dest " + e1.rot().dest() + " -> " + center);
		System.out
				.println("e2.rot() dest " + e2.rot().dest() + " -> " + center);
		System.out
				.println("e3.rot() dest " + e3.rot().dest() + " -> " + center);
		e1.rot().setDest(center);
		e2.rot().setDest(center);
		e3.rot().setDest(center);
	}

	private Point getCenter(Edge e1, Edge e2, Edge e3) {
		Point p = e1.orig(), q = e2.orig(), r = e3.orig();
		Color o1 = p.getColor(), o2 = q.getColor(), o3 = r.getColor();
		p.setColor(ColorSpecial.AMARANTH);
		q.setColor(ColorSpecial.AMARANTH);
		r.setColor(ColorSpecial.AMARANTH);
		List<Point> points = new ArrayList<Point>(3);
		points.add(p);
		points.add(q);
		points.add(r);
		Circle c = GeometryManager.newCircle(points);
		CG.animationDelay();
		GeometryManager.destroy(c);
		p.setColor(o1);
		q.setColor(o2);
		r.setColor(o3);
		double px = p.getX(), py = p.getY();
		double qx = q.getX(), qy = q.getY();
		double rx = r.getX(), ry = r.getY();

		double det = (px - qx) * (py - ry) - (py - qy) * (px - rx);
		float x = (float) ((p.plus(q).div(2).dot(p.sub(q)) * (py - ry) - (py - qy)
				* (p.plus(r).div(2).dot(p.sub(r)))) / det);
		float y = (float) (((px - qx) * p.plus(r).div(2).dot(p.sub(r)) - p
				.plus(q).div(2).dot(p.sub(q))
				* (px - rx)) / det);
		return new PointComponent(x, y);
	}
}