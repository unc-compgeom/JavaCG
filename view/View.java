package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.CGObservable;
import util.CGObserver;
import algorithms.*;
import cg.Drawable;
import cg.PointComponent;
import cg.PointSet;
import cg.PointSetComponent;
import cg.PolygonComponent;

public class View {
	private JFrame f;
	private AlgorithmPanel p;
	private ButtonPanel b;

	public View() {
		this.f = new JFrame("Algorithm Visualizer");
		f.setLayout(new BorderLayout());
		p = new AlgorithmPanel();
		b = new ButtonPanel(this);
		f.add(b, BorderLayout.NORTH);
		f.add(p, BorderLayout.CENTER);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void makeRandomPoints() {
		p.makeRandomPoints();
	}

	public void runGrahm() {
		p.runGrahm();
	}

	public void runJarvis() {
		p.runJarvis();
	}

	public void runMelkman() {
		p.runMelkman();
	}

	public void reset() {
		p.reset();
	}
}

class ButtonPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 733262376848960367L;
	private View parent;

	public ButtonPanel(View parent) {
		super();
		this.parent = parent;
		JButton randomPoints = new JButton("Generate random points");
		randomPoints.setActionCommand("makeRandomPoints");
		randomPoints.addActionListener(this);
		add(randomPoints);
		JButton runGrahm = new JButton("Grahm Scan");
		runGrahm.setActionCommand("runGrahm");
		runGrahm.addActionListener(this);
		add(runGrahm);
		JButton runJarvis = new JButton("Jarvis March");
		runJarvis.setActionCommand("runJarvis");
		runJarvis.addActionListener(this);
		add(runJarvis);
		JButton runMelkman = new JButton("Melkman");
		runMelkman.setActionCommand("runMelkman");
		runMelkman.addActionListener(this);
		add(runMelkman);
		JButton reset = new JButton("Reset");
		reset.setActionCommand("reset");
		reset.addActionListener(this);
		add(reset);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "makeRandomPoints":
			parent.makeRandomPoints();
			break;
		case "runGrahm":
			parent.runGrahm();
			break;
		case "runJarvis":
			parent.runJarvis();
			break;
		case "runMelkman":
			parent.runMelkman();
			break;
		case "reset":
			parent.reset();
			break;
		default:
			System.out.println("Default action handler: do nothing");
			break;
		}
	}
}

class AlgorithmPanel extends JPanel implements MouseListener, CGObserver {
	private static final long serialVersionUID = 717443380063382616L;
	private List<Drawable> drawableItems;
	private PointSetComponent p;
	private List<Thread> runningAlgorithms;

	AlgorithmPanel() {
		super();
		addMouseListener(this);
		p = new PointSetComponent();
		drawableItems = new LinkedList<Drawable>();
		drawableItems.add(p);
		JLabel j = new JLabel("test area");
		add(j);
		runningAlgorithms = new LinkedList<Thread>();
	}

	public void reset() {
		drawableItems.removeAll(drawableItems);
		p = new PointSetComponent();
		drawableItems.add(p);
		for (Thread t : runningAlgorithms) {
			t.stop();
			// there must be a better way
		}
		runningAlgorithms.removeAll(runningAlgorithms);
		repaint();
	}

	public void runMelkman() {
		final PointSet poly = p;
		final AlgorithmPanel me = this;
		runningAlgorithms.add(new Thread(new Runnable() {
			@Override
			public void run() {
				PolygonComponent ch = new PolygonComponent();
				ch.setColor(Color.RED);
				ch.addObserver(me);
				Melkman.doMelkman(poly, ch);
			}
		}));
		runningAlgorithms.get(runningAlgorithms.size() - 1).start();

		p = new PointSetComponent();
		drawableItems.add(p);
	}

	public void runJarvis() {
		final PointSet poly = p;
		final AlgorithmPanel me = this;
		runningAlgorithms.add(new Thread(new Runnable() {
			@Override
			public void run() {
				PolygonComponent ch = new PolygonComponent();
				ch.setColor(Color.RED);
				ch.addObserver(me);
				JarvisMarch.doJarvisMarch(poly, ch);
			}
		}));
		runningAlgorithms.get(runningAlgorithms.size() - 1).start();
		p = new PointSetComponent();
		drawableItems.add(p);
	}

	public void runGrahm() {
		final PointSet poly = p;
		final AlgorithmPanel me = this;
		runningAlgorithms.add(new Thread(new Runnable() {
			@Override
			public void run() {
				PolygonComponent ch = new PolygonComponent();
				ch.setColor(Color.RED);
				ch.addObserver(me);
				GrahmScan.doGrahmScan(poly, ch);
			}
		}));
		runningAlgorithms.get(runningAlgorithms.size() - 1).start();
		p = new PointSetComponent();
		drawableItems.add(p);
	}

	public void makeRandomPoints() {
		int width = getSize().width;
		int height = getSize().height;
		Random Ayn = new Random();
		for (int i = 0; i < 64; i++) {
			p.addPoint(new PointComponent(Ayn.nextInt(width), Ayn
					.nextInt(height)));
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Drawable c : drawableItems) {
			c.paintComponent(g);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		p.addPoint(new PointComponent(e.getPoint().x, e.getPoint().y));
		repaint();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void update(CGObservable o) {
		if (!drawableItems.contains(o)) {
			drawableItems.add(o);
		}
		repaint();
	}
}
