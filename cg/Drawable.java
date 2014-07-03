package cg;

import java.awt.Color;
import java.awt.Graphics;

public interface Drawable {

	public void paintComponent(Graphics g);

	public Color getColor();

	public boolean isInvisible();

	public void setColor(Color c);

	public void setInvisible(boolean isInvisible);
}
