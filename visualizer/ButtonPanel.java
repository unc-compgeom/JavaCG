package visualizer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import algorithms.Algorithm;

class ButtonPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 733262376848960367L;

	private final ActionListener a;
	private final Map<String, JComponent> elements;

	public ButtonPanel(ActionListener a) {
		super();
		this.a = a;
		elements = new HashMap<String, JComponent>();
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		// draw polygon/points
		JButton drawPolygon = new JButton("Draw polygon");
		drawPolygon.setActionCommand("viewEnablePolygon");
		drawPolygon.addActionListener(a);
		drawPolygon.addActionListener(this);
		gc.gridx = 0;
		elements.put("drawPolygon", drawPolygon);
		add(drawPolygon, gc);

		// random points
		JButton randomPoints = new JButton("Generate random points");
		randomPoints.setActionCommand("viewMakeRandomPoints");
		randomPoints.addActionListener(a);
		gc.gridx++;
		elements.put("randomPoints", randomPoints);
		add(randomPoints, gc);

		// algorithms
		JComboBox<Algorithm> algorithms = new JComboBox<Algorithm>(
				Algorithm.values());
		gc.gridx++;
		elements.put("algorithms", algorithms);
		add(algorithms, gc);

		// run
		JButton run = new JButton("Run");
		run.setActionCommand("run");
		run.addActionListener(this);
		gc.gridx++;
		elements.put("run", run);
		add(run, gc);

		// reset
		JButton reset = new JButton("Reset");
		reset.setActionCommand("reset");
		reset.addActionListener(a);
		gc.gridx++;
		elements.put("reset", reset);
		add(reset, gc);

		// delay field
		JTextField delay = new JTextField("Delay (ns)");
		delay.setActionCommand("delaySet");
		delay.addActionListener(a);
		delay.setMinimumSize(new Dimension(200, 25));
		gc.gridx++;
		elements.put("delay", delay);
		add(delay, gc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "viewEnablePolygon": {
			JButton drawPolygon = (JButton) elements.get("drawPolygon");
			JButton randomPoints = (JButton) elements.get("randomPoints");
			drawPolygon.setText("Draw points");
			drawPolygon.setActionCommand("viewEnablePoints");
			randomPoints.setActionCommand("viewMakeRandomPolygon");
			randomPoints.setText("Generate Random Polygon");
			break;
		}
		case "viewEnablePoints": {
			JButton drawPolygon = (JButton) elements.get("drawPolygon");
			JButton randomPoints = (JButton) elements.get("randomPoints");
			drawPolygon.setText("Draw polygon");
			drawPolygon.setActionCommand("viewEnablePolygon");
			randomPoints.setActionCommand("viewMakeRandomPoints");
			randomPoints.setText("Generate Random Points");
			break;
		}
		case "run":
			@SuppressWarnings("unchecked")
			JComboBox<Algorithm> algorithms = (JComboBox<Algorithm>) elements
					.get("algorithms");
			a.actionPerformed(new ActionEvent(this,
					ActionEvent.ACTION_PERFORMED, Algorithm.values()[algorithms
							.getSelectedIndex()].toString()));
			break;
		default:
			System.out.println("Unhandled action in ButtonPanel: "
					+ e.getActionCommand());
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 25);
	}
}