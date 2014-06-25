package quadEdge;

class QuadEdge {

	public static Edge makeEdge() {
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

	public static void splice(Edge a, Edge b) {
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

	public static Edge connect(Edge a, Edge b) {
		Edge e = makeEdge();
		splice(e, a.lnext());
		splice(e.sym(), b);
		e.setOrig(a.dest());
		e.setDest(b.orig());
		return e;
	}

	public static void swap(Edge e) {
		Edge a = e.oPrev();
		Edge b = e.sym().oPrev();
		splice(e, a);
		splice(e.sym(), b);
		splice(e, a.lnext());
		splice(e.sym(), b.lnext());
		splice(e.sym(), b.lnext());
		e.setOrig(a.dest());
		e.setDest(b.dest());
	}

	public static void deleteEdge(Edge e) {
		splice(e, e.oPrev());
		splice(e.sym(), e.sym().oPrev());
	}
}
