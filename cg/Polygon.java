package cg;

import java.util.Deque;

public interface Polygon extends Drawable, Deque<Vertex>, VertexSet, Cloneable {
	@Override
	public Polygon clone();

	@Override
	public Polygon cloneEmpty();
}
