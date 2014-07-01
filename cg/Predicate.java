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
		System.out
				.println(a
						+ " "
						+ b
						+ " "
						+ c
						+ " "
						+ " triArea = "
						+ ((b.getX() - a.getX()) * (c.getY() - a.getY()) - (b
								.getY() - a.getY()) * (c.getX() - a.getX())));
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
		boolean t = triArea(a, b, c) > 0;
		System.out.println(t);
		return t;
	}

	public static boolean rightOf(Point p, Edge e) {
		return ccw(p, e.dest(), e.orig());
	}

	public static boolean leftOf(Point p, Edge e) {
		return ccw(p, e.orig(), e.dest());
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
}
