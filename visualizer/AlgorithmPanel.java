package visualizer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import util.CGObservable;
import util.CGObserver;
import cg.Drawable;
import cg.PointSetComponent;

class AlgorithmPanel extends JPanel implements MouseListener, CGObserver {
	private static final long serialVersionUID = 717443380063382616L;
	private List<Drawable> drawableItems;
	private PointSetComponent p;
	private ActionListener a;

	AlgorithmPanel(ActionListener a) {
		super();
		this.a = a;
		addMouseListener(this);
		p = new PointSetComponent();
		drawableItems = new LinkedList<Drawable>();
		drawableItems.add(p);
		JLabel j = new JLabel("test area");
		add(j);
	}

	/**
	 * JPanel methods
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Drawable c : drawableItems) {
			c.paintComponent(g);
		}
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		a.actionPerformed(new ActionEvent(getSize(),
				ActionEvent.ACTION_PERFORMED, "viewResized"));
	};

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
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
	public void update(CGObservable o) {
		if (!drawableItems.contains(o)) {
			drawableItems.add(o);
			
		}
		repaint();
	}
}