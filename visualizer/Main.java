package visualizer;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cg.GeometryManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// set up and start the view;
		ViewModel m = new ViewModel();
		ViewController c = new ViewController(m);
		View v = new View(primaryStage, c);
		GeometryManager.addObserver(v);
	}
}
