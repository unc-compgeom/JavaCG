package cg;

import java.util.List;

import util.CGObserver;

public interface Polygon extends PointSet  {
	public Line getLine(int lineNum);
	public void addObservers(List<CGObserver> observers);

}
