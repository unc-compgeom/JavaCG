package algorithms;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import util.ColorSpecial;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

class JarvisMarch {

	/**
	 * Finds the convex hull using the Jarvis March.
	 *
	 * @param points
	 *            the point set
	 * @return a {@link cg.Polygon} representing the convex hull
	 */
	public static Polygon findConvexHull(final PointSet points) {
		final Polygon hull = GeometryManager.newPolygon();
		hull.setColor(ColorSpecial.PASTEL_GREEN);
		final Point min = CG.findSmallestYX(points);
		Point p, q = min;
		int i = 0;
		do {
			hull.addLast(q);
			p = hull.get(i);
			q = nextHullPoint(points, p);
			i++;
		} while (!q.equals(min));
		// close hull
		hull.add(hull.getFirst());
		return hull;
	}

	private static Point nextHullPoint(final PointSet points, final Point p) {
		Point q = p;
		for (final Point r : points) {
			final Orientation o = Predicate.findOrientation(p, q, r);
			if (o == Orientation.COUNTERCLOCKWISE || o == Orientation.COLINEAR
					&& CG.distSquared(p, r) > CG.distSquared(p, q)) {
				q = r;
			}
		}
		return q;
	}
}
