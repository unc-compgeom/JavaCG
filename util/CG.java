package util;

import java.awt.Color;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import cg.Vertex;
import cg.VertexSet;
import cg.VertexSetComponent;

public class CG {

	/**
	 * This method finds the point in the set <code>points</code> that has the
	 * smallest Y value. If there is a tie, it finds the point with the smallest
	 * Y and X value.
	 * 
	 * @param points
	 * @return the point with the lowest Y, X value.
	 */
	public static Vertex findSmallestYX(VertexSet points) {
		Vertex min = points.get(0);
		int minY = min.getY();
		Vertex lookingAt;
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
	public static VertexSet lexicographicalSort(VertexSet points) {
		PriorityQueue<Vertex> sorter = new PriorityQueue<Vertex>();
		sorter.addAll(points);
		VertexSet sorted = new VertexSetComponent();
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
	public static VertexSet sortByAngle(VertexSet points) {
		final Vertex compare = points.getFirst();
		PriorityQueue<Vertex> sorter = new PriorityQueue<Vertex>(
				new Comparator<Vertex>() {
					@Override
					public int compare(Vertex p, Vertex q) {
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
		VertexSet sorted = new VertexSetComponent();
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
	public static long distSquared(Vertex p, Vertex q) {
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
	public static long cross(Vertex o, Vertex a, Vertex b) {
		return (a.getX() - o.getX()) * (b.getY() - o.getY())
				- (a.getY() - o.getY()) * (b.getX() - o.getX());
	}

	public static Color randomColor() {
		Random Ayn = new Random();
		return new Color(Ayn.nextInt(256), Ayn.nextInt(256), Ayn.nextInt(256));
	}
}
