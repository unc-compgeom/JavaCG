package cg;

import java.util.Iterator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class QuadEdgeComponent extends AbstractGeometry implements QuadEdge {

	private final Edge first;

	QuadEdgeComponent() {
		final int scale = 16364;
		final Point a = new PointComponent(-1 * scale - 1, 2 * scale);
		final Point b = new PointComponent(-1 * scale, -1 * scale);
		final Point c = new PointComponent(2 * scale, -1 * scale);

		final Edge ea = makeEdge();
		ea.setCoordinates(a, b);

		final Edge eb = makeEdge();
		eb.setCoordinates(b, c);
		splice(ea.sym(), eb);

		final Edge ec = makeEdge();
		ec.setCoordinates(c, a);
		splice(eb.sym(), ec);

		splice(ec.sym(), ea);
		first = ec;
		ea.setInvisible(true);
		eb.setInvisible(true);
		ec.setInvisible(true);
	}

	@Override
	public Edge connect(final Edge a, final Edge b) {
		final Edge e = makeEdge();
		e.setCoordinates(a.dest(), b.orig());
		splice(e, a.lNext());
		splice(e.sym(), b);
		return e;
	}

	@Override
	public void deleteEdge(final Edge e) {
		splice(e, e.oPrev());
		splice(e.sym(), e.sym().oPrev());
	}

	@Override
	public Edge get(final int i) {
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

	private boolean isWall(final Edge e) {
		return e.orig().compareTo(e.lNext().orig()) >= 0
				&& e.lNext().orig().compareTo(e.lPrev().orig()) > 0;

	}

	public Iterator<Edge> iterator() {
		return new Iterator<Edge>() {
			private final Edge e = first;
			private Edge next = e;
			private boolean firstCase = first != null;

			@Override
			public boolean hasNext() {
				if (firstCase) {
					firstCase = false;
					return true;
				} else {
					return next != e;
				}
			}

			@Override
			public Edge next() {
				final Edge tmp = next;
				if (isWall(next) || isWall(next.sym())) {
					next = next.rPrev();
				} else {
					next = next.oNext();
				}
				return tmp;
			}
		};
	}

	@Override
	public Edge makeEdge() {
		final Edge[] edges = new Edge[4];
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
	public void paint(final GraphicsContext gc) {
		if (isInvisible()) {
			return;
		}
		// push old values onto the "stack"
		final Paint oldFill = gc.getFill();
		final Paint oldStroke = gc.getStroke();
		final double oldSize = gc.getLineWidth();
		final Color c = super.getColor();
		if (c != null) {
			gc.setFill(c);
			gc.setStroke(c.darker());
		}
		gc.setLineWidth(GeometryManager.getSize());

		synchronized (this) {
			Edge e = first;
			do {
				if (isWall(e) || isWall(e.sym())) {
					e.paint(gc);
					e = e.rPrev();
				} else {
					e.paint(gc);
					e = e.oNext();
				}
			} while (e != first);
		}

		// restore gc from the "stack"
		gc.setFill(oldFill);
		gc.setStroke(oldStroke);
		gc.setLineWidth(oldSize);
	}

	@Override
	public void splice(final Edge a, final Edge b) {
		final Edge alpha = a.oNext().rot();
		final Edge beta = b.oNext().rot();

		final Edge t1 = b.oNext();
		final Edge t2 = a.oNext();
		final Edge t3 = beta.oNext();
		final Edge t4 = alpha.oNext();

		a.setNext(t1);
		b.setNext(t2);
		alpha.setNext(t3);
		beta.setNext(t4);
	}

	@Override
	public void swap(final Edge e) {
		final Edge a = e.oPrev();
		final Edge b = e.sym().oPrev();
		e.setCoordinates(a.dest(), b.dest());
		splice(e, a);
		splice(e.sym(), b);
		splice(e, a.lNext());
		splice(e.sym(), b.lNext());
	}
}
