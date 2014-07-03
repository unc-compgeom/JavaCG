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
	 * Forces this object to paint itself.
	 * 
	 * @param g
	 *            Graphics context
	 */
	public void paint(Graphics g);

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
	 * Sets the color of this object. The object will be painted with this
	 * color.
	 * 
	 * @param c
	 *            the color
	 */
	public void setColor(Color c);

	/**
	 * Sets the visibility of this object. The object will not be painted if it
	 * is invisible.
	 * 
	 * @param isInvisible
	 */
	public void setInvisible(boolean isInvisible);
}
