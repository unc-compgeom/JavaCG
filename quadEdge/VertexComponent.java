package quadEdge;

import cg.Point;

public class VertexComponent implements Vertex {
	private Edge edge;
	private Point origin;

	@Override
	public Edge getEdge() {
		return edge;
	}

	@Override
	public Point getPosition() {
		return origin;
	}

	protected void setEdge(Edge edge) {
		this.edge = edge;
	}

	protected void setPosition(Point origin) {
		this.origin = origin;
	}

}
