package cg;

import java.awt.Color;
import java.awt.Graphics;

import util.CGObservable;

public class PolygonComponent extends PointSetComponent implements Polygon,
		CGObservable {

	private static final long serialVersionUID = 410516590550449010L;

	public PolygonComponent() {
	}

	public PolygonComponent(Point[] pts) {
		super(pts);
	}

	@Override
	public Line getLine(int lineNum) {
		Line line = new LineComponent();
		line.setP1(getPoint(lineNum % numPoints()));
		line.setP2(getPoint((lineNum + 1) % numPoints()));
		return line;
	}

	@Override
	public void paintComponent(Graphics g) {
		Color c = super.getColor();
		for (int i = 0; i < numPoints(); i++) {
			g.setColor(c);
			getLine(i).paintComponent(g);
		}
	}
}
