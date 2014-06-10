package visualizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

class ButtonPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 733262376848960367L;
	private JButton randomPoints, drawPolygon;
	public ButtonPanel(ActionListener a) {
		super();
		drawPolygon = new JButton("Draw polygon");
		drawPolygon.setActionCommand("viewEnablePolygon");
		drawPolygon.addActionListener(a);
		drawPolygon.addActionListener(this);
		add(drawPolygon);
		randomPoints = new JButton("Generate random points");
		randomPoints.setActionCommand("viewMakeRandomPoints");
		randomPoints.addActionListener(a);
		add(randomPoints);
		JButton runGrahm = new JButton("Grahm Scan");
		runGrahm.setActionCommand("runGrahm");
		runGrahm.addActionListener(a);
		add(runGrahm);
		JButton runJarvis = new JButton("Jarvis March");
		runJarvis.setActionCommand("runJarvis");
		runJarvis.addActionListener(a);
		add(runJarvis);
		JButton runMelkman = new JButton("Melkman");
		runMelkman.setActionCommand("runMelkman");
		runMelkman.addActionListener(a);
		add(runMelkman);
		JButton runChan = new JButton("Chan");
		runChan.setActionCommand("runChan");
		runChan.addActionListener(a);
		add(runChan);
		JButton runCalipers = new JButton("Calipers");
		runCalipers.setActionCommand("runCalipers");
		runCalipers.addActionListener(a);
		add(runCalipers);
		JButton runMonotoneChain = new JButton("Monotone Chain");
		runMonotoneChain.setActionCommand("runMonotoneChain");
		runMonotoneChain.addActionListener(a);
		add(runMonotoneChain);
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
		}

	}
}