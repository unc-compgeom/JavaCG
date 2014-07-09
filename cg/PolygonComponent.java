package cg;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;

public class PolygonComponent extends PointSetComponent implements Polygon {
	private static final long serialVersionUID = -1523644503244611934L;

	protected PolygonComponent() {
		super();
	}

	@Override
	public void paint(Graphics g) {
		if (isInvisible())
			return;
		// draw edges, then points
		synchronized (this) {
			Iterator<Point> it = super.iterator();
			Point p, q;
			if (super.size() > 1) {
				p = it.next();
				Graphics2D g2D = (Graphics2D) g;
				g2D.setStroke(new BasicStroke(GeometryManager.getSize()));
				g2D.setColor(super.getColor());
				while (it.hasNext()) {
					q = it.next();
					g2D.drawLine((int) p.getX(), (int) p.getY(),
							(int) q.getX(), (int) q.getY());
					p = q;
				}
				g2D.setStroke(new BasicStroke());
			}
			super.paint(g);
		}
	}

	@Override
	public Point get(int i) {
		// this method should throw an exception if i is out of bounds
		return super.get(i);
	}
}
