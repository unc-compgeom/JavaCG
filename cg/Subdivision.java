package cg;

import util.Drawable;
import util.DuplicatePointException;

/**
 * This interface specifies the requirements of an object that can be used to
 * represent a Delaunay Triangulation or a generic subdivision of a polygon.
 *
 * @author Vance Miller
 *
 */
public interface Subdivision extends Drawable {

	/**
	 * Computes and returns the dual of this graph.
	 * 
	 * @return the dual graph
	 */
	public Subdivision getDual();

	/**
	 * Inserts {@link Point} p into the subdivision.
	 *
	 * @param p
	 *            the Point to insert
	 * @throws DuplicatePointException
	 *             iff p is already part of the subdivision
	 */
	public void insertSite(Point p) throws DuplicatePointException;

	/**
	 * Returns the <tt>Edge</tt> that contains <tt>p</tt> or the edge of a
	 * triangle containing <tt>p</tt>.
	 *
	 * @param q
	 *            the point to locate
	 * @return the edge that p is on;
	 * @throws DuplicatePointException
	 *             iff <tt>p</tt> is already in this subdivision
	 *
	 */
	public Edge locate(Point q) throws DuplicatePointException;
}
