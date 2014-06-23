package algorithms;

import java.awt.Color;
import java.util.ListIterator;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import cg.GeometryManager;
import cg.Polygon;
import cg.Point;
import cg.PointSet;

public class BentleyFaustPreparata {
	public static void findConvexHull(PointSet points, Polygon hull) {
		ListIterator<Point> it = points.listIterator();
		Point v = it.next();
		int minX = v.getX(), maxX = v.getX();
		Point minMin = v, minMax = v, maxMin = v, maxMax = v;
		// find min/max vertices
		while (it.hasNext()) {
			v = it.next();
			if (v.getX() < minX) {
				minX = v.getX();
				minMin = v;
				minMax = v;
			} else if (v.getX() > maxX) {
				maxX = v.getX();
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
			int index = (v.getX() - minX);
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
		lower.setColor(Color.MAGENTA);
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
		upper.setColor(Color.BLUE);
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
		for (Point vt : lower) {
			hull.add(vt);
		}
		for (Point vt : upper) {
			hull.add(vt);
		}
		// clean up
		GeometryManager.destroyGeometry(upper);
		GeometryManager.destroyGeometry(lower);
	}
}

class VertexHolder {
	private Point min;
	private Point max;

	public Point getMin() {
		return this.min;
	}

	public void setMin(Point min) {
		this.min = min;
	}

	public Point getMax() {
		return this.max;
	}

	public void setMax(Point max) {
		this.max = max;
	}
}