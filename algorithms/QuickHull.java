package algorithms;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import util.ColorSpecial;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

class QuickHull {
	private static double distance(final Point A, final Point B, final Point C) {
		final float ABx = B.getX() - A.getX();
		final float ABy = B.getY() - A.getY();
		final float ACx = A.getX() - C.getX();
		final float ACy = A.getY() - C.getY();
		double num = ABx * ACy - ABy * ACx;
		if (num < 0) {
			num = -num;
		}
		return num;
	}

	/**
	 * Computes the convex hull recursively with the QuickHull algorithm.
	 *
	 * @param points
	 *            the point set
	 * @return a {@link cg.Polygon} representing the convex hull
	 */
	public static Polygon findConvexHull(final PointSet points) {
		final Polygon hull = GeometryManager.newPolygon();
		hull.setColor(ColorSpecial.PASTEL_GREEN);
		final Point[] minMax = findMinMaxX(points);
		hull.add(minMax[0]);
		hull.add(minMax[1]);
		// divide the point set
		findHull(points, hull, minMax[0], minMax[1]);
		findHull(points, hull, minMax[1], minMax[0]);
		// close the hull
		hull.add(hull.getFirst());
		return hull;
	}

	private static void findHull(final PointSet points, final Polygon hull,
			final Point a, final Point b) {
		final PointSet sub = GeometryManager.newPointSet();
		sub.setColor(CG.randomColor());

		// get only points counterclockwise of segment ab
		for (final Point point : points) {
			final Orientation o = Predicate.findOrientation(point, a, b);
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
		for (final Point point : sub) {
			final double newDist = distance(a, b, point);
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

	private static Point[] findMinMaxX(final PointSet points) {
		final Point[] minMax = { points.get(0), points.get(0) };
		for (final Point point : points) {
			if (point.getX() < minMax[0].getX()) {
				minMax[0] = point;
			} else if (point.getX() > minMax[1].getX()) {
				minMax[1] = point;
			}
		}
		return minMax;
	}
}
