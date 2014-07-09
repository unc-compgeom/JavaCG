package algorithms;

import predicates.Predicate;
import util.ColorSpecial;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

public class Melkman {

	public static Polygon findConvexHull(PointSet points) {
		Polygon hull = GeometryManager.newPolygon();
		hull.setColor(ColorSpecial.PASTEL_GREEN);
		Point p0 = points.get(0);
		Point p1 = points.get(1);
		hull.addFirst(p0);
		hull.addFirst(p1);
		hull.addLast(GeometryManager.newPoint(p1));
		for (int i = 2; i < points.size(); i++) {
			if (!Predicate.isLeftOrInside(hull.getSecondToLast(),
					hull.getLast(), points.get(i))
					|| !Predicate.isLeftOrInside(points.get(i),
							hull.getFirst(), hull.getSecond())) {
				while (!Predicate.isLeftOrInside(hull.getSecondToLast(),
						hull.getLast(), points.get(i))) {
					// fails when initial points are colinear
					hull.removeLast();

				}
				while (!Predicate.isLeftOrInside(points.get(i),
						hull.getFirst(), hull.getSecond())) {
					hull.removeFirst();
				}
				hull.addFirst(points.get(i));
				hull.addLast(GeometryManager.newPoint(points.get(i)));
			}
		}
		return hull;
	}
}
