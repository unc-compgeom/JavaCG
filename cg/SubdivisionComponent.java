package cg;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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
		edges.add(ea);
		edges.add(eb);
		edges.add(ec);
		notifyObservers();
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
			notifyObservers();
		}
		Edge base = QuadEdge.makeEdge();
		edges.add(base);
		base.setOrig(e.orig());
		base.setDest(new PointComponent(p.getX(), p.getY()));
		QuadEdge.splice(base, e);
		this.startingEdge = base;
		do {
			base = QuadEdge.connect(e, base.sym());
			e = base.oPrev();
		} while (e.lnext() != startingEdge);
		do {
			Edge t = e.oPrev();
			if (Predicate.rightOf(t.dest(), e)
					&& Predicate.inCircle(e.orig(), t.dest(), e.dest(), p)) {
				QuadEdge.swap(e);
				notifyObservers();
				e = e.oPrev();
			} else if (e.oNext() == startingEdge) {
				return;
			} else {
				e = e.oNext().lprev();
			}
		} while (true);
	}

	/**
	 * Returns the <tt>Edge</tt> that contains <tt>p</tt> or the edge of a
	 * triangle containing <tt>p</tt>.
	 * 
	 * @param q
	 *            the point to locate
	 * @return the edge that p is on;
	 */
	public Edge locate(Point p) {
		Edge e = startingEdge;
		if (Predicate.rightOf(p, e)) {
			e = e.sym();
		}
		while (true) {
			if (p == e.dest() || p == e.orig()) {
				return e;
			} else {
				int whichOp = 0;
				if (!Predicate.rightOf(p, e.oNext())) {
					whichOp+=1;
				}
				if (!Predicate.rightOf(p, e.dPrev())) {
					whichOp+=2;
				}
				switch (whichOp){
				case 0: return e;
				case 1: e = e.oNext();
				case 2: e = e.dPrev();
				default: if ()
				}
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		if (isInvisible())
			return;
		for (Edge e : edges) {
			e.paintComponent(g);
		}
	}
}