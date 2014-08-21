package cg;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import util.CGObserver;
import util.Drawable;

/**
 * This class consists of many static methods that enable an algorithm designer
 * to get access to geometry objects. Call
 * <tt>GeometryManager.new[Object]()</tt> to get an instance of a {@link cg}
 * object that will draw itself when manipulated by an algorithm.
 *
 * @author Vance Miller
 */
public class GeometryManager {
	public static void addObserver(final CGObserver o) {
		synchronized (observers) {
			observers.add(o);
		}
	}

	/**
	 * Build the bare geometry object by setting universal parameters. Invoke
	 * this method inside a factory constructor before returning the new
	 * geometry.
	 *
	 * @param d
	 *            the geometry to be built
	 * @return the finished geometry
	 */
	private static Drawable buildGeometry(final Drawable d) {
		synchronized (dispersedObjects) {
			dispersedObjects.add(d);
			return d;
		}
	}

	/**
	 * Remove one {@link Drawable} object from the set of drawn objects.
	 *
	 * @param d
	 *            The <tt>Drawable</tt> to remove.
	 */
	public static void destroy(final Drawable d) {
		synchronized (dispersedObjects) {
			dispersedObjects.remove(d);
		}
		notifyAllObservers();
	}

	/**
	 * Get all {@link Drawable} objects currently managed by the
	 * <tt>GeometryManager</tt>.
	 *
	 * @return a <tt>List</tt> of <tt>Drawable</tt> geometry
	 */
	public static List<Drawable> getAllGeometry() {
		return dispersedObjects;
	}

	/**
	 * Get the animation delay in milliseconds.
	 *
	 * @return the animation delay
	 */
	public static int getDelay() {
		return delay;
	}

	/**
	 * Get the {@link CGObserver}s that are observing all instances of cg
	 * objects.
	 *
	 * @return a List of <tt>CGObserver</tt>s
	 */
	public static List<CGObserver> getObservers() {
		return observers;
	}

	/**
	 * Gets the draw size for all geometry objects (to be used in the
	 * <tt>paintComponent()</tt> method).
	 *
	 * @return the draw size
	 */
	public static int getSize() {
		return size;
	}

	/**
	 * Creates a new {@link Circle} initialized with the same properties as
	 * <code>Circle c</code>. This factory constructor automatically registers
	 * observers with the object and sets its draw size.
	 *
	 * @param c
	 *            the circle to clone Points on the circumference of the circle.
	 * @return <tt>Circle</tt> object
	 */
	public static Circle newCircle(final Circle c) {
		final Circle ci = new CircleComponent(c);
		return (Circle) buildGeometry(ci);
	}

	/**
	 * Creates a new {@link Circle} initialized with the
	 * <code>PointSet points</code> that are on the circumference of the circle.
	 * This factory constructor automatically registers observers with the
	 * object and sets its draw size.
	 *
	 * @param points
	 *            Points on the circumference of the circle.
	 * @return <tt>Circle</tt> object
	 */
	public static Circle newCircle(final List<Point> points) {
		final Circle c = new CircleComponent(points);
		return (Circle) buildGeometry(c);
	}

	/**
	 * Creates a {@link Point} whose location is given by <code>x</code> and
	 * <code>y</code>. This factory constructor automatically registers
	 * observers with the object and sets its draw size.
	 *
	 * @param x
	 *            the x-coordinate of the point
	 * @param y
	 *            the y-coordinate of the point
	 * @return a <tt>Point</tt> object
	 */
	public static Point newPoint(final float x, final float y) {
		final Point v = new PointComponent(x, y);
		return (Point) buildGeometry(v);
	}

	/**
	 * Creates a {@link Point} whose location is given by Point <code>p</code>.
	 * This factory constructor automatically registers observers with the
	 * object and sets its draw size.
	 *
	 * @param p
	 *            the point that contains the location of the new point
	 * @return a <tt>Point</tt> object
	 */
	public static Point newPoint(final Point p) {
		final Point pt = new PointComponent(p);
		return (Point) buildGeometry(pt);
	}

	/**
	 * Creates an empty {@link PointSet}. This factory constructor automatically
	 * registers observers with the object and sets its draw size.
	 *
	 * @return a <tt>PointSet</tt> object
	 */
	public static PointSet newPointSet() {
		final PointSet vs = new PointSetComponent();
		return (PointSet) buildGeometry(vs);
	}

	/**
	 * Creates a {@link PointSet} populated with <tt>points</tt>. This factory
	 * constructor automatically registers observers with the object and sets
	 * its draw size.
	 *
	 * @param points
	 *            A <tt>PointSet</tt> to clone and insert into the new
	 *            </tt>PointSet</tt>
	 * @return A clone of the <tt>points</tt>
	 */
	public static PointSet newPointSet(final PointSet points) {
		final PointSet s = new PointSetComponent();
		s.setColor(points.getColor());
		points.forEach(s::addNoDelay);
		return (PointSet) buildGeometry(s);
	}

	/**
	 * Creates a new empty {@link Polygon}. This factory constructor
	 * automatically registers observers with the object and sets its draw size.
	 *
	 * @return an empty <tt>Polygon</tt> object
	 */
	public static Polygon newPolygon() {
		final Polygon p = new PolygonComponent();
		return (Polygon) buildGeometry(p);
	}

	/**
	 * Creates a new empty {@link QuadEdge}. This factory constructor
	 * automatically registers observers with the object and sets its draw size.
	 *
	 * @return an empty <tt>QuadEdge</tt> object
	 */
	public static QuadEdge newQuadEdge() {
		final QuadEdge q = new QuadEdgeComponent();
		return (QuadEdge) buildGeometry(q);
	}

	/**
	 * Creates a new {@link Segment} from <tt>Point(x1, y1)</tt> to
	 * <tt>Point(x2, y2)</tt>. This factory constructor automatically registers
	 * observers with the object and sets its draw size.
	 *
	 * @param x1
	 *            X-coordinate of tail
	 * @param y1
	 *            Y-coordinate of tail
	 * @param x2
	 *            X-coordinate of head
	 * @param y2
	 *            Y-coordinate of head
	 * @return a <tt>Point</tt> object
	 */
	public static Segment newSegment(final int x1, final int y1, final int x2,
			final int y2) {
		final Segment v = new SegmentComponent(x1, y1, x2, y2);
		return (Segment) buildGeometry(v);
	}

	/**
	 * Creates a new {@link Segment} from <tt>v1</tt> to <tt>v2</tt>. This
	 * factory constructor automatically registers observers with the object and
	 * sets its draw size.
	 *
	 * @param p1
	 *            The tail of the segment
	 * @param p2
	 *            The head of the segment
	 * @return a <tt>Segment</tt> object
	 */
	public static Segment newSegment(final Point p1, final Point p2) {
		final Segment s = new SegmentComponent(p1, p2);
		return (Segment) buildGeometry(s);
	}

	/**
	 * Creates a new {@link Subdivision} for a triangulation. The Subdivision is
	 * initialized with three vertices. This factory constructor automatically
	 * registers observers with the object and sets its draw size
	 *
	 * @return a <tt>Subdivision</tt> object
	 */
	public static Subdivision newSubdivision() {
		final Subdivision s = new SubdivisionComponent();
		return (Subdivision) buildGeometry(s);
	}

	/**
	 * Notifies all observers of dispersed geometry.
	 */
	private static void notifyAllObservers() {
		synchronized (observers) {
			for (final CGObserver o : observers) {
				o.tellToDraw();
			}
		}
	}

	/**
	 * Notifies all observers of dispersed geometry. This method should be
	 * called by geometry objects when their observable parameters are modified.
	 */
	static void notifyObservers() {
		notifyAllObservers();
		try {
			Thread.sleep(delay);
		} catch (final InterruptedException ignored) {
		}
	}

	/**
	 * Notifies all observers of dispersed geometry. This method should be
	 * called by geometry objects when their observable parameters are modified
	 * and the respective tellToDraw should not be accompanied by an animation
	 * delay.
	 */
	static void notifyObserversNoDelay() {
		notifyAllObservers();
	}

	/**
	 * Remove all geometry from the GeometryManager's records. This operation
	 * also removes all observers of all geometry objects.
	 */
	public static void removeAllGeometry() {
		synchronized (dispersedObjects) {
			dispersedObjects.removeAll(dispersedObjects);
		}
		notifyAllObservers();
	}

	/**
	 * Removes all observers from the geometry objects but keeps the geometry
	 * objects intact.
	 */
	public static void removeAllObservers() {
		synchronized (observers) {
			observers.removeAll(observers);
		}
	}

	/**
	 * Remove {@link CGObserver} <tt>o</tt> from all geometry objects.
	 *
	 * @param o
	 *            The <tt>CGObserver</tt> to remove
	 */
	public static void removeObserver(final CGObserver o) {
		synchronized (observers) {
			observers.remove(o);
		}
	}

	/**
	 * Set the one delay to rule them all. All geometry objects will animate at
	 * this speed.
	 *
	 * @param i
	 *            The animation delay in nanoseconds
	 */
	public static void setDelay(final int i) {
		delay = i;
	}

	/**
	 * Set the draw size for all geometry objects. If the current draw size is
	 * small, it becomes large. If the current draw size is large, it becomes
	 * small.
	 */
	public static void sizeToggle() {
		if (size == SMALLSIZE) {
			size = LARGESIZE;
		} else {
			size = SMALLSIZE;
		}
		notifyAllObservers();
	}

	private static int delay = 150;

	private static final List<Drawable> dispersedObjects = Collections
			.synchronizedList(new LinkedList<>());

	private static final int LARGESIZE = 5;

	private static final List<CGObserver> observers = Collections
			.synchronizedList(new LinkedList<>());

	private static int size = 1;

	private static final int SMALLSIZE = 1;
}
