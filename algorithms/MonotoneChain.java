package algorithms;

import java.awt.Color;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.Polygon;
import cg.Vertex;
import cg.VertexSet;

public class MonotoneChain {

	public static void findConvexHull(VertexSet points, Polygon hull) {
		VertexSet sorted = CG.lexicographicalSort(points);
		// lower hull
		Polygon lower = hull.cloneEmpty();
		lower.setColor(Color.green);
		for (int i = 0; i < sorted.size(); i++) {
			Vertex p = sorted.get(i);
			while (lower.size() > 1
					&& Predicate.findOrientation(p, lower.getSecondToLast(),
							lower.getLast()) != Orientation.COUNTERCLOCKWISE) {
				lower.removeLast();
			}
			lower.addLast(sorted.get(i));
		}
		// upper hull
		Polygon upper = hull.cloneEmpty();
		upper.setColor(Color.blue);
		for (int i = sorted.size() - 1; i >= 0; i--) {
			Vertex p = sorted.get(i);
			while (upper.size() > 1
					&& Predicate.findOrientation(p, upper.getSecondToLast(),
							upper.getLast()) != Orientation.COUNTERCLOCKWISE) {
				upper.removeLast();
			}
			upper.addLast(sorted.get(i));
		}
		// join
		for (int i = 0; i < lower.size(); i++) {
			hull.add(lower.get(i));
		}
		for (int i = 0; i < upper.size(); i++) {
			hull.add(upper.get(i));
		}
		// clean up
		upper.clear();
		upper.removeAllObservers();
		lower.clear();
		lower.removeAllObservers();
	}
}
