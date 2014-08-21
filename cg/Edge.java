package cg;

import util.Drawable;

/**
 * An interface that specifies the edge data structure which implements the
 * quadedge algebra. The quadedge algebra was described in a paper by Guibas and
 * Stolfi,
 * "Primitives for the manipulation of general subdivisions and the computation of Voronoi diagrams"
 * , ACM Transactions on Graphics, 4(2), 1985, 75-123.
 *
 * Each edge is grouped into a collection of 4 edges, linked via their rot
 * references, called a {@link QuadEdge}. Any edge in the group may be accessed
 * using a series of rot() operations. QuadEdges in a {@link Subdivision} are
 * linked together via their next references.
 */
public interface Edge extends Drawable {
	/**
	 * Gets the {@link Point} for the edge's destination
	 *
	 * @return the destination point
	 */
	public Point dest();

	/**
	 * Gets the next CCW {@link Edge} around (into) the destination of this edge
	 *
	 * @return the next destination edge
	 */
	public Edge dNext();

	/**
	 * Gets the next CW {@link Edge} around (into) the destination of this edge
	 *
	 * @return the previous destination edge
	 */
	public Edge dPrev();

	/**
	 * Gets the dual of this edge, directed from its left to its right
	 *
	 * @return the inverse rotated edge
	 */
	public Edge invRot();

	/**
	 * Gets the CCW {@link Edge} around the left face following this edge
	 *
	 * @return the next left face edge
	 */
	public Edge lNext();

	/**
	 * Gets the CCW {@link Edge} around the left face before this edge
	 *
	 * @return the previous left face edge
	 */
	public Edge lPrev();

	/**
	 * Gets the next CCW {@link Edge} around the origin of this edge
	 *
	 * @return the next linked edge
	 */
	public Edge oNext();

	/**
	 * Gets the next CW {@link Edge} around (from) the origin of this edge
	 *
	 * @return the previous edge
	 */
	public Edge oPrev();

	/**
	 * Gets the {@link Point} for the edge's origin
	 *
	 * @return the origin point
	 */
	public Point orig();

	/**
	 * Gets the {@link Edge} around the right face ccw following this edge
	 *
	 * @return the next right face edge
	 */
	public Edge rNext();

	/**
	 * Gets the dual of this edge, directed from its right to its left
	 *
	 * @return the rotated edge
	 */
	public Edge rot();

	/**
	 * Gets the {@link Edge} around the right face ccw before this edge
	 *
	 * @return the previous right face edge
	 */
	public Edge rPrev();

	/**
	 * Sets the coordinates of this edge.
	 *
	 * @param origin
	 *            the new origin
	 * @param destination
	 *            the new destination
	 */
	public void setCoordinates(Point origin, Point destination);

	/**
	 * Sets the {@link Point} for this edge's destination
	 *
	 * @param d
	 *            the destination point
	 */
	public void setDest(Point d);

	/**
	 * Sets the connected {@link Edge}
	 *
	 * @param next
	 *            the next edge
	 */
	public void setNext(Edge next);

	/**
	 * Sets the {@link Point} for this edge's origin
	 *
	 * @param o
	 *            the origin point
	 */
	public void setOrig(Point o);

	/**
	 * Sets the dual of this edge, directed from its right to its left
	 *
	 * @param rot
	 *            the rotated edge
	 */
	public void setRot(Edge rot);

	/**
	 * Gets the {@link Edge} from the destination to the origin of this edge
	 *
	 * @return the sym of this edge
	 */
	public Edge sym();
}
