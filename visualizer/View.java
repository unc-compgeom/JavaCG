package visualizer;

import cg.Drawable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import util.CGObserver;

import java.awt.event.ActionEvent;

public class View implements CGObserver {
	//private final AlgorithmCanvas c;
	private final Canvas canvas;

	public View(Stage primaryStage, ViewController a) {
		primaryStage.setTitle("Algorithm Visualizer 2.0");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.gif")));

		Group root = new Group();
		canvas = new Canvas(400, 300);
		canvas.addEventHandler(EventType.ROOT, (event) -> {
			System.out.println("change");
		});
		root.getChildren().add(canvas);

		ToolBar tb = CGToolBar.makeToolBar(a);

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(tb);
		borderPane.setCenter(root);

		Scene scene = new Scene(borderPane, 300, 300);
		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
				a.actionPerformed(new ActionEvent(newSceneWidth, ActionEvent.ACTION_PERFORMED, "viewWidthResized"));
			}
		});
		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
				a.actionPerformed(new ActionEvent(newSceneHeight, ActionEvent.ACTION_PERFORMED, "viewHeightResized"));
			}
		});
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void update(Drawable o) {
		System.out.print("updating..");
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawShapes(gc);
		//p.update(o);
		System.out.println("  updated");
	}

	private void drawShapes(GraphicsContext gc) {
		gc.setFill(javafx.scene.paint.Color.GREEN);
		gc.setStroke(javafx.scene.paint.Color.BLUE);
		gc.setLineWidth(5);
		gc.strokeLine(40, 10, 10, 40);
		gc.fillOval(10, 60, 30, 30);
		gc.strokeOval(60, 60, 30, 30);
		gc.fillRoundRect(110, 60, 30, 30, 10, 10);
		gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
		gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
		gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
		gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
		gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
		gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
		gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
		gc.fillPolygon(new double[] {10, 40, 10, 40},
				new double[] {210, 210, 240, 240}, 4);
		gc.strokePolygon(new double[] {60, 90, 60, 90},
				new double[] {210, 210, 240, 240}, 4);
		gc.strokePolyline(new double[] {110, 140, 110, 140},
				new double[] {210, 210, 240, 240}, 4);
	}
}