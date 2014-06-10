package util;

import java.util.Comparator;
import java.util.PriorityQueue;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import cg.Point;
import cg.PointSet;
import cg.PointSetComponent;

public class CG {
	public static void sleep() {
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
		}
	}

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
		PointSet sorted = new PointSetComponent();
		while (!sorter.isEmpty()) {
			sorted.add(sorter.remove());
		}
		return sorted;
	}

	/**
	 * This method sorts <code>points</code> in increasing order by the angle
	 * they make with the point at location zero.
	 * 
	 * @param points
	 *            the points to sort
	 * @return the sorted set of points
	 */
	public static PointSet sortByAngle(PointSet points) {
		final Point compare = points.getFirst();
		PriorityQueue<Point> sorter = new PriorityQueue<Point>(
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
		PointSet sorted = new PointSetComponent();
		while (!sorter.isEmpty()) {
			sorted.add(sorter.remove());
		}
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
}
