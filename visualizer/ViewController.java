/**
 * Sample Skeleton for 'AlgorithmVisualizer.fxml' Controller Class
 */

package visualizer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import util.CGObserver;
import util.Drawable;
import algorithms.Algorithm;
import cg.GeometryManager;

public class ViewController implements CGObserver {
	private ViewModel model;

	@FXML
	// ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML
	// URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML
	// fx:id="algorithms"
	private ComboBox<Algorithm> algorithms; // Value injected by FXMLLoader

	@FXML
	// fx:id="canvas"
	private Canvas canvas; // Value injected by FXMLLoader

	@FXML
	// fx:id="pointsPolygon"
	private ToggleGroup pointsPolygon; // Value injected by FXMLLoader

	@FXML
	// fx:id="progress"
	private ProgressBar progressBar; // Value injected by FXMLLoader

	@FXML
	// fx:id="drawMode"
	private ToggleGroup drawMode; // Value injected by FXMLLoader

	@FXML
	// fx:id="scrollPane"
	private ScrollPane scrollPane; // Value injected by FXMLLoader

	@FXML
	// fx:id="speed"
	private Slider speed; // Value injected by FXMLLoader

	@FXML
	void circleFired() {
		Task<?> t = new Task<Object>() {
			@Override
			protected Void call() throws Exception {
				model.setInsertionMode(ViewModel.InsertionMode.CIRCLE);
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert algorithms != null : "fx:id=\"algorithms\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";
		assert canvas != null : "fx:id=\"canvas\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";
		assert pointsPolygon != null : "fx:id=\"pointsPolygon\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";
		assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";
		assert drawMode != null : "fx:id=\"drawMode\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";
		assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";
		assert speed != null : "fx:id=\"speed\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";

		algorithms.getItems().addAll(Algorithm.values());
		model = new ViewModel();
		GeometryManager.addObserver(this);
		sliderDragFired();
	}

	@FXML
	void lineFired() {
		Task<?> t = new Task<Object>() {
			@Override
			protected Void call() throws Exception {
				model.setInsertionMode(ViewModel.InsertionMode.LINE);
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void mouseClickFired(MouseEvent event) {
		Task<?> t = new Task<Object>() {
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
		Task<?> t = new Task<Object>() {
			@Override
			protected Void call() throws Exception {
				model.preview(event.getX(), event.getY());
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void pointFired() {
		Task<?> t = new Task<Object>() {
			@Override
			protected Void call() throws Exception {
				model.setInsertionMode(ViewModel.InsertionMode.INCREMENTAL);
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void pointsFired() {
		Task<?> t = new Task<Object>() {
			@Override
			protected Void call() throws Exception {
				model.enablePolygon(false);
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void polygonFired() {
		Task<?> t = new Task<Object>() {
			@Override
			protected Void call() throws Exception {
				model.enablePolygon(true);
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void randomFired() {
		Task<?> t = new Task<Object>() {
			@Override
			protected Void call() throws Exception {
				model.makeRandom(10, 10, 790, 590);
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void resetFired() {
		Task<?> t = new Task<Object>() {
			@Override
			protected Void call() throws Exception {
				model.reset();
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void runFired() {
		Task<?> t = new Task<Object>() {
			@Override
			protected Void call() throws Exception {
				updateProgress(-1, -1);
				Algorithm algorithm = algorithms.getSelectionModel()
						.getSelectedItem();
				if (algorithm != null) {
					model.runAlgorithm(algorithm);
				}
				updateProgress(0, 0);
				return null;
			}
		};
		progressBar.progressProperty().bind(t.progressProperty());
		new Thread(t).start();
	}

	@FXML
	void sizeFired() {
		Task<?> t = new Task<Object>() {
			@Override
			protected Void call() throws Exception {
				GeometryManager.sizeToggle();
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void sliderDragFired() {
		Task<?> t = new Task<Object>() {
			@Override
			protected Void call() throws Exception {
				GeometryManager
						.setDelay((int) (Math.exp(speed.getValue() * 8)) - 1);
				return null;
			}
		};
		new Thread(t).start();
	}

	@Override
	public void tellToDraw() {
		Platform.runLater(new Task<Void>() {
			@Override
			public Void call() {
				GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.clearRect(0, 0, 800, 600);

				gc.setStroke(Color.BLACK);
				gc.setFill(Color.BLACK);
				gc.setLineWidth(1);
				List<Drawable> geometry = GeometryManager.getAllGeometry();
				synchronized (geometry) {
					geometry.stream().forEach(d -> d.paint(gc));
				}
				return null;
			}
		});
	}
}
