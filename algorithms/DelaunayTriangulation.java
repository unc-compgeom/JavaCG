package algorithms;

import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Subdivision;
import util.ColorSpecial;
import util.DuplicatePointException;

class DelaunayTriangulation {

	/**
	 * Computes the Delaunay triangulation of the point set.
	 * @param points the point set
	 * @return a {@link Subdivision} representing the Delaunay Triangulation
	 */
	public static Subdivision triangulate(PointSet points) {
		// the bulk of the code for Delaunay triangulation is located in ../cg/SubdivisionComponent
		Subdivision s = GeometryManager.newSubdivision();
		s.setColor(ColorSpecial.PASTEL_GREEN);
		for (Point p : points) {
			try {
				s.insertSite(p);
			} catch (DuplicatePointException ignored) {
				// don't insert duplicates.
			}
		}
		return s;
	}
}
