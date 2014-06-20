package algorithms;

import java.awt.Color;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.GeometryManager;
import cg.Polygon;
import cg.Vertex;
import cg.VertexSet;

public class Calipers {

	/*
	 * Support lines still intersect convex hull if not final answer
	 * 
	 * Rotating caliper algorithm input Vertex set in 2D. Output is maximum
	 * diameter of the convex hull and the minimum width of the convex hull.
	 */

	public static void doCalipers(VertexSet Vertexs, Polygon hull,
			Polygon diamline) {
		Polygon support1 = GeometryManager.getPolygon();
		Polygon support2 = GeometryManager.getPolygon();
		support1.setColor(Color.BLUE);
		support2.setColor(Color.BLUE);
		hull = getConvexHull(Vertexs, hull);
		int i = 0;
		int j = 1;
		double diam = -1;
		// double width = Double.POSITIVE_INFINITY;
		double tempdiam;
		while (cwIntersection(0, j, hull)) {
			j++;
		}
		while (j != 0) {
			Vertex pi = hull.get(i);
			Vertex pj = hull.get(j);
			tempdiam = Math.pow((pi.getX() - pj.getX()), 2)
					+ Math.pow((pi.getY() - pj.getY()), 2);
			if (tempdiam > diam) {
				diam = tempdiam;
				if (!diamline.isEmpty()) {
					diamline.remove();
					diamline.remove();
				}
				if (!support1.isEmpty()) {
					support1.remove();
					support1.remove();
				}
				if (!support2.isEmpty()) {
					support2.remove();
					support2.remove();
				}
				diamline.add(pi);
				diamline.add(pj);
				Vertex vector = perpendicular(i, j, hull);
				support1.add(GeometryManager.getVertex(
						pi.getX() + vector.getX(), pi.getY() + vector.getY()));
				support1.add(GeometryManager.getVertex(
						pi.getX() - vector.getX(), pi.getY() - vector.getY()));
				support2.add(GeometryManager.getVertex(
						pj.getX() + vector.getX(), pj.getY() + vector.getY()));
				support2.add(GeometryManager.getVertex(
						pj.getX() - vector.getX(), pj.getY() - vector.getY()));
			}
			if (cwIntersection(i + 1, j, hull)) {
				j = (j + 1) % hull.size();
			} else {
				i++;
			}
		}
	}

	/** Uses Jarvis march to compute convex hull */
	private static Polygon getConvexHull(VertexSet points, Polygon hull) {
		/*
		 * Vertex min = leftmost(pl); do { hull.add(min); min = pl.get(min !=
		 * pl.get(0) ? 0 : 1); for (int i = 1; i < pl.size(); i++) { if
		 * (Predicate.isPointLeftOfLine(hull.getLast(), min,pl.get(i))) { min =
		 * pl.get(i); } } } while (!min.equals(hull.get(0))); return hull;
		 */
		Vertex min = CG.findSmallestYX(points);
		Vertex p, q = min;
		int i = 0;
		do {
			hull.addLast(q);
			p = hull.get(i);
			q = nextHullPoint(points, p);
			i++;
		} while (!q.equals(min));
		return hull;
	}

	private static Vertex nextHullPoint(VertexSet points, Vertex p) {
		Vertex q = p;
		for (int i = 0; i < points.size(); i++) {
			Vertex r = points.get(i);
			Orientation o = Predicate.findOrientation(p, q, r);
			if (o == Orientation.COUNTERCLOCKWISE
					|| (o == Orientation.COLINEAR && CG.distSquared(p, r) > CG
							.distSquared(p, q))) {
				q = r;
			}
		}
		return q;
	}

	/*
	 * private static Vertex leftmost(VertexSet p) { Vertex leftmost = p.get(0);
	 * for (int i = 1; i < p.size(); i++) { if (leftmost.getX() >
	 * p.get(i).getX()) { leftmost = p.get(i); } } return leftmost; }
	 */

	private static boolean cwIntersection(int i, int j, Polygon hull) {
		int tempi = i == 0 ? hull.size() - 1 : i - 1;
		Vertex Pa = GeometryManager.getVertex(hull.get(tempi).getX(),
				hull.get(tempi).getY());
		Vertex Pb = GeometryManager.getVertex(hull.get(i).getX(), hull.get(i)
				.getY());
		Vertex Pd = GeometryManager.getVertex(hull.get((j + 1) % hull.size())
				.getX(), hull.get((j + 1) % hull.size()).getY());
		Pd.setX(Pd.getX() - hull.get(j).getX() + Pb.getX());
		Pd.setY(Pd.getY() - hull.get(j).getY() + Pb.getY());
		return Predicate.findOrientation(Pa, Pb, Pd) == Predicate.Orientation.CLOCKWISE;
	}

	private static Vertex perpendicular(int i, int j, Polygon hull) {
		double[][] perp1 = { { 0.0, -1.0 }, { 1.0, 0.0 } };
		double[][] vector1 = { { 0 }, { 0 } };
		double[][] vector2 = { { 0 }, { 0 } };
		double[][] vector3 = { { 0 }, { 0 } };
		double[][] result1 = { { 0 }, { 0 } };
		if (cwIntersection(i + 1, j, hull)) {
			i = i == 0 ? hull.size() - 1 : i - 1;
		} else {
			j = j == 0 ? hull.size() - 1 : j - 1;
		}
		vector1[0][0] = hull.get((i + 1) % hull.size()).getX()
				- hull.get(i).getX();
		vector1[1][0] = hull.get((i + 1) % hull.size()).getY()
				- hull.get(i).getY();
		double dist = Math.sqrt(Math.pow((hull.get((i + 1) % hull.size())
				.getX() - hull.get(i).getX()), 2)
				+ Math.pow((hull.get((i + 1) % hull.size()).getY() - hull
						.get(i).getY()), 2));
		vector1[0][0] = vector1[0][0] / dist;
		vector1[1][0] = vector1[1][0] / dist;
		vector2[0][0] = hull.get((j + 1) % hull.size()).getX()
				- hull.get(j).getX();
		vector2[1][0] = hull.get((j + 1) % hull.size()).getY()
				- hull.get(j).getY();
		dist = Math.sqrt(Math.pow(
				(hull.get((j + 1) % hull.size()).getX() - hull.get(j).getX()),
				2)
				+ Math.pow((hull.get((j + 1) % hull.size()).getY() - hull
						.get(j).getY()), 2));
		vector2[0][0] = vector2[0][0] / dist;
		vector2[1][0] = vector2[1][0] / dist;
		vector3[0][0] = (vector1[0][0] + vector2[0][0]) / 2;
		vector3[1][0] = (vector1[1][0] + vector2[1][0]) / 2;
		for (int a = 0; a < 2; a++) {
			for (int b = 0; b < 1; b++) {
				for (int c = 0; c < 2; c++) {
					result1[a][b] += perp1[a][c] * vector3[c][b];
				}
			}
		}
		return GeometryManager.getVertex((int) (result1[0][0] * 90),
				(int) (result1[1][0] * 90));
	}

}