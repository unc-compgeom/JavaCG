package algorithms;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.Polygon;
import cg.Point;
import cg.PointSet;

public class JarvisMarch {
	public static void findConvexHull(PointSet points, Polygon hull) {
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
	}

	private static Point nextHullPoint(PointSet points, Point p) {
		Point q = p;
		for (Point r : points) {
			Orientation o = Predicate.findOrientation(p, q, r);
			if (o == Orientation.COUNTERCLOCKWISE
					|| (o == Orientation.COLINEAR && CG.distSquared(p, r) > CG
							.distSquared(p, q))) {
				q = r;
			}
		}
		return q;
	}
}
