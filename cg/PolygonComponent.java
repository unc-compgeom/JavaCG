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
	public void paintComponent(Graphics g) {
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
					g2D.drawLine(p.getX(), p.getY(), q.getX(), q.getY());
					p = q;
				}
				g2D.setStroke(new BasicStroke());
			}
			super.paintComponent(g);
		}
	}

	@Override
	public Point get(int i) {
		while (i < 0) {
			i = i + this.size();
		}
		return super.get(i % this.size());
	}

	@Override
	public Segment getCCWEdge(int index) {
		return GeometryManager.newSegment(this.get(index), this.get(index + 1));
	}
}
