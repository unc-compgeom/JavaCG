package algorithms;

import java.awt.Color;

import predicates.Predicate;
import cg.Circle;
import cg.CircleComponent;
import cg.Vertex;
import cg.VertexSet;

public class SmallestEnclosingCircle {
	public static void findSmallestEnclosingCircle(VertexSet vertices,
			Circle result) {
		VertexSet s = vertices.clone();
		s.setColor(Color.ORANGE);
		result = SEC(s, vertices.cloneEmpty());
	}

	private static Circle SEC(VertexSet vertices, VertexSet set) {
		Circle c;
		VertexSet S = vertices.clone();
		VertexSet P = set.clone();
		if (P.size() == 3 || S.size() == 0) {
			// make circle from result;
			c = new CircleComponent(P);
			c.addObservers(P.getObservers());
			c.setSize(P.getSize());
		} else {
			Vertex s = S.remove(0);
			c = SEC(S, P);
			if (!Predicate.isVertexInCircle(s, c)) {
				P.add(s);
				c = SEC(S, P);
			}
		}
		return c;
	}
}