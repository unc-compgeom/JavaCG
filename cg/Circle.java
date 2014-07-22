package cg;

import util.Drawable;

import java.util.List;

/**
 * A circle.
 *
 * @author Vance Miller
 */
public interface Circle extends Drawable {
	/**
	 * Returns the diameter of the circle.
	 *
	 * @return the diameter
	 */
	public double getDiameter();

	/**
	 * Returns a {@link Point} that represents the top left corner of the circle's bounding box.
	 *
	 * @return the top left corner of the circle
	 */
	public Point getTopLeft();

	/**
	 * Returns a list of {@link Point}s on the circumference of the circle.
	 *
	 * @return points that define the circle
	 */
	public List<Point> getPoints();

}
