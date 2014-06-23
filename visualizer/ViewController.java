package visualizer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import algorithms.Algorithm;
import cg.GeometryManager;

class ViewController implements ActionListener {
	private final ViewModel model;

	public ViewController(ViewModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				switch (e.getActionCommand()) {
				case "viewMakeRandomPoints":
					model.makeRandomPoints();
					break;
				case "viewMakeRandomPolygon":
					model.makeRandomPolygon();
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
					model.addPoint(GeometryManager.newPoint(p.x, p.y));
					break;
				case "viewEnablePolygon":
					model.enablePolygon();
					break;
				case "viewEnablePoints":
					model.enablePoints();
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
			}
		});
	}

}
