package algorithms;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.Polygon;
import cg.Vertex;
import cg.VertexSet;

public class GrahmScan {

	public static void findConvexHull(VertexSet points, Polygon hull) {
		Vertex smallest = CG.findSmallestYX(points);
		VertexSet sorted = CG.sortByAngle(points, smallest);
		hull.push(sorted.get(0));
		hull.push(sorted.get(1));
		int i = 2;
		while (i < sorted.size()) {
			Vertex p2 = hull.getFirst();
			Vertex p1 = hull.getSecond();
			if (Predicate.findOrientation(sorted.get(i), p1, p2) == Orientation.CLOCKWISE) {
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
