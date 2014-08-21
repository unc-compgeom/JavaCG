package cg;

import java.util.Iterator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PolygonComponent extends PointSetComponent implements Polygon {
	private static final long serialVersionUID = -1523644503244611934L;

	PolygonComponent() {
		super();
	}

	@Override
	public void paint(final GraphicsContext gc) {
		if (isInvisible()) {
			return;
		}
		// push old values onto the "stack"
		final Paint oldFill = gc.getFill();
		final Paint oldStroke = gc.getStroke();
		final double oldSize = gc.getLineWidth();
		final Color c = super.getColor();
		if (c != null) {
			gc.setFill(c);
			gc.setStroke(c.darker());
		}
		gc.setLineWidth(GeometryManager.getSize());

		final Iterator<Point> it = super.iterator();
		Point p, q;
		synchronized (this) {
			if (super.size() > 1) {
				p = it.next();
				gc.setLineWidth(GeometryManager.getSize());
				while (it.hasNext()) {
					q = it.next();
					gc.strokeLine(p.getX(), p.getY(), q.getX(), q.getY());
					p = q;
				}
			}
		}
		super.paint(gc);

		// restore gc from the "stack"
		gc.setFill(oldFill);
		gc.setStroke(oldStroke);
		gc.setLineWidth(oldSize);
	}
}
