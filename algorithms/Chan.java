package algorithms;

import java.awt.Color;

import util.CG;
import cg.Polygon;
import cg.VertexSet;

public class Chan {

	public static void doChan(VertexSet points, Polygon hull) {
		for (int t = 1; t < points.size(); t++) {
			int m = (int) Math.pow(2, Math.pow(2, t));
			VertexSet[] divided = new VertexSet[m];
			Polygon[] hulls = new Polygon[m];
			for (int ps = 0; ps < hulls.length; ps++) {
				Color c = CG.randomColor();
				divided[ps] = points.cloneEmpty();
				divided[ps].setColor(c);
				hulls[ps] = hull.cloneEmpty();
				hulls[ps].setColor(c);
				for (int i = 0; i < m && i + ps * m < points.size(); i++) {
					divided[ps].addFirst(points.get(i + ps * m));
				}
				hulls[ps].addObservers(hull.getObservers());
				GrahmScan.doGrahmScan(divided[ps], hulls[ps]);
			}
			int[] minHullPt = getMinHullPoint(hulls);
			hull.addFirst(hulls[minHullPt[0]].get(minHullPt[1]));
			// TODO fix
			// for (int x = 0; x < m; x++) {
			// Point p = nextHullPoint(hulls, hull.getLast());
			// if (p.equals(hull.getFirst())) {
			// return;
			// } else {
			// hull.addLast(p);
			// }
			// }
			for (VertexSet p : divided) {
				p.clear();
				p.removeAllObservers();
			}
			for (Polygon p : hulls) {
				p.clear();
				p.removeAllObservers();
			}
			hull.clear();
		}
	}

	private static int[] getMinHullPoint(Polygon[] hulls) {
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

	// public static void makeHull(PointSet points, Polygon hull, int m, int h)
	// {
	// int numPartitions = (int) Math.ceil(points.size() / m);
	// PointSet[] partitions = new PointSet[numPartitions];
	// Polygon[] convexHulls = new PolygonComponent[numPartitions];
	// // 1. partition P into subsets P_1 ... P_ceil(n/m)
	// partition(points, m, partitions);
	// for (int i = 0; i < partitions.length; i++) {
	// // 3. compute conv(P_i) by Grahm scan and store its vertices in an
	// // array in ccw order
	// GrahmScan.doGrahmScan(partitions[i], convexHulls[i]);
	// }
	// //Point p0, p1 = CG.findSmallestYX(points);
	// for (int k = 1; k < h; k++) {
	// for (int i = 1; i < convexHulls.length; i++) {
	// // 8. compute the point q_i \in P_i that maximizes the angle
	// // p_{k-1}, p_k, q
	//
	// }
	// // 9. p_k+1 = the point q from {q_1...q_ceil(n/m) that maximizes the
	// // angle p_{k-1}, p_k, q
	// // 10. if p_{k+1} == p_1 then return finish
	// }
	// // 11. return incomplete
	// }

	// /**
	// * Partition points into ceil(n/m) subsets each of size at most m.
	// *
	// * @param points
	// * @param m
	// * @return an array of sets that make up the partitions
	// */
	// private static void partition(PointSet points, int m, PointSet[]
	// partitions) {
	// int i = 0;
	// int j = 0;
	// for (Point p : points) {
	// partitions[i].add(p);
	// j++;
	// if (j == m) {
	// j = 0;
	// i++;
	// }
	// }
	// }
}
