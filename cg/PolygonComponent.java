package cg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;

public class PolygonComponent extends PointSetComponent implements Polygon {
	private static final long serialVersionUID = -1523644503244611934L;

	protected PolygonComponent() {
		super();
	}

	@Override
	public Point get(int i) {
		// this method should throw an exception if i is out of bounds
		return super.get(i);
	}

	@Override
	public void paint(Graphics g) {
		synchronized (this) {
			if (isInvisible()) {
				return;
			}
			Color oldG = g.getColor(), c = super.getColor();
			if (c != null) {
				g.setColor(c);
			}
			// draw edges, then points
			Iterator<Point> it = super.iterator();
			Point p, q;
			if (super.size() > 1) {
				p = it.next();
				Graphics2D g2D = (Graphics2D) g;
				g2D.setStroke(new BasicStroke(GeometryManager.getSize()));
				while (it.hasNext()) {
					q = it.next();
					g2D.drawLine((int) p.getX(), (int) p.getY(),
							(int) q.getX(), (int) q.getY());
					p = q;
				}
				g2D.setStroke(new BasicStroke());
			}
			super.paint(g);
			g.setColor(oldG);
		}
	}
}
