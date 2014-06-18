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

	private static Circle SEC(VertexSet S, VertexSet P) {
		if (P.size() == 3 || S.size() == 0) {
			// make circle from result;
			return new CircleComponent(P);
		}
		Vertex s = S.remove(0);
		Circle c = SEC(S, P);
		if (!Predicate.isVertexInCircle(s, c)) {
			P.add(s);
			c = SEC(S, P);
		}
		return c;
	}
}