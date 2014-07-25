package algorithms;

import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Polygon;
import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import util.ColorSpecial;

class GrahamScan {

	/**
	 * Finds the convex hull using the Graham scan.
	 *
	 * @param points the point set
	 * @return a {@link cg.Polygon} representing the convex hull
	 */
	public static Polygon findConvexHull(PointSet points) {
		Polygon hull = GeometryManager.newPolygon();
		hull.setColor(ColorSpecial.PASTEL_GREEN);
		// degenerate case handling
		if (points.size() == 0) {
			return hull;
		} else if (points.size() == 1) {
			hull.add(points.get(0));
			return hull;
		}
		// Graham Scan
		Point smallest = CG.findSmallestYX(points);
		PointSet sorted = CG.sortByAngle(points, smallest);
		hull = GeometryManager.newPolygon();
		hull.setColor(ColorSpecial.PASTEL_GREEN);
		hull.push(sorted.get(0));
		hull.push(sorted.get(1));
		int i = 2;
		while (i < sorted.size()) {
			Point p2 = hull.getFirst();
			Point p1 = hull.getSecond();
			Orientation o = Predicate.findOrientation(sorted.get(i), p1, p2);
			if (o == Orientation.CLOCKWISE) {
				hull.push(sorted.get(i));
				i++;
			} else if (o == Orientation.COLINEAR) {
				hull.pop();
				hull.push(sorted.get(i));
				i++;
			} else {
				hull.pop();
			}
		}
		// close hull
		hull.addFirst(hull.getLast());
		return hull;
	}
}
