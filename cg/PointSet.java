package cg;

import java.util.Collection;
import java.util.Deque;
import java.util.List;

public interface PointSet extends Drawable, List<Point>, Deque<Point>,
		Collection<Point>, java.io.Serializable {

	@Override
	public void clear();

	public Point getSecond();

	public Point getSecondToLast();

	public void remove(Point pt);

	public void addNoDelay(Point v);

}
