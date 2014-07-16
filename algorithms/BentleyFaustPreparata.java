package algorithms;

import java.util.ListIterator;

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
	 * all points are at integer coordinates.
	 * 
	 * @param points
	 *            the raw points
	 * @return the convex hull
	 */
	public static Polygon findConvexHull(PointSet points) {
		ListIterator<Point> it = points.listIterator();
		Point v = it.next();
		int minX = (int) v.getX(), maxX = (int) v.getX();
		Point minMin = v, minMax = v, maxMin = v, maxMax = v;
		// find min/max vertices
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
		VertexHolder[] buckets = new VertexHolder[maxX - minX + 1];
		// include an extra bucket for first/last
		buckets[0] = new VertexHolder();
		buckets[0].setMin(minMin);
		buckets[0].setMax(minMax);
		buckets[buckets.length - 1] = new VertexHolder();
		buckets[buckets.length - 1].setMin(maxMin);
		buckets[buckets.length - 1].setMax(maxMax);
		// get the min/max points for each division
		it = points.listIterator();
		while (it.hasNext()) {
			v = it.next();
			int index = (int) (v.getX() - minX);
			if (buckets[index] == null) {
				buckets[index] = new VertexHolder();
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
		// lower
		Polygon lower = GeometryManager.newPolygon();
		lower.setColor(ColorSpecial.MAGENTA);
		lower.add(minMin);
		for (int i = 1; i < buckets.length; i++) {
			if (buckets[i] == null) {
				continue;
			}
			Point p = buckets[i].getMin();
			if (p == null) {
				continue;
			}
			while (lower.size() > 1
					&& Predicate.findOrientation(p, lower.getSecondToLast(),
							lower.getLast()) != Orientation.COUNTERCLOCKWISE) {
				lower.removeLast();
			}
			lower.addLast(buckets[i].getMin());
		}
		// upper
		Polygon upper = GeometryManager.newPolygon();
		upper.setColor(ColorSpecial.BLUE);
		upper.add(maxMax);
		for (int i = buckets.length - 2; i >= 0; i--) {
			if (buckets[i] == null) {
				continue;
			}
			Point p = buckets[i].getMax();
			if (p == null) {
				continue;
			}
			while (upper.size() > 1
					&& Predicate.findOrientation(p, upper.getSecondToLast(),
							upper.getLast()) != Orientation.COUNTERCLOCKWISE) {
				upper.removeLast();
			}
			upper.addLast(buckets[i].getMax());
		}
		// join
		Polygon hull = GeometryManager.newPolygon();
		hull.setColor(ColorSpecial.PASTEL_GREEN);
		for (Point point : lower) {
			hull.add(point);
		}
		for (Point point : upper) {
			hull.add(point);
		}
		// connect the first and last edges
		hull.add(hull.getFirst());
		// clean up
		GeometryManager.destroy(upper);
		GeometryManager.destroy(lower);
		return hull;
	}
}

class VertexHolder {
	private Point min;
	private Point max;

	public Point getMax() {
		return max;
	}

	public Point getMin() {
		return min;
	}

	public void setMax(Point max) {
		this.max = max;
	}

	public void setMin(Point min) {
		this.min = min;
	}
}