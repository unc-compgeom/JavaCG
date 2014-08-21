package algorithms;

import util.ColorSpecial;
import util.Drawable;
import cg.PointSet;

/**
 * This enum lists all algorithms that are implemented in this project. To add
 * an algorithm to the list of algorithms in the view, add it to this enum.
 */
public enum Algorithm {
	BENTLEY_FAUST_PREPARATA("Bentley Faust Preparata"), DELAUNAY_TRIANGULATION(
			"Delaunay Triangulation"), GRAHAM_SCAN("Graham scan"), JARVIS_MARCH(
			"Jarvis march"), MELKMAN("Melkman"), MONOTONE_CHAIN(
			"Monotone chain"), SEGMENT_INTERSECTION("Segment intersection"), QUICKHULL(
			"Quickhull"), WELZL("Welzl");

	/**
	 * Run an algorithm, given a set of points and an algorithm type.
	 *
	 * @param algorithm
	 *            the algorithm type
	 * @param points
	 *            the data set
	 * @return the algorithm's result
	 */
	public static Drawable runAlgorithm(final Algorithm algorithm,
			final PointSet points) {
		points.setColor(ColorSpecial.PASTEL_GRAY);
		switch (algorithm) {
		case BENTLEY_FAUST_PREPARATA:
			return BentleyFaustPreparata.findConvexHull(points);
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
		case SEGMENT_INTERSECTION:
			return SegmentIntersection.findIntersection(points);
		case WELZL:
			return Welzl.findSmallestEnclosingCircle(points);
		default:
			System.out
					.println(algorithm
							+ " is not yet implemented in algorithms/Algorithm>runAlgorithm()");
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
	 * @param s
	 *            the name of the algorithm in normal text
	 */
	private Algorithm(final String s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return s;
	}
}
