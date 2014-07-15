package algorithms;

import java.awt.Color;

import predicates.Predicate;
import util.ColorSpecial;
import cg.Circle;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;

class Welzl {
	public static Circle findSmallestEnclosingCircle(PointSet points) {
		Circle result;
		PointSet s = GeometryManager.newPointSet(points);
		result = SEC(s, GeometryManager.newPointSet());
		result.setColor(ColorSpecial.PASTEL_GREEN);
		return result;
	}

	private static Circle SEC(PointSet points, PointSet set) {
		Circle c;
		PointSet S = GeometryManager.newPointSet(points);
		PointSet P = GeometryManager.newPointSet(set);
		P.setColor(Color.PINK);

		if (P.size() == 3 || S.size() == 0) {
			// make circle from result;
			c = GeometryManager.newCircle(P);
			c.setColor(Color.PINK);
		} else {
			Point s = S.remove(0);
			if (s.getColor() == null) {
				s.setColor(ColorSpecial.SMALT);
			} else {
				s.setColor(s.getColor().darker());
			}
			c = SEC(S, P);
			if (!Predicate.isPointInCircle(s, c)) {
				P.add(s);
				c.setColor(new Color(220, 220, 220));
				Circle tmp = c;
				c = SEC(S, P);
				GeometryManager.destroy(tmp);
			}
			s.setColor(null);
		}

		GeometryManager.destroy(S);
		GeometryManager.destroy(P);
		return c;
	}
}