package quadEdge;

public class FaceComponent implements Face {

	private Edge edge;

	@Override
	public Edge getEdge() {
		return edge;
	}

	protected void setEdge(Edge edge) {
		this.edge = edge;
	}

}
