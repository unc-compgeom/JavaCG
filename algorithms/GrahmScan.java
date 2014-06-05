package algorithms;

import predicates.Predicate;
import util.CG;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

public class GrahmScan {

	public static void doGrahmScan(PointSet points, Polygon hull) {
		Point min = findSmallestYX(points);
		PointSet sorted = CG.sortByAngle(points, min);
		hull.push(sorted.getPoint(0));
		hull.push(sorted.getPoint(1));
		int i = 2;
		while (i < points.numPoints()) {
			if (Predicate.isPointLeftOrOnSegment(sorted.getPoint(i),
					hull.getSecond(), hull.getFirst())) {
				hull.push(sorted.getPoint(i));
				i++;
			} else {
				hull.pop();
			}
		}
	}

	private static Point findSmallestYX(PointSet points) {
		Point min = points.getPoint(0);
		int minY = min.getY();
		Point lookingAt;
		for (int i = 1; i < points.numPoints(); i++) {
			lookingAt = points.getPoint(i);
			if (lookingAt.getY() <= minY) {
				if (lookingAt.getY() < minY) {
					min = lookingAt;
					minY = min.getY();
				} else {
					min = (min.getX() < lookingAt.getX()) ? min : lookingAt;
					minY = min.getY();
				}
			}
		}
		return min;
	}
}
