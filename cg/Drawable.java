package cg;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This interface specifies the requirements for an object to be visualized.
 * 
 * @author Vance Miller
 * 
 */
public interface Drawable {

	/**
	 * Returns the current color of this object.
	 * 
	 * @return a color
	 */
	public Color getColor();

	/**
	 * Returns true iff this object is invisible. Invisible objects do not
	 * appear on the screen when paint() is called.
	 * 
	 * @return true iff the object is invisible
	 */
	public boolean isInvisible();

	/**
	 * Forces this object to paint itself.
	 * 
	 * @param g
	 *            Graphics context
	 */
	public void paint(Graphics g);

	/**
	 * Sets the color of this object. The object will be painted with this
	 * color. The color transition is animated.
	 * 
	 * @param c
	 *            the color
	 */
	public void setColor(Color c);

	/**
	 * Sets the color of this object. The object will be painted with this
	 * color. The color transition is not animated.
	 * 
	 * @param c
	 *            the color
	 */
	public void setColorNoAnim(Color c);

	/**
	 * Sets the visibility of this object. The object will not be painted if it
	 * is invisible.
	 * 
	 * @param isInvisible is the object invisible
	 */
	public void setInvisible(boolean isInvisible);
}
