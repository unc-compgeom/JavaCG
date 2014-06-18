package cg;

import java.awt.Color;
import java.awt.Graphics;

import util.CGObservable;

public interface Drawable extends CGObservable {

	public void paintComponent(Graphics g);

	public Color getColor();

	public void setColor(Color c);

	public void setSize(Integer size);

	Integer getSize();
}
