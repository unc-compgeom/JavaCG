package cg;

import java.util.Collection;
import java.util.Deque;
import java.util.List;

public interface VertexSet extends Drawable, List<Vertex>, Deque<Vertex>,
		Collection<Vertex>, Cloneable, java.io.Serializable {

	@Override
	public void clear();

	public VertexSet clone();

	public VertexSet cloneEmpty();

	public Vertex getSecond();

	public Vertex getSecondToLast();

	public void remove(Vertex pt);

}
