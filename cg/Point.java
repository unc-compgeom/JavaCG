package cg;

import util.CGObservable;

public interface Point extends Comparable<Point>, CGObservable {

	public void setPoint(Point p1);

	public void setPoint(int x1, int y1);

	public int getX();

	public int getY();

	public Point add(Point p1);

	public Point sub(Point p1);
	
}
