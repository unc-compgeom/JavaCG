package cg;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class contains all generic geometry methods. All geometry objects should
 * inherit from this class with minimal method overriding as necessary.
 * 
 * @author Vance Miller
 * 
 */
public abstract class AbstractGeometry implements Drawable {
	private Color c;
	private boolean invisible;

	AbstractGeometry() {
		this.c = Color.black;
	}

	@Override
	public Color getColor() {
		return this.c;
	}

	protected void notifyObserversNoDelay() {
		GeometryManager.notifyObserversNoDelay();
	}

	protected void notifyObservers() {
		GeometryManager.notifyObservers();
	}

	@Override
	public abstract void paint(Graphics g);

	@Override
	public void setColor(Color c) {
		this.c = c;
		notifyObserversNoDelay();
	}

	@Override
	public boolean isInvisible() {
		return invisible;
	}

	@Override
	public void setInvisible(boolean isInvisible) {
		this.invisible = isInvisible;
	}
}
