package visualizer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;

import util.CGObserver;
import cg.Drawable;
import cg.GeometryManager;

class AlgorithmPanel extends JPanel implements MouseListener, CGObserver {
	private static final long serialVersionUID = 717443380063382616L;
	private final ActionListener a;

	AlgorithmPanel(ActionListener a) {
		super();
		this.a = a;
		addMouseListener(this);
	}

	/**
	 * JPanel methods
	 */

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 300);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		List<Drawable> geometry = GeometryManager.getAllGeometry();
		synchronized(geometry) {
			for (Drawable d : geometry) {
				d.paintComponent(g);
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
	 * Mouse listener methods
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		a.actionPerformed(new ActionEvent(e.getPoint(),
				ActionEvent.ACTION_PERFORMED, "viewAddPoint"));
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

	/**
	 * Observer methods
	 */
	@Override
	public void update(Drawable o) {
		repaint();
	}
}
