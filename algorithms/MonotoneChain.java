package algorithms;

import java.awt.Color;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.Point;
import cg.PointSet;
import cg.Polygon;
import cg.PolygonComponent;

public class MonotoneChain {

	public static void doMonotoneChain(PointSet points, Polygon hull) {
		PointSet sorted = CG.lexicographicalSort(points);
		// lower hull
		Polygon lower = new PolygonComponent();
		lower.addObservers(hull.getObservers());
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
		Polygon upper = new PolygonComponent();
		upper.addObservers(hull.getObservers());
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
		for (int i = 0; i < lower.size(); i++) {
			hull.add(lower.get(i));
		}
		for (int i = 0; i < upper.size(); i++) {
			hull.add(upper.get(i));
		}
		// clean up
		upper.removeAll();
		upper.removeAllObservers();
		lower.removeAll();
		lower.removeAllObservers();
	}
}
