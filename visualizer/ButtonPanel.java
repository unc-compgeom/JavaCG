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
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import algorithms.Algorithm;
import cg.GeometryManager;

class ButtonPanel extends JPanel implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 733262376848960367L;
	private final ActionListener a;
	private final Map<String, JComponent> elements;

	public ButtonPanel(ActionListener a) {
		super();
		this.a = a;
		elements = new HashMap<String, JComponent>();
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		// build buttons
		// draw polygon/points
		JButton enablePolygon = new JButton("Polygon");
		enablePolygon.setActionCommand("enablePolygon");
		enablePolygon.addActionListener(a);
		enablePolygon.addActionListener(this);
		gc.gridx = 0;
		elements.put("enablePolygon", enablePolygon);
		add(enablePolygon, gc);

		// single point
		JButton makePoint = new JButton("p");
		makePoint.setActionCommand("makePoint");
		makePoint.addActionListener(a);
		makePoint.addActionListener(this);
		makePoint.setEnabled(false); // initialized
		gc.gridx++;
		elements.put("makePoint", makePoint);
		add(makePoint, gc);

		// circle
		JButton makeCircle = new JButton("c");
		makeCircle.setActionCommand("makeCircle");
		makeCircle.addActionListener(a);
		makeCircle.addActionListener(this);
		gc.gridx++;
		elements.put("makeCircle", makeCircle);
		add(makeCircle, gc);

		// line
		JButton makeLine = new JButton("l");
		makeLine.setActionCommand("makeLine");
		makeLine.addActionListener(a);
		makeLine.addActionListener(this);
		gc.gridx++;
		elements.put("makeLine", makeLine);
		add(makeLine, gc);

		// random points
		JButton makeRandom = new JButton("r");
		makeRandom.setActionCommand("makeRandom");
		makeRandom.addActionListener(a);
		gc.gridx++;
		elements.put("makeRandom", makeRandom);
		add(makeRandom, gc);

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

		// speed slider
		int initialValue = (int) (Math.log10(GeometryManager.getDelay() + 1) * 10);
		JSlider speed = new JSlider(SwingConstants.HORIZONTAL, 0, 35,
				initialValue);
		speed.setMajorTickSpacing(5);
		speed.setMinorTickSpacing(1);
		speed.setPaintTicks(true);
		speed.addChangeListener(this);
		speed.setMinimumSize(new Dimension(160, 40));
		gc.gridx++;
		elements.put("speed", speed);
		add(speed, gc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "enablePolygon": {
			JButton enablePolygon = (JButton) elements.get("enablePolygon");
			enablePolygon.setText("  Points ");
			enablePolygon.setActionCommand("enablePoints");
			break;
		}
		case "enablePoints": {
			JButton enablePolygon = (JButton) elements.get("enablePolygon");
			enablePolygon.setText("Polygon");
			enablePolygon.setActionCommand("enablePolygon");
			break;
		}
		case "makeCircle": {
			JButton makeCircle = (JButton) elements.get("makeCircle");
			makeCircle.setEnabled(false);
			JButton makeLine = (JButton) elements.get("makeLine");
			makeLine.setEnabled(true);
			JButton makePoint = (JButton) elements.get("makePoint");
			makePoint.setEnabled(true);
			break;
		}
		case "makeLine": {
			JButton makeCircle = (JButton) elements.get("makeCircle");
			makeCircle.setEnabled(true);
			JButton makeLine = (JButton) elements.get("makeLine");
			makeLine.setEnabled(false);
			JButton makePoint = (JButton) elements.get("makePoint");
			makePoint.setEnabled(true);
			break;
		}
		case "makePoint": {
			JButton makeCircle = (JButton) elements.get("makeCircle");
			makeCircle.setEnabled(true);
			JButton makeLine = (JButton) elements.get("makeLine");
			makeLine.setEnabled(true);
			JButton makePoint = (JButton) elements.get("makePoint");
			makePoint.setEnabled(false);
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
		return new Dimension(700, 55);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(elements.get("speed"))) {
			final int s = ((JSlider) e.getSource()).getValue();
			new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					a.actionPerformed(new ActionEvent(this,
							ActionEvent.ACTION_PERFORMED, "speedSet", s));
					return null;
				}
			}.execute();
		}
	}
}