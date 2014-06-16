package quadEdge;

import cg.Vertex;
import cg.VertexComponent;

public class Edge {
	private int num;
	private Edge next;
	private Vertex o;
	public QuadEdge q;

	public Edge(Vertex o) {

	}

	public Edge() {
		this(new VertexComponent(0, 0));
	}

	public void splice(Edge e, Edge r) {

	}

	public Edge rotate() {
		return null;

	}

	public Edge inverseRotation() {
		return null;

	}

	public Edge symmetric() {
		return null;
	}

	public Edge getONext() {
		return null;
	}

	public Edge getOPrev() {
		return null;
	}

	public Edge getDNext() {
		return null;
	}

	public Edge getDPrev() {
		return null;
	}

	public Edge getLNext() {
		return null;
	}

	public Edge getLPrev() {
		return null;
	}

	public Edge getRNext() {
		return null;
	}

	public Edge getRPrev() {
		return null;
	}

	public Vertex getOrigin() {
		return o;
	}

	public Vertex getDestination() {
		return o;
	}

	public QuadEdge getQuadEdge() {
		return q;
	}

}
