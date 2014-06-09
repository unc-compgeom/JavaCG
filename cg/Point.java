package cg;

import util.CGObservable;

public interface Point extends Comparable<Point>, CGObservable {

	public void setX(int x);

	public void setY(int y);

	public int getX();

	public int getY();

	public Point add(Point p1);

	public Point sub(Point p1);

}
