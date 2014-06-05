package cg;

import util.CGObservable;

public interface Line extends CGObservable {

	public Point getP1();

	public void setP1(Point p1);

	public Point getP2();

	public void setP2(Point p2);
}
