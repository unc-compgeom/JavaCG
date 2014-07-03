package predicates;

import util.CG;
import cg.Circle;
import cg.Edge;
import cg.Point;

public class Predicate {
	public enum Orientation {
		CLOCKWISE, COLINEAR, COUNTERCLOCKWISE
	};

	/**
	 * Tests if {@link Point} p is ahead of the segment from q to r.
	 * 
	 * @param p
	 *            a point
	 * @param q
	 *            segment endpoint
	 * @param r
	 *            segment endpoint
	 * @return true iff <tt>p</tt> is ahead of <tt>qr</tt>.
	 */
	public static boolean ahead(Point p, Point q, Point r) {
		return p.sub(q).dot(r.sub(q)) > CG.distSquared(q, r);
	}

	/**
	 * Tests if ordered points are part of a counterclockwise curve.
	 * 
	 * @param a
	 *            point 1
	 * @param b
	 *            point 2
	 * @param c
	 *            point 3
	 * @return true iff a, b, and c are oriented counterclockwise.
	 */
	public static boolean ccw(Point a, Point b, Point c) {
		return triArea(a, b, c) > 0;
	}

	public static Orientation findOrientation(Point p, Point q, Point r) {
		// find the determinant of the matrix
		// [[px, py, 1]
		// [qx, qy, 1]
		// [rx, ry, 1]]
		// ==
		// [[ q.x-p.x, q.y-p.y]
		// [ r.x-p.x, r.y-p.y]]
		long det = (q.getX() - p.getX()) * (r.getY() - p.getY())
				- (q.getY() - p.getY()) * (r.getX() - p.getX());
		Orientation o;
		if (det > 0) {
			o = Orientation.COUNTERCLOCKWISE;
		} else if (det < 0) {
			o = Orientation.CLOCKWISE;
		} else {
			o = Orientation.COLINEAR;
		}
		return o;
	}

	/**
	 * Tests if the point <tt>d</tt> is inside the circle defined by points
	 * <tt>a</tt>, <tt>b</tt>, and <tt>c</tt>.
	 * 
	 * @param a
	 *            Point on circle
	 * @param b
	 *            Point on circle
	 * @param c
	 *            Point on circle
	 * @param d
	 *            Point to be tested
	 * @return true iff d is in the circle
	 */
	public static boolean inCircle(Point a, Point b, Point c, Point d) {
		return (a.getX() * a.getX() + a.getY() * a.getY()) * triArea(b, c, d)
				- (b.getX() * b.getX() + b.getY() * b.getY())
				* triArea(a, c, d)
				+ (c.getX() * c.getX() + c.getY() * c.getY())
				* triArea(a, b, d)
				- (d.getX() * d.getX() + d.getY() * d.getY())
				* triArea(a, b, c) > 0;
	}

	public static boolean isLeftOrInside(Point p, Point q, Point r) {
		/* test = (qx-px)*(ry-py) - (qy-py)*(rx-px); */
		double det = (q.getX() - p.getX()) * (r.getY() - p.getY())
				- (q.getY() - p.getY()) * (r.getX() - p.getX());
		if (det == 0) {
			det = (q.getX() - r.getX()) * (r.getX() - p.getX())
					+ (q.getY() - r.getY()) * (r.getY() - p.getY());
		}
		return det >= 0;
	}

	public static boolean isVertexInCircle(Point s, Circle c) {
		// correctly handles the case of the null circle (radiusSquared = -1)
		// correctly handles the case of the one point circle (radiusSquared =
		// 0);
		if (c.getRadiusSquared() == -1) {
			return false;
		}
		return CG.distSquared(s, c.getOrigin()) <= c.getRadiusSquared();
	}

	public static boolean isVertexInCircleByPoints(Point s, Circle c) {
		// find three points, a, b, c, in the circle
		// find the determinant of the matrix:
		// [[a_x, a_y, a_x^2 + a_y^2, 1]
		// [b_x, b_y, b_x^2 + b_y^2, 1]
		// [c_x, c_y, c_x^2 + c_y^2, 1]
		// [s_x, s_y, s_x^2 + s_y^2, 1]]
		// ==
		// [[a_x - s_x, a_y - s_y, (a_x - s_x)^2 + (a_y - s_y)^2]
		// [b_x - s_x, b_y - s_y, (b_x - s_x)^2 + (b_y - s_y)^2]
		// [c_x - s_x, c_y - s_y, (c_x - s_x)^2 + (c_y - s_y)^2]]
		int x = c.getOrigin().getX();
		int y = c.getOrigin().getY();
		int radius = (int) Math.sqrt(c.getRadiusSquared());
		int ax = x, ay = y + radius;
		int bx = x, by = y - radius;
		int cx = x + radius, cy = y;
		int sx = s.getX(), sy = s.getY();

		long result = ((ax - sx) * (ax - sx) + (ay - sy) * (ay - sy))
				* ((bx - sx) * (cy - sy) - (by - sy) * (cx - sx))
				- ((bx - sx) * (bx - sx) + (by - sy) * (by - sy))
				* ((ax - sx) * (cy - sy) - (ay - sy) * (cx - sx))
				+ ((cx - sx) * (cx - sx) + (cy - sy) * (cy - sy))
				* ((ax - sx) * (by - sy) - (ay - sy) * (bx - sx));
		return (result < 0) ? false : true;
	}

	public static boolean leftOf(Point p, Edge e) {
		return triArea(p, e.orig(), e.dest()) > 0;
	}

	public static boolean leftOrAhead(Point p, Point q, Point r) {
		long tmp = triArea(p, q, r);
		return tmp > 0 || (tmp == 0 && ahead(p, q, r));
	}

	public static boolean onEdge(Point p, Edge e) {
		Point a = e.orig();
		Point b = e.dest();
		if (triArea(a, b, p) == 0) {
			long dot = p.sub(a).dot(b.sub(a));
			long distSq = CG.distSquared(a, b);
			return 0 <= dot && dot <= distSq;
		} else {
			return false;
		}
	}

	public static boolean rightOf(Point p, Edge e) {
		return triArea(p, e.orig(), e.dest()) < 0;
	}

	public static boolean rightOrAhead(Point p, Point q, Point r) {
		long tmp = triArea(p, q, r);
		return tmp < 0 || (tmp == 0 && ahead(p, q, r));
	}

	/**
	 * Calculates twice the signed area of the triangle defined by
	 * {@link Points} a, b, and c. If a, b, and c are in counterclockwise order,
	 * the area is positive, if they are co-linear the area is zero, else the
	 * area is negative.
	 * 
	 * @param a
	 *            a Point
	 * @param b
	 *            a Point
	 * @param c
	 *            a Point
	 * @return twice the signed area
	 */
	public static long triArea(Point a, Point b, Point c) {
		return (b.getX() - a.getX()) * (c.getY() - a.getY())
				- (b.getY() - a.getY()) * (c.getX() - a.getX());
	}
}
