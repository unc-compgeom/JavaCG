package algorithms;

import java.awt.Color;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.GeometryManager;
import cg.Polygon;
import cg.Point;
import cg.PointSet;

public class MonotoneChain {

	public static void findConvexHull(PointSet points, Polygon hull) {
		PointSet sorted = CG.lexicographicalSort(points);
		// lower hull
		Polygon lower = GeometryManager.newPolygon();
		lower.setColor(Color.green);
		for (int i = 0; i < sorted.size(); i++) {
			Point p = sorted.get(i);
			while (lower.size() > 1
					&& Predicate.findOrientation(p, lower.getSecondToLast(),
							lower.getLast()) != Orientation.COUNTERCLOCKWISE) {
				lower.removeLast();
			}
			lower.addLast(sorted.get(i));
		}
		// upper hull
		Polygon upper = GeometryManager.newPolygon();
		upper.setColor(Color.blue);
		for (int i = sorted.size() - 1; i >= 0; i--) {
			Point p = sorted.get(i);
			while (upper.size() > 1
					&& Predicate.findOrientation(p, upper.getSecondToLast(),
							upper.getLast()) != Orientation.COUNTERCLOCKWISE) {
				upper.removeLast();
			}
			upper.addLast(sorted.get(i));
		}
		// join
		for (Point p : lower) {
			hull.add(p);
		}
		for (Point p : upper) {
			hull.add(p);
		}
		// clean up
		GeometryManager.destroyGeometry(upper);
		GeometryManager.destroyGeometry(lower);
	}
}
