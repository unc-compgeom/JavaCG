package algorithms;

import util.ColorSpecial;
import util.DuplicatePointException;
import util.MalformedTriangulationException;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Subdivision;

public class DelaunayTriangulation {
	public static Subdivision triangulate(PointSet points) {
		Subdivision s = GeometryManager.newSubdivision();
		s.setColor(ColorSpecial.PASTEL_GREEN);
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
		return s;
	}
}
