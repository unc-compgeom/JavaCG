package visualizer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JPanel;

import util.CGObserver;
import cg.Drawable;
import cg.GeometryManager;

class AlgorithmPanel extends JPanel implements MouseListener,
		MouseMotionListener, CGObserver {
	private static final long serialVersionUID = 717443380063382616L;
	private final ActionListener a;

	AlgorithmPanel(ActionListener a) {
		super();
		this.a = a;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * JPanel methods
	 */

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 300);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point location = e.getPoint();
		location.setLocation(location.getX(), getHeight() - location.getY());
		a.actionPerformed(new ActionEvent(location,
				ActionEvent.ACTION_PERFORMED, "mouseMoved"));
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * Mouse listener methods
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		Point location = e.getPoint();
		location.setLocation(location.getX(), getHeight() - location.getY());
		a.actionPerformed(new ActionEvent(location,
				ActionEvent.ACTION_PERFORMED, "viewAddPoint"));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.translate(0, getHeight());
		((Graphics2D) g).scale(1, -1);
		List<Drawable> geometry = GeometryManager.getAllGeometry();
		synchronized (geometry) {
			for (Drawable d : geometry) {
				d.paint(g);
			}
		}
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		a.actionPerformed(new ActionEvent(getSize(),
				ActionEvent.ACTION_PERFORMED, "viewResized"));
	}

	/**
	 * Observer methods
	 */
	@Override
	public void update(Drawable o) {
		repaint();
	}
}
