package algorithms;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

public class GrahmScan {

	public static void findConvexHull(PointSet points, Polygon hull) {
		Point smallest = CG.findSmallestYX(points);
		PointSet sorted = CG.sortByAngle(points, smallest);
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
	}
}
