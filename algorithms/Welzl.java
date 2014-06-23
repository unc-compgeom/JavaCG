package algorithms;

import java.awt.Color;

import predicates.Predicate;
import cg.Circle;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;

public class Welzl {
	public static void findSmallestEnclosingCircle(PointSet points,
			Circle result) {
		PointSet s = GeometryManager.newPointSet(points);
		s.setColor(Color.ORANGE);
		result = SEC(s, GeometryManager.newPointSet());
	}

	private static Circle SEC(PointSet points, PointSet set) {
		Circle c;
		PointSet S = GeometryManager.newPointSet(points);
		PointSet P = GeometryManager.newPointSet(set);
		P.setColor(Color.PINK);

		if (P.size() == 3 || S.size() == 0) {
			// make circle from result;
			c = GeometryManager.newCircle(P);
			c.setColor(Color.RED);
		} else {
			Point s = S.remove(0);
			c = SEC(S, P);
			if (!Predicate.isVertexInCircle(s, c)) {
				P.add(s);
				c.setColor(new Color(220, 220, 220));
				Circle tmp = c;
				c = SEC(S, P);
				GeometryManager.destroyGeometry(tmp);
			}
		}

		GeometryManager.destroyGeometry(S);
		GeometryManager.destroyGeometry(P);
		return c;
	}
}