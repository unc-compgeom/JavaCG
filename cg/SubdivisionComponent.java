package cg;

import java.awt.Graphics;

public class SubdivisionComponent extends AbstractGeometry implements
		Subdivision {
	private Edge startingEdge;

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
		QuadEdge.splice(ec, ea);

		this.startingEdge = ea;
	}

	@Override
	public void insertSite(Point p) {
		Edge e = locate(p);
		if ((p == e.orig() || p == e.dest())) {
			// point is already in the subdivision
			return;
		} else if (Predicate.onEdge(p, e)) {
			e = e.oPrev();
			QuadEdge.deleteEdge(e.oNext());
		}
		Edge base = QuadEdge.makeEdge();
		base.setOrig(e.orig());
		base.setDest(new PointComponent(p.getX(), p.getY()));
		QuadEdge.splice(base, e);
		this.startingEdge = base;
		do {
			base = QuadEdge.connect(e, base.sym());
			e = base.oPrev();
			System.out.println("connected " + e + " to " + base);
		} while (e.lnext() != startingEdge);
		System.out.println("done connecting bases");
		do {
			Edge t = e.oPrev();
			if (Predicate.rightOf(t.dest(), e)
					&& Predicate.inCircle(e.orig(), t.dest(), e.dest(), p)) {
				QuadEdge.swap(e);
				e = e.oPrev();
				System.out.println("is right and incircle " + e);
			} else if (e.oNext() == startingEdge) {
				System.out.println("back to beginning");
				return;
			} else {
				e = e.oNext().lprev();
				System.out.println("popped " + e);
			}
			System.out.println("Triangulation satisfied for " + t);
		} while (true);
	}

	/**
	 * Returns an <tt>Edge</tt> that <tt>p</tt> is on.
	 * 
	 * @param p
	 *            the point to locate
	 * @return the edge that p is on;
	 */
	public Edge locate(Point p) {
		Edge e = startingEdge;
		while (true) {
			if (p == e.orig() || p == e.dest()) {
				return e;
			} else if (Predicate.rightOf(p, e)) {
				e = e.sym();
			} else if (!Predicate.rightOf(p, e.oNext())) {
				e = e.oNext();
			} else if (!Predicate.rightOf(p, e.dPrev())) {
				e = e.dPrev();
			} else {
				return e;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		if (isInvisible())
			return;
		Edge e = startingEdge;
		do {
			e.paintComponent(g);
			e = e.oNext();
		} while (e != startingEdge);
	}
}
