package visualizer;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import util.CGObserver;
import cg.Drawable;

public class View implements CGObserver {
	private final JFrame f;
	private final AlgorithmPanel p;
	private final ButtonPanel b;

	public View(ActionListener a) {
		this.f = new JFrame("Algorithm Visualizer");
		try {
			Image image = ImageIO.read(getClass().getResource("icon.gif"));
			f.setIconImage(image);
		} catch (IOException e) {
			// use default image
		}
		f.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		b = new ButtonPanel(a);
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridheight = 1;
		gc.gridwidth = 1;
		gc.weightx = 1;
		gc.weighty = 0;
		f.add(b, gc);

		p = new AlgorithmPanel(a);
		JPanel algHolder = new JPanel(new BorderLayout());
		algHolder.setBorder(BorderFactory.createTitledBorder(""));
		algHolder.add(p, BorderLayout.CENTER);
		gc.gridy = -1;
		gc.fill = GridBagConstraints.BOTH;
		gc.weighty = 1;
		gc.weightx = 1;
		f.add(algHolder, gc);

		// build menu
		JMenuBar menuBar = new JMenuBar();
		JMenu view = new JMenu("View");
		view.setMnemonic(KeyEvent.VK_V);
		view.getAccessibleContext().setAccessibleDescription(
				"Set the display properties of this visualizer");
		menuBar.add(view);
		JCheckBoxMenuItem large = new JCheckBoxMenuItem("Large");
		large.setMnemonic(KeyEvent.VK_L);
		large.addActionListener(a);
		large.setActionCommand("setLarge");
		view.add(large);
		f.setJMenuBar(menuBar);

		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	@Override
	public void update(Drawable o) {
		p.update(o);
	}
}