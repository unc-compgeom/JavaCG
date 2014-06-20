package algorithms;

import java.awt.Color;

import util.CG;
import cg.GeometryManager;
import cg.Polygon;
import cg.VertexSet;

public class Chan {

	public static void findConvexHull(VertexSet points, Polygon hull) {
		for (int t = 1; t < points.size(); t++) {
			int m = (int) Math.pow(2, Math.pow(2, t));
			VertexSet[] divided = new VertexSet[m];
			Polygon[] hulls = new Polygon[m];
			for (int ps = 0; ps < hulls.length; ps++) {
				Color c = CG.randomColor();
				divided[ps] = GeometryManager.getVertexSet();
				divided[ps].setColor(c);
				hulls[ps] = GeometryManager.getPolygon();
				hulls[ps].setColor(c);
				for (int i = 0; i < m && i + ps * m < points.size(); i++) {
					divided[ps].addFirst(points.get(i + ps * m));
				}
				GrahmScan.findConvexHull(divided[ps], hulls[ps]);
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
				GeometryManager.destroyGeometry(p);
			}
			for (Polygon p : hulls) {
				GeometryManager.destroyGeometry(p);
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
}
