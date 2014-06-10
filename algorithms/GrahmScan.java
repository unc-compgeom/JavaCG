package algorithms;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

public class GrahmScan {

	public static void doGrahmScan(PointSet points, Polygon hull) {
		Point smallest = CG.findSmallestYX(points);
		points.remove(smallest);
		points.addFirst(smallest);
		PointSet sorted = CG.sortByAngle(points);

		hull.push(sorted.get(0));
		hull.push(sorted.get(1));
		int i = 2;
		while (i < sorted.size()) {
			Point p1 = hull.getSecond();
			Point p2 = hull.getFirst();
			if (Predicate.findOrientation(sorted.get(i), p1, p2) == Orientation.CLOCKWISE) {
				hull.push(sorted.get(i));
				i++;
			} else {
				hull.pop();
			}
		}
	}
}
