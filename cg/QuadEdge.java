package cg;

/**
 * A collection of four directed edges designed for representing general
 * subdivisions of orientable manifolds. This data structure is described in a
 * paper by Guibas and Stolfi (1985).
 * 
 * @author Vance Miller
 * 
 */
interface QuadEdge extends Drawable {
	/**
	 * Creates a new QuadEdge collection that connects the destination of
	 * <tt>a</tt> to the origin of <tt>b</tt>, so that all three have the same
	 * left face after the connection is complete and the data pointers of the
	 * new edge are set.
	 * 
	 * @param a
	 *            an edge
	 * @param b
	 *            an edge
	 * @return the new edge connecting <tt>a</tt> and <tt>b</tt>
	 */
	public Edge connect(Edge a, Edge b);

	/**
	 * Disconnects edge e from the collection of edges.
	 * 
	 * @param e
	 *            the edge to delete
	 */
	public void deleteEdge(Edge e);

	/**
	 * Gets the {@link Edge} at index i. Edges are numbered in the order in
	 * which they are traversed. Order is arbitrary and will not be consistent
	 * between splices.
	 * 
	 * @param i
	 *            edge number
	 * @return the edge at traversal location i
	 */
	public Edge get(int i);

	/**
	 * Creates an empty edge quartet.
	 * 
	 * @return the edge
	 */
	public Edge makeEdge();

	/**
	 * Splices two edges together or apart. Splice affects the two edge rings
	 * around the origins of a and b, and, independently, the two edge rings
	 * around the left faces of a and b. In each case, (i) if the two rings are
	 * distinct, Splice will combine them into one, or (ii) if the two are the
	 * same ring, Splice will break it into two separate pieces. Thus, Splice
	 * can be used both to attach the two edges together, and to break them
	 * apart.
	 * 
	 * @param a
	 *            an edge
	 * @param b
	 *            an edge
	 */
	public void splice(Edge a, Edge b);

	/**
	 * Turns an edge counterclockwise inside its enclosing quadrilateral.
	 * 
	 * @param e
	 *            the edge to swap
	 */
	public void swap(Edge e);
}
