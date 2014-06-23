package quadEdge;

public interface Edge {
	public Vertex getDestination();

	public Face getLeft();

	public Edge getNextLeft();

	public Edge getNextRight();

	public Edge getOpposite();

	public Vertex getOrigin();

	public Edge getPrevLeft();

	public Edge getPrevRight();

	public Face getRight();

	public Edge getRotLeft();

	public Edge getRotRight();

}
