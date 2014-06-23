package cg;

public interface Point extends Comparable<Point>, Cloneable, Drawable {

	public void setX(int x);

	public void setY(int y);

	public int getX();

	public int getY();

	public Point add(Point v);

	public Point sub(Point v);

	public Point div(double i);

	public Point mult(double i);

	public int dot(Point v);

	public Point clone();

	public double distanceSquared(Point v);

}
