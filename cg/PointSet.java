package cg;

import java.util.Deque;

import util.CGObservable;

public interface PointSet extends CGObservable, Deque<Point> {

	public void clear();

	public boolean isEmpty();

	public int numPoints();

	public void addPoint(Point pt);

	public void removePoint(Point pt);

	public void removeElementAt(int i);

	public Point getPoint(int index);

	public void setPoint(int index, Point pt);

	public void setPoint(int index, int x, int y);

	public Point getSecond();

	public Point getSecondToLast();

}
