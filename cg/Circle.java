package cg;

/**
 * A circle.
 * 
 * @author Vance Miller
 * 
 */
public interface Circle extends Drawable {
	/**
	 * Returns the radius of the circle squared.
	 * 
	 * @return the radius squared
	 */
	public int getRadiusSquared();

	/**
	 * Returns a {@link Point} that represents the center of the circle
	 * 
	 * @return the center of the circle
	 */
	public Point getOrigin();

}
