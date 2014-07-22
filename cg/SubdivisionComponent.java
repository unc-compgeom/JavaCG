package cg;

import predicates.Predicate;
import util.DuplicatePointException;

/**
 * This class can be used to represent a Delaunay Triangulation. It uses the
 * {@link QuadEdge} data structure to maintain edge data.
 *
 * @author Vance Miller
 */
public class SubdivisionComponent extends QuadEdgeComponent implements
		Subdivision {
	private Edge startingEdge;

	SubdivisionComponent() {
		// TODO find a triangle large enough to encompass <tt>points</tt>
		super();
		startingEdge = get(0);
		isReady = true;
		notifyObservers(this);
		synchronized (this) {
			notifyAll();
		}
	}

	@Override
	public synchronized void insertSite(Point p) throws DuplicatePointException {
		isReady = false;
		Edge e = locate(p);
		if (Predicate.onEdge(p, e)) {
			e = e.oPrev();
			deleteEdge(e.oNext());
		}
		// connect the new point to the vertices of the containing triangle
		Edge base = makeEdge();
		base.setCoordinates(e.orig(), GeometryManager.newPoint(p));
		splice(base, e);
		this.startingEdge = base;
		// add edges
		do {
			base = connect(e, base.sym());
			e = base.oPrev();
		} while (e.lNext() != startingEdge);
		// examine suspect edges and ensure that the Delaunay condition is
		// satisfied
		do {
			Edge t = e.oPrev();
			if (Predicate.rightOf(t.dest(), e)
					&& Predicate.isPointInCircle(p, e.orig(), t.dest(),
					e.dest())) {
				swap(e);
				e = e.oPrev();
			} else if (e.oNext() == startingEdge) {
				isReady = true;
				notifyObservers(this);
				notifyAll();
				return;
			} else {
				e = e.oNext().lPrev();
			}
		} while (true);
	}

	@Override
	public synchronized Edge locate(Point q) throws DuplicatePointException {
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
	public synchronized void getDual() {

	}

	private boolean isWall(Edge e) {
		try {
			return e.orig().compareTo(e.lNext().orig()) >= 0
					&& e.lNext().orig().compareTo(e.lPrev().orig()) > 0;
		} catch (NullPointerException ee) {
			System.out.println(e + " " + e.sym());
			ee.printStackTrace();
			return true;
		}
	}

	private void traverse(Edge start) {
		Edge e = start;
		do {
			// something to e
			if (isWall(e) || isWall(e.sym())) {
				e = e.rPrev();
			} else {
				e = e.oNext();
			}
		} while (e != start);
	}

//	private void fixTriangle(Edge e1, Edge e2, Edge e3) {
//		Point center = getCenter(e1, e2, e3);
//		System.out
//				.println("e1.rot() dest " + e1.rot().dest() + " -> " + center);
//		System.out
//				.println("e2.rot() dest " + e2.rot().dest() + " -> " + center);
//		System.out
//				.println("e3.rot() dest " + e3.rot().dest() + " -> " + center);
//		e1.rot().setDest(center);
//		e2.rot().setDest(center);
//		e3.rot().setDest(center);
//	}
//
//	private Point getCenter(Edge e1, Edge e2, Edge e3) {
//		Point p = e1.orig(), q = e2.orig(), r = e3.orig();
//		Color o1 = p.getColor(), o2 = q.getColor(), o3 = r.getColor();
//		p.setColor(ColorSpecial.AMARANTH);
//		q.setColor(ColorSpecial.AMARANTH);
//		r.setColor(ColorSpecial.AMARANTH);
//		List<Point> points = new ArrayList<Point>(3);
//		points.add(p);
//		points.add(q);
//		points.add(r);
//		Circle c = GeometryManager.newCircle(points);
//		CG.animationDelay();
//		GeometryManager.destroy(c);
//		p.setColor(o1);
//		q.setColor(o2);
//		r.setColor(o3);
//		double px = p.getX(), py = p.getY();
//		double qx = q.getX(), qy = q.getY();
//		double rx = r.getX(), ry = r.getY();
//
//		double det = (px - qx) * (py - ry) - (py - qy) * (px - rx);
//		float x = (float) ((p.plus(q).div(2).dot(p.sub(q)) * (py - ry) - (py - qy)
//				* (p.plus(r).div(2).dot(p.sub(r)))) / det);
//		float y = (float) (((px - qx) * p.plus(r).div(2).dot(p.sub(r)) - p
//				.plus(q).div(2).dot(p.sub(q))
//				* (px - rx)) / det);
//		return new PointComponent(x, y);
//	}
}