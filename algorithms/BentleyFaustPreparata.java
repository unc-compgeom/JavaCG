package algorithms;

import java.util.ListIterator;
import java.util.stream.Collectors;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.ColorSpecial;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

class BentleyFaustPreparata {
	/**
	 * Computes the convex hull using the Bentley Faust Preparata. Precondition:
	 * all points are at integer coordinates. If points are not at integer
	 * coordinates they will be rounded to integers.
	 *
	 * @param points
	 *            the raw points
	 * @return the convex hull
	 */
	public static Polygon findConvexHull(final PointSet points) {
		// find min/max vertices
		ListIterator<Point> it = points.listIterator();
		Point v = it.next();
		int minX = (int) v.getX(), maxX = (int) v.getX();
		Point minMin = v, minMax = v, maxMin = v, maxMax = v;
		while (it.hasNext()) {
			v = it.next();
			if (v.getX() < minX) {
				minX = (int) v.getX();
				minMin = v;
				minMax = v;
			} else if (v.getX() > maxX) {
				maxX = (int) v.getX();
				maxMin = v;
				maxMax = v;
			} else if (v.getX() == minX) {
				if (v.getY() > minMax.getY()) {
					minMax = v;
				} else if (v.getY() < minMin.getY()) {
					minMin = v;
				}
			} else if (v.getX() == maxX) {
				if (v.getY() > maxMax.getY()) {
					maxMax = v;
				} else if (v.getY() < maxMin.getY()) {
					maxMin = v;
				}
			}
		}

		// divide points into buckets by x coordinate
		final PointHolder[] buckets = new PointHolder[maxX - minX + 1];
		// include an extra bucket for first/last
		buckets[0] = new PointHolder();
		buckets[0].setMin(minMin);
		buckets[0].setMax(minMax);
		buckets[buckets.length - 1] = new PointHolder();
		buckets[buckets.length - 1].setMin(maxMin);
		buckets[buckets.length - 1].setMax(maxMax);
		// get the min/max points for each division
		it = points.listIterator();
		while (it.hasNext()) {
			v = it.next();
			final int index = (int) (v.getX() - minX);
			if (buckets[index] == null) {
				buckets[index] = new PointHolder();
			}
			if (Predicate.findOrientation(v, minMin, maxMin) == Predicate.Orientation.CLOCKWISE) {
				// vertex is below line from minMin to maxMin
				if (buckets[index].getMin() == null) {
					buckets[index].setMin(v);
				} else if (v.getY() < buckets[index].getMin().getY()) {
					buckets[index].setMin(v);
				}
			} else if (Predicate.findOrientation(v, minMax, maxMax) == Predicate.Orientation.COUNTERCLOCKWISE) {
				// vertex is above the line from minMax to maxMax
				if (buckets[index].getMax() == null) {
					buckets[index].setMax(v);
				} else if (v.getY() > buckets[index].getMax().getY()) {
					buckets[index].setMax(v);
				}
			}
		}
		// modification of Monotone Chain
		// lower convex hull
		final Polygon lowerHull = GeometryManager.newPolygon();
		lowerHull.setColor(ColorSpecial.MAGENTA);
		lowerHull.add(minMin);
		for (int i = 1; i < buckets.length; i++) {
			if (buckets[i] == null) {
				continue;
			}
			final Point p = buckets[i].getMin();
			if (p == null) {
				continue;
			}
			while (lowerHull.size() > 1
					&& Predicate.findOrientation(p,
							lowerHull.getSecondToLast(), lowerHull.getLast()) != Orientation.COUNTERCLOCKWISE) {
				lowerHull.removeLast();
			}
			lowerHull.addLast(buckets[i].getMin());
		}

		// upper convex hull
		final Polygon upperHull = GeometryManager.newPolygon();
		upperHull.setColor(ColorSpecial.BLUE);
		upperHull.add(maxMax);
		for (int i = buckets.length - 2; i >= 0; i--) {
			if (buckets[i] == null) {
				continue;
			}
			final Point p = buckets[i].getMax();
			if (p == null) {
				continue;
			}
			while (upperHull.size() > 1
					&& Predicate.findOrientation(p,
							upperHull.getSecondToLast(), upperHull.getLast()) != Orientation.COUNTERCLOCKWISE) {
				upperHull.removeLast();
			}
			upperHull.addLast(buckets[i].getMax());
		}

		// join the upper and lower convex hulls
		final Polygon hull = GeometryManager.newPolygon();
		hull.setColor(ColorSpecial.PASTEL_GREEN);
		hull.addAll(lowerHull.stream().collect(Collectors.toList()));
		hull.addAll(upperHull.stream().collect(Collectors.toList()));

		// connect the first and last edges
		hull.add(hull.getFirst());

		// clean up
		GeometryManager.destroy(upperHull);
		GeometryManager.destroy(lowerHull);
		return hull;
	}
}

/**
 * A helper class to hold two Points.
 */
class PointHolder {
	private Point min;
	private Point max;

	public Point getMax() {
		return max;
	}

	public Point getMin() {
		return min;
	}

	public void setMax(final Point max) {
		this.max = max;
	}

	public void setMin(final Point min) {
		this.min = min;
	}
}