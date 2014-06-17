package visualizer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import algorithms.Algorithm;
import cg.VertexComponent;

public class Controller implements ActionListener {
	private final ViewModel model;
	private View view;

	public Controller(ViewModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "viewMakeRandomPoints":
			model.makeRandomPoints();
			break;
		case "viewMakeRandomPolygon":
			model.makeRandomPolygon();
			break;
		case "reset":
			model.reset();
			view.reset();
			break;
		case "setLarge":
			model.setLarge();
			break;
		case "speedSet":
			model.setDelay((int) (Math.pow(10, e.getModifiers() / 10.0) - 1));
			break;
		case "viewResized":
			model.setSize((Dimension) e.getSource());
			break;
		case "viewAddPoint":
			java.awt.Point p = (java.awt.Point) e.getSource();
			model.addPoint(new VertexComponent(p.x, p.y));
			break;
		case "viewEnablePolygon":
			model.enablePolygon();
			break;
		case "viewEnablePoints":
			model.enablePoints();
			break;
		default:
			try {
				model.runAlgorithm(Algorithm.fromString(e.getActionCommand()));
			} catch (IllegalArgumentException exc) {
				System.out.println("Unhandled action in Controller: "
						+ e.getActionCommand() + " " + e.getSource());
			}
			break;
		}
	}

	public void addView(View v) {
		this.view = v;
	}

}
