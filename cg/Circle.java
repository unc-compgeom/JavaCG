package cg;

import java.util.List;

import util.Drawable;

/**
 * A circle.
 *
 * @author Vance Miller
 *
 */
public interface Circle extends Drawable {
	// /**
	// * Returns the radius of the circle squared.
	// *
	// * @return the radius squared
	// */
	// public int getRadiusSquared();
	//
	// /**
	// * Returns a {@link Point} that represents the center of the circle
	// *
	// * @return the center of the circle
	// */
	// public Point getOrigin();

	/**
	 * Returns a list of {@link Point}s on the circumference of the circle.
	 *
	 * @return points that define the circle
	 */
	public List<Point> getPoints();

}
