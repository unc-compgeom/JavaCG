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

import algorithms.Chan;
import algorithms.Calipers;
import algorithms.GrahmScan;
import algorithms.JarvisMarch;
import algorithms.Melkman;
import cg.PointComponent;
import cg.PointSet;
import cg.PointSetComponent;
import cg.Polygon;
import cg.PolygonComponent;

public class ViewModel implements CGObservable, CGObserver {
	private Dimension size;
	private Polygon polygon;
	private PointSet pointSet;
	private List<Thread> runningAlgorithms;
	private boolean isPolygonActive; // either draw polygon or point set
	private List<CGObserver> observers;
	private List<PointSet> drawnObjects;
	private int delay = 250; // animation delay in ms.

	public ViewModel() {
		isPolygonActive = false;
		pointSet = new PointSetComponent();
		polygon = new PolygonComponent();
		drawnObjects = new LinkedList<PointSet>();
		drawnObjects.add(pointSet);
		drawnObjects.add(polygon);
		runningAlgorithms = new LinkedList<Thread>();
		observers = new Vector<CGObserver>();
	}

	public void makeRandomPoints() {
		int width = size.width;
		int height = size.height;
		Random Ayn = new Random();
		for (int i = 0; i < 64; i++) {
			pointSet.add(new PointComponent(Ayn.nextInt(width), Ayn
					.nextInt(height)));
		}
		notifyObservers(0);
	}

	public void makeRandomPolygon() {
		int width = size.width;
		int height = size.height;
		Random Ayn = new Random();
		for (int i = 0; i < 64; i++) {
			polygon.add(new PointComponent(Ayn.nextInt(width), Ayn
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
		for (PointSet o : drawnObjects) {
			o.removeAllObservers();
			o.removeAll(o);
		}
		for (Thread t : runningAlgorithms) {
			t.stop();
			// there must be a better way
		}
		runningAlgorithms.removeAll(runningAlgorithms);
		notifyObservers(0);
	}

	public void runMelkman() {
		final PointSet poly = (isPolygonActive) ? polygon : pointSet;
		final Polygon ch = new PolygonComponent();
		drawnObjects.add(ch);
		ch.addObserver(this);
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
			drawnObjects.add(polygon);
		} else {
			pointSet = new PointSetComponent();
			drawnObjects.add(pointSet);
		}

	}

	public void runJarvis() {
		final PointSet poly = (isPolygonActive) ? polygon : pointSet;
		final Polygon ch = new PolygonComponent();
		drawnObjects.add(ch);
		ch.addObserver(this);
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
			drawnObjects.add(polygon);
		} else {
			pointSet = new PointSetComponent();
			drawnObjects.add(pointSet);
		}

	}

	public void runGrahm() {
		final PointSet poly = (isPolygonActive) ? polygon : pointSet;
		final Polygon ch = new PolygonComponent();
		drawnObjects.add(ch);
		ch.addObserver(this);
		ch.setColor(Color.RED);
		(new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				GrahmScan.doGrahmScan(poly, ch);
				return null;
			}
			
		}).execute();
//		runningAlgorithms.add(new Thread(new Runnable() {
//			@Override
//			public void run() {
//				GrahmScan.doGrahmScan(poly, ch);
//			}
//		}));
//		runningAlgorithms.get(runningAlgorithms.size() - 1).start();
		if (isPolygonActive) {
			polygon = new PolygonComponent();
			drawnObjects.add(polygon);
		} else {
			pointSet = new PointSetComponent();
			drawnObjects.add(pointSet);
		}
	}

	public void runChan() {
		final PointSet poly = (isPolygonActive) ? polygon : pointSet;
		final Polygon ch = new PolygonComponent();
		drawnObjects.add(ch);
		ch.addObserver(this);
		ch.setColor(Color.RED);
		runningAlgorithms.add(new Thread(new Runnable() {
			@Override
			public void run() {
				Chan.doChan(poly, ch);
			}
		}));
		runningAlgorithms.get(runningAlgorithms.size() - 1).start();
		if (isPolygonActive) {
			polygon = new PolygonComponent();
			drawnObjects.add(polygon);
		} else {
			pointSet = new PointSetComponent();
			drawnObjects.add(pointSet);
		}
	}
	
	public void runCalipers(){
		final PointSet poly = (isPolygonActive) ? polygon : pointSet;
		final Polygon ch = new PolygonComponent();
		final Polygon diamline = new PolygonComponent();
		final Polygon support1 = new PolygonComponent();
		final Polygon support2 = new PolygonComponent();
		drawnObjects.add(ch);
		ch.addObservers(observers);
		ch.setColor(Color.RED);
		drawnObjects.add(diamline);
		diamline.addObservers(observers);
		diamline.setColor(Color.GREEN);
		drawnObjects.add(support1);
		support1.addObservers(observers);
		support1.setColor(Color.BLUE);
		drawnObjects.add(support2);
		support2.addObservers(observers);
		support2.setColor(Color.BLUE);
		runningAlgorithms.add(new Thread(new Runnable(){
			@Override
			public void run(){
				Calipers.doCalipers(poly, ch, diamline, support1, support2);
			}
		}));
		runningAlgorithms.get(runningAlgorithms.size()-1).start();
		if (isPolygonActive) {
			polygon = new PolygonComponent();
			drawnObjects.add(polygon);
		} else {
			pointSet = new PointSetComponent();
			drawnObjects.add(pointSet);
		}
	}

	public void addPoint(PointComponent p) {
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

	private void notifyObservers() {
		notifyObservers(delay);
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
