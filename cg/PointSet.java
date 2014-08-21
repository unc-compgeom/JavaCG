package cg;

import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

import util.Drawable;

/**
 * A set of points that behaves as a doubly ended queue, stack, linked list, or
 * generic collection.
 *
 * @author Vance Miller
 *
 */
public interface PointSet extends Drawable, List<Point>, Deque<Point>,
Collection<Point>, java.io.Serializable {

	/**
	 * Adds a point to the point set and notifies its observers without any
	 * animation delay.
	 *
	 * @param x
	 *            the x-coordinate of the point
	 * @param y
	 *            the y-coordinate of the point
	 */
	public void addNoDelay(float x, float y);

	/**
	 * Adds a point to the point set and notifies its observers without any
	 * animation delay.
	 *
	 * @param v
	 *            the point to add.
	 */
	public void addNoDelay(Point v);

	/**
	 * Retrieves, but does not remove, the second element of this list. This
	 * method differs from {@link peekSecond} only in that it throws an
	 * exception if this list is empty.
	 *
	 * @return the second element of this list.
	 */
	public Point getSecond() throws NoSuchElementException;

	/**
	 * Returns the second-to-last element of this list.
	 *
	 * @return the second-to-last element of this list.
	 */
	public Point getSecondToLast();

	/**
	 * Retrieves, but does not remove, the second element of this list, or
	 * returns null if this list is empty.
	 *
	 * @return the second element of this list.
	 */
	public Point peekSecond();

	/**
	 * Retrieves, but does not remove, the second-to-last element of this list,
	 * or returns null if this list is empty.
	 *
	 * @return the second-to-last element of this list.
	 */
	public Point peekSecondToLast();

}
