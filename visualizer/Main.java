package visualizer;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cg.GeometryManager;

public class Main {
	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// use default L&F
		}
		// set up and start the view;
		ViewModel m = new ViewModel();
		ViewController c = new ViewController(m);
		View v = new View(c);
		GeometryManager.addObserver(v);
	}

}
