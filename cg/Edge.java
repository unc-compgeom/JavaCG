package cg;


public interface Edge extends Drawable {
	public Edge dNext();

	public Edge dPrev();

	public Point dest();

	public Edge invRot();

	public Edge lnext();

	public Edge lprev();

	public Edge oNext();

	public Edge oPrev();

	public Point orig();

	public Edge rnext();

	public Edge rot();

	public Edge rprev();

	public void setNext(Edge next);

	public void setOrig(Point o);

	public void setDest(Point d);

	public void setRot(Edge rot);

	public Edge sym();
}
