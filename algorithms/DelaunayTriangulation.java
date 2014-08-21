package algorithms;

import util.ColorSpecial;
import util.DuplicatePointException;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Subdivision;

class DelaunayTriangulation {

	/**
	 * Computes the Delaunay triangulation of the point set.
	 * 
	 * @param points
	 *            the point set
	 * @return a {@link Subdivision} representing the Delaunay Triangulation
	 */
	public static Subdivision triangulate(final PointSet points) {
		// the bulk of the code for Delaunay triangulation is located in
		// ../cg/SubdivisionComponent
		final Subdivision s = GeometryManager.newSubdivision();
		s.setColor(ColorSpecial.PASTEL_GREEN);
		for (final Point p : points) {
			try {
				s.insertSite(p);
			} catch (final DuplicatePointException ignored) {
				// don't insert duplicates.
			}
		}
		return s;
	}
}
