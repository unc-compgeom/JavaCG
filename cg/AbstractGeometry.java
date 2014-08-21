package cg;

import javafx.scene.canvas.GraphicsContext;
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

	private void animateChange(Color oldColor, final Color newColor) {
		if (newColor == null) {
			return;
		}
		if (oldColor == null) {
			oldColor = Color.BLACK;
		}
		if (oldColor == newColor) {
			return;
		}
		// animateChange
		Color tmp = new Color(oldColor.getRed(), oldColor.getGreen(),
				oldColor.getBlue(), oldColor.getOpacity());
		final int delay = GeometryManager.getDelay();
		final double dr = (newColor.getRed() - tmp.getRed()) / (delay * 1.0);
		final double dg = (newColor.getGreen() - tmp.getGreen())
				/ (delay * 1.0);
		final double db = (newColor.getBlue() - tmp.getBlue()) / (delay * 1.0);
		for (int i = 0; i < delay; i++) {
			tmp = new Color(oldColor.getRed() + dr * i, oldColor.getGreen()
					+ dg * i, oldColor.getBlue() + db * i,
					oldColor.getOpacity());
			c = tmp;
			notifyObserversNoDelay();
			try {
				Thread.sleep(1);
			} catch (final InterruptedException e) {
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

	void notifyObservers() {
		GeometryManager.notifyObservers();
	}

	void notifyObserversNoDelay() {
		GeometryManager.notifyObserversNoDelay();
	}

	@Override
	public abstract void paint(GraphicsContext gc);

	@Override
	public void setColor(final Color c) {
		synchronized (this) {
			animateChange(this.c, c);
			this.c = c;
		}
	}

	@Override
	public void setColorNoAnim(final Color c) {
		synchronized (this) {
			this.c = c;
			notifyObserversNoDelay();
		}
	}

	@Override
	public void setInvisible(final boolean isInvisible) {
		invisible = isInvisible;
		notifyObserversNoDelay();
	}
}
