package cg;

import java.awt.Color;
import java.awt.Graphics;

public interface Drawable {

	public void paintComponent(Graphics g);

	public Color getColor();

	public void setColor(Color c);

	public void setInvisible(boolean visible);
}
