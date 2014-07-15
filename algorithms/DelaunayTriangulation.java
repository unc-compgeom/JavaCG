package algorithms;

import util.ColorSpecial;
import util.DuplicatePointException;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Subdivision;

class DelaunayTriangulation {
	public static Subdivision triangulate(PointSet points) {
		Subdivision s = GeometryManager.newSubdivision();
		s.setColor(ColorSpecial.PASTEL_GREEN);
		for (Point p : points) {
			try {
				s.insertSite(p);
			} catch (DuplicatePointException e) {
				// don't insert duplicates.
			}
		}
		return s;
	}
}
