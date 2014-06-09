package visualizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

class ButtonPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 733262376848960367L;

	public ButtonPanel(ActionListener a) {
		super();
		JButton drawPolygon = new JButton("Draw polygon");
		drawPolygon.setActionCommand("viewEnablePolygon");
		drawPolygon.addActionListener(a);
		drawPolygon.addActionListener(this);
		add(drawPolygon);
		JButton randomPoints = new JButton("Generate random points");
		randomPoints.setActionCommand("makeRandomPoints");
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
		JButton runCalipers = new JButton("Calipers");
		runCalipers.setActionCommand("runCalipers");
		runCalipers.addActionListener(a);
		add(runCalipers);
		JButton reset = new JButton("Reset");
		reset.setActionCommand("reset");
		reset.addActionListener(a);
		add(reset);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "viewEnablePolygon": {
			JButton b = (JButton) e.getSource();
			b.setText(" Draw points ");
			b.setActionCommand("viewEnablePoints");
			break;
		}
		case "viewEnablePoints": {
			JButton b = (JButton) e.getSource();
			b.setText("Draw Polygon");
			b.setActionCommand("viewEnablePolygon");
			break;
		}
		}

	}
}