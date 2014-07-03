package cg;

import util.CG;

/**
 * Predicates for Delaunay Triangulations
 * 
 * @author Vance Miller
 * 
 */
public class Predicate {

	/**
	 * Tests if {@link Point} p is ahead of the segment from q to r;
	 * 
	 * @param p
	 *            a point
	 * @param q
	 *            segment endpoint
	 * @param r
	 *            segment endpoint
	 * @return true iff <tt>p</tt> is ahead of <tt>qr</tt>
	 */
	public static boolean ahead(Point p, Point q, Point r) {
		return p.sub(q).dot(r.sub(q)) > CG.distSquared(q, r);
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

	public static boolean ccw(Point a, Point b, Point c) {
		return triArea(a, b, c) > 0;
	}

	public static boolean rightOf(Point p, Edge e) {
		return triArea(p, e.orig(), e.dest()) < 0;
	}

	public static boolean leftOf(Point p, Edge e) {
		return triArea(p, e.orig(), e.dest()) > 0;
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

	public static boolean rightOrAhead(Point p, Point q, Point r) {
		long tmp = triArea(p, q, r);
		return tmp < 0 || (tmp == 0 && ahead(p, q, r));
	}

	public static boolean leftOrAhead(Point p, Point q, Point r) {
		long tmp = triArea(p, q, r);
		return tmp > 0 || (tmp == 0 && ahead(p, q, r));
	}
}
