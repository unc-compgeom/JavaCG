package cg;

import java.util.Deque;

import util.Drawable;

/**
 * A Polygon is a @link PointSet} with edges between its points.
 *
 * @author Vance Miller
 *
 */
public interface Polygon extends Drawable, Deque<Point>, PointSet, Cloneable {

}
