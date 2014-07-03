package cg;

public interface Point extends Comparable<Point>, Cloneable, Drawable {

	/**
	 * Sets the x-coordinate.
	 * 
	 * @param x
	 *            the x-coordinate.
	 */
	public void setX(int x);

	/**
	 * Sets the y-coordinate.
	 * 
	 * @param y
	 *            the y-coordinate.
	 */
	public void setY(int y);

	/**
	 * Gets the x-coordinate.
	 * 
	 * @return the x-coordinate.
	 */
	public int getX();

	/**
	 * Gets the y-coordinate.
	 * 
	 * @return the y-coordinate.
	 */
	public int getY();

	/**
	 * Adds this point's coordinates to another point's coordinates. Returns a
	 * new point at location (this.x + p.x, this.y + p.y).
	 * 
	 * @param p
	 *            the point to add
	 * @return a new point at location (this.x + p.x, this.y + p.y).
	 */
	public Point add(Point p);

	/**
	 * Subtracts another point's coordinates from this point's coordinates.
	 * Returns a new point with location (this.x - p.x, this.y - p.y).
	 * 
	 * @param p
	 *            the point to subtract
	 * @return a new point at location (this.x - p.x, this.y - p.y).
	 */
	public Point sub(Point p);

	/**
	 * Divides this point's coordinates by a scalar. Returns a new point with
	 * location (this.x/i, this.y/i).
	 * 
	 * @param i
	 *            the scalar
	 * @return a new point at location (this.x/i, this.y/i).
	 */
	public Point div(double i);

	/**
	 * Multiplies this point's coordinates by a scalar. Returns a new point with
	 * location (this.x*i, this.y*i).
	 * 
	 * @param i
	 *            the scalar
	 * @return a new point at location (this.x*i, this.y*i).
	 */
	public Point mult(double i);

	/**
	 * Computes the dot product of this point with point p. Returns this.x*p.x +
	 * this.y * p.y.
	 * 
	 * @param p
	 *            the point
	 * @return this.x*p.x + this.y * p.y.
	 */
	public int dot(Point p);

	/**
	 * Computes the distance squared from this point to point p.
	 * 
	 * @param p
	 * @return the distance squared from this point to point p.
	 */
	public double distanceSquared(Point p);

}
