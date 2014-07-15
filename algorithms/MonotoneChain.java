package algorithms;

import java.awt.Color;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import util.ColorSpecial;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

class MonotoneChain {

	public static Polygon findConvexHull(PointSet points) {
		CG.lexicographicalSort(points);
		// lower hull
		Polygon lower = GeometryManager.newPolygon();
		lower.setColor(Color.green);
		for (int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			while (lower.size() > 1
					&& Predicate.findOrientation(p, lower.getSecondToLast(),
							lower.getLast()) != Orientation.COUNTERCLOCKWISE) {
				lower.removeLast();
			}
			lower.addLast(points.get(i));
		}
		// upper hull
		Polygon upper = GeometryManager.newPolygon();
		upper.setColor(Color.blue);
		for (int i = points.size() - 1; i >= 0; i--) {
			Point p = points.get(i);
			while (upper.size() > 1
					&& Predicate.findOrientation(p, upper.getSecondToLast(),
							upper.getLast()) != Orientation.COUNTERCLOCKWISE) {
				upper.removeLast();
			}
			upper.addLast(points.get(i));
		}
		// join
		Polygon hull = GeometryManager.newPolygon();
		hull.setColor(ColorSpecial.PASTEL_GREEN);
		for (Point p : lower) {
			hull.add(p);
		}
		for (Point p : upper) {
			hull.add(p);
		}
		// clean up
		GeometryManager.destroy(upper);
		GeometryManager.destroy(lower);
		return hull;
	}
}
