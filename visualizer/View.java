package visualizer;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import util.CGObservable;
import util.CGObserver;

public class View implements CGObserver {
	private JFrame f;
	private AlgorithmPanel p;
	private ButtonPanel b;

	public View(Controller c) {
		this.f = new JFrame("Algorithm Visualizer");
		f.setLayout(new BorderLayout());
		p = new AlgorithmPanel(c);
		b = new ButtonPanel(c);
		f.add(b, BorderLayout.NORTH);
		f.add(p, BorderLayout.CENTER);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	@Override
	public void update(CGObservable arg) {
		p.update(arg);
	}
}