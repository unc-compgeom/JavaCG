package visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

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
import algorithms.SmallestEnclosingCircle;
import cg.Circle;
import cg.CircleComponent;
import cg.Drawable;
import cg.Polygon;
import cg.PolygonComponent;
import cg.VertexComponent;
import cg.VertexSet;
import cg.VertexSetComponent;

public class ViewModel implements CGObservable, CGObserver {
	private static final int LARGESIZE = 5;
	private static final int SMALLSIZE = 1;
	private Dimension size;
	private Polygon polygon;
	private VertexSet pointSet;
	private boolean isPolygonActive; // either draw polygon or point set
	private final List<CGObserver> observers;
	private List<Drawable> drawnObjects;
	private int delay = 250; // animation delay in ms.
	private boolean isLarge;

	public ViewModel() {
		isPolygonActive = false;
		isLarge = false;
		pointSet = new VertexSetComponent();
		polygon = new PolygonComponent();
		pointSet.addObserver(this);
		polygon.addObserver(this);
		drawnObjects = new LinkedList<Drawable>();
		drawnObjects.add(pointSet);
		drawnObjects.add(polygon);
		observers = new Vector<CGObserver>();
	}

	public void makeRandomPoints() {
		int width = size.width;
		int height = size.height;
		Random Ayn = new Random();
		for (int i = 0; i < 64; i++) {
			pointSet.add(new VertexComponent(Ayn.nextInt(width), Ayn
					.nextInt(height)));
		}
		notifyObservers(0);
	}

	public void makeRandomPolygon() {
		int width = size.width;
		int height = size.height;
		Random Ayn = new Random();
		for (int i = 0; i < 64; i++) {
			polygon.add(new VertexComponent(Ayn.nextInt(width), Ayn
					.nextInt(height)));
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
		for (Drawable o : drawnObjects) {
			o.removeAllObservers();
		}
		drawnObjects = null;
		drawnObjects = new LinkedList<Drawable>();
		pointSet = null;
		pointSet = new VertexSetComponent();
		polygon = null;
		polygon = new PolygonComponent();
		pointSet.addObserver(this);
		polygon.addObserver(this);
		polygon.setSize((isLarge) ? LARGESIZE : SMALLSIZE);
		pointSet.setSize((isLarge) ? LARGESIZE : SMALLSIZE);
		drawnObjects.add(pointSet);
		drawnObjects.add(polygon);
		notifyObservers(0);
	}

	public void runAlgorithm(final Algorithm algorithm) {
		final VertexSet points = (isPolygonActive) ? polygon : pointSet;
		final Polygon hull = new PolygonComponent();
		hull.setSize((isLarge) ? LARGESIZE : SMALLSIZE);
		drawnObjects.add(hull);
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
						Polygon diamline = new PolygonComponent();
						drawnObjects.add(diamline);
						diamline.addObservers(hull.getObservers());
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
					case SMALLEST_ENCLOSING_CIRCLE:
						Circle c = new CircleComponent(1, 1, 1);
						c.addObservers(hull.getObservers());
						drawnObjects.add(c);
						SmallestEnclosingCircle.findSmallestEnclosingCircle(
								points, c);
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
			polygon = new PolygonComponent();
			drawnObjects.add(polygon);
		} else {
			pointSet = new VertexSetComponent();
			drawnObjects.add(pointSet);
		}
	}

	public void addPoint(VertexComponent p) {
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
		for (CGObserver o : observers) {
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
		observers.add(o);
	}

	@Override
	public void addObservers(List<CGObserver> observers) {
		this.observers.addAll(observers);
	}

	@Override
	public void removeObserver(CGObserver o) {
		observers.remove(o);
	}

	@Override
	public void removeAllObservers() {
		observers.removeAll(observers);
	}

	@Override
	public List<CGObserver> getObservers() {
		return observers;
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
			for (CGObservable d : drawnObjects) {
				((Drawable) d).setSize(LARGESIZE);
			}
			isLarge = true;
		} else {
			for (CGObservable d : drawnObjects) {
				((Drawable) d).setSize(SMALLSIZE);
			}
			isLarge = false;
		}
		notifyObservers(0);
	}
}
