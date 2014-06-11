package visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.SwingWorker;

import util.CGObservable;
import util.CGObserver;
import algorithms.Algorithm;
import algorithms.Chan;
import algorithms.Calipers;
import algorithms.GrahmScan;
import algorithms.JarvisMarch;
import algorithms.Melkman;
import algorithms.MonotoneChain;
import algorithms.QuickHull;
import cg.VertexComponent;
import cg.VertexSet;
import cg.VertexSetComponent;
import cg.Polygon;
import cg.PolygonComponent;

public class ViewModel implements CGObservable, CGObserver {
	private Dimension size;
	private Polygon polygon;
	private VertexSet pointSet;
	private boolean isPolygonActive; // either draw polygon or point set
	private List<CGObserver> observers;
	private List<VertexSet> drawnObjects;
	private int delay = 250; // animation delay in ms.

	public ViewModel() {
		isPolygonActive = false;
		pointSet = new VertexSetComponent();
		polygon = new PolygonComponent();
		drawnObjects = new LinkedList<VertexSet>();
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
		for (VertexSet o : drawnObjects) {
			o.removeAllObservers();
			o.removeAll(o);
		}
		drawnObjects = null;
		drawnObjects = new LinkedList<VertexSet>();
		pointSet = null;
		pointSet = new VertexSetComponent();
		polygon = null;
		polygon = new PolygonComponent();
		notifyObservers(0);
	}

	public void runAlgorithm(final Algorithm algorithm) {
		final VertexSet points = (isPolygonActive) ? polygon : pointSet;
		final Polygon hull = new PolygonComponent();
		drawnObjects.add(hull);
		hull.addObserver(this);
		hull.setColor(Color.RED);
		(new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				switch (algorithm) {
				case CALIPERS:
					Polygon diamline = new PolygonComponent();
					drawnObjects.add(diamline);
					diamline.addObservers(hull.getObservers());
					diamline.setColor(Color.GREEN);
					Calipers.doCalipers(points, hull, diamline);
					break;
				case CHAN:
					Chan.doChan(points, hull);
					break;
				case GRAHM_SCAN:
					GrahmScan.doGrahmScan(points, hull);
					break;
				case JARVIS_MARCH:
					JarvisMarch.doJarvisMarch(points, hull);
					break;
				case MELKMAN:
					Melkman.doMelkman(points, hull);
					break;
				case MONOTONE_CHAIN:
					MonotoneChain.doMonotoneChain(points, hull);
					break;
				case QUICKHULL:
					QuickHull.doQuickHull(points, hull);
					break;
				default:
					System.out
							.println("Algorithm not yet implemented in ViewModel.");
					break;
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
	public void paintComponent(Graphics g) {
	}

	@Override
	public void setColor(Color c) {
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
		notifyObservers(o, delay);
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

}
