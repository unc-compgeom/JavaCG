package visualizer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cg.PointComponent;

public class Controller implements ActionListener {
	ViewModel model;

	public Controller(ViewModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		// model actions
		case "viewMakeRandomPoints":
			model.makeRandomPoints();
			break;
		case "viewMakeRandomPolygon":
			model.makeRandomPolygon();
			break;
		case "runGrahm":
			model.runGrahm();
			break;
		case "runJarvis":
			model.runJarvis();
			break;
		case "runMelkman":
			model.runMelkman();
			break;
		case "runCalipers":
			model.runCalipers();
			break;
		case "reset":
			model.reset();
			break;
		// view actions
		case "viewResized":
			model.setSize((Dimension) e.getSource());
			break;
		case "viewAddPoint":
			java.awt.Point p = (java.awt.Point) e.getSource();
			model.addPoint(new PointComponent(p.x, p.y));
			break;
		case "viewEnablePolygon":
			model.enablePolygon();
			break;
		case "viewEnablePoints":
			model.enablePoints();
			break;
		default:
			System.out
					.println("Default action handler; do nothing for action: "
							+ e.getActionCommand());
			break;
		}
	}

}
