package visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import algorithms.Algorithm;
import algorithms.BentleyFaustPreparata;
import algorithms.Calipers;
import algorithms.Chan;
import algorithms.DelaunayTriangulation;
import algorithms.GrahmScan;
import algorithms.JarvisMarch;
import algorithms.Melkman;
import algorithms.MonotoneChain;
import algorithms.QuickHull;
import algorithms.Welzl;
import cg.Drawable;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Polygon;
import cg.Segment;

public class ViewModel {
	enum InsertionMode {
		POINTS_CIRCLE(false, true), POINTS_INCREMENTAL(false, false), POINTS_LINE(
				false, true), POINTS_RANDOM(false, false), POLYGON_CIRCLE(true,
				true), POLYGON_INCREMENTAL(true, false), POLYGON_LINE(true,
				true), POLYGON_RANDOM(true, false);
		final boolean isPolygon;
		final boolean isTwoClick;

		InsertionMode(boolean isPolygon, boolean isTwoClick) {
			this.isPolygon = isPolygon;
			this.isTwoClick = isTwoClick;
		}
	}

	private InsertionMode mode;
	private final int NUMPOINTS = 64;
	private PointSet pointSet;
	private Polygon polygon;
	private Point firstPoint;
	private Dimension size;

	public ViewModel() {
		pointSet = GeometryManager.newPointSet();
		polygon = GeometryManager.newPolygon();
		mode = InsertionMode.POINTS_INCREMENTAL;
	}

	public void draw(int x, int y) {
		if (firstPoint != null) {
			int x1 = firstPoint.getX();
			int y1 = firstPoint.getY();
			GeometryManager.destroyGeometry(firstPoint);
			firstPoint = null;
			GeometryManager.destroyGeometry(preview);
			preview = null;
			switch (mode) {
			case POINTS_CIRCLE:
				pointSet.addAll(makeCirclePoints(x1, y1, x, y));
				break;
			case POINTS_LINE:
				pointSet.addAll(makeLinePoints(x1, y1, x, y));
				break;
			case POLYGON_CIRCLE:
				polygon.addAll(makeCirclePolygon(x1, y1, x, y));
				break;
			case POLYGON_LINE:
				polygon.addAll(makeLinePolygon(x1, y1, x, y));
				break;
			default:
				System.out
						.println("Unhandled insertion mode in ViewModel.draw(): "
								+ mode);
				break;
			}
		} else if (mode.isTwoClick) {
			firstPoint = GeometryManager.newPoint(x, y);
			firstPoint.setColor(Color.CYAN);
		} else {
			switch (mode) {
			case POINTS_INCREMENTAL:
				pointSet.addNoDelay(GeometryManager.newPoint(x, y));
				break;
			case POINTS_RANDOM:
				pointSet.addAll(makeRandomPoints());
				break;
			case POLYGON_INCREMENTAL:
				polygon.addNoDelay(GeometryManager.newPoint(x, y));
				break;
			case POLYGON_RANDOM:
				polygon.addAll(makeRandomPolygon());
				break;
			default:
				System.out
						.println("Unhandled insertion mode in ViewModel.draw(): "
								+ mode);
				break;
			}
		}
	}

	public Dimension getSize() {
		return size;
	}

	public PointSet makeCirclePoints(int origX, int origY, int radX, int radY) {
		PointSet pointSet = GeometryManager.newPointSet();
		double radius = Math.hypot(radX - origX, radY - origY);
		for (int i = 0; i < NUMPOINTS; i++) {
			pointSet.addNoDelay(GeometryManager.newPoint(
					origX
							+ (int) (Math.cos(2 * Math.PI * i / NUMPOINTS) * radius),
					origY
							+ (int) (Math.sin(2 * Math.PI * i / NUMPOINTS) * radius)));
		}
		return pointSet;
	}

	public Polygon makeCirclePolygon(int origX, int origY, int radX, int radY) {
		Polygon polygon = GeometryManager.newPolygon();
		double radius = Math.hypot(radX - origX, radY - origY);
		for (int i = 0; i < NUMPOINTS; i++) {
			polygon.addNoDelay(GeometryManager.newPoint(
					origX
							+ (int) (Math.cos(2 * Math.PI * i / NUMPOINTS) * radius),
					origY
							+ (int) (Math.sin(2 * Math.PI * i / NUMPOINTS) * radius)));
		}
		return polygon;
	}

	public PointSet makeLinePoints(int x1, int y1, int x2, int y2) {
		PointSet pointSet = GeometryManager.newPointSet();
		double x = x1;
		double y = y1;
		double dx = (x2 - x1) / (double) NUMPOINTS;
		double dy = (y2 - y1) / (double) NUMPOINTS;
		for (int i = 0; i < NUMPOINTS; i++) {
			pointSet.addNoDelay(GeometryManager.newPoint((int) x, (int) y));
			x += dx;
			y += dy;
		}
		return pointSet;
	}

	public Polygon makeLinePolygon(int x1, int y1, int x2, int y2) {
		Polygon polygon = GeometryManager.newPolygon();
		double x = x1;
		double y = y1;
		double dx = (x2 - x1) / (double) NUMPOINTS;
		double dy = (y2 - y1) / (double) NUMPOINTS;
		for (int i = 0; i < NUMPOINTS; i++) {
			polygon.addNoDelay(GeometryManager.newPoint((int) x, (int) y));
			x += dx;
			y += dy;
		}
		return polygon;
	}

	public PointSet makeRandomPoints() {
		PointSet pointSet = GeometryManager.newPointSet();
		int width = size.width;
		int height = size.height;
		Random Ayn = new Random();
		for (int i = 0; i < NUMPOINTS; i++) {
			pointSet.addNoDelay(GeometryManager.newPoint(Ayn.nextInt(width),
					Ayn.nextInt(height)));
		}
		return pointSet;
	}

	public Polygon makeRandomPolygon() {
		Polygon polygon = GeometryManager.newPolygon();
		int width = size.width;
		int height = size.height;
		Random Ayn = new Random();
		for (int i = 0; i < NUMPOINTS; i++) {
			polygon.addNoDelay(GeometryManager.newPoint(Ayn.nextInt(width),
					Ayn.nextInt(height)));
		}
		return polygon;
	}

	private Drawable preview;

	public void preview(int x, int y) {
		if (firstPoint == null) {
			return;
		}
		GeometryManager.destroyGeometry(preview);
		preview = null;
		switch (mode) {
		case POINTS_CIRCLE:
			preview = makeCirclePoints(firstPoint.getX(), firstPoint.getY(), x,
					y);
			break;
		case POINTS_LINE:
			preview = makeLinePoints(firstPoint.getX(), firstPoint.getY(), x, y);
			break;
		case POLYGON_CIRCLE:
			preview = makeCirclePolygon(firstPoint.getX(), firstPoint.getY(),
					x, y);
			break;
		case POLYGON_LINE:
			preview = makeLinePolygon(firstPoint.getX(), firstPoint.getY(), x,
					y);
			break;
		default:
			System.out
					.println("Unhandled insertion mode in ViewModel.preview(): "
							+ mode);
		}
	}

	public void reset() {
		GeometryManager.removeAllGeometry();
		pointSet = GeometryManager.newPointSet();
		polygon = GeometryManager.newPolygon();
	}

	public void runAlgorithm(final Algorithm algorithm) {
		final PointSet points = (mode.isPolygon) ? polygon : pointSet;
		final Polygon hull = GeometryManager.newPolygon();
		hull.setColor(Color.RED);
		try {
			switch (algorithm) {
			case BENTLEY_FAUST_PREPARATA:
				BentleyFaustPreparata.findConvexHull(points, hull);
				break;
			case CALIPERS:
				Segment width = GeometryManager.newSegment(-1, -1, -1, -1);
				Segment diameter = GeometryManager.newSegment(-1, -1, -1, -1);
				Calipers.doCalipers(points, hull, width, diameter);
				break;
			case CHAN:
				Chan.findConvexHull(points, hull);
				break;
			case DELAUNAY_TRIANGULATION:
				DelaunayTriangulation.doDelaunay(points);
				break;
			case GRAHM_SCAN:
				GrahmScan.findConvexHull(points, hull);
				break;
			case JARVIS_MARCH:
				JarvisMarch.findConvexHull(points, hull);
				break;
			case MELKMAN:
				Melkman.findConvexHull(points, hull);
				break;
			case MONOTONE_CHAIN:
				MonotoneChain.findConvexHull(points, hull);
				break;
			case QUICKHULL:
				QuickHull.findConvexHull(points, hull);
				break;
			case Welzl:
				Welzl.findSmallestEnclosingCircle(points, null);
				break;
			default:
				System.out.println(algorithm
						+ " not yet implemented in ViewModel.");
				break;
			}
		} catch (Exception e) {
			System.out.println("Exception while running " + algorithm);
			e.printStackTrace();
		}
		if (mode.isPolygon) {
			polygon = GeometryManager.newPolygon();
		} else {
			pointSet = GeometryManager.newPointSet();
		}
	}

	/**
	 * Choose how the points should be drawn onto the screen.
	 * 
	 * @param mode
	 */
	public void setInsertionMode(InsertionMode mode) {
		this.mode = mode;
		if (mode == InsertionMode.POINTS_RANDOM) {
			draw(0, 0);
			this.mode = InsertionMode.POINTS_INCREMENTAL;
		} else if (mode == InsertionMode.POLYGON_RANDOM) {
			draw(0, 0);
			this.mode = InsertionMode.POLYGON_INCREMENTAL;
		}
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
}
