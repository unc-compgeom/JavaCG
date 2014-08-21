package algorithms;

import java.util.stream.Collectors;

import javafx.scene.paint.Color;
import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import util.ColorSpecial;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

class MonotoneChain {

	/**
	 * Computes the convex hull using the monotone chain algorithm
	 *
	 * @param points
	 *            the point set
	 * @return a {@link cg.Polygon} representing the convex hull
	 */
	public static Polygon findConvexHull(final PointSet points) {
		CG.lexicographicalSort(points);
		// lower hull
		final Polygon lower = GeometryManager.newPolygon();
		lower.setColor(Color.GREEN);
		for (int i = 0; i < points.size(); i++) {
			final Point p = points.get(i);
			while (lower.size() > 1
					&& Predicate.findOrientation(p, lower.getSecondToLast(),
							lower.getLast()) != Orientation.COUNTERCLOCKWISE) {
				lower.removeLast();
			}
			lower.addLast(points.get(i));
		}
		// upper hull
		final Polygon upper = GeometryManager.newPolygon();
		upper.setColor(Color.BLUE);
		for (int i = points.size() - 1; i >= 0; i--) {
			final Point p = points.get(i);
			while (upper.size() > 1
					&& Predicate.findOrientation(p, upper.getSecondToLast(),
							upper.getLast()) != Orientation.COUNTERCLOCKWISE) {
				upper.removeLast();
			}
			upper.addLast(points.get(i));
		}
		// join
		final Polygon hull = GeometryManager.newPolygon();
		hull.setColor(ColorSpecial.PASTEL_GREEN);
		hull.addAll(lower.stream().collect(Collectors.toList()));
		hull.addAll(upper.stream().collect(Collectors.toList()));
		// clean up
		GeometryManager.destroy(upper);
		GeometryManager.destroy(lower);
		return hull;
	}
}
