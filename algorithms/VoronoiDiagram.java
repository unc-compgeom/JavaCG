package algorithms;

import cg.PointSet;
import cg.Subdivision;

class VoronoiDiagram {

	/**
	 * Computes the Voronoi Diagram of a point set by first computing the
	 * Delaunay triangulation and then calculating the dual graph.
	 *
	 * @param points the point set
	 * @return a {@link cg.Subdivision} representing the Voronoi Diagram
	 */
	public static Subdivision makeVoronoiDiagram(PointSet points) {
		Subdivision t = DelaunayTriangulation.triangulate(points);
		return t.getDual();
	}
}
