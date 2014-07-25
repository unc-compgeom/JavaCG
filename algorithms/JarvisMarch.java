package algorithms;

import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Polygon;
import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import util.ColorSpecial;

class JarvisMarch {

	/**
	 * Finds the convex hull using the Jarvis March.
	 *
	 * @param points the point set
	 * @return a {@link cg.Polygon} representing the convex hull
	 */
	public static Polygon findConvexHull(PointSet points) {
		Polygon hull = GeometryManager.newPolygon();
		hull.setColor(ColorSpecial.PASTEL_GREEN);
		Point min = CG.findSmallestYX(points);
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

	private static Point nextHullPoint(PointSet points, Point p) {
		Point q = p;
		for (Point r : points) {
			Orientation o = Predicate.findOrientation(p, q, r);
			if (o == Orientation.COUNTERCLOCKWISE || o == Orientation.COLINEAR
					&& CG.distSquared(p, r) > CG.distSquared(p, q)) {
				q = r;
			}
		}
		return q;
	}
}
