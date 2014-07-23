package visualizer;

import algorithms.Algorithm;
import cg.GeometryManager;
import cg.Point;
import cg.PointSet;
import cg.Polygon;
import javafx.scene.paint.Color;
import util.Drawable;

import java.util.Random;

public class ViewModel {
	enum InsertionMode {
		CIRCLE(true), INCREMENTAL(false), LINE(true);
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
	private int width, height;

	private Drawable preview;

	public ViewModel() {
		pointSet = GeometryManager.newPointSet();
		polygon = GeometryManager.newPolygon();
		mode = InsertionMode.INCREMENTAL;
		polygonEnabled = false;
		width = 1000;
		height = 1000;
	}

	public void draw(double x, double y) {
		if (firstPoint != null) {
			int x1 = (int) firstPoint.getX();
			int y1 = (int) firstPoint.getY();
			GeometryManager.destroy(firstPoint);
			firstPoint = null;
			GeometryManager.destroy(preview);
			preview = null;

			float[] coordinates;
			switch (mode) {
				case CIRCLE:
					coordinates = makeCircleCoordinates(x1, y1, x, y);
					break;
				case LINE:
					coordinates = makeLineCoordinates(x1, y1, x, y);
					break;
				default:
					System.out
							.println("Unhandled insertion mode in ViewModel.draw(): "
									+ mode);
					return;
			}
			if (!polygonEnabled) {
				for (int i = 0; i < coordinates.length; i += 2) {
					pointSet.addNoDelay(coordinates[i], coordinates[i + 1]);
				}
			} else {
				for (int i = 0; i < coordinates.length; i += 2) {
					polygon.addNoDelay(coordinates[i], coordinates[i + 1]);
				}
			}
		} else if (mode.isTwoClick) {
			firstPoint = GeometryManager.newPoint((float) x, (float) y);
			firstPoint.setColor(Color.CYAN);
		} else {
			switch (mode) {
				case INCREMENTAL:
					if (!polygonEnabled) {
						pointSet.addNoDelay((float) x, (float) y);
					} else {
						polygon.addNoDelay((float) x, (float) y);
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

	private float[] makeCircleCoordinates(double origX, double origY, double radX, double radY) {
		double radius = Math.hypot(radX - origX, radY - origY);
		int numPoints = radius / 4 > 5 ? (int) (radius / 4) : 5;
		float[] coordinates = new float[2 * numPoints];
		for (int i = 0; i < coordinates.length; i += 2) {
			coordinates[i] = (float) (origX + Math.cos(Math.PI * i / numPoints) * radius);
			coordinates[i + 1] = (float) (origY + Math.sin(Math.PI * i / numPoints) * radius);
		}
		return coordinates;
	}

	private float[] makeLineCoordinates(double x1, double y1, double x2, double y2) {
		float x = (float) x1;
		float y = (float) y1;
		int numPoints = (int) Math.hypot(x2 - x1, y2 - y1) / 16 + 2;
		float[] coordinates = new float[2 * numPoints];
		double dx = (x2 - x1) / (double) numPoints;
		double dy = (y2 - y1) / (double) numPoints;
		for (int i = 0; i < coordinates.length; i += 2) {
			coordinates[i] = x;
			coordinates[i + 1] = y;
			x += dx;
			y += dy;
		}
		return coordinates;
	}

	void makeRandom(float x1, float y1, float x2, float y2) {
		float width = x2 - x1;
		float height = y2 - y1;
		int numPoints = (int) Math.sqrt(width * height) / 16;
		float[] points = new float[numPoints * 2];
		Random Ayn = new Random();
		for (int i = 0; i < points.length; i += 2) {
			points[i] = Ayn.nextFloat() * width + x1;
			points[i + 1] = Ayn.nextFloat() * height + y1;
		}
		if (!polygonEnabled) {
			for (int i = 0; i < points.length; i += 2) {
				pointSet.addNoDelay(points[i], points[i + 1]);
			}
		} else {
			for (int i = 0; i < points.length; i += 2) {
				polygon.addNoDelay(points[i], points[i + 1]);
			}
		}
	}

	public void preview(double x, double y) {
		if (firstPoint == null) {
			return;
		}
		GeometryManager.destroy(preview);
		preview = null;

		float[] coordinates;
		switch (mode) {
			case CIRCLE:
				coordinates = makeCircleCoordinates(firstPoint.getX(), firstPoint.getY(), x, y);
				break;
			case LINE:
				coordinates = makeLineCoordinates(firstPoint.getX(), firstPoint.getY(), x, y);
				break;
			default:
				System.out
						.println("Unhandled insertion mode in ViewModel.preview(): "
								+ mode);
				return;
		}

		if (!polygonEnabled) {
			PointSet tmp = GeometryManager.newPointSet();
			for (int i = 0; i < coordinates.length; i += 2) {
				tmp.addNoDelay(coordinates[i], coordinates[i + 1]);
			}
			preview = tmp;
		} else {
			Polygon tmp = GeometryManager.newPolygon();
			for (int i = 0; i < coordinates.length; i += 2) {
				tmp.addNoDelay(coordinates[i], coordinates[i + 1]);
			}
			preview = tmp;
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
		this.mode = mode;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
