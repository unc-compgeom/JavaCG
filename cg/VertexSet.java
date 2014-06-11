package cg;

import java.util.Deque;
import java.util.List;

import util.CGObservable;

public interface VertexSet extends CGObservable, List<Vertex>, Deque<Vertex>,
		Cloneable, java.io.Serializable {

	public void remove(Vertex pt);

	public void clear();

	public Vertex getSecond();

	public Vertex getSecondToLast();

}
