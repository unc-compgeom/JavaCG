//package visualizer;
//
//import algorithms.Algorithm;
//import cg.GeometryManager;
//import javafx.collections.FXCollections;
//import javafx.geometry.Orientation;
//import javafx.scene.control.*;
//
//import java.awt.event.ActionEvent;
//
///**
// * Created by Vance Miller on 7/16/2014.
// */
//public class CGToolBar {
//
//	public static ToolBar makeToolBar(ViewController a) {
//		Button enablePolygon, makePoint, makeCircle, makeLine, makeRandom, run, reset;
//		ComboBox<Algorithm> algorithms;
//		Slider speed;
//		ToolBar tb = new ToolBar(
//				enablePolygon = new Button("Polygon"),
//				new Separator(Orientation.VERTICAL),
//				makePoint = new Button("p"),
//				makeCircle = new Button("c"),
//				makeLine = new Button("l"),
//				makeRandom = new Button("r"),
//				new Separator(Orientation.VERTICAL),
//				algorithms = new ComboBox<Algorithm>(
//						FXCollections.observableArrayList(Algorithm.values())),
//				run = new Button("Run"),
//				reset = new Button("Reset"),
//				new Separator(Orientation.VERTICAL),
//				speed = new Slider(0, 35,
//						(Math.log10(GeometryManager.getDelay() + 1) * 10))
//		);
//
//		enablePolygon.setOnAction((event) -> {
//			if (enablePolygon.getText().equalsIgnoreCase("Polygon")) {
//				enablePolygon.setText("  Points ");
//				a.actionPerformed(new java.awt.event.ActionEvent(tb,
//						java.awt.event.ActionEvent.ACTION_PERFORMED, "enablePolygon"));
//			} else {
//				enablePolygon.setText("Polygon");
//				a.actionPerformed(new java.awt.event.ActionEvent(tb,
//						java.awt.event.ActionEvent.ACTION_PERFORMED, "enablePoints"));
//			}
//		});
//		// single point
//		makePoint.setDisable(true);
//		makePoint.setOnAction((event) -> {
//			makePoint.setDisable(true);
//			makeCircle.setDisable(false);
//			makeLine.setDisable(false);
//			a.actionPerformed(new java.awt.event.ActionEvent(tb, java.awt.event.ActionEvent.ACTION_PERFORMED, "makePoint"));
//		});
//		// circle
//		makeCircle.setOnAction((event) -> {
//			makePoint.setDisable(false);
//			makeCircle.setDisable(true);
//			makeLine.setDisable(false);
//			a.actionPerformed(new java.awt.event.ActionEvent(tb, java.awt.event.ActionEvent.ACTION_PERFORMED, "makeCircle"));
//		});
//		// line
//		makeLine.setOnAction((event) -> {
//			makePoint.setDisable(false);
//			makeCircle.setDisable(false);
//			makeLine.setDisable(true);
//			a.actionPerformed(new java.awt.event.ActionEvent(tb, java.awt.event.ActionEvent.ACTION_PERFORMED, "makeLine"));
//		});
//
//		// random points
//		makeRandom.setOnAction((event) -> a.actionPerformed(new ActionEvent(tb, ActionEvent.ACTION_PERFORMED, "makeRandom")));
//		// run
//		run.setOnAction((event) -> {
//			a.actionPerformed(new ActionEvent(tb,
//					ActionEvent.ACTION_PERFORMED, algorithms.getSelectionModel().getSelectedItem().toString()));
//		});
//
//		// reset
//		reset.setOnAction((event) -> {
//			a.actionPerformed(new java.awt.event.ActionEvent(tb, java.awt.event.ActionEvent.ACTION_PERFORMED, "reset"));
//		});
//
//		// speed slider
//		speed.setShowTickLabels(true);
//		speed.setShowTickMarks(true);
//		speed.setMajorTickUnit(5);
//		speed.setMinorTickCount(5);
//		speed.valueProperty().addListener((event) -> {
//			double s = speed.getValue();
//			a.actionPerformed(new ActionEvent(tb,
//					ActionEvent.ACTION_PERFORMED, "speedSet", (int) s));
//		});
//		return tb;
//	}
//}
