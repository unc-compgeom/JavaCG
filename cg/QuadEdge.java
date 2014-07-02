package cg;

/**
 * A collection of four directed edges designed for representing general
 * subdivisions of orientable manifolds. This data structure is described in a
 * paper by Guibas and Stolfi (1985).
 * 
 * @author Vance Miller
 * 
 */
class QuadEdge {
	/**
	 * Creates a new QuadEdge collection that connects the destination of
	 * <tt>a</tt> to the origin of <tt>b</tt>, so that all three have the same
	 * left face after the connection is complete and the data pointers of the
	 * new edge are set.
	 * 
	 * @param a
	 *            an edge
	 * @param b
	 *            an edge
	 * @return the new edge connecting <tt>a</tt> and <tt>b</tt>
	 */
	public static Edge connect(Edge a, Edge b) {
		Edge e = makeEdge();
		splice(e, a.lnext());
		splice(e.sym(), b);
		e.setOrig(a.dest());
		e.setDest(b.orig());
		return e;
	}

	/**
	 * Disconnects edge e from the collection of edges.
	 * 
	 * @param e
	 *            the edge to delete
	 */
	public static void deleteEdge(Edge e) {
		splice(e, e.oPrev());
		splice(e.sym(), e.sym().oPrev());
	}

	/**
	 * Creates an empty edge quartet.
	 * 
	 * @return the edge
	 */
	public static Edge makeEdge() {
		QuadEdge q = new QuadEdge();
		return q.edges[0];
	}

	/**
	 * Splices two edges together or apart. Splice affects the two edge rings
	 * around the origins of a and b, and, independently, the two edge rings
	 * around the left faces of a and b. In each case, (i) if the two rings are
	 * distinct, Splice will combine them into one, or (ii) if the two are the
	 * same ring, Splice will break it into two separate pieces. Thus, Splice
	 * can be used both to attach the two edges together, and to break them
	 * apart.
	 * 
	 * @param a
	 *            an edge
	 * @param b
	 *            an edge
	 */
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

	/**
	 * Turns an edge counterclockwise inside its enclosing quadrilateral.
	 * 
	 * @param e
	 *            the edge to swap
	 */
	public static void swap(Edge e) {
		String tmp = e.toString();
		Edge a = e.oPrev();
		Edge b = e.sym().oPrev();
		splice(e, a);
		splice(e.sym(), b);
		splice(e, a.lnext());
		splice(e.sym(), b.lnext());
		e.setOrig(a.dest());
		e.setDest(b.dest());
		System.out.println("swapped " + tmp + " to " + e);
	}

	Edge[] edges;

	private QuadEdge() {
		edges = new Edge[4];
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
	}
}
