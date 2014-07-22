package cg;

import util.CGObserver;
import util.Drawable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class consists of many static methods that enable an algorithm designer
 * to get access to geometry objects. Call
 * <tt>GeometryManager.new[Object]()</tt> to get an instance of a {@link cg}
 * object that will draw itself when manipulated by an algorithm.
 *
 * @author Vance Miller
 */
public class GeometryManager {
	private static int delay = 150;
	private static final List<Drawable> dispersedObjects = Collections
			.synchronizedList(new LinkedList<Drawable>());
	private static final int LARGESIZE = 5;
	private static final List<CGObserver> observers = Collections
			.synchronizedList(new LinkedList<CGObserver>());
	private static int size = 1;
	private static final int SMALLSIZE = 1;

	public synchronized static void addObserver(CGObserver o) {
		synchronized (observers) {
			observers.add(o);
		}
	}

	/**
	 * Build the bare geometry object by setting universal parameters. Invoke
	 * this method inside a factory constructor before returning the new
	 * geometry.
	 *
	 * @param d the geometry to be built
	 * @return the finished geometry
	 */
	private static Drawable buildGeometry(Drawable d) {
		synchronized (dispersedObjects) {
			dispersedObjects.add(d);
			return d;
		}
	}

	/**
	 * Remove one {@link Drawable} object from the set of drawn objects.
	 *
	 * @param d The <tt>Drawable</tt> to remove.
	 */
	public synchronized static void destroy(Drawable d) {
		dispersedObjects.remove(d);
		notifyAllObservers(d, false);
	}

	/**
	 * Get all {@link Drawable} objects currently managed by the
	 * <tt>GeometryManager</tt>.
	 *
	 * @return a <tt>List</tt> of <tt>Drawable</tt> geometry
	 */
	public synchronized static List<Drawable> getAllGeometry() {
		return dispersedObjects;
	}

	/**
	 * Get the animation delay in milliseconds.
	 *
	 * @return the animation delay
	 */
	public synchronized static int getDelay() {
		return delay;
	}

	/**
	 * Get the {@link CGObserver}s that are observing all instances of cg
	 * objects.
	 *
	 * @return a List of <tt>CGObserver</tt>s
	 */
	public synchronized static List<CGObserver> getObservers() {
		return observers;
	}

	/**
	 * Gets the draw size for all geometry objects (to be used in the
	 * <tt>paintComponent()</tt> method).
	 *
	 * @return the draw size
	 */
	public synchronized static int getSize() {
		return size;
	}

	/**
	 * Creates a new {@link Circle} initialized with the same properties as
	 * <code>Circle c</code>. This factory constructor automatically registers
	 * observers with the object and sets its draw size.
	 *
	 * @param c the circle to clone Points on the circumference of the circle.
	 * @return <tt>Circle</tt> object
	 */
	public synchronized static Circle newCircle(Circle c) {
		Circle ci = new CircleComponent(c);
		return (Circle) buildGeometry(ci);
	}

	/**
	 * Creates a new {@link Circle} initialized with the
	 * <code>PointSet points</code> that are on the circumference of the circle.
	 * This factory constructor automatically registers observers with the
	 * object and sets its draw size.
	 *
	 * @param points Points on the circumference of the circle.
	 * @return <tt>Circle</tt> object
	 */
	public synchronized static Circle newCircle(List<Point> points) {
		Circle c = new CircleComponent(points);
		return (Circle) buildGeometry(c);
	}

	/**
	 * Creates a {@link Point} whose location is given by <code>x</code> and
	 * <code>y</code>. This factory constructor automatically registers
	 * observers with the object and sets its draw size.
	 *
	 * @param x the x-coordinate of the point
	 * @param y the y-coordinate of the point
	 * @return a <tt>Point</tt> object
	 */
	public synchronized static Point newPoint(float x, float y) {
		Point v = new PointComponent(x, y);
		return (Point) buildGeometry(v);
	}

	/**
	 * Creates a {@link Point} whose location is given by Point <code>p</code>.
	 * This factory constructor automatically registers observers with the
	 * object and sets its draw size.
	 *
	 * @param p the point that contains the location of the new point
	 * @return a <tt>Point</tt> object
	 */
	public synchronized static Point newPoint(Point p) {
		Point pt = new PointComponent(p);
		return (Point) buildGeometry(pt);
	}

	/**
	 * Creates an empty {@link PointSet}. This factory constructor automatically
	 * registers observers with the object and sets its draw size.
	 *
	 * @return a <tt>PointSet</tt> object
	 */
	public synchronized static PointSet newPointSet() {
		PointSet vs = new PointSetComponent();
		return (PointSet) buildGeometry(vs);
	}

	/**
	 * Creates a {@link PointSet} populated with <tt>points</tt>. This factory
	 * constructor automatically registers observers with the object and sets
	 * its draw size.
	 *
	 * @param points A <tt>PointSet</tt> to clone and insert into the new
	 *               </tt>PointSet</tt>
	 * @return A clone of the <tt>points</tt>
	 */
	public synchronized static PointSet newPointSet(PointSet points) {
		PointSet s = new PointSetComponent();
		s.setColor(points.getColor());
		for (Point point : points) {
			s.addNoDelay(point);
		}
		return (PointSet) buildGeometry(s);
	}

	/**
	 * Creates a new empty {@link Polygon}. This factory constructor
	 * automatically registers observers with the object and sets its draw size.
	 *
	 * @return an empty <tt>Polygon</tt> object
	 */
	public synchronized static Polygon newPolygon() {
		Polygon p = new PolygonComponent();
		return (Polygon) buildGeometry(p);
	}

	/**
	 * Creates a new empty {@link QuadEdge}. This factory constructor
	 * automatically registers observers with the object and sets its draw size.
	 *
	 * @return an empty <tt>QuadEdge</tt> object
	 */
	public synchronized static QuadEdge newQuadEdge() {
		QuadEdge q = new QuadEdgeComponent();
		return (QuadEdge) buildGeometry(q);
	}

	/**
	 * Creates a new {@link Segment} from <tt>Point(x1, y1)</tt> to
	 * <tt>Point(x2, y2)</tt>. This factory constructor automatically registers
	 * observers with the object and sets its draw size.
	 *
	 * @param x1 X-coordinate of tail
	 * @param y1 Y-coordinate of tail
	 * @param x2 X-coordinate of head
	 * @param y2 Y-coordinate of head
	 * @return a <tt>Point</tt> object
	 */
	public synchronized static Segment newSegment(int x1, int y1, int x2, int y2) {
		Segment v = new SegmentComponent(x1, y1, x2, y2);
		return (Segment) buildGeometry(v);
	}

	/**
	 * Creates a new {@link Segment} from <tt>v1</tt> to <tt>v2</tt>. This
	 * factory constructor automatically registers observers with the object and
	 * sets its draw size.
	 *
	 * @param p1 The tail of the segment
	 * @param p2 The head of the segment
	 * @return a <tt>Segment</tt> object
	 */
	public synchronized static Segment newSegment(Point p1, Point p2) {
		Segment s = new SegmentComponent(p1, p2);
		return (Segment) buildGeometry(s);
	}

	/**
	 * Creates a new {@link Subdivision} for a triangulation. The Subdivision is
	 * initialized with three vertices. This factory constructor automatically
	 * registers observers with the object and sets its draw size
	 *
	 * @return a <tt>Subdivision</tt> object
	 */
	public synchronized static Subdivision newSubdivision() {
		Subdivision s = new SubdivisionComponent();
		return (Subdivision) buildGeometry(s);
	}

	/**
	 * Notifies all observers of dispersed geometry.
	 */
	private static void notifyAllObservers(Drawable d, boolean isInsertion) {
		synchronized (observers) {
			for (CGObserver o : observers) {
				o.update(d, isInsertion);

			}
		}
	}

	/**
	 * Notifies all observers of the Drawable object. This method should be
	 * called by geometry objects when their observable parameters are modified.
	 */
	static void notifyObservers(Drawable d) {
		notifyAllObservers(d, true);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// continue without delay
		}
	}

	/**
	 * Notifies all observers of the Drawable. This method should be
	 * called by geometry objects when their observable parameters are modified
	 * and the respective update should not be accompanied by an animation
	 * delay.
	 */
	static void notifyObserversNoDelay(Drawable d) {
		notifyAllObservers(d, true);
	}

	/**
	 * Remove all geometry from the GeometryManager's records. This operation
	 * also removes all observers of all geometry objects.
	 */
	public synchronized static void removeAllGeometry() {
		synchronized (dispersedObjects) {
			dispersedObjects.removeAll(dispersedObjects);
		}
		notifyAllObservers(null, false);
	}

	/**
	 * Removes all observers from the geometry objects but keeps the geometry
	 * objects intact.
	 */
	public synchronized static void removeAllObservers() {
		synchronized (observers) {
			observers.removeAll(observers);
		}
	}

	/**
	 * Remove {@link CGObserver} <tt>o</tt> from all geometry objects.
	 *
	 * @param o The <tt>CGObserver</tt> to remove
	 */
	public synchronized static void removeObserver(CGObserver o) {
		synchronized (observers) {
			observers.remove(o);
		}
	}

	/**
	 * Set the one delay to rule them all. All geometry objects will animate at
	 * this speed.
	 *
	 * @param i The animation delay in nanoseconds
	 */
	public synchronized static void setDelay(int i) {
		delay = i;
	}

	/**
	 * Set the draw size for all geometry objects. If the current draw size is
	 * small, it becomes large. If the current draw size is large, it becomes
	 * small.
	 */
	public synchronized static void sizeToggle() {
		if (size == SMALLSIZE) {
			size = LARGESIZE;
		} else {
			size = SMALLSIZE;
		}
		notifyAllObservers(null, false);
	}
}
