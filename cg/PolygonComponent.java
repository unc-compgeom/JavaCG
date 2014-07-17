package cg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Iterator;

public class PolygonComponent extends PointSetComponent implements Polygon {
	private static final long serialVersionUID = -1523644503244611934L;

	PolygonComponent() {
		super();
	}

	@Override
	public void paint(GraphicsContext gc) {
		if (isInvisible()) {
			return;
		}
		Paint oldStroke = gc.getStroke();
		Paint oldFill = gc.getFill();
		Color c = super.getColor();
		if (c != null) {
			gc.setStroke(c);
			gc.setFill(c);
		}
		Iterator<Point> it = super.iterator();
		Point p, q;
		if (super.size() > 1) {
			p = it.next();
			gc.setLineWidth(GeometryManager.getSize());
			while (it.hasNext()) {
				q = it.next();
				gc.strokeLine(p.getX(), p.getY(),
						q.getX(), q.getY());
				p = q;
			}
		}
		super.paint(gc);
		gc.setStroke(oldStroke);
		gc.setFill(oldFill);
	}
}
