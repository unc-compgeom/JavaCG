package algorithms;

import util.DuplicatePointException;
import util.MalformedTriangulationException;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Subdivision;

public class DelaunayTriangulation {
	public static void doDelaunay(PointSet points) {
		// TODO find a triangle large enough to encompass <tt>points</tt>
		Subdivision s = GeometryManager.newSubdivision(
				GeometryManager.newPoint(-1, -1),
				GeometryManager.newPoint(1000, -1),
				GeometryManager.newPoint(-1, 1000));
		for (Point p : points) {
			try {
				s.insertSite(p);
			} catch (DuplicatePointException | MalformedTriangulationException e) {

				e.printStackTrace();
			}
		}

	}
}
