package visualizer;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cg.GeometryManager;

class Main {
	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// handle exception
		}
		// set up and start the view;
		GeometryManager.addObserver(new View(
				new ViewController(new ViewModel())));
	}
}
