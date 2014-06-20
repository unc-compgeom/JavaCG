package cg;

import java.util.Deque;

public interface Polygon extends Drawable, Deque<Vertex>, VertexSet, Cloneable {

	public Segment getCCWEdge(int index);
	
}
