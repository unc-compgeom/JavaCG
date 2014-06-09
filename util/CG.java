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

	public static PointSet sortByAngle(PointSet p, final Point pt) {
		PriorityQueue<Point> sorter = new PriorityQueue<Point>(
				11, new Comparator<Point>() {
					@Override
					public int compare(Point p1, Point p2) {
						Orientation orient = Predicate.findOrientation(pt, p1,
								p2);
						if (orient == Orientation.COLINEAR) {
							return (distSquared(pt, p1) > distSquared(pt, p2)) ? 1
									: -1;
						} else {
							return (orient == Orientation.COUNTERCLOCKWISE) ? -1
									: 1;
						}
					}

					private int distSquared(Point p, Point q) {
						int px = p.getX(), py = p.getY(), qx = q.getX(), qy = q
								.getY();
						return (px - qx) * (px - qx) + (py - qy) * (py - qy);
					}
				});
		for (int i = 0; i < p.numPoints(); i++) {
			sorter.add(p.getPoint(i));
		}
		PointSet points = new PointSetComponent();
		while (!sorter.isEmpty()) {
			points.addPoint(sorter.remove());
		}
		return points;
	}

}
