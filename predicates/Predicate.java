package predicates;

import cg.Circle;
import cg.HalfEdge;
import cg.Vertex;

public class Predicate {
	public enum Orientation {
		COUNTERCLOCKWISE, CLOCKWISE, COLINEAR
	};

	public static boolean isLeftOrInside(Vertex p, Vertex q, Vertex r) {
		/* test = (qx-px)*(ry-py) - (qy-py)*(rx-px); */
		double det = (q.getX() - p.getX()) * (r.getY() - p.getY())
				- (q.getY() - p.getY()) * (r.getX() - p.getX());
		if (det == 0) {
			det = (q.getX() - r.getX()) * (r.getX() - p.getX())
					+ (q.getY() - r.getY()) * (r.getY() - p.getY());
		}
		return det >= 0;
	}

	public static Orientation findOrientation(Vertex p, Vertex q, Vertex r) {
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

	public static boolean isPointOnLine(Vertex p, HalfEdge l) {
		Vertex p1 = l.getOrigin(), p2 = l.getOrigin();
		return findOrientation(p, p1, p2) == Orientation.COLINEAR;
	}

	/**
	 * 
	 * @param p
	 * @param q
	 * @param r
	 * @return T/F: Point p is left of or on segment qr.
	 */
	public static boolean isPointLeftOrOnSegment(Vertex p, Vertex q, Vertex r) {
		Orientation o = findOrientation(p, q, r);
		if (o == Orientation.COUNTERCLOCKWISE) {
			return true;
		} else {
			Vertex a = q.sub(p), b = r.sub(p);
			return o == Orientation.COLINEAR
					&& Math.abs(a.getX() - b.getX()) > 0
					&& Math.abs(a.getY() - b.getY()) > 0;
		}
	}

	public static boolean isPointLeftOfLine(Vertex p, Vertex q, Vertex r) {
		return findOrientation(p, q, r) == Orientation.COUNTERCLOCKWISE;
	}

	public static boolean isVertexInCircle(Vertex s, Circle c) {
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
		int radius = c.getRadius();
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
}
