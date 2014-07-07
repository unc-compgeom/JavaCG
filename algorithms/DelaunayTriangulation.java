package algorithms;

import java.awt.Color;

import util.DuplicatePointException;
import util.MalformedTriangulationException;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Subdivision;

public class DelaunayTriangulation {
	public static void doDelaunay(PointSet points) {
		// TODO find a triangle large enough to encompass <tt>points</tt>
		int scale = 16364;
		Subdivision s = GeometryManager.newSubdivision(
				GeometryManager.newPoint(-1 * scale, -1 * scale),
				GeometryManager.newPoint(2 * scale, -1 * scale),
				GeometryManager.newPoint(-1 * scale, 2 * scale));
		s.setColor(Color.GREEN.darker());
		for (Point p : points) {
			try {
				s.insertSite(p);
			} catch (DuplicatePointException e) {
				// don't insert duplicates.
			} catch (MalformedTriangulationException e) {
				System.out
						.println("Need to initialize a bigger starting subdivision for Delaunay Triangulation");
				e.printStackTrace();
			}
		}

	}
}
