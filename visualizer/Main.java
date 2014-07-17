package visualizer;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cg.GeometryManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			BorderPane page = FXMLLoader.load(Main.class.getResource("AlgorithmVisualizer.fxml"));
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Algorithm Visualizer 2.0");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.gif")));
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		// set up and start the view;
//		ViewModel m = new ViewModel();
//		ViewController c = new ViewController(m);
//		View v = new View(primaryStage, c);
//		GeometryManager.addObserver(v);
	}
}
