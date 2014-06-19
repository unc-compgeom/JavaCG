package algorithms;

import java.awt.Color;

import predicates.Predicate;
import cg.Circle;
import cg.GeometryManager;
import cg.Vertex;
import cg.VertexSet;

public class Welzl {
	public static void findSmallestEnclosingCircle(VertexSet vertices,
			Circle result) {
		VertexSet s = GeometryManager.getVertexSet(vertices);
		s.setColor(Color.ORANGE);
		result = SEC(s, GeometryManager.getVertexSet());
	}

	private static Circle SEC(VertexSet vertices, VertexSet set) {
		Circle c;
		VertexSet S = vertices.clone();
		VertexSet P = set.clone();
		if (P.size() == 3 || S.size() == 0) {
			// make circle from result;
			c = GeometryManager.getCircle(P);
			c.setColor(Color.RED);
		} else {
			Vertex s = S.remove(0);
			c = SEC(S, P);
			if (!Predicate.isVertexInCircle(s, c)) {
				P.add(s);
				c.setColor(new Color(220, 220, 220));
				Circle tmp = c;
				c = SEC(S, P);
				GeometryManager.destroyGeometry(tmp);
			}
		}
		return c;
	}
}