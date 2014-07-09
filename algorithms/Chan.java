package algorithms;

import java.awt.Color;

import util.CG;
import util.ColorSpecial;
import cg.GeometryManager;
import cg.PointSet;
import cg.Polygon;

public class Chan {

	public static Polygon findConvexHull(PointSet points) {
		int t = 1;
		int m, h;
		Polygon hull;
		do {
			m = h = (int) Math.pow(2, Math.pow(2, t));
			if (m > points.size()) {
				return findConvexHull(points, points.size(), points.size());
			}
			hull = findConvexHull(points, m, h);
			if (!hull.isEmpty()) {
				return hull;
			}
			t++;
		} while (true);

	}

	private static Polygon findConvexHull(PointSet points, double m, int h) {
		Polygon hull = GeometryManager.newPolygon();
		hull.setColor(ColorSpecial.PASTEL_GREEN);
		// Partition points into ceil(n/m) subsets each of size at most m.
		PointSet[] partitions = new PointSet[(int) Math.ceil(points.size() / m)];
		Polygon[] hulls = new Polygon[(int) Math.ceil(points.size() / m)];
		for (int i = 0; i < partitions.length; i++) {
			Color c = CG.randomColor();
			partitions[i] = GeometryManager.newPointSet();
			partitions[i].setColor(c);
			hulls[i] = GeometryManager.newPolygon();
			hulls[i].setColor(c);
			for (int j = 0; j < m && j + i * m < points.size(); j++) {
				partitions[i].add(points.get((int) (j + i * m)));
			}
		}
		// compute the convex hull of each partition
		for (int i = 0; i < partitions.length; i++) {
			hulls[i] = GrahmScan.findConvexHull(partitions[i]);
		}
		// initialize the convex hull
		int[] hullAndPoint = getLeftmostHullAndPoint(hulls);
		hull.add(hulls[hullAndPoint[0]].get(hullAndPoint[1]));
		hullAndPoint = getRightmostHullAndPoint(hulls);
		hull.add(hulls[hullAndPoint[0]].get(hullAndPoint[1]));
		// conquer
		for (int k = 1; k < m; k++) {
			for (int i = 1; i < hulls.length; i++) {
				// find point q_i that maximizes <p_{k-1}p_{k}q_{i}
			}
		}
		// TODO fix
		// Clean up
		for (PointSet p : partitions) {
			GeometryManager.destroy(p);
		}
		for (Polygon p : hulls) {
			GeometryManager.destroy(p);
		}
		// if done {
		// return hull;
		// } else
		{
			hull.clear();
		}
		// TODO remove this line
		return hull;
	}

	private static int[] getLeftmostHullAndPoint(Polygon[] hulls) {
		int minHull = 0;
		int minPt = 0;
		for (int i = 0; i < hulls.length; i++) {
			for (int j = 0; j < hulls[i].size(); j++) {
				if (hulls[i].get(j).compareTo(hulls[minHull].get(minPt)) < 0) {
					minHull = i;
					minPt = j;
				}
			}
		}
		return new int[] { minHull, minPt };
	}

	private static int[] getRightmostHullAndPoint(Polygon[] hulls) {
		int maxHull = 0;
		int maxPt = 0;
		for (int i = 0; i < hulls.length; i++) {
			for (int j = 0; j < hulls[i].size(); j++) {
				if (hulls[i].get(j).compareTo(hulls[maxHull].get(maxPt)) > 0) {
					maxHull = i;
					maxPt = j;
				}
			}
		}
		return new int[] { maxHull, maxPt };
	}
}
