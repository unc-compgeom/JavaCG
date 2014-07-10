package cg;

import util.DuplicatePointException;
import util.MalformedTriangulationException;

/**
 * This interface specifies the requirements of an object that can be used to
 * represent a Delaunay Triangulation or a generic subdivision of a polygon.
 * 
 * @author Vance Miller
 * 
 */
public interface Subdivision extends Drawable {
	/**
	 * Inserts {@link Point} p into the subdivision.
	 * 
	 * @param p
	 *            the Point to insert
	 * @throws DuplicatePointException
	 *             iff p is already part of the subdivision
	 * @throws MalformedTriangulationException
	 *             iff p is outside of the subdivision
	 */
	public void insertSite(Point p) throws DuplicatePointException,
			MalformedTriangulationException;

	/**
	 * Returns the <tt>Edge</tt> that contains <tt>p</tt> or the edge of a
	 * triangle containing <tt>p</tt>.
	 * 
	 * @param q
	 *            the point to locate
	 * @return the edge that p is on;
	 * @throws DuplicatePointException
	 *             iff <tt>p</tt> is already in this subdivision
	 * @throws MalformedTriangulationException
	 *             iff <tt>p</tt> is not in the triangulation
	 */
	public Edge locate(Point q) throws DuplicatePointException,
			MalformedTriangulationException;

	public void getDual();
}
