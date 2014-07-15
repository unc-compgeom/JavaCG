package predicates;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import util.CG;
import cg.Circle;
import cg.Edge;
import cg.GeometryManager;
import cg.Point;
import cg.Segment;

public class Predicate {
	public enum Orientation {
		CLOCKWISE, COLINEAR, COUNTERCLOCKWISE
	}

	private static int ahead = 0;
	private static int ccw = 0;
	private static int findOrientation = 0;
	private static int isLeftOrInside = 0;
	private static int isPointInCircle = 0;
	private static int leftOf = 0;
	private static int leftOrAhead = 0;
	private static int onEdge = 0;
	private static int rightOf = 0;
	private static int rightOrAhead = 0;
	private static int triArea = 0;

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
	private static boolean ahead(Point p, Point q, Point r) {
		ahead++;
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
	 * @return true iff a, b, and color are oriented counterclockwise.
	 */
	private static boolean ccw(Point a, Point b, Point c) {
		ccw++;
		return triArea(a, b, c) > 0;
	}

	public static Orientation findOrientation(Point p, Point q, Point r) {
		findOrientation++;
		// find the determinant of the matrix
		// [[px, py, 1]
		// [qx, qy, 1]
		// [rx, ry, 1]]
		// ==
		// [[ q.x-p.x, q.y-p.y]
		// [ r.x-p.x, r.y-p.y]]
		double det = (q.getX() - p.getX()) * (r.getY() - p.getY())
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

	public static boolean isLeftOrInside(Point p, Point q, Point r) {
		isLeftOrInside++;
		/* test = (qx-px)*(ry-py) - (qy-py)*(rx-px); */
		double det = (q.getX() - p.getX()) * (r.getY() - p.getY())
				- (q.getY() - p.getY()) * (r.getX() - p.getX());
		if (det == 0) {
			det = (q.getX() - r.getX()) * (r.getX() - p.getX())
					+ (q.getY() - r.getY()) * (r.getY() - p.getY());
		}
		return det >= 0;
	}

	/**
	 * Tests if {@link Point} s is inside of or on the circumference of
	 * {@link Circle} color.
	 * 
	 * @param s
	 *            the point
	 * @param c
	 *            the circle
	 * @return true iff s is inside or on the boundary of the circle
	 */
	public static boolean isPointInCircle(Point s, Circle c) {
		isPointInCircle++;
		// correctly handles the case of the null circle (no points).
		// correctly handles the case of the one-point circle (one point).
		// correctly handles the case of the two-point circle (diameter).
		List<Point> points = c.getPoints();
		if (points.size() == 0) {
			return false;
		} else if (points.size() == 1) {
			return s.compareTo(points.get(0)) == 0;
		} else if (points.size() == 2) {
			Point origin = GeometryManager.newPoint(points.get(1)
					.sub(points.get(0)).div(2).plus(points.get(0)));
			double radiusSquared = points.get(0).distanceSquared(origin);
			boolean isInCircle = CG.distSquared(s, origin) <= radiusSquared;
			// animation code
			Circle tmp = GeometryManager.newCircle(points);
			Segment rad = GeometryManager.newSegment(s, origin);
			Color old = s.getColor();
			rad.setColorNoAnim(Color.YELLOW);
			tmp.setColorNoAnim(Color.YELLOW);
			origin.setColor(Color.YELLOW);
			CG.animationDelay();
			if (isInCircle) {
				tmp.setColor(Color.GREEN);
				rad.setColor(Color.GREEN);
			} else {
				tmp.setColor(Color.RED);
				rad.setColor(Color.RED);
			}
			CG.animationDelay();
			GeometryManager.destroy(origin);
			GeometryManager.destroy(rad);
			s.setColor(old);
			GeometryManager.destroy(tmp);
			return isInCircle;
		} else {
			isPointInCircle--;
			Point p0 = points.get(0), p1 = points.get(1), p2 = points.get(2);
			if (ccw(p0, p1, p2)) {
				return isPointInCircle(s, points.get(0), points.get(1),
						points.get(2));
			} else {
				return isPointInCircle(s, points.get(0), points.get(2),
						points.get(1));
			}
		}
	}

	/**
	 * Tests if a {@link Point} is inside the {@link Circle} defined by points
	 * <tt>a</tt>, <tt>b</tt>, and <tt>color</tt>. Points must be oriented
	 * counterclockwise.
	 * 
	 * @param test
	 *            Point to be tested
	 * @param a
	 *            Point on circle
	 * @param b
	 *            Point on circle
	 * @param c
	 *            Point on circle
	 * 
	 * @return true iff <tt>test</tt> is in the circle
	 */
	public static boolean isPointInCircle(Point test, Point a, Point b, Point c) {
		isPointInCircle++;
		float ax = a.getX(), ay = a.getY();
		float bx = b.getX(), by = b.getY();
		float cx = c.getX(), cy = c.getY();
		float dx = test.getX(), dy = test.getY();
		double det = (ax * ax + ay * ay) * triArea(b, c, test)
				- (bx * bx + by * by) * triArea(a, c, test)
				+ (cx * cx + cy * cy) * triArea(a, b, test)
				- (dx * dx + dy * dy) * triArea(a, b, c);
		boolean isInCircle = det > 0;
		if (det == 0) {
			return false;
		}
		// animation code
		List<Point> points = new ArrayList<Point>(3);
		points.add(a);
		points.add(b);
		points.add(c);
		Circle tmp = GeometryManager.newCircle(points);
		tmp.setColorNoAnim(Color.YELLOW);
		// compute circle's origin
		Point p = points.get(0), q = points.get(1), r = points.get(2);
		double px = p.getX(), py = p.getY();
		double qx = q.getX(), qy = q.getY();
		double rx = r.getX(), ry = r.getY();
		double det2 = (px - qx) * (py - ry) - (py - qy) * (px - rx);
		float x = (float) ((p.plus(q).div(2).dot(p.sub(q)) * (py - ry) - (py - qy)
				* p.plus(r).div(2).dot(p.sub(r))) / det2);
		float y = (float) (((px - qx) * p.plus(r).div(2).dot(p.sub(r)) - p
				.plus(q).div(2).dot(p.sub(q))
				* (px - rx)) / det2);
		Point origin = GeometryManager.newPoint(x, y);
		Segment rad = GeometryManager.newSegment(origin, test);
		Color old = test.getColor();
		rad.setColorNoAnim(Color.YELLOW);
		CG.animationDelay();
		if (isInCircle) {
			tmp.setColor(Color.GREEN);
			rad.setColor(Color.GREEN);
		} else {
			tmp.setColor(Color.RED);
			rad.setColor(Color.RED);
		}
		CG.animationDelay();
		GeometryManager.destroy(tmp);
		GeometryManager.destroy(rad);
		GeometryManager.destroy(origin);
		test.setColor(old);
		return isInCircle;
	}

	public static boolean leftOf(Point p, Edge e) {
		leftOf++;
		return triArea(p, e.orig(), e.dest()) > 0;
	}

	public static boolean leftOrAhead(Point p, Point q, Point r) {
		leftOrAhead++;
		double tmp = triArea(p, q, r);
		return tmp > 0 || tmp == 0 && ahead(p, q, r);
	}

	public static boolean onEdge(Point p, Edge e) {
		onEdge++;
		Point a = e.orig();
		Point b = e.dest();
		if (triArea(a, b, p) == 0) {
			double dot = p.sub(a).dot(b.sub(a));
			double distSq = CG.distSquared(a, b);
			return 0 <= dot && dot <= distSq;
		} else {
			return false;
		}
	}

	public static boolean rightOf(Point p, Edge e) {
		rightOf++;
		return triArea(p, e.orig(), e.dest()) < 0;
	}

	public static boolean rightOrAhead(Point p, Point q, Point r) {
		rightOrAhead++;
		double tmp = triArea(p, q, r);
		return tmp < 0 || tmp == 0 && ahead(p, q, r);
	}

	/**
	 * Calculates twice the signed area of the triangle defined by
	 * {@link Points} a, b, and color. If a, b, and color are in
	 * counterclockwise order, the area is positive, if they are co-linear the
	 * area is zero, else the area is negative.
	 * 
	 * @param a
	 *            a Point
	 * @param b
	 *            a Point
	 * @param c
	 *            a Point
	 * @return twice the signed area
	 */
	private static double triArea(Point a, Point b, Point c) {
		triArea++;
		return b.sub(a).getX() * c.sub(a).getY() - b.sub(a).getY()
				* c.sub(a).getX();
	}
}
