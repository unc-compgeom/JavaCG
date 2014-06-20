package algorithms;

import java.awt.Color;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.GeometryManager;
import cg.Polygon;
import cg.Segment;
import cg.Point;
import cg.PointSet;

public class Calipers {

	/*
	 * 
	 * Rotating caliper algorithm input Point set in 2D. Output is maximum
	 * diameter of the convex hull and the minimum width of the convex hull.
	 */

	public static void doCalipers(PointSet points, Polygon hull) {
		int i = 0;
		int j = 1;
		double diam = -1;
		double width = Double.POSITIVE_INFINITY;

		// Visualization Variables
		Segment diamSupport1 = GeometryManager.newSegment(-1, -1, -1, -1);
		Segment diamSupport2 = GeometryManager.newSegment(-1, -1, -1, -1);
		Segment widthSupport1 = GeometryManager.newSegment(-1, -1, -1, -1);
		Segment widthSupport2 = GeometryManager.newSegment(-1, -1, -1, -1);
		Segment widthline = GeometryManager.newSegment(-1, -1, -1, -1);
		Segment diamline = GeometryManager.newSegment(-1, -1, -1, -1);
		diamSupport1.setColor(Color.BLUE);
		diamSupport2.setColor(Color.BLUE);
		widthSupport1.setColor(Color.BLUE);
		widthSupport2.setColor(Color.BLUE);
		diamline.setColor(Color.GREEN);
		widthline.setColor(Color.ORANGE);

		hull = getConvexHull(points, hull);

		// Initialization Step
		while (cwIntersection(0, j, hull)) {
			j++;
		}

		// Rotation Step
		while (j < hull.size()) {
			//check if new max diameter or minimum width at points i and j
			diam = checkDiameter(diam, i, j, hull, diamline, diamSupport1, diamSupport2);
			width = checkWidth(width, i, j, hull, widthline, widthSupport1, widthSupport2);
			if (cwIntersection(i + 1, j, hull)) {
				j++;
			} else if (cwIntersection(j + 1, i, hull)) {
				i++;
			} else {
				j++;
				i++;
			}
		}
	}

	/* Jarvis march is used to compute the convex hull */
	private static Polygon getConvexHull(PointSet points, Polygon hull) {
		Point min = CG.findSmallestYX(points);
		Point p, q = min;
		int i = 0;
		do {
			hull.addLast(q);
			p = hull.get(i);
			q = nextHullPoint(points, p);
			i++;
		} while (!q.equals(min));
		return hull;
	}

	private static Point nextHullPoint(PointSet points, Point p) {
		Point q = p;
		for (int i = 0; i < points.size(); i++) {
			Point r = points.get(i);
			Orientation o = Predicate.findOrientation(p, q, r);
			if (o == Orientation.COUNTERCLOCKWISE
					|| (o == Orientation.COLINEAR && CG.distSquared(p, r) > CG
							.distSquared(p, q))) {
				q = r;
			}
		}
		return q;
	}

	/*
	 *Takes line segment j to j+1 and translates it so that the tail (point originally at j)
	 *is now at point i.  If the point translated(j+1) lies clockwise to the line i-1 to i,
	 *then true is returned.  Otherwise false is returned.
	 */
	private static boolean cwIntersection(int i, int j, Polygon hull) {
		Point Pa = hull.get(i - 1).clone();
		Point Pb = hull.get(i).clone();
		Point Pd = hull.get(j + 1).clone();
		Pd.setX(Pd.getX() - hull.get(j).getX() + Pb.getX());
		Pd.setY(Pd.getY() - hull.get(j).getY() + Pb.getY());
		return Predicate.findOrientation(Pa, Pb, Pd) == Predicate.Orientation.CLOCKWISE;
	}
	
	/*
	 * Measures the squared distance between vertices i and j.  If the squared
	 * distance is less than the current squared distance, then the diamline is
	 * updated to endpoints i and j.  The parallel support lines are then updated to be
	 * tangent to both i and j.
	 */
	public static double checkDiameter(double diam, int i, int j, Polygon hull,
			Segment diamline, Segment diamSupport1, Segment diamSupport2) {
		Point pi = hull.get(i);
		Point pj = hull.get(j);
		double tempdiam = pi.distanceSquared(pj);
		if (tempdiam > diam) {
			int tan1, tan2;
			diam = tempdiam;
			diamline.update(pi, pj);
			if (cwIntersection(i + 1, j, hull)) {
				tan1 = j;
				tan2 = j + 1;
			} else {
				tan1 = i;
				tan2 = i + 1;
			}
			//The support basis stores the direction that the support lines will point
			//It is then translated to the Point.  The head of the line as well as the
			//head of its reflection about its tail are the endpoints for the support lines.
			Segment supportBasis = GeometryManager.newSegment(hull.get(tan1),
					hull.get(tan2));
			supportBasis = supportBasis.setVisible(false);
			supportBasis.translate(pi);
			diamSupport1.update(supportBasis.getHead(), supportBasis
					.tailReflection().getHead());
			supportBasis.translate(pj);
			diamSupport2.update(supportBasis.getHead(), supportBasis
					.tailReflection().getHead());
		}
		return diam;
	}

	/*
	 * Finds the distance squared between parallel tangents where at least one tangent 
	 * lies on the edge of the convex hull.  If the new distance squared is less than the
	 * previous distance squared, then the width line is updated as well as its supports.
	 */
	public static double checkWidth(double width, int i, int j, Polygon hull,
			Segment widthline, Segment widthSupport1, Segment widthSupport2) {
		int r, p, q;
		double tempwidth;
		//If the cwIntersection test returns true, then i has a tangent parallel to the line
		//j to j+1.  Thus the distance between i and the line j to j+1 is found.
		if (cwIntersection(i + 1, j, hull)) {
			r = i;
			p = j;
			q = j + 1;
		//Otherwise j has a tangent parallel to the line i to i+1.  The distance between
		//j and the line i to i+1 is found.
		} else {
			r = j;
			p = i;
			q = i + 1;
		}
		//line segment from p to q
		Segment pq = GeometryManager.newSegment(hull.get(p), hull.get(q));
		pq = pq.setVisible(false);
		//line segment at r parallel to the line segment p to q
		Segment rrq = GeometryManager.newSegment(hull.get(p), hull.get(q));
		rrq = rrq.setVisible(false);
		rrq.translate(hull.get(r));
		//intersection of line pq with the line perpendicular to rrq
		Point intersection = rrq.perpendicular().intersection(pq);
		//distance from r to the intersection point is a candidate width
		tempwidth = intersection.distanceSquared(hull.get(r));
		if (tempwidth < width) {
			width = tempwidth;
			widthline.update(hull.get(r), intersection);
			//The support basis stores the direction that the support lines will point
			//It is then translated to the Point.  The head of the line as well as the
			//head of its reflection about its tail are the endpoints for the support lines.
			Segment supportBasis = GeometryManager.newSegment(rrq.getTail(),
					intersection);
			supportBasis = supportBasis.setVisible(false);
			supportBasis = supportBasis.perpendicular();
			supportBasis.translate(rrq.getTail());
			widthSupport1.update(supportBasis.getHead(), supportBasis
					.tailReflection().getHead());
			supportBasis.translate(intersection);
			widthSupport2.update(supportBasis.getHead(), supportBasis
					.tailReflection().getHead());
		}
		return width;
	}
}