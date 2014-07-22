package cg;

import javafx.scene.paint.Color;
import util.Drawable;

/**
 * This class contains all generic geometry methods. All geometry objects should
 * inherit from this class with minimal method overriding as necessary.
 *
 * @author Vance Miller
 */
public abstract class AbstractGeometry implements Drawable {
	private Color c;
	private boolean invisible;
	protected boolean isReady;

	AbstractGeometry() {
		isReady = false;
	}

	private synchronized void animateColorChange(Color oldColor, Color newColor) {
		if (newColor == null) {
			return;
		}
		if (oldColor == null) {
			oldColor = Color.BLACK;
		}
		if (oldColor == newColor) {
			return;
		}
		// animateColorChange
		Color tmp = new Color(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue(), oldColor.getOpacity());
		final int delay = GeometryManager.getDelay();
		double dr = (newColor.getRed() - tmp.getRed()) / (delay * 1.0);
		double dg = (newColor.getGreen() - tmp.getGreen()) / (delay * 1.0);
		double db = (newColor.getBlue() - tmp.getBlue()) / (delay * 1.0);
		for (int i = 0; i < delay; i++) {
			tmp = new Color(oldColor.getRed() + (dr * i),
					oldColor.getGreen() + (dg * i), oldColor.getBlue()
					+ (db * i), oldColor.getOpacity());
			c = tmp;
			notifyObserversNoDelay(this);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Color getColor() {
		return c;
	}

	@Override
	public boolean isInvisible() {
		return invisible;
	}

	@Override
	public boolean isReady() {
		return isReady;
	}

	void notifyObservers(Drawable d) {
		GeometryManager.notifyObservers(d);
	}

	void notifyObserversNoDelay(Drawable d) {
		GeometryManager.notifyObserversNoDelay(d);
	}

	@Override
	public synchronized void setColor(Color c) {
		animateColorChange(this.c, c);
		this.c = c;
	}

	@Override
	public synchronized void setColorNoAnim(Color c) {
		this.c = c;
		notifyObserversNoDelay(this);
	}

	@Override
	public synchronized void setInvisible(boolean isInvisible) {
		invisible = isInvisible;
		notifyObserversNoDelay(this);
	}
}
