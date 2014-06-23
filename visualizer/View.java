package visualizer;

import algorithms.Algorithm;
import cg.Drawable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.CGObserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class View implements CGObserver {
	private final AlgorithmPanel p;

	public View(Stage primaryStage, ActionListener a) {
		primaryStage.setTitle("Algorithm Visualizer 2.0");

		BorderPane borderPane = new BorderPane();
		ToolBar tb = makeToolbar();
		VBox sidebar = makeSidebar();
		//Node content = addDrawPane();
		borderPane.setTop(tb);
		borderPane.setLeft(sidebar);
//		borderPane.setCenter(content);
		//	VBox root = new VBox();
		//primaryStage.setScene(new Scene(root, 600, 500));
		Scene scene = new Scene(borderPane, 300, 300);
		primaryStage.setScene(scene);
		primaryStage.show();

		////////////////////
		JFrame f = new JFrame("Algorithm Visualizer");
		try {
			Image image = ImageIO.read(getClass().getResource("icon.gif"));
			f.setIconImage(image);
		} catch (IOException e) {
			// use default image
		}
		f.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		ButtonPanel b = new ButtonPanel(a);
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridheight = 1;
		gc.gridwidth = 1;
		gc.weightx = 1;
		gc.weighty = 0;
		f.add(b, gc);

		p = new AlgorithmPanel(a);
		JPanel algHolder = new JPanel(new BorderLayout());
		algHolder.setBorder(BorderFactory.createTitledBorder(""));
		algHolder.add(p, BorderLayout.CENTER);
		gc.gridy = -1;
		gc.fill = GridBagConstraints.BOTH;
		gc.weighty = 1;
		gc.weightx = 1;
		f.add(algHolder, gc);

		// build menu
		JMenuBar menuBar = new JMenuBar();
		JMenu view = new JMenu("View");
		view.setMnemonic(KeyEvent.VK_V);
		view.getAccessibleContext().setAccessibleDescription(
				"Set the display properties of this visualizer");
		menuBar.add(view);
		JCheckBoxMenuItem large = new JCheckBoxMenuItem("Large");
		large.setMnemonic(KeyEvent.VK_L);
		large.addActionListener(a);
		large.setActionCommand("setLarge");
		view.add(large);
		f.setJMenuBar(menuBar);

		f.pack();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	private ToolBar makeToolbar() {
		Button reset = new Button("Reset");
		Button pointsPolygon = new Button("Draw Polygon");
		Button random = new Button("Random Points");
		Slider speed = new Slider(0, 10, 5);
		reset.setOnAction((event) -> {
			System.out.print("reset");
		});
		pointsPolygon.setOnAction((event) -> {
			pointsPolygon.setText("Draw Points");
			random.setText("Random Polygon");
			System.out.println("pointsPolygon");
		});
		random.setOnAction((event) -> {
			System.out.println("random");
		});
		speed.setShowTickLabels(true);
		speed.setShowTickMarks(true);
		speed.setMajorTickUnit(5);
		speed.setMinorTickCount(5);
		speed.valueProperty().addListener((event) -> {
			System.out.println("Speed " + speed.getValue());
		});
		ToolBar tb = new ToolBar(reset, new Separator(Orientation.VERTICAL), pointsPolygon, random,
				new Separator(Orientation.VERTICAL), speed);
		return tb;
	}

	private VBox makeSidebar() {
		VBox vb = new VBox();
		ObservableList<Algorithm> content = FXCollections.observableArrayList(Algorithm.values());
		ListView algorithms = new ListView<Algorithm>(content);
		Button run = new Button("Run");
		algorithms.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		algorithms.getSelectionModel().selectedItemProperty().addListener((event)->{
			System.out.println(event);
		});
		run.setOnAction((event)->{
			System.out.println(event);
		});
		vb.getChildren().add(algorithms);
		vb.getChildren().add(run);
		return vb;
	}

//	private Node addDrawPane() {
////		Group root = new Group();
////		Canvas canvas = new Canvas(400, 300);
////		root.getChildren().add(canvas);
//		return root;
//	}

	@Override
	public void update(Drawable o) {
		p.update(o);
	}
}