package algorithms;

import cg.PointSet;
import cg.Subdivision;

class VoronoiDiagram {
	public static Subdivision makeVoroniDiagram(PointSet points) {
		Subdivision t = DelaunayTriangulation.triangulate(points);
		makeVoroniDiagram(t);
		return null;
	}

	private static void makeVoroniDiagram(Subdivision t) {
		t.getDual();
	}
}
