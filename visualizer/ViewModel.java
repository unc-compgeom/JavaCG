package visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import algorithms.Algorithm;
import cg.Drawable;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Polygon;

public class ViewModel {
	enum InsertionMode {
		CIRCLE(true), INCREMENTAL(false), LINE(true), RANDOM(false);
		final boolean isTwoClick;

		InsertionMode(boolean isTwoClick) {
			this.isTwoClick = isTwoClick;
		}
	}

	private boolean polygonEnabled;
	private InsertionMode mode;
	private PointSet pointSet;
	private Polygon polygon;
	private Point firstPoint;
	private Dimension size;

	private Drawable preview;

	public ViewModel() {
		pointSet = GeometryManager.newPointSet();
		polygon = GeometryManager.newPolygon();
		mode = InsertionMode.INCREMENTAL;
		polygonEnabled = false;
	}

	public void draw(int x, int y) {
		if (firstPoint != null) {
			int x1 = (int) firstPoint.getX();
			int y1 = (int) firstPoint.getY();
			GeometryManager.destroy(firstPoint);
			firstPoint = null;
			GeometryManager.destroy(preview);
			preview = null;
			switch (mode) {
			case CIRCLE:
				if (!polygonEnabled) {
					pointSet.addAll(makeCirclePoints(x1, y1, x, y));
				} else {
					polygon.addAll(makeCirclePolygon(x1, y1, x, y));
				}
				break;
			case LINE:
				if (!polygonEnabled) {
					pointSet.addAll(makeLinePoints(x1, y1, x, y));
				} else {
					polygon.addAll(makeLinePolygon(x1, y1, x, y));
				}
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
			case INCREMENTAL:
				if (!polygonEnabled) {
					pointSet.addNoDelay(GeometryManager.newPoint(x, y));
				} else {
					polygon.addNoDelay(GeometryManager.newPoint(x, y));
				}
				break;
			case RANDOM:
				if (!polygonEnabled) {
					float[] points = makeRandomCoordinates();
					for (int i = 0; i < points.length; i += 2) {
						pointSet.addNoDelay(points[i], points[i + 1]);
					}
				} else {
					float[] points = makeRandomCoordinates();
					for (int i = 0; i < points.length; i += 2) {
						polygon.addNoDelay(points[i], points[i + 1]);
					}
				}
				break;
			default:
				System.out
						.println("Unhandled insertion mode in ViewModel.draw(): "
								+ mode);
				break;
			}
		}
	}

	public void enablePolygon(boolean b) {
		polygonEnabled = b;
	}

	public Dimension getSize() {
		return size;
	}

	PointSet makeCirclePoints(int origX, int origY, int radX, int radY) {
		PointSet pointSet = GeometryManager.newPointSet();
		double radius = Math.hypot(radX - origX, radY - origY);
		int numPoints = radius / 4 > 5 ? (int) (radius / 4) : 5;
		for (int i = 0; i < numPoints; i++) {
			pointSet.addNoDelay(GeometryManager.newPoint(
					origX
							+ (int) (Math.cos(2 * Math.PI * i / numPoints) * radius),
					origY
							+ (int) (Math.sin(2 * Math.PI * i / numPoints) * radius)));
		}
		return pointSet;
	}

	Polygon makeCirclePolygon(int origX, int origY, int radX, int radY) {
		Polygon polygon = GeometryManager.newPolygon();
		double radius = Math.hypot(radX - origX, radY - origY);
		int numPoints = radius / 4 > 5 ? (int) (radius / 4) : 5;
		for (int i = 0; i < numPoints; i++) {
			polygon.addNoDelay(GeometryManager.newPoint(
					origX
							+ (int) (Math.cos(2 * Math.PI * i / numPoints) * radius),
					origY
							+ (int) (Math.sin(2 * Math.PI * i / numPoints) * radius)));
		}
		return polygon;
	}

	PointSet makeLinePoints(int x1, int y1, int x2, int y2) {
		PointSet pointSet = GeometryManager.newPointSet();
		double x = x1;
		double y = y1;
		int numPoints = (int) Math.hypot(x2 - x1, y2 - y1) / 16;
		double dx = (x2 - x1) / (double) numPoints;
		double dy = (y2 - y1) / (double) numPoints;
		pointSet.addNoDelay(GeometryManager.newPoint((int) x, (int) y));
		for (int i = 0; i < numPoints; i++) {
			x += dx;
			y += dy;
			pointSet.addNoDelay(GeometryManager.newPoint((int) x, (int) y));
		}
		return pointSet;
	}

	Polygon makeLinePolygon(int x1, int y1, int x2, int y2) {
		Polygon polygon = GeometryManager.newPolygon();
		double x = x1;
		double y = y1;
		int numPoints = (int) Math.hypot(x2 - x1, y2 - y1) / 16;
		double dx = (x2 - x1) / (double) numPoints;
		double dy = (y2 - y1) / (double) numPoints;
		polygon.addNoDelay(GeometryManager.newPoint((int) x, (int) y));
		for (int i = 0; i < numPoints; i++) {
			x += dx;
			y += dy;
			polygon.addNoDelay(GeometryManager.newPoint((int) x, (int) y));
		}
		return polygon;
	}

	float[] makeRandomCoordinates() {
		int width = size.width;
		int height = size.height;
		int numPoints = (int) Math.sqrt(width * height) / 16;
		float[] pointSet = new float[numPoints * 2];
		Random Ayn = new Random();
		for (int i = 0; i < pointSet.length; i += 2) {
			pointSet[i] = Ayn.nextFloat() * width;
			pointSet[i + 1] = Ayn.nextFloat() * height;
		}
		return pointSet;
	}

	public void preview(int x, int y) {
		if (firstPoint == null) {
			return;
		}
		GeometryManager.destroy(preview);
		preview = null;
		switch (mode) {
		case CIRCLE:
			if (!polygonEnabled) {
				preview = makeCirclePoints((int) firstPoint.getX(),
						(int) firstPoint.getY(), x, y);
			} else {
				preview = makeCirclePolygon((int) firstPoint.getX(),
						(int) firstPoint.getY(), x, y);
			}
			break;
		case LINE:
			if (!polygonEnabled) {
				preview = makeLinePoints((int) firstPoint.getX(),
						(int) firstPoint.getY(), x, y);
			} else {
				preview = makeLinePolygon((int) firstPoint.getX(),
						(int) firstPoint.getY(), x, y);
			}
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
		final PointSet points = polygonEnabled ? polygon : pointSet;
		if (polygonEnabled) {
			polygon = GeometryManager.newPolygon();
		} else {
			pointSet = GeometryManager.newPointSet();
		}
		try {
			Algorithm.run(algorithm, points);
		} catch (Exception e) {
			System.out.println("Exception while running " + algorithm);
			e.printStackTrace();
		}
	}

	/**
	 * Choose how the points should be drawn onto the screen.
	 * 
	 * @param mode the insertion mode
	 */
	public void setInsertionMode(InsertionMode mode) {
		if (mode == InsertionMode.RANDOM) {
			InsertionMode tmp = this.mode;
			this.mode = mode;
			draw(0, 0);
			this.mode = tmp;
		} else {
			this.mode = mode;
		}
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
}
