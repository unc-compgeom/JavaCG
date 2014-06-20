package cg;

import java.util.Deque;

public interface Polygon extends Drawable, Deque<Point>, PointSet, Cloneable {

	public Segment getCCWEdge(int index);
	
}
