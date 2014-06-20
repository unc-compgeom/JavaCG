package algorithms;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.Polygon;
import cg.Vertex;
import cg.VertexSet;

public class JarvisMarch {
	public static void findConvexHull(VertexSet points, Polygon hull) {
		Vertex min = CG.findSmallestYX(points);
		Vertex p, q = min;
		int i = 0;
		do {
			hull.addLast(q);
			p = hull.get(i);
			q = nextHullPoint(points, p);
			i++;
		} while (!q.equals(min));
	}

	private static Vertex nextHullPoint(VertexSet points, Vertex p) {
		Vertex q = p;
		for (Vertex r : points) {
			Orientation o = Predicate.findOrientation(p, q, r);
			if (o == Orientation.COUNTERCLOCKWISE
					|| (o == Orientation.COLINEAR && CG.distSquared(p, r) > CG
							.distSquared(p, q))) {
				q = r;
			}
		}
		return q;
	}
}
