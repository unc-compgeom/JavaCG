package algorithms;

import predicates.Predicate;
import cg.Polygon;
import cg.Point;
import cg.PointSet;

public class Melkman {

	public static void findConvexHull(PointSet points, Polygon hull) {
		Point p0 = points.get(0);
		Point p1 = points.get(1);
		hull.addFirst(p0);
		hull.addFirst(p1);
		hull.addLast(p1.clone());
		for (int i = 2; i < points.size(); i++) {
			if (!Predicate.isLeftOrInside(hull.getSecondToLast(),
					hull.getLast(), points.get(i))
					|| !Predicate.isLeftOrInside(points.get(i),
							hull.getFirst(), hull.getSecond())) {
				while (!Predicate.isLeftOrInside(hull.getSecondToLast(),
						hull.getLast(), points.get(i))) {
					hull.removeLast();

				}
				while (!Predicate.isLeftOrInside(points.get(i),
						hull.getFirst(), hull.getSecond())) {
					hull.removeFirst();
				}
				hull.addFirst(points.get(i));
				hull.addLast(points.get(i).clone());
			}
		}
	}
}
