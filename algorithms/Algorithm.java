package algorithms;

import cg.PointSet;
import util.ColorSpecial;
import util.Drawable;

/**
 * This enum lists all algorithms that are implemented in this project.
 * To add an algorithm to the list of algorithms in the view, add it to this enum.
 */
public enum Algorithm {
	BENTLEY_FAUST_PREPARATA("Bentley Faust Preparata"), CALIPERS("Calipers"), CHAN(
			"Chan"), DELAUNAY_TRIANGULATION("Delaunay Triangulation"), GRAHAM_SCAN(
			"Graham scan"), JARVIS_MARCH("Jarvis march"), MELKMAN("Melkman"), MONOTONE_CHAIN(
			"Monotone chain"), QUICKHULL("Quickhull"), VORONI_DIAGRAM(
			"Voroni Diagram"), WELZL("Welzl");

	/**
	 * Run an algorithm, given a set of points and an algorithm type.
	 *
	 * @param algorithm the algorithm type
	 * @param points    the data set
	 * @return the algorithm's result
	 */
	public static Drawable runAlgorithm(Algorithm algorithm, PointSet points) {
		points.setColor(ColorSpecial.PASTEL_GRAY);
		switch (algorithm) {
			case BENTLEY_FAUST_PREPARATA:
				return BentleyFaustPreparata.findConvexHull(points);
			case CALIPERS:
				return Calipers.doCalipers(points);
			case CHAN:
				return Chan.findConvexHull(points);
			case DELAUNAY_TRIANGULATION:
				return DelaunayTriangulation.triangulate(points);
			case GRAHAM_SCAN:
				return GrahamScan.findConvexHull(points);
			case JARVIS_MARCH:
				return JarvisMarch.findConvexHull(points);
			case MELKMAN:
				return Melkman.findConvexHull(points);
			case MONOTONE_CHAIN:
				return MonotoneChain.findConvexHull(points);
			case QUICKHULL:
				return QuickHull.findConvexHull(points);
			case VORONI_DIAGRAM:
				return VoronoiDiagram.makeVoronoiDiagram(points);
			case WELZL:
				return Welzl.findSmallestEnclosingCircle(points);
			default:
				System.out.println(algorithm
						+ " is not yet implemented in algorithms/Algorithm>runAlgorithm()" );
				return null;
		}
	}

	/**
	 * The algorithm's name.
	 */
	private final String s;

	/**
	 * Constructor for Algorithm.
	 *
	 * @param s the name of the algorithm in normal text
	 */
	private Algorithm(String s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return s;
	}
}
