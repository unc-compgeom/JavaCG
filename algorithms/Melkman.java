package algorithms;

import predicates.Predicate;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

public class Melkman {

	public static void doMelkman(PointSet points, Polygon hull) {
		Point p0 = points.getPoint(0);
		Point p1 = points.getPoint(1);
		hull.addFirst(p0);
		hull.addFirst(p1);
		hull.addLast(p1);
		for (int i = 2; i < points.numPoints(); i++) {
			if (!Predicate.isLeftOrInside(hull.getSecondToLast(),
					hull.getLast(), points.getPoint(i))
					|| !Predicate.isLeftOrInside(points.getPoint(i),
							hull.getFirst(), hull.getSecond())) {
				while (!Predicate.isLeftOrInside(hull.getSecondToLast(),
						hull.getLast(), points.getPoint(i))) {
					hull.removeLast();

				}
				while (!Predicate.isLeftOrInside(points.getPoint(i),
						hull.getFirst(), hull.getSecond())) {
					hull.removeFirst();
				}
				hull.addFirst(points.getPoint(i));
				hull.addLast(points.getPoint(i));
			}
		}
	}
}
