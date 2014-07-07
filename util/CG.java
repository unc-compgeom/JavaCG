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

	public static int cross = 0;
	public static int distSquared = 0;
	public static int findSmallestYX = 0;
	public static int lexicographicalSort = 0;
	public static int randomColor = 0;
	public static int sortByAngle = 0;

	/**
	 * 
	 * @param o
	 * @param a
	 * @param b
	 * @return the cross product of the two vectors oa, ob
	 */
	public static long cross(Point o, Point a, Point b) {
		// increment the call counter
		cross++;
		// do work
		return (a.getX() - o.getX()) * (b.getY() - o.getY())
				- (a.getY() - o.getY()) * (b.getX() - o.getX());
	}

	/**
	 * 
	 * @param p
	 * @param q
	 * @return distance squared between points p and q.
	 */
	public static long distSquared(Point p, Point q) {
		// increment the call counter
		distSquared++;
		// do work
		long dx = p.getX() - q.getX(), dy = p.getY() - q.getY();
		return dx * dx + dy * dy;
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
		// increment the call counter
		findSmallestYX++;
		// do work
		Point min = points.get(0);
		int minY = min.getY();
		Point lookingAt;
		Color oldLooking = null;
		min.setColor(Color.GREEN);
		for (int i = 1; i < points.size(); i++) {
			lookingAt = points.get(i);
			if (lookingAt != min) {
				oldLooking = lookingAt.getColor();
				lookingAt.setColor(Color.ORANGE);
			}
			if (lookingAt.getY() <= minY) {
				if (lookingAt.getY() < minY) {
					min.setColor(oldLooking);
					min = lookingAt;
					minY = min.getY();
					min.setColor(Color.GREEN);
				} else {
					min.setColor(oldLooking);
					min = (min.getX() < lookingAt.getX()) ? min : lookingAt;
					minY = min.getY();
					min.setColor(Color.GREEN);
				}
			}
			animationDelay();
			if (lookingAt != min) {
				lookingAt.setColor(oldLooking);
			}
		}
		min.setColor(oldLooking);
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
		// increment the call counter
		lexicographicalSort++;
		// do work
		PriorityQueue<Point> sorter = new PriorityQueue<Point>();
		sorter.addAll(points);
		PointSet sorted = GeometryManager.newPointSet();
		Color c = sorted.getColor();
		sorted.setColor(Color.cyan);
		while (!sorter.isEmpty()) {
			sorted.add(sorter.remove());
		}
		sorted.setColor(c);
		return sorted;
	}

	public static Color randomColor() {
		// increment the call counter
		randomColor++;
		// do work
		Random Ayn = new Random();
		return new Color(100 + Ayn.nextInt(156), 100 + Ayn.nextInt(156),
				100 + Ayn.nextInt(156));
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
		// increment the call counter
		sortByAngle++;
		// do work
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
		PointSet sorted = GeometryManager.newPointSet();
		Color c = sorted.getColor();
		sorted.setColor(Color.cyan);
		while (!sorter.isEmpty()) {
			sorted.add(sorter.remove());
		}
		sorted.setColor(c);
		return sorted;
	}

	public static void animationDelay() {
		try {
			Thread.sleep(GeometryManager.getDelay());
		} catch (InterruptedException e) {

		}
	}
}
