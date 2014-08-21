package cg;

import util.Drawable;

public interface Point extends Comparable<Point>, Cloneable, Drawable {

	/**
	 * Computes the distance squared from this point to point p.
	 *
	 * @param p
	 *            a point
	 * @return the distance squared from this point to point p.
	 */
	public double distanceSquared(Point p);

	/**
	 * Divides this point's coordinates by a scalar. Returns a new point with
	 * location (this.x/i, this.y/i).
	 *
	 * @param i
	 *            the scalar
	 * @return a new point at location (this.x/i, this.y/i).
	 */
	public Point div(float i);

	/**
	 * Computes the dot product of this point with point p. Returns this.x*p.x +
	 * this.y * p.y.
	 *
	 * @param p
	 *            the point
	 * @return this.x*p.x + this.y * p.y.
	 */
	public double dot(Point p);

	/**
	 * Gets the x-coordinate.
	 *
	 * @return the x-coordinate.
	 */
	public float getX();

	/**
	 * Gets the y-coordinate.
	 *
	 * @return the y-coordinate.
	 */
	public float getY();

	/**
	 * Multiplies this point's coordinates by a scalar. Returns a new point with
	 * location (this.x*i, this.y*i).
	 *
	 * @param i
	 *            the scalar
	 * @return a new point at location (this.x*i, this.y*i).
	 */
	public Point mult(float i);

	/**
	 * Adds this point's coordinates to another point's coordinates. Returns a
	 * new point at location (this.x + p.x, this.y + p.y).
	 *
	 * @param p
	 *            the point to add
	 * @return a new point at location (this.x + p.x, this.y + p.y).
	 */
	public Point plus(Point p);

	/**
	 * Sets the x-coordinate.
	 *
	 * @param x
	 *            the x-coordinate.
	 */
	public void setX(float x);

	/**
	 * Sets the y-coordinate.
	 *
	 * @param y
	 *            the y-coordinate.
	 */
	public void setY(float y);

	/**
	 * Subtracts another point's coordinates from this point's coordinates.
	 * Returns a new point with location (this.x - p.x, this.y - p.y).
	 *
	 * @param p
	 *            the point to subtract
	 * @return a new point at location (this.x - p.x, this.y - p.y).
	 */
	public Point sub(Point p);

}
