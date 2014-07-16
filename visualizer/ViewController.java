package visualizer;

import algorithms.Algorithm;
import cg.GeometryManager;
import visualizer.ViewModel.InsertionMode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ViewController implements ActionListener {
	private final ViewModel model;

	public ViewController(ViewModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		switch (e.getActionCommand()) {
			case "enablePolygon":
				model.enablePolygon(true);
				break;
			case "enablePoints":
				model.enablePolygon(false);
				break;
			case "makeCircle":
				model.setInsertionMode(InsertionMode.CIRCLE);
				break;
			case "makeLine":
				model.setInsertionMode(InsertionMode.LINE);
				break;
			case "makePoint":
				model.setInsertionMode(InsertionMode.INCREMENTAL);
				break;
			case "makeRandom":
				model.setInsertionMode(InsertionMode.RANDOM);
				break;
			case "mouseMoved": {
				Point p = (Point) e.getSource();
				int x = p.x;
				int y = p.y;
				model.preview(x, y);
				break;
			}
			case "reset":
				model.reset();
				break;
			case "setLarge":
				GeometryManager.setSmallLarge();
				break;
			case "speedSet":
				GeometryManager.setDelay((int) (Math.pow(10.0,
						e.getModifiers() / 10.0) - 1.0));
				break;
			case "viewWidthResized":
				model.setWidth(((Number) e.getSource()).intValue());
				break;
			case "viewHeightResized":
				model.setHeight(((Number) e.getSource()).intValue());
				break;
			case "viewAddPoint": {
				java.awt.Point p = (java.awt.Point) e.getSource();
				model.draw(p.x, p.y);
				break;
			}
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
}
