package util;

import java.awt.Color;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;

public class CG {

	/**
	 * This method finds the point in the set <code>points</code> that has the
	 * smallest Y value. If there is a tie, it finds the point with the smallest
	 * Y and X value.
	 * 
	 * @param points
	 * @return the point with the lowest Y, X value.
	 */
	public static Point findSmallestYX(PointSet points) {
		Point min = points.get(0);
		int minY = min.getY();
		Point lookingAt;
		for (int i = 1; i < points.size(); i++) {
			lookingAt = points.get(i);
			if (lookingAt.getY() <= minY) {
				if (lookingAt.getY() < minY) {
					min = lookingAt;
					minY = min.getY();
				} else {
					min = (min.getX() < lookingAt.getX()) ? min : lookingAt;
					minY = min.getY();
				}
			}
		}
		return min;
	}

	/**
	 * Sort the points of <code>points</code> by x-coordinate (in case of a tie,
	 * sort by y-coordinate).
	 * 
	 * @param points
	 * @return A PointSet of the sorted points
	 */
	public static PointSet lexicographicalSort(PointSet points) {
		PriorityQueue<Point> sorter = new PriorityQueue<Point>();
		sorter.addAll(points);
		PointSet sorted = GeometryManager.newVertexSet();
		Color c = sorted.getColor();
		sorted.setColor(Color.cyan);
		while (!sorter.isEmpty()) {
			sorted.add(sorter.remove());
		}
		sorted.setColor(c);
		return sorted;
	}

	/**
	 * This method sorts <code>points</code> in increasing order by the angle
	 * they make with the point <code>compare</code>.
	 * 
	 * @param points
	 *            the points to sort
	 * @param compare
	 *            the point from which all angles originate
	 * @return A set of points sorted by angle
	 */
	public static PointSet sortByAngle(PointSet points, final Point compare) {
		PriorityQueue<Point> sorter = new PriorityQueue<Point>(11,
				new Comparator<Point>() {
					@Override
					public int compare(Point p, Point q) {
						Orientation o = Predicate
								.findOrientation(q, compare, p);
						if (o == Orientation.CLOCKWISE) {
							return -1;
						} else if (o == Orientation.COUNTERCLOCKWISE) {
							return 1;
						} else {
							// colinear
							long dP = distSquared(compare, p);
							long dQ = distSquared(compare, q);
							if (dP < dQ) {
								return -1;
							} else if (dP > dQ) {
								return 1;
							} else {
								return 0;
							}
						}
					}

				});
		sorter.addAll(points);
		PointSet sorted = GeometryManager.newVertexSet();
		Color c = sorted.getColor();
		sorted.setColor(Color.cyan);
		while (!sorter.isEmpty()) {
			sorted.add(sorter.remove());
		}
		sorted.setColor(c);
		return sorted;
	}

	/**
	 * 
	 * @param p
	 * @param q
	 * @return distance squared between points p and q.
	 */
	public static long distSquared(Point p, Point q) {
		long dx = p.getX() - q.getX(), dy = p.getY() - q.getY();
		return dx * dx + dy * dy;
	}

	/**
	 * 
	 * @param o
	 * @param a
	 * @param b
	 * @return the cross product of the two vectors oa, ob
	 */
	public static long cross(Point o, Point a, Point b) {
		return (a.getX() - o.getX()) * (b.getY() - o.getY())
				- (a.getY() - o.getY()) * (b.getX() - o.getX());
	}

	public static Color randomColor() {
		Random Ayn = new Random();
		return new Color(100 + Ayn.nextInt(156), 100 + Ayn.nextInt(156),
				100 + Ayn.nextInt(156));
	}
}
