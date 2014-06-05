package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.CGObservable;
import util.CGObserver;
import algorithms.GrahmScan;
import algorithms.JarvisMarch;
import algorithms.Melkman;
import cg.Drawable;
import cg.PointComponent;
import cg.Polygon;
import cg.PolygonComponent;

public class View {
	private JFrame f;
	private AlgorithmPanel p;

	public View() {
		this.f = new JFrame("Algorithm Visualizer");
		f.setLayout(new BorderLayout());
		p = new AlgorithmPanel();
		f.add(p, BorderLayout.CENTER);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}

class AlgorithmPanel extends JPanel implements MouseListener, CGObserver {
	private static final long serialVersionUID = 717443380063382616L;
	private List<Drawable> drawableItems;
	private PolygonComponent p;

	AlgorithmPanel() {
		super();
		addMouseListener(this);
		p = new PolygonComponent();
		drawableItems = new LinkedList<Drawable>();
		drawableItems.add(p);
		JLabel j = new JLabel("test area");
		add(j);
	}

	public Runnable doAlgorithm(Polygon p) {
		final Polygon poly = p;
		final AlgorithmPanel me = this;
		return new Runnable() {
			@Override
			public void run() {
				PolygonComponent ch = new PolygonComponent();
				ch.setColor(Color.RED);
				ch.addObserver(me);
				// swap this out with any algorithm to visualize
				//JarvisMarch.doJarvisMarch(poly, ch);
				//Melkman.doMelkman(poly, ch);
				GrahmScan.doGrahmScan(poly, ch);
			}
		};
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
		if (e.getButton() == MouseEvent.BUTTON3) {
			new Thread(doAlgorithm((Polygon) p.clone())).start();
			p = new PolygonComponent();
			drawableItems.add(p);
		} else {
			p.addPoint(new PointComponent(e.getPoint().x, e.getPoint().y));
			repaint();
		}
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


