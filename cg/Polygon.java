package cg;

import java.util.Deque;

import util.CGObservable;

public interface Polygon extends CGObservable, Deque<Vertex>, VertexSet {
	//public HalfEdge getHalfEdge(int lineNum);

}
