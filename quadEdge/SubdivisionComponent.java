package quadEdge;

import cg.Point;

class SubdivisionComponent implements Subdivision {
	private final Edge startingEdge;

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
		// TODO Auto-generated method stub

	}

	private Edge locate(Point p) {
		return startingEdge;

	}

}
