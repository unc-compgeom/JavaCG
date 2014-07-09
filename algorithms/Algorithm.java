package algorithms;

import util.ColorSpecial;
import cg.Drawable;
import cg.PointSet;

public enum Algorithm {
	BENTLEY_FAUST_PREPARATA("Bentley Faust Preparata"), CALIPERS("Calipers"), CHAN(
			"Chan"), DELAUNAY_TRIANGULATION("Delaunay Triangulation"), GRAHM_SCAN(
			"Grahm scan"), JARVIS_MARCH("Jarvis march"), MELKMAN("Melkman"), MONOTONE_CHAIN(
			"Monotone chain"), QUICKHULL("Quickhull"), WELZL("Welzl");

	private String s;

	private Algorithm(String s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return s;
	}

	public static Algorithm fromString(String s) {
		if (s != null) {
			for (Algorithm a : Algorithm.values()) {
				if (s.equalsIgnoreCase(a.toString())) {
					return a;
				}
			}
		}
		throw new IllegalArgumentException("No constant with text " + s
				+ " found");
	}

	public static Drawable run(Algorithm algorithm, PointSet points) {
		points.setColor(ColorSpecial.PASTEL_GRAY);
		switch (algorithm) {
		case BENTLEY_FAUST_PREPARATA:
			return BentleyFaustPreparata.findConvexHull(points);
		case CALIPERS:
			return Calipers.doCalipers(points);
		case CHAN:
			return Chan.findConvexHull(points);
		case DELAUNAY_TRIANGULATION:
			return DelaunayTriangulation.doDelaunay(points);
		case GRAHM_SCAN:
			return GrahmScan.findConvexHull(points);
		case JARVIS_MARCH:
			return JarvisMarch.findConvexHull(points);
		case MELKMAN:
			return Melkman.findConvexHull(points);
		case MONOTONE_CHAIN:
			return MonotoneChain.findConvexHull(points);
		case QUICKHULL:
			return QuickHull.findConvexHull(points);
		case WELZL:
			return Welzl.findSmallestEnclosingCircle(points);
		default:
			System.out
					.println(algorithm + " not yet implemented in ViewModel.");
			return null;
		}
	}
}
