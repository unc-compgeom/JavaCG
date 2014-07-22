/**
 * Sample Skeleton for 'AlgorithmVisualizer.fxml' Controller Class
 */

package visualizer;

import algorithms.Algorithm;
import cg.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import util.CGObserver;
import util.Drawable;

import java.net.URL;
import java.util.*;

public class ViewController implements CGObserver {
	private ViewModel model;
	private GraphicsContext gc;

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
	// fx:id="drawMode"
	private ToggleGroup drawMode; // Value injected by FXMLLoader

	@FXML
	// fx:id="speed"
	private Slider speed; // Value injected by FXMLLoader

	@FXML
	void mouseClickFired(final MouseEvent event) {
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
	void mouseMoveFired(final MouseEvent event) {
		Task<Void> t = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				model.preview(event.getX(), event.getY());
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void pointsFired() {
		Task<Void> t = new Task<Void>() {
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
		Task<Void> t = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				model.enablePolygon(true);
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void pointFired() {
		Task<Void> t = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				model.setInsertionMode(ViewModel.InsertionMode.INCREMENTAL);
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void circleFired() {
		Task<Void> t = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				model.setInsertionMode(ViewModel.InsertionMode.CIRCLE);
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void lineFired() {
		Task<Void> t = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				model.setInsertionMode(ViewModel.InsertionMode.LINE);
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void randomFired() {
		Task<Void> t = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				model.setInsertionMode(ViewModel.InsertionMode.RANDOM);
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void runFired() {
		Task t = new Task() {
			@Override
			protected Void call() throws Exception {
				model.runAlgorithm(algorithms.getSelectionModel()
						.getSelectedItem());
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void resetFired() {
		Task<Void> t = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				model.reset();
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void sliderDragFired() {
		Task<Void> t = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				GeometryManager.setDelay((int) (speed.getValue() * 1000));
				return null;
			}
		};
		new Thread(t).start();
	}

	@FXML
	void sizeFired() {
		Task<Void> t = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				GeometryManager.sizeToggle();
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
		assert drawMode != null : "fx:id=\"drawMode\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";
		assert speed != null : "fx:id=\"speed\" was not injected: check your FXML file 'AlgorithmVisualizer.fxml'.";

		algorithms.getItems().addAll(Algorithm.values());
		model = new ViewModel();
		GeometryManager.addObserver(this);
		gc = canvas.getGraphicsContext2D();
	}

	@Override
	public void update(final Drawable d, boolean isInsertion) {
		new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				if (isInsertion) {
					synchronized (d) {
						try {
							d.wait();
						} catch (InterruptedException ignored) {
						}
						Platform.runLater(() -> paintStuff(d));
					}
				} else {
					Platform.runLater(() -> gc.clearRect(0, 0, 1000, 1000));
					for (Drawable drawable : GeometryManager.getAllGeometry()) {
						synchronized (drawable) {
							while (!drawable.isReady()) {
								try {
									drawable.wait();
								} catch (InterruptedException ignored) {
								}
							}
							Platform.runLater(() -> paintStuff(drawable));
						}
					}
				}
				return null;
			}

			private void paintStuff(Drawable d) {
				if (d == null || d.isInvisible()) {
					return;
				}
				Paint oldFill = gc.getFill();
				Paint oldStroke = gc.getStroke();
				double oldWidth = gc.getLineWidth();
				if (d.getColor() != null) {
					gc.setFill(d.getColor());
					gc.setStroke(d.getColor());
				}
				gc.setLineWidth(GeometryManager.getSize());

				if (d instanceof Point) {
					paintPoint((Point) d);
				} else if (d instanceof Polygon) {
					paintPolygon((Polygon) d);
				} else if (d instanceof PointSet) {
					paintPointSet((PointSet) d);
				} else if (d instanceof QuadEdge) {
					paintQuadEdge((QuadEdge) d);
				} else if (d instanceof Segment) {
					paintSegment((Segment) d);
				} else if (d instanceof Edge) {
					paintEdge((Edge) d);
				} else if (d instanceof Circle) {
					paintCircle((Circle) d);
				}

				gc.setFill(oldFill);
				gc.setStroke(oldStroke);
				gc.setLineWidth(oldWidth);
			}

			private void paintCircle(Circle circle) {
				gc.strokeOval(circle.getTopLeft().getX(), circle.getTopLeft().getY(), circle.getDiameter(), circle.getDiameter());
			}

			private void paintEdge(Edge edge) {
				gc.strokeLine(edge.orig().getX(), edge.orig().getY(), edge.dest().getX(), edge.dest().getY());
				paintStuff(edge.orig());
				paintStuff(edge.dest());
			}

			private void paintSegment(Segment segment) {
				gc.strokeLine(segment.getHead().getX(), segment.getHead().getY(), segment.getTail().getX(), segment.getTail().getY());
				paintStuff(segment.getHead());
				paintStuff(segment.getTail());
			}

			private void paintPointSet(PointSet pointSet) {
				pointSet.forEach(this::paintStuff);
			}

			private void paintPolygon(final Polygon polygon) {
				try {
					Iterator<Point> it = polygon.iterator();
					Point p, q;
					p = it.next();
					while (it.hasNext()) {
						q = it.next();
						gc.strokeLine(p.getX(), p.getY(), q.getX(), q.getY());
						p = q;
					}
					polygon.forEach(this::paintStuff);
				} catch (NoSuchElementException ignored) {
				}
			}

			private void paintPoint(Point point) {
				double size = gc.getLineWidth();
				if (size < 2) {
					gc.setLineWidth(2);
				}
				gc.fillOval((point.getX() - size), (point.getY() - size), 2 * size, 2 * size);
				gc.setLineWidth(1);
				gc.strokeOval((point.getX() - size), (point.getY() - size), 2 * size, 2 * size);
			}

			private void paintQuadEdge(QuadEdge quadEdge) {
				Iterator<Edge> it = quadEdge.iterator();
				Set<Point> points = new TreeSet<Point>();
				while (it.hasNext()) {
					Edge e = it.next();
					paintEdge(e);
					points.add(e.orig());
					points.add(e.dest());

				}
			}

		}).start();
	}

}
