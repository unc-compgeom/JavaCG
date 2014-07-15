package visualizer;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cg.GeometryManager;

public class Main {
	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		// set up and start the view;
		GeometryManager.addObserver(new View(
				new ViewController(new ViewModel())));
	}
}
