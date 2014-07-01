package algorithms;

import cg.GeometryManager;
import cg.PointSet;
import cg.Subdivision;

public class DelaunayTriangulation {
	public static void doDelaunay(PointSet points) {
		// TODO find a triangle large enough to encompass <tt>points</tt>
		Subdivision s = GeometryManager.newSubdivision(
				GeometryManager.newPoint(0, 0),
				GeometryManager.newPoint(100, 0),
				GeometryManager.newPoint(0, 100));
		s.insertSite(GeometryManager.newPoint(50, 50));
		// points.removeAll(points);
		// points.add(GeometryManager.newPoint(0, 50));
		// points.add(GeometryManager.newPoint(50, 50));
		// points.add(GeometryManager.newPoint(50, 0));
		// s.setColor(Color.CYAN);
		// for (Point p : points) {
		// p.setColor(Color.red);
		// s.insertSite(p);
		// System.out.println("inserted " + p);
		// }
	}
}
