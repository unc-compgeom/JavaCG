package quadEdge;

import cg.Point;

public class Predicate {
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
		return ccw(p, e.dest(), e.orig());
	}

	public static boolean leftOf(Point p, Edge e) {
		return ccw(p, e.orig(), e.dest());
	}

	public static boolean onEdge(Point p, Edge e) {
		Point a = e.orig();
		Point b = e.dest();
		if (triArea(a, b, p) == 0) {
			// TODO correct this
			if (p.getX() >= a.getX()) {
				return p.getX() <= b.getX();
			} else if (p.getX() <= a.getX()) {
				return p.getX() >= b.getX();
			} else if (p.getY() >= a.getY()) {
				return p.getY() <= b.getY();
			} else if (p.getY() <= a.getY()) {
				return p.getY() >= b.getY();
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
