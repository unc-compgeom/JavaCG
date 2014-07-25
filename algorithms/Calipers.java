package algorithms;

import cg.*;
import predicates.Predicate;
import util.ColorSpecial;

class Calipers {

	/**
	 * This method measures the squared distance between vertices i and j. If
	 * the squared distance is less than the current squared distance, then
	 * <tt>diamline</tt>'s endpoints become i and j. The parallel support lines
	 * are then updated to be tangent to both i and j.
	 * 
	 * @param diam
	 * @param i
	 * @param j
	 * @param hull
	 * @param diamline
	 * @param diamSupport1
	 * @param diamSupport2
	 * @return
	 */
	private static double checkDiameter(double diam, int i, int j, Polygon hull,
	                                    Segment diamline, Segment diamSupport1, Segment diamSupport2) {
		Point pi = get(hull, i);
		Point pj = get(hull, j);
		double tempdiam = pi.distanceSquared(pj);
		if (tempdiam > diam) {
			int tan1, tan2;
			diam = tempdiam;
			diamline.setTail(pi);
			diamline.setHead(pj);
			if (cwIntersection(i + 1, j, hull)) {
				tan1 = j;
				tan2 = j + 1;
			} else {
				tan1 = i;
				tan2 = i + 1;
			}
			// The support basis stores the direction that the support lines
			// will point
			// It is then translated to the Point. The head of the line as well
			// as the
			// head of its reflection about its tail are the endpoints for the
			// support lines.
			Segment supportBasis = GeometryManager.newSegment(get(hull, tan1),
					get(hull, tan2));
			supportBasis.setInvisible(true);
			supportBasis.translate(pi);
			diamSupport1.setTail(supportBasis.getHead());
			diamSupport1.setHead(supportBasis.tailReflection().getHead());
			supportBasis.translate(pj);
			diamSupport2.setTail(supportBasis.getHead());
			diamSupport2.setHead(supportBasis.tailReflection().getHead());
		}
		return diam;
	}

	/**
	 * This method finds the distance squared between parallel tangents where at
	 * least one tangent lies on the edge of the convex hull. If the new
	 * distance squared is less than the previous distance squared, then the
	 * width line is updated as well as its supports.
	 * 
	 * @param width
	 * @param i
	 * @param j
	 * @param hull
	 * @param widthline
	 * @param widthSupport1
	 * @param widthSupport2
	 * @return
	 */
	private static double checkWidth(double width, int i, int j, Polygon hull,
	                                 Segment widthline, Segment widthSupport1, Segment widthSupport2) {
		int r, p, q;
		double tempwidth;
		// If the cwIntersection test returns true, then i has a tangent
		// parallel to the line
		// j to j+1. Thus the distance between i and the line j to j+1 is found.
		if (cwIntersection(i + 1, j, hull)) {
			r = i;
			p = j;
			q = j + 1;
			// Otherwise j has a tangent parallel to the line i to i+1. The
			// distance between
			// j and the line i to i+1 is found.
		} else {
			r = j;
			p = i;
			q = i + 1;
		}
		// line segment from p to q
		Segment pq = GeometryManager.newSegment(get(hull, p), get(hull, q));
		pq.setInvisible(true);
		// line segment at r parallel to the line segment p to q
		Segment rrq = GeometryManager.newSegment(get(hull, p), get(hull, q));
		rrq.setInvisible(true);
		rrq.translate(get(hull, r));
		// intersection of line pq with the line perpendicular to rrq
		Point intersection = rrq.findPerpendicular().findIntersection(pq);
		// distance from r to the intersection point is a candidate width
		tempwidth = intersection.distanceSquared(get(hull, r));
		if (tempwidth < width) {
			width = tempwidth;
			widthline.setTail(get(hull, r));
			widthline.setHead(intersection);
			// The support basis stores the direction that the support lines
			// will point
			// It is then translated to the Point. The head of the line as well
			// as the
			// head of its reflection about its tail are the endpoints for the
			// support lines.
			Segment supportBasis = GeometryManager.newSegment(rrq.getTail(),
					intersection);
			supportBasis.setInvisible(true);
			supportBasis = supportBasis.findPerpendicular();
			supportBasis.translate(rrq.getTail());
			widthSupport1.setTail(supportBasis.getHead());
			widthSupport1.setHead(supportBasis.tailReflection().getHead());
			supportBasis.translate(intersection);
			widthSupport2.setTail(supportBasis.getHead());
			widthSupport2.setHead(supportBasis.tailReflection().getHead());
		}
		return width;
	}

	/**
	 * This method takes line segment j to j+1 and translates it so that the
	 * tail (point originally at j)is now at point i. If the point
	 * translated(j+1) lies clockwise to the line i-1 to i,then true is
	 * returned. Otherwise false is returned.
	 * 
	 * @param i
	 * @param j
	 * @param hull
	 * @return
	 */
	private static boolean cwIntersection(int i, int j, Polygon hull) {
		Point Pa = GeometryManager.newPoint(get(hull, i - 1));
		Point Pb = GeometryManager.newPoint(get(hull, i));
		Point Pd = GeometryManager.newPoint(get(hull, j + 1));
		Pd.setX(Pd.getX() - get(hull, j).getX() + Pb.getX());
		Pd.setY(Pd.getY() - get(hull, j).getY() + Pb.getY());
		return Predicate.findOrientation(Pa, Pb, Pd) == Predicate.Orientation.CLOCKWISE;
	}

	/**
	 * The rotating caliper algorithm calculates the maximum diameter of the
	 * convex hull and minimum width of the convex hull given a point set.
	 * 
	 * @param points
	 *            the point set
	 * 
	 * @return the convex hull
	 */
	public static Polygon doCalipers(PointSet points) {
		Polygon hull = GeometryManager.newPolygon();
		hull.setColor(ColorSpecial.PASTEL_GREEN);
		Segment widthLine = GeometryManager.newSegment(-1, -1, -1, -1);
		Segment diameterLine = GeometryManager.newSegment(-1, -1, -1, -1);
		int i = 0;
		int j = 1;
		double diam = -1;
		double width = Double.POSITIVE_INFINITY;

		// Visualization Variables
		Segment diamSupport1 = GeometryManager.newSegment(-1, -1, -1, -1);
		Segment diamSupport2 = GeometryManager.newSegment(-1, -1, -1, -1);
		Segment widthSupport1 = GeometryManager.newSegment(-1, -1, -1, -1);
		Segment widthSupport2 = GeometryManager.newSegment(-1, -1, -1, -1);

		diamSupport1.setColor(ColorSpecial.BLUE);
		diamSupport2.setColor(ColorSpecial.BLUE);
		widthSupport1.setColor(ColorSpecial.BLUE);
		widthSupport2.setColor(ColorSpecial.BLUE);
		diameterLine.setColor(ColorSpecial.GREEN);
		widthLine.setColor(ColorSpecial.ORANGE);

		// Jarvis march is used to find the convex hull
		hull = JarvisMarch.findConvexHull(points);

		// Initialization Step
		while (cwIntersection(0, j, hull)) {
			j++;
		}

		// Rotation Step
		while (j < hull.size()) {
			// check if new max diameter or minimum width at points i and j
			diam = checkDiameter(diam, i, j, hull, diameterLine, diamSupport1,
					diamSupport2);
			width = checkWidth(width, i, j, hull, widthLine, widthSupport1,
					widthSupport2);
			if (cwIntersection(i + 1, j, hull)) {
				j++;
			} else if (cwIntersection(j + 1, i, hull)) {
				i++;
			} else {
				j++;
				i++;
			}
		}
		GeometryManager.destroy(diamSupport1);
		GeometryManager.destroy(diamSupport2);
		GeometryManager.destroy(widthSupport1);
		GeometryManager.destroy(widthSupport2);
		return hull;
	}

	private static Point get(PointSet points, int index) {
		while (index < 0) {
			index += points.size();
		}
		return points.get(index % points.size());
	}
}