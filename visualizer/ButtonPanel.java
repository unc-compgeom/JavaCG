package visualizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import algorithms.Algorithm;

class ButtonPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 733262376848960367L;
	private JButton randomPoints, drawPolygon;
	private JComboBox<Algorithm> algorithms;
	private ActionListener a;

	public ButtonPanel(ActionListener a) {
		super();
		this.a = a;
		drawPolygon = new JButton("Draw polygon");
		drawPolygon.setActionCommand("viewEnablePolygon");
		drawPolygon.addActionListener(a);
		drawPolygon.addActionListener(this);
		add(drawPolygon);
		randomPoints = new JButton("Generate random points");
		randomPoints.setActionCommand("viewMakeRandomPoints");
		randomPoints.addActionListener(a);
		add(randomPoints);
		algorithms = new JComboBox<Algorithm>(Algorithm.values());
		add(algorithms);
		JButton run = new JButton("Run");
		run.setActionCommand("run");
		run.addActionListener(this);
		add(run);
		JButton reset = new JButton("Reset");
		reset.setActionCommand("reset");
		reset.addActionListener(a);
		add(reset);
		JTextField delay = new JTextField("Delay (ns)");
		delay.setActionCommand("delaySet");
		delay.addActionListener(a);
		add(delay);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "viewEnablePolygon": {
			drawPolygon.setText("Draw points");
			drawPolygon.setActionCommand("viewEnablePoints");
			randomPoints.setActionCommand("viewMakeRandomPolygon");
			randomPoints.setText("Generate Random Polygon");
			break;
		}
		case "viewEnablePoints": {
			drawPolygon.setText("Draw polygon");
			drawPolygon.setActionCommand("viewEnablePolygon");
			randomPoints.setActionCommand("viewMakeRandomPoints");
			randomPoints.setText("Generate Random Points");
			break;
		}
		case "run":
			a.actionPerformed(new ActionEvent(this,
					ActionEvent.ACTION_PERFORMED, Algorithm.values()[algorithms
							.getSelectedIndex()].toString()));
			break;
		default:
			System.out.println("Unhandled action in ButtonPanel: " + e.getActionCommand());
		}

	}
}