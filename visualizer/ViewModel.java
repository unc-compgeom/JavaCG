package visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.SwingWorker;

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

public class ViewModel {
	private boolean isPolygonActive; // either draw polygon or point set
	private VertexSet pointSet;
	private Polygon polygon;
	private Dimension size;

	public ViewModel() {
		this.isPolygonActive = false;
		this.pointSet = GeometryManager.newVertexSet();
		this.polygon = GeometryManager.newPolygon();
	}

	public void addPoint(Vertex v) {
		if (isPolygonActive) {
			polygon.addNoDelay(v);
		} else {
			pointSet.addNoDelay(v);
		}
	}

	public void enablePoints() {
		isPolygonActive = false;
	}

	public void enablePolygon() {
		isPolygonActive = true;
	}

	public Dimension getSize() {
		return size;
	}

	public void makeRandomPoints() {
		int width = size.width;
		int height = size.height;
		Random Ayn = new Random();
		for (int i = 0; i < 64; i++) {
			pointSet.addNoDelay(GeometryManager.newVertex(Ayn.nextInt(width),
					Ayn.nextInt(height)));
		}
	}

	public void makeRandomPolygon() {
		int width = size.width;
		int height = size.height;
		Random Ayn = new Random();
		for (int i = 0; i < 64; i++) {
			polygon.addNoDelay(GeometryManager.newVertex(Ayn.nextInt(width),
					Ayn.nextInt(height)));
		}
	}

	public void reset() {
		GeometryManager.removeAllGeometry();
		pointSet = GeometryManager.newVertexSet();
		polygon = GeometryManager.newPolygon();
	}

	public void runAlgorithm(final Algorithm algorithm) {
		final VertexSet points = (isPolygonActive) ? polygon : pointSet;
		final Polygon hull = GeometryManager.newPolygon();
		hull.setColor(Color.RED);
		SwingWorker<Void, Void> w = (new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					switch (algorithm) {
					case BENTLEY_FAUST_PREPARATA:
						BentleyFaustPreparata.findConvexHull(points, hull);
						break;
					case CALIPERS:
						Calipers.doCalipers(points, hull);
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
					System.out.println("Exception while running " + algorithm);
					e.printStackTrace();
				}
				return null;
			}
		});
		w.execute();
		if (isPolygonActive) {
			polygon = GeometryManager.newPolygon();
		} else {
			pointSet = GeometryManager.newVertexSet();
		}
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
}
