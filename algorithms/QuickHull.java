package algorithms;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.Vertex;
import cg.VertexSet;
import cg.VertexSetComponent;
import cg.Polygon;

public class QuickHull {
	public static void doQuickHull(VertexSet points, Polygon hull) {
		Vertex[] minMax = findMinMaxX(points);
		hull.add(minMax[0]);
		hull.add(minMax[1]);
		// divide the point set
		findHull(points, hull, minMax[0], minMax[1]);
		findHull(points, hull, minMax[1], minMax[0]);
	}

	private static void findHull(VertexSet points, Polygon hull, Vertex a, Vertex b) {
		VertexSet sub = new VertexSetComponent();
		sub.addObservers(hull.getObservers());
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

		sub.clear();
		sub.removeAllObservers();
	}

	private static long distance(Vertex A, Vertex B, Vertex C) {
		int ABx = B.getX() - A.getX();
		int ABy = B.getY() - A.getY();
		int num = ABx * (A.getY() - C.getY()) - ABy * (A.getX() - C.getX());
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
