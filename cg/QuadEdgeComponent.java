package cg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.util.Iterator;

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
		isReady = true;
	}

	@Override
	public synchronized Edge connect(Edge a, Edge b) {
		isReady = false;
		Edge e = makeEdge();
		splice(e, a.lNext());
		splice(e.sym(), b);
		e.setCoordinates(a.dest(), b.orig());
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return e;
	}

	@Override
	public synchronized void deleteEdge(Edge e) {
		isReady = false;
		splice(e, e.oPrev());
		splice(e.sym(), e.sym().oPrev());
		isReady = true;
		notifyObservers(this);
		notifyAll();
	}

	@Override
	public synchronized Edge get(int i) {
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
	public synchronized Iterator<Edge> iterator() {
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
				Edge tmp = next;
				if (isWall(next) || isWall(next.sym())) {
					next = next.rPrev();
				} else {
					next = next.oNext();
				}
				return tmp;
			}
		};
	}

	private boolean isWall(Edge e) {
		return e.orig().compareTo(e.lNext().orig()) >= 0
				&& e.lNext().orig().compareTo(e.lPrev().orig()) > 0;

	}

	@Override
	public synchronized void splice(Edge a, Edge b) {
		isReady = false;
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
		isReady = true;
		notifyObservers(this);
		notifyAll();
	}

	@Override
	public synchronized void swap(Edge e) {
		isReady = false;
		Edge a = e.oPrev();
		Edge b = e.sym().oPrev();
		splice(e, a);
		splice(e.sym(), b);
		splice(e, a.lNext());
		splice(e.sym(), b.lNext());
		e.setCoordinates(a.dest(), b.dest());
		isReady = true;
		notifyObservers(this);
		notifyAll();
	}
}
