package predicates;

import cg.Line;
import cg.Point;

public class Predicate {
	public enum Orientation {
		COUNTERCLOCKWISE, CLOCKWISE, COLINEAR
	};

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

	public static Orientation findOrientation(Point p, Point q, Point r) {
		// find the determinant of the matrix
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

	public static boolean isPointOnLine(Point p, Line l) {
		Point p1 = l.getP1(), p2 = l.getP1();
		return findOrientation(p, p1, p2) == Orientation.COLINEAR;
	}

	/**
	 * 
	 * @param p
	 * @param q
	 * @param r
	 * @return T/F: Point p is left of or on segment qr.
	 */
	public static boolean isPointLeftOrOnSegment(Point p, Point q, Point r) {
		Orientation o = findOrientation(p, q, r);
		if (o == Orientation.COUNTERCLOCKWISE) {
			return true;
		} else {
			Point a = q.sub(p), b = r.sub(p);
			return o == Orientation.COLINEAR
					&& Math.abs(a.getX() - b.getX()) > 0
					&& Math.abs(a.getY() - b.getY()) > 0;
		}
	}

	public static boolean isPointLeftOfLine(Point p, Point q, Point r) {
		return findOrientation(p, q, r) == Orientation.COUNTERCLOCKWISE;
	}
}
