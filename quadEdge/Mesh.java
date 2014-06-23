package quadEdge;

public interface Mesh {
	public Edge[] getEdges();

	public Face[] getFaces();

	public Vertex[] getVertices();

	public void splice(Edge e1, Edge e2);

	public Edge makeEdge();

}
