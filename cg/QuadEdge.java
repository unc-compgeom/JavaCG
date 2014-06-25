package cg;

import java.awt.Graphics;

public class QuadEdge extends AbstractGeometry implements Drawable {
	private Edge first;

	public QuadEdge() {

	}

	public Edge makeEdge(Point origin, Point destination) {
		Edge e0 = new EdgeComponent();
		Edge e1 = new EdgeComponent();
		Edge e2 = new EdgeComponent();
		Edge e3 = new EdgeComponent();

		e0.setRot(e1);
		e1.setRot(e2);
		e2.setRot(e3);
		e3.setRot(e0);

		e0.setNext(e0);
		e1.setNext(e3);
		e2.setNext(e2);
		e3.setNext(e1);

		e0.setOrig(origin);
		e2.setOrig(destination);
		if (first == null) {
			first = e0;
		}
		return e0;
	}

	@Override
	public void paintComponent(Graphics g) {
		Edge go = first;
		while (go.oNext() != first) {
			go.paintComponent(g);
			go = go.oNext();
		}
	}

	public void splice(Edge e0, Edge e1) {
		Edge alpha = e0.oNext().rot();
		Edge beta = e1.oNext().rot();

		Edge t0 = e1.oNext();
		Edge t1 = e0.oNext();
		Edge t2 = beta.oNext();
		Edge t3 = alpha.oNext();

		e0.setNext(t0);
		e1.setNext(t1);
		alpha.setNext(t2);
		beta.setNext(t3);
	}

}
