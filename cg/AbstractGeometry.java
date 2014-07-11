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
	}

	@Override
	public Color getColor() {
		return c;
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
		synchronized (this) {
			animateChange(this.c, c);
			this.c = c;
		}
	}

	@Override
	public void setColorNoAnim(Color c) {
		synchronized (this) {
			this.c = c;
			notifyObserversNoDelay();
		}
	}

	private void animateChange(Color oldColor, Color newColor) {
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
		Color tmp = new Color(oldColor.getRGB());
		final int delay = GeometryManager.getDelay();
		double dr = (newColor.getRed() - tmp.getRed()) / (delay * 1.0);
		double dg = (newColor.getGreen() - tmp.getGreen()) / (delay * 1.0);
		double db = (newColor.getBlue() - tmp.getBlue()) / (delay * 1.0);
		System.out
				.println("transitioning from " + oldColor + " to " + newColor);
		System.out.println(dr + ", " + dg + ", " + db);
		for (int i = 0; i < delay; i++) {
			tmp = new Color(oldColor.getRed() + (int) (dr * i),
					oldColor.getGreen() + (int) (dg * i), oldColor.getBlue()
							+ (int) (db * i));
			this.c = tmp;
			notifyObserversNoDelay();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isInvisible() {
		return invisible;
	}

	@Override
	public void setInvisible(boolean isInvisible) {
		invisible = isInvisible;
		notifyObserversNoDelay();
	}
}
