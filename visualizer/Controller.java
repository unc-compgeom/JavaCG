package visualizer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import algorithms.Algorithm;
import cg.VertexComponent;

public class Controller implements ActionListener {
	private ViewModel model;
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
		case "delaySet":
			try {
				model.setDelay(Integer.parseInt(((JTextField) e.getSource())
						.getText()));
				((JTextField) e.getSource()).selectAll();
			} catch (NumberFormatException exc) {
				((JTextField) e.getSource()).setText("250");
				((JTextField) e.getSource()).selectAll();
			}
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
