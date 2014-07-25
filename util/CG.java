package util;

import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import javafx.scene.paint.Color;
import predicates.Predicate;
import predicates.Predicate.Orientation;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class CG {

	/**
	 * Causes the calling thread to sleep for the current animation delay.
	 */
	public static void animationDelay() {
		try {
			Thread.sleep(GeometryManager.getDelay());
		} catch (InterruptedException ignored) {
			// do nothing
		}
	}

	/**
	 * Computes the distance squared between points p and q
	 *
	 * @param p a point
	 * @param q a point
	 * @return distance squared between points p and q.
	 */
	public static double distSquared(Point p, Point q) {
		// increment the call counter
		// do work
		double dx = p.getX() - q.getX(), dy = p.getY() - q.getY();
		return dx * dx + dy * dy;
	}

	/**
	 * This method finds the point in the set <code>points</code> that has the
	 * smallest Y value. If there is a tie, it finds the point with the smallest
	 * Y and X value.
	 *
	 * @param points the set of points
	 * @return the point with the lowest Y, X value.
	 */
	public static Point findSmallestYX(PointSet points) {
		// increment the call counter
		// do work
		Point min = points.get(0);
		double minY = min.getY();
		Point lookingAt;
		Color oldLooking = null;
		min.setColor(ColorSpecial.GREEN);
		for (int i = 1; i < points.size(); i++) {
			lookingAt = points.get(i);
			if (lookingAt != min) {
				oldLooking = lookingAt.getColor();
				lookingAt.setColor(ColorSpecial.ORANGE);
			}
			if (lookingAt.getY() <= minY) {
				if (lookingAt.getY() < minY) {
					min.setColor(oldLooking);
					min = lookingAt;
					minY = min.getY();
					min.setColor(ColorSpecial.GREEN);
				} else {
					min.setColor(oldLooking);
					min = min.getX() < lookingAt.getX() ? min : lookingAt;
					minY = min.getY();
					min.setColor(ColorSpecial.GREEN);
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
	 * Sort {@link PointSet} by x-coordinate (in case of a tie, sort by
	 * y-coordinate).
	 *
	 * @param points the point set to sort
	 */
	public static void lexicographicalSort(PointSet points) {
		// increment the call counter
		// do work
		PriorityQueue<Point> sorter = new PriorityQueue<>();
		sorter.addAll(points);
		PointSet sorted = GeometryManager.newPointSet();
		sorted.setColor(ColorSpecial.CYAN);
		while (!sorter.isEmpty()) {
			sorted.add(sorter.remove());
		}
		points.removeAll(points);
		points.addAll(sorted);
	}

	public static Color randomColor() {
		// increment the call counter
		// do work
		Random Ayn = new Random();
		return new Color(Ayn.nextDouble(), Ayn.nextDouble(), Ayn.nextDouble(), 1);
	}

	/**
	 * This method sorts <code>points</code> in increasing order by the angle
	 * they make with the point <code>compare</code>.
	 *
	 * @param points  the points to sort
	 * @param compare the point from which all angles originate
	 * @return A set of points sorted by angle
	 */
	public static PointSet sortByAngle(PointSet points, final Point compare) {
		// increment the call counter
		// do work
		PriorityQueue<Point> sorter = new PriorityQueue<>(11,
				(p, q) -> {
					Orientation o = Predicate
							.findOrientation(q, compare, p);
					if (o == Orientation.CLOCKWISE) {
						return -1;
					} else if (o == Orientation.COUNTERCLOCKWISE) {
						return 1;
					} else {
						// co-linear
						double dP = distSquared(compare, p);
						double dQ = distSquared(compare, q);
						if (dP < dQ) {
							return -1;
						} else if (dP > dQ) {
							return 1;
						} else {
							return 0;
						}
					}
				});
		sorter.addAll(points);
		PointSet sorted = GeometryManager.newPointSet();
		Color c = sorted.getColor();
		sorted.setColor(ColorSpecial.CYAN);
		while (!sorter.isEmpty()) {
			sorted.add(sorter.remove());
		}
		sorted.setColor(c);
		return sorted;
	}
}
