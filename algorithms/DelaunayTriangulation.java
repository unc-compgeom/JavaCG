package algorithms;

import java.awt.Color;

import cg.GeometryManager;
import cg.Point;
import cg.PointComponent;
import cg.PointSet;
import cg.Subdivision;

public class DelaunayTriangulation {
	public static void doDelaunay(PointSet points) {
		// TODO find a triangle large enough to encompass <tt>points</tt>
		Subdivision s = GeometryManager.newSubdivision(
				new PointComponent(0, 0), new PointComponent(100, 0),
				new PointComponent(0, 100));
		points.removeAll(points);
		points.add(GeometryManager.newPoint(0, 50));
		points.add(GeometryManager.newPoint(50, 50));
		points.add(GeometryManager.newPoint(50, 0));
		s.setColor(Color.CYAN);
		for (Point p : points) {
			p.setColor(Color.red);
			s.insertSite(p);
			System.out.println("inserted " + p);
		}
	}
}
