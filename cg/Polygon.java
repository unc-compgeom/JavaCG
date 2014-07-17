package cg;

import util.Drawable;

import java.util.Deque;

/**
 * A Polygon is a @link PointSet} with edges between its points.
 * 
 * @author Vance Miller
 * 
 */
public interface Polygon extends Drawable, Deque<Point>, PointSet, Cloneable {

}
