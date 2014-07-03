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
				GeometryManager.newPoint(-4096, -4096),
				GeometryManager.newPoint(16384, -4096),
				GeometryManager.newPoint(-4096, 16384));
		for (Point p : points) {
			try {
				s.insertSite(p);
			} catch (DuplicatePointException e) {
				// don't insert duplicates.
			} catch (MalformedTriangulationException e) {
				System.out
						.println("Need to initialize a bigger starting subdivision for Delaunay Triangulation");
			}
		}

	}
}
