package algorithms;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.GeometryManager;
import cg.Polygon;
import cg.Vertex;
import cg.VertexSet;

public class QuickHull {
	public static void findConvexHull(VertexSet points, Polygon hull) {
		Vertex[] minMax = findMinMaxX(points);
		hull.add(minMax[0]);
		hull.add(minMax[1]);
		// divide the point set
		findHull(points, hull, minMax[0], minMax[1]);
		findHull(points, hull, minMax[1], minMax[0]);
	}

	private static void findHull(VertexSet points, Polygon hull, Vertex a,
			Vertex b) {
		VertexSet sub = GeometryManager.getVertexSet();
		sub.setColor(CG.randomColor());

		// get only points counterclockwise of segment ab
		for (Vertex point : points) {
			Orientation o = Predicate.findOrientation(point, a, b);
			if (o == Orientation.COUNTERCLOCKWISE) {
				sub.add(point);
			}
		}
		if (sub.isEmpty()) {
			return;
		}
		// find farthest point from segment ab
		Vertex c = sub.get(0);
		long distance = 0;
		for (Vertex point : sub) {
			long newDist = distance(a, b, point);
			if (newDist > distance) {
				distance = newDist;
				c = point;
			}
		}
		// add point to hull between ab
		hull.add(hull.indexOf(b), c);
		findHull(sub, hull, a, c);
		findHull(sub, hull, c, b);
		// clean up
		GeometryManager.destroyGeometry(sub);
	}

	private static long distance(Vertex A, Vertex B, Vertex C) {
		int ABx = B.getX() - A.getX();
		int ABy = B.getY() - A.getY();
		int ACx = A.getX() - C.getX();
		int ACy = A.getY() - C.getY();
		int num = ABx * ACy - ABy * ACx;
		if (num < 0)
			num = -num;
		return num;
	}

	private static Vertex[] findMinMaxX(VertexSet points) {
		Vertex[] minMax = { points.get(0), points.get(0) };
		for (Vertex point : points) {
			if (point.getX() < minMax[0].getX()) {
				minMax[0] = point;
			} else if (point.getX() > minMax[1].getX()) {
				minMax[1] = point;
			}
		}
		return minMax;
	}
}
