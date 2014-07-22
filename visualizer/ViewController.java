/**
 * Sample Skeleton for 'AlgorithmVisualizer.fxml' Controller Class
 */

package visualizer;

import algorithms.Algorithm;
import cg.GeometryManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import util.CGObserver;
import util.Drawable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewController implements CGObserver {
	private ViewModel model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="algorithms"
	private ComboBox<Algorithm> algorithms; // Value injected by FXMLLoader

	@FXML // fx:id="canvas"
	private Canvas canvas; // Value injected by FXMLLoader

	@FXML // fx:id="pointsPolygon"
	private ToggleGroup pointsPolygon; // Value injected by FXMLLoader

	@FXML // fx:id="drawMode"
	private ToggleGroup drawMode; // Value injected by FXMLLoader

	@FXML // fx:id="speed"
	private Slider speed; // Value injected by FXMLLoader

	@FXML
	void mouseClickFired(MouseEvent event) {
		Task t = new Task() {
			@Override
			protected Void call() throws Exception {
				model.draw(event.getX(), event.getY());
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void mouseMoveFired(MouseEvent event) {
		Task t = new Task() {
			@Override
			protected Void call() throws Exception {
				model.preview(event.getX(), event.getY());
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void pointsFired(ActionEvent event) {
		model.enablePolygon(false);
	}

	@FXML
	void polygonFired(ActionEvent event) {
		model.enablePolygon(true);
	}

	@FXML
	void pointFired(ActionEvent event) {
		model.setInsertionMode(ViewModel.InsertionMode.INCREMENTAL);
	}

	@FXML
	void circleFired(ActionEvent event) {
		model.setInsertionMode(ViewModel.InsertionMode.CIRCLE);
	}

	@FXML
	void lineFired(ActionEvent event) {
		model.setInsertionMode(ViewModel.InsertionMode.LINE);
	}

	@FXML
	void randomFired(ActionEvent event) {
		model.setInsertionMode(ViewModel.InsertionMode.RANDOM);
	}

	@FXML
	void runFired(ActionEvent event) {
		Task t = new Task() {
			@Override
			protected Void call() throws Exception {
				model.runAlgorithm(algorithms.getSelectionModel().getSelectedItem());
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void resetFired(ActionEvent event) {
		model.reset();
	}

	@FXML
	void sliderDragFired() {
		GeometryManager.setDelay((int) (Math.exp(speed.getValue()*8))-1);
	}

	@FXML
	void sizeFired(ActionEvent event) {
		GeometryManager.sizeToggle();
	}


	@FXML
		// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert algorithms != null : "fx:id=\"algorithms\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";
		assert canvas != null : "fx:id=\"canvas\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";
		assert pointsPolygon != null : "fx:id=\"pointsPolygon\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";
		assert drawMode != null : "fx:id=\"drawMode\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";
		assert speed != null : "fx:id=\"speed\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";

		algorithms.getItems().addAll(Algorithm.values());
		model = new ViewModel();
		GeometryManager.addObserver(this);
		sliderDragFired();
	}

	@Override
	public void tellToDraw() {
		Platform.runLater(
				new Runnable() {
					@Override
					public void run() {
						GraphicsContext gc = canvas.getGraphicsContext2D();
						gc.clearRect(0, 0, 1000, 1000);
						List<Drawable> geometry = GeometryManager.getAllGeometry();
						synchronized (geometry) {
							for (Drawable d : geometry) {
								d.paint(gc);
							}
						}
					}
				}
		);
	}
}
