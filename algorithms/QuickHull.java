package algorithms;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

public class QuickHull {
	public static void findConvexHull(PointSet points, Polygon hull) {
		Point[] minMax = findMinMaxX(points);
		hull.add(minMax[0]);
		hull.add(minMax[1]);
		// divide the point set
		findHull(points, hull, minMax[0], minMax[1]);
		findHull(points, hull, minMax[1], minMax[0]);
		// close the hull
		hull.add(hull.getFirst());
	}

	private static void findHull(PointSet points, Polygon hull, Point a, Point b) {
		PointSet sub = GeometryManager.newPointSet();
		sub.setColor(CG.randomColor());

		// get only points counterclockwise of segment ab
		for (Point point : points) {
			Orientation o = Predicate.findOrientation(point, a, b);
			if (o == Orientation.COUNTERCLOCKWISE) {
				sub.add(point);
			}
		}
		if (sub.isEmpty()) {
			return;
		}

		// find farthest point from segment ab
		Point c = sub.get(0);
		double distance = 0;
		for (Point point : sub) {
			double newDist = distance(a, b, point);
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
		GeometryManager.destroy(sub);
	}

	private static double distance(Point A, Point B, Point C) {
		float ABx = B.getX() - A.getX();
		float ABy = B.getY() - A.getY();
		float ACx = A.getX() - C.getX();
		float ACy = A.getY() - C.getY();
		double num = ABx * ACy - ABy * ACx;
		if (num < 0)
			num = -num;
		return num;
	}

	private static Point[] findMinMaxX(PointSet points) {
		Point[] minMax = { points.get(0), points.get(0) };
		for (Point point : points) {
			if (point.getX() < minMax[0].getX()) {
				minMax[0] = point;
			} else if (point.getX() > minMax[1].getX()) {
				minMax[1] = point;
			}
		}
		return minMax;
	}
}
