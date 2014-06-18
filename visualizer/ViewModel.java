package visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import util.CGObservable;
import util.CGObserver;
import algorithms.Algorithm;
import algorithms.BentleyFaustPreparata;
import algorithms.Calipers;
import algorithms.Chan;
import algorithms.GrahmScan;
import algorithms.JarvisMarch;
import algorithms.Melkman;
import algorithms.MonotoneChain;
import algorithms.QuickHull;
import algorithms.Welzl;
import cg.GeometryManager;
import cg.Polygon;
import cg.Vertex;
import cg.VertexSet;

public class ViewModel implements CGObservable, CGObserver {
	private static final int LARGESIZE = 5;
	private static final int SMALLSIZE = 1;
	private Dimension size;
	private Polygon polygon;
	private VertexSet pointSet;
	private boolean isPolygonActive; // either draw polygon or point set

	private int delay = 250; // animation delay in ms.
	private boolean isLarge;

	public ViewModel() {
		GeometryManager.setSize(1);
		isPolygonActive = false;
		isLarge = false;
		pointSet = GeometryManager.getVertexSet();
		polygon = GeometryManager.getPolygon();
	}

	public void makeRandomPoints() {
		int width = size.width;
		int height = size.height;
		Random Ayn = new Random();
		for (int i = 0; i < 64; i++) {
			pointSet.add(GeometryManager.getVertex(Ayn.nextInt(width),
					Ayn.nextInt(height)));
		}
		notifyObservers(0);
	}

	public void makeRandomPolygon() {
		int width = size.width;
		int height = size.height;
		Random Ayn = new Random();
		for (int i = 0; i < 64; i++) {
			polygon.add(GeometryManager.getVertex(Ayn.nextInt(width),
					Ayn.nextInt(height)));
		}
		notifyObservers(0);
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public void reset() {
		GeometryManager.removeAllGeometry();
		pointSet = null;
		pointSet = GeometryManager.getVertexSet();
		polygon = null;
		polygon = GeometryManager.getPolygon();
		notifyObservers(0);
	}

	public void runAlgorithm(final Algorithm algorithm) {
		final VertexSet points = (isPolygonActive) ? polygon : pointSet;
		final Polygon hull = GeometryManager.getPolygon();
		hull.setSize((isLarge) ? LARGESIZE : SMALLSIZE);
		hull.addObserver(this);
		hull.setColor(Color.RED);
		(new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					switch (algorithm) {
					case BENTLEY_FAUST_PREPARATA:
						BentleyFaustPreparata.findConvexHull(points, hull);
						break;
					case CALIPERS:
						Polygon diamline = GeometryManager.getPolygon();
						diamline.setColor(Color.GREEN);
						Calipers.doCalipers(points, hull, diamline);
						break;
					case CHAN:
						Chan.findConvexHull(points, hull);
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
						System.out
								.println("Algorithm not yet implemented in ViewModel.");
						break;
					}
				} catch (Exception e) {
					System.out.println("Exception while running algorithm "
							+ algorithm);
					e.printStackTrace();
				}
				return null;
			}
		}).execute();
		if (isPolygonActive) {
			polygon = GeometryManager.getPolygon();
		} else {
			pointSet = GeometryManager.getVertexSet();
		}
	}

	public void addPoint(Vertex p) {
		if (isPolygonActive) {
			polygon.add(p);
		} else {
			pointSet.add(p);
		}
		notifyObservers(0);
	}

	public void enablePolygon() {
		isPolygonActive = true;
	}

	public void enablePoints() {
		isPolygonActive = false;
	}

	private void notifyObservers(CGObservable d, int delay) {
		for (CGObserver o : GeometryManager.getObservers()) {
			o.update(d, delay);
		}
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
		}
	}

	private void notifyObservers(int delay) {
		if (isPolygonActive) {
			notifyObservers(polygon, delay);
		} else {
			notifyObservers(pointSet, delay);
		}
	}

	@Override
	public void addObserver(CGObserver o) {
		GeometryManager.addObserver(o);
	}

	@Override
	public void addObservers(List<CGObserver> observers) {
		for (CGObserver o : observers) {
			GeometryManager.addObserver(o);
		}
	}

	@Override
	public void removeObserver(CGObserver o) {
		GeometryManager.removeObserver(o);
	}

	@Override
	public void removeAllObservers() {
		GeometryManager.removeAllObservers();
	}

	@Override
	public List<CGObserver> getObservers() {
		return GeometryManager.getObservers();
	}

	@Override
	public void update(CGObservable o, int delay) {
		notifyObservers(o, delay);
	}

	@Override
	public void update(CGObservable o) {
		if (o.equals(pointSet) || o.equals(polygon)) {
			notifyObservers(o, 0);
		} else {
			notifyObservers(o, delay);
		}
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void setLarge() {
		if (!isLarge) {
			GeometryManager.setSize(LARGESIZE);
			isLarge = true;
		} else {
			GeometryManager.setSize(SMALLSIZE);
			isLarge = false;
		}
		notifyObservers(0);
	}
}
