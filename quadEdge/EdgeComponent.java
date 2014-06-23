package quadEdge;

public class EdgeComponent implements Edge {
	private Vertex origin;
	private Edge opposite;
	private Edge nextLeft;
	private Face left;

	@Override
	public Vertex getDestination() {
		return opposite.getOrigin();
	}

	@Override
	public Face getLeft() {
		return left;
	}

	@Override
	public Edge getNextLeft() {
		return nextLeft;
	}

	@Override
	public Edge getNextRight() {
		return opposite.getNextLeft().getNextLeft().getOpposite();
	}

	@Override
	public Edge getOpposite() {
		return opposite;
	}

	@Override
	public Vertex getOrigin() {
		return origin;
	}

	@Override
	public Edge getPrevLeft() {
		return nextLeft.getNextLeft();
	}

	@Override
	public Edge getPrevRight() {
		return opposite.getNextLeft().getOpposite();
	}

	@Override
	public Face getRight() {
		return opposite.getLeft();
	}

	@Override
	public Edge getRotLeft() {
		return nextLeft.getNextLeft().getOpposite();
	}

	@Override
	public Edge getRotRight() {
		return opposite.getNextLeft();
	}

}
