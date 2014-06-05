package algorithms;

import predicates.Predicate;
import util.CG;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

public class JarvisMarch {
	public static void doJarvisMarch(PointSet pl, Polygon hull) {
		Point min = leftmost(pl);
		do {
			hull.addPoint(min);
			CG.sleep();
			min = pl.getPoint(min != pl.getPoint(0) ? 0 : 1);
			for (int i = 1; i < pl.numPoints(); i++) {
				if (!Predicate.isPointLeftOrOnSegment(pl.getPoint(i),
						hull.getLast(), min)) {
					min = pl.getPoint(i);
				}
			}
		} while (!min.equals(hull.getPoint(0)));
	}

	/**
	 * Find the leftmost point of polygon p
	 * 
	 * @param p
	 * @return the leftmost point
	 */
	private static Point leftmost(PointSet p) {
		Point leftmost = p.getPoint(0);
		for (int i = 1; i < p.numPoints(); i++) {
			if (leftmost.getX() > p.getPoint(i).getX()) {
				leftmost = p.getPoint(i);
			}
		}
		return leftmost;
	}
}
