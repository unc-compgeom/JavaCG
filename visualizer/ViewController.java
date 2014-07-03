package visualizer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.SwingWorker;

import visualizer.ViewModel.InsertionMode;
import algorithms.Algorithm;
import cg.GeometryManager;

public class ViewController implements ActionListener {
	private final ViewModel model;

	public ViewController(ViewModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		SwingWorker<Void, Void> w = (new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				switch (e.getActionCommand()) {
				case "enablePolygon":
					model.setInsertionMode(InsertionMode.POLYGON_INCREMENTAL);
					break;
				case "enablePoints":
					model.setInsertionMode(InsertionMode.POINTS_INCREMENTAL);
					break;
				case "makeCirclePoints":
					model.setInsertionMode(InsertionMode.POINTS_CIRCLE);
					break;
				case "makeCirclePolygon":
					model.setInsertionMode(InsertionMode.POLYGON_CIRCLE);
					break;
				case "makeLinePoints":
					model.setInsertionMode(InsertionMode.POINTS_LINE);
					break;
				case "makeLinePolygon":
					model.setInsertionMode(InsertionMode.POLYGON_LINE);
					break;
				case "makePoints":
					model.setInsertionMode(InsertionMode.POINTS_RANDOM);
					break;
				case "makePolygon":
					model.setInsertionMode(InsertionMode.POLYGON_RANDOM);
					break;
				case "mouseMoved":
					MouseEvent me = (MouseEvent) e.getSource();
					int x = me.getX();
					int y = me.getY();
					model.preview(x, y);
					break;
				case "reset":
					model.reset();
					break;
				case "setLarge":
					GeometryManager.setSmallLarge();
					break;
				case "speedSet":
					GeometryManager.setDelay((int) (Math.pow(10,
							e.getModifiers() / 10.0) - 1));
					break;
				case "viewResized":
					model.setSize((Dimension) e.getSource());
					break;
				case "viewAddPoint":
					java.awt.Point p = (java.awt.Point) e.getSource();
					model.draw(p.x, p.y);
					break;
				default:
					try {
						model.runAlgorithm(Algorithm.fromString(e
								.getActionCommand()));
					} catch (IllegalArgumentException exc) {
						System.out.println("Unhandled action in Controller: "
								+ e.getActionCommand() + " " + e.getSource());
					}
					break;
				}
				return null;
			}
		});
		w.execute();
	}
}
