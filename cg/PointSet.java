package cg;

import java.util.Deque;
import java.util.List;

import util.CGObservable;

public interface PointSet extends CGObservable, List<Point>, Deque<Point>,
		Cloneable, java.io.Serializable {

	public void remove(Point pt);

	public void removeAll();

	public Point getSecond();

	public Point getSecondToLast();

}
