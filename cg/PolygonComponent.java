package cg;

import java.awt.Color;
import java.awt.Graphics;

import util.CGObservable;

public class PolygonComponent extends PointSetComponent implements Polygon,
		CGObservable {

	private static final long serialVersionUID = 410516590550449010L;
	
	public PolygonComponent() {
	}

	@Override
	public Line getLine(int lineNum) {
		return new LineComponent(get(lineNum % size()), get((lineNum + 1)
				% size()));
	}

	@Override
	public void paintComponent(Graphics g) {
		for (int i = 0; i < size(); i++) {
			super.paintComponent(g);
			Line l = getLine(i);
			l.setColor(g.getColor());
			l.paintComponent(g);
		}
	}
	@Override
	public void setColor(Color c) {
		super.setColor(c);
	}
}
