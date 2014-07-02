package cg;

import java.awt.Color;
import java.awt.Graphics;

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
	public abstract void paintComponent(Graphics g);

	@Override
	public void setColor(Color c) {
		this.c = c;
		notifyObserversNoDelay();
	}

	@Override
	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	protected boolean isInvisible() {
		return invisible;
	}
}
