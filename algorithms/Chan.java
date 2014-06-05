package algorithms;

import cg.Point;
import cg.Polygon;

public class Chan {

	public void doChan(Polygon points, Polygon hull) {
		// first add the leftmost point to the convex hull and move it to index
		// zero.  
		swap(0, leftmostIndex(points), points);
		hull.add(points.getPoint(0));
		doChan(0, points.numPoints(), hull.getLast(), points, hull);
	}

	private void doChan(int left, int right, Point hullpt, Polygon points, Polygon hull) {
		// Vector direction
		if (hullpt.equals(hull.getLast())) {
			// direction = new VectorComponent(1,0);
		} else {
			// direction = new VectorComponent(hull.last().sub(hullpt)).perp();
		}
	}

	private int leftmostIndex(Polygon points) {
		int leftmostIndex = 0;
		int leftmostX = points.getPoint(leftmostIndex).getX();
		for (int i = 1; i < points.numPoints(); i++) {
			if (points.getPoint(i).getX() < leftmostX) {
				leftmostIndex = i;
				leftmostX = points.getPoint(leftmostIndex).getX();
			}
		}
		return leftmostIndex;
	}

	private void swap(int i, int j, Polygon points) {
		Point p0 = points.getPoint(i);
		Point p1 = points.getPoint(j);
		points.setPoint(j, p0);
		points.setPoint(i, p1);
	}
}
