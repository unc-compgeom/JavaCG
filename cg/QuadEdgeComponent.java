package cg;

import java.awt.Color;
import java.awt.Graphics;

public class QuadEdgeComponent extends AbstractGeometry implements QuadEdge {

	private final Edge first;

	QuadEdgeComponent() {
		int scale = 16364;
		Point a = new PointComponent(-1 * scale - 1, 2 * scale);
		Point b = new PointComponent(-1 * scale, -1 * scale);
		Point c = new PointComponent(2 * scale, -1 * scale);

		Edge ea = makeEdge();
		ea.setCoordinates(a, b);

		Edge eb = makeEdge();
		splice(ea.sym(), eb);
		eb.setCoordinates(b, c);

		Edge ec = makeEdge();
		splice(eb.sym(), ec);
		ec.setCoordinates(c, a);

		splice(ec.sym(), ea);
		this.first = ec;
		ea.setInvisible(true);
		eb.setInvisible(true);
		ec.setInvisible(true);
	}

	@Override
	public Edge connect(Edge a, Edge b) {
		Edge e = makeEdge();
		splice(e, a.lNext());
		splice(e.sym(), b);
		e.setCoordinates(a.dest(), b.orig());
		return e;
	}

	@Override
	public void deleteEdge(Edge e) {
		splice(e, e.oPrev());
		splice(e.sym(), e.sym().oPrev());
	}

	@Override
	public Edge get(int i) {
		int count = 0;
		Edge e = first;
		do {
			if (i == count++) {
				return e;
			}
			if (isWall(e) || isWall(e.sym())) {
				e = e.rPrev();
			} else {
				e = e.oNext();
			}
		} while (e != first);
		throw new IndexOutOfBoundsException();
	}

	@Override
	public Edge makeEdge() {
		Edge[] edges = new Edge[4];
		edges[0] = new EdgeComponent();
		edges[1] = new EdgeComponent();
		edges[2] = new EdgeComponent();
		edges[3] = new EdgeComponent();

		edges[0].setRot(edges[1]);
		edges[1].setRot(edges[2]);
		edges[2].setRot(edges[3]);
		edges[3].setRot(edges[0]);

		edges[0].setNext(edges[0]);
		edges[1].setNext(edges[3]);
		edges[2].setNext(edges[2]);
		edges[3].setNext(edges[1]);
		return edges[0];
	}

	@Override
	public void paint(Graphics g) {
		if (isInvisible())
			return;
		Color oldG = g.getColor(), c = super.getColor();
		if (c != null) {
			g.setColor(c);
		}
		Edge e = first;
		do {
			if (isWall(e) || isWall(e.sym())) {
				g.setColor(g.getColor().darker());
				e.paint(g);
				g.setColor(g.getColor().brighter());
				e = e.rPrev();
			} else {
				e.paint(g);
				e = e.oNext();
			}
		} while (e != first);
		g.setColor(oldG);
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

	@Override
	public void splice(Edge a, Edge b) {
		Edge alpha = a.oNext().rot();
		Edge beta = b.oNext().rot();

		Edge t1 = b.oNext();
		Edge t2 = a.oNext();
		Edge t3 = beta.oNext();
		Edge t4 = alpha.oNext();

		a.setNext(t1);
		b.setNext(t2);
		alpha.setNext(t3);
		beta.setNext(t4);
	}

	@Override
	public void swap(Edge e) {
		Edge a = e.oPrev();
		Edge b = e.sym().oPrev();
		splice(e, a);
		splice(e.sym(), b);
		splice(e, a.lNext());
		splice(e.sym(), b.lNext());
		e.setCoordinates(a.dest(), b.dest());
	}
}
