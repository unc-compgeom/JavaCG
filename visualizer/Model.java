package visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import util.CGObservable;
import util.CGObserver;
import algorithms.GrahmScan;
import algorithms.JarvisMarch;
import algorithms.Melkman;
import cg.PointComponent;
import cg.PointSet;
import cg.PointSetComponent;
import cg.Polygon;
import cg.PolygonComponent;

public class Model implements CGObservable {
	private Dimension size;
	private Polygon polygon;
	private PointSet pointSet;
	private List<Thread> runningAlgorithms;
	private boolean isPolygonActive; // either draw polygon or point set
	private List<CGObserver> observers;

	public Model() {
		isPolygonActive = false;
		pointSet = new PointSetComponent();
		polygon = new PolygonComponent();
		runningAlgorithms = new LinkedList<Thread>();
		observers = new Vector<CGObserver>();
	}

	public void makeRandomPoints() {
		int width = size.width;
		int height = size.height;
		Random Ayn = new Random();
		for (int i = 0; i < 64; i++) {
			pointSet.addPoint(new PointComponent(Ayn.nextInt(width), Ayn
					.nextInt(height)));
		}
		notifyObservers();
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public void reset() {
		pointSet.removeAll(pointSet);
		for (Thread t : runningAlgorithms) {
			t.stop();
			// there must be a better way
		}
		runningAlgorithms.removeAll(runningAlgorithms);
		notifyObservers();
	}

	public void runMelkman() {
		final PointSet poly = (isPolygonActive) ? polygon : pointSet;
		final Polygon ch = new PolygonComponent();
		ch.addObservers(observers);
		ch.setColor(Color.RED);
		runningAlgorithms.add(new Thread(new Runnable() {
			@Override
			public void run() {
				Melkman.doMelkman(poly, ch);
			}
		}));
		runningAlgorithms.get(runningAlgorithms.size() - 1).start();
		if (isPolygonActive) {
			polygon = new PolygonComponent();
		} else {
			pointSet = new PointSetComponent();
		}

	}

	public void runJarvis() {
		final PointSet poly = (isPolygonActive) ? polygon : pointSet;
		final Polygon ch = new PolygonComponent();
		ch.addObservers(observers);
		ch.setColor(Color.RED);
		runningAlgorithms.add(new Thread(new Runnable() {
			@Override
			public void run() {
				JarvisMarch.doJarvisMarch(poly, ch);
			}
		}));
		runningAlgorithms.get(runningAlgorithms.size() - 1).start();
		if (isPolygonActive) {
			polygon = new PolygonComponent();
		} else {
			pointSet = new PointSetComponent();
		}

	}

	public void runGrahm() {
		final PointSet poly = (isPolygonActive) ? polygon : pointSet;
		final Polygon ch = new PolygonComponent();
		ch.addObservers(observers);
		ch.setColor(Color.RED);
		runningAlgorithms.add(new Thread(new Runnable() {
			@Override
			public void run() {
				GrahmScan.doGrahmScan(poly, ch);
			}
		}));
		runningAlgorithms.get(runningAlgorithms.size() - 1).start();
		if (isPolygonActive) {
			polygon = new PolygonComponent();
		} else {
			pointSet = new PointSetComponent();
		}
	}

	public void addPoint(PointComponent p) {
		if (isPolygonActive) {
			polygon.addPoint(p);
		} else {
			pointSet.addPoint(p);
		}
		notifyObservers();
	}

	private void notifyObservers(CGObservable d) {
		for (CGObserver o : observers) {
			o.update(d);
		}
	}

	private void notifyObservers() {
		if (isPolygonActive) {
			notifyObservers(polygon);
		} else {
			notifyObservers(pointSet);
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

	public void enablePolygon() {
		isPolygonActive = true;
	}

	public void enablePoints() {
		isPolygonActive = false;
	}

}
