package algorithms;

import cg.Point;
import cg.PointComponent;
import cg.PointSet;
import cg.Subdivision;
import cg.SubdivisionComponent;

public class DelaunayTriangulation {
	public static void doDelaunay(PointSet points) {
		// TODO find a triangle large enough to encompass <tt>points</tt>
		Subdivision s = new SubdivisionComponent(new PointComponent(0, 0),
				new PointComponent(1000, 0), new PointComponent(0, 1000));
		for (Point p : points) {
			s.insertSite(p);
			System.out.println("inserted " + p);
		}
	}
}
