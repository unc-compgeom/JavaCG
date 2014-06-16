package visualizer;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import util.CGObservable;
import util.CGObserver;

public class View implements CGObserver {
	private final JFrame f;
	private final AlgorithmPanel p;
	private final ButtonPanel b;

	public View(Controller c) {
		this.f = new JFrame("Algorithm Visualizer");
		try {
			Image image = ImageIO.read(getClass().getResource("icon.gif"));
			f.setIconImage(image);

		} catch (IOException e) {
			// use default image
		}
		f.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		b = new ButtonPanel(c);
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridheight = 1;
		gc.gridwidth = 1;
		f.add(b, gc);

		p = new AlgorithmPanel(c);
		JPanel algHolder = new JPanel(new BorderLayout());
		algHolder.setBorder(BorderFactory.createTitledBorder(""));
		algHolder.add(p, BorderLayout.CENTER);
		gc.gridy = -1;
		gc.fill = GridBagConstraints.BOTH;
		gc.weighty = 1;
		f.add(algHolder, gc);

		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void reset() {
		p.reset();
	}

	@Override
	public void update(CGObservable arg, int delay) {
		p.update(arg, delay);
	}

	@Override
	public void update(CGObservable o) {
		p.update(o);
	}
}