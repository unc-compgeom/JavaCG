package algorithms;

import javafx.scene.paint.Color;
import predicates.Predicate;
import util.ColorSpecial;
import cg.Circle;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;

class Welzl {

	/**
	 * Find's the smallest enclosing circle by Welzl's algorithm
	 *
	 * @param points
	 *            the point set
	 * @return a {@link Circle} representing the smallest enclosing circle
	 */
	public static Circle findSmallestEnclosingCircle(final PointSet points) {
		Circle result;
		final PointSet s = GeometryManager.newPointSet(points);
		result = SEC(s, GeometryManager.newPointSet());
		result.setColor(ColorSpecial.PASTEL_GREEN);
		return result;
	}

	private static Circle SEC(final PointSet points, final PointSet set) {
		Circle c;
		final PointSet S = GeometryManager.newPointSet(points);
		final PointSet P = GeometryManager.newPointSet(set);
		P.setColor(Color.PINK);

		if (P.size() == 3 || S.size() == 0) {
			// make circle from result;
			c = GeometryManager.newCircle(P);
			c.setColor(Color.PINK);
		} else {
			final Point s = S.remove(0);
			if (s.getColor() == null) {
				s.setColor(ColorSpecial.SMALT);
			} else {
				s.setColor(s.getColor().darker());
			}
			c = SEC(S, P);
			if (!Predicate.isPointInCircle(s, c)) {
				P.add(s);
				c.setColor(ColorSpecial.LAVENDER_GRAY);
				final Circle tmp = c;
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