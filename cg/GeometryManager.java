package cg;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import util.CGObserver;

public class GeometryManager {
	private static List<CGObserver> observers = Collections
			.synchronizedList(new LinkedList<CGObserver>());
	private static List<Drawable> dispersedObjects = Collections
			.synchronizedList(new LinkedList<Drawable>());
	private static int size = 1;
	private static int delay = 250;
	private static final int LARGESIZE = 5;
	private static final int SMALLSIZE = 1;

	public static void addObserver(CGObserver o) {
		observers.add(o);
		for (Drawable d : dispersedObjects) {
			d.addObserver(o);
		}
	}

	private static Drawable buildGeometry(Drawable d) {
		d.addObservers(observers);
		dispersedObjects.add(d);
		return d;
	}

	/**
	 * Remove one <tt>Drawable</tt> object from the set of drawn objects.
	 * 
	 * @param d
	 *            The <tt>Drawable</tt> to remove.
	 */
	public static void destroyGeometry(Drawable d) {
		d.removeAllObservers();
		dispersedObjects.remove(d);
		d = null;
	}

	/**
	 * Get all <tt>Drawable</tt> objects currently managed by the
	 * <tt>GeometryManager</tt>.
	 * 
	 * @return a <tt>List</tt> of <tt>Drawable</tt> geometry
	 */
	public static List<Drawable> getAllGeometry() {
		return dispersedObjects;
	}

	/**
	 * Creates a new <tt>Circle</tt> initialized with a center point and radius.
	 * This factory constructor automatically registers observers with the
	 * object and sets its draw size.
	 * 
	 * @param x
	 *            Center x-coordinate
	 * @param y
	 *            Center y-coordinate
	 * @param radiusSquared
	 * @return <tt>Circle</tt> object
	 */
	public static Circle getCircle(int x, int y, int radiusSquared) {
		Circle c = new CircleComponent(x, y, radiusSquared);
		return (Circle) buildGeometry(c);
	}

	/**
	 * Creates a new <tt>Circle</tt> initialized with the
	 * <code>VertexSet vertices</code> that are on the circumference of the
	 * circle. This factory constructor automatically registers observers with
	 * the object and sets its draw size.
	 * 
	 * @param vertices
	 *            Vertices on the circumference of the circle.
	 * @return <tt>Circle</tt> object
	 */
	public static Circle getCircle(VertexSet vertices) {
		Circle c = new CircleComponent(vertices);
		return (Circle) buildGeometry(c);
	}

	/**
	 * Creates a new <tt>HalfEdge</tt>. Parameters must be set manually after
	 * calling this method. This factory constructor automatically registers
	 * observers with the object and sets its draw size.
	 * 
	 * @return an uninitialized <tt>HalfEdge</tt> object
	 */
	public static HalfEdge getHalfEdge() {
		HalfEdge he = new HalfEdgeComponent();
		return (HalfEdge) buildGeometry(he);
	}

	/**
	 * Get the <tt>CGObservers</tt> that are observing all instances of cg
	 * objects.
	 * 
	 * @return a List of <tt>CGObservers</tt>
	 */
	public static List<CGObserver> getObservers() {
		return observers;
	}

	/**
	 * Creates a new empty <tt>Polygon</tt>. This factory constructor
	 * automatically registers observers with the object and sets its draw size.
	 * 
	 * @return an empty <tt>Polygon</tt> object
	 */
	public static Polygon getPolygon() {
		Polygon p = new PolygonComponent();
		return (Polygon) buildGeometry(p);
	}

	/**
	 * Creates a <tt>Vertex</tt> whose location is given by <code>x</code> and
	 * <code>y</code>. This factory constructor automatically registers
	 * observers with the object and sets its draw size.
	 * 
	 * @param x
	 *            the x-coordinate of the vertex
	 * @param y
	 *            the y-coordinate of the vertex
	 * @return a <tt>Vertex</tt> object
	 */
	public static Vertex getVertex(int x, int y) {
		Vertex v = new VertexComponent(x, y);
		return (Vertex) buildGeometry(v);
	}

	/**
	 * Creates a <tt>Vertex</tt> whose location is given by <code>x</code> and
	 * <code>y</code> and whose incident edge is given by <code>incident</code>.
	 * This factory constructor automatically registers observers with the
	 * object and sets its draw size.
	 * 
	 * @param x
	 *            the x-coordinate of the vertex
	 * @param y
	 *            the y-coordinate of the vertex
	 * @param incident
	 *            the edge incident on this vertex
	 * @return a <tt>Vertex</tt> object
	 */
	public static Vertex getVertex(int x, int y, HalfEdge incident) {
		Vertex v = new VertexComponent(x, y, incident);
		return (Vertex) buildGeometry(v);
	}

	/**
	 * Creates an empty <tt>VertexSet</tt>. This factory constructor
	 * automatically registers observers with the object and sets its draw size.
	 * 
	 * @return a <tt>VertexSet</tt> object
	 */
	public static VertexSet getVertexSet() {
		VertexSet vs = new VertexSetComponent();
		return (VertexSet) buildGeometry(vs);
	}

	/**
	 * Remove all geometry from the GeometryManager's records. This operation
	 * also removes all observers of all geometry objects.
	 */
	public static void removeAllGeometry() {
		for (Drawable d : dispersedObjects) {
			d.removeAllObservers();
			d = null;
		}
		dispersedObjects.removeAll(dispersedObjects);
		for (CGObserver o : observers) {
			o.update(null);
		}
	}

	/**
	 * Removes all observers from the geometry objects but keeps the geometry
	 * objects intact.
	 */
	public static void removeAllObservers() {
		for (Drawable d : dispersedObjects) {
			d.removeAllObservers();
		}
		observers.removeAll(observers);
	}

	/**
	 * Remove <tt>CGObserver o</tt> from all geometry objects.
	 * 
	 * @param o
	 *            The <tt>CGObserver</tt> to remove
	 */
	public static void removeObserver(CGObserver o) {
		for (Drawable d : dispersedObjects) {
			d.removeObserver(o);
		}
		observers.remove(o);
	}

	/**
	 * Set the one delay to rule them all. All geometry objects will animate at
	 * this speed.
	 * 
	 * @param i
	 *            The animation delay in nanoseconds
	 */
	public static void setDelay(int i) {
		delay = i;
	}

	/**
	 * Set the draw size for all geometry objects. If the current draw size is
	 * small, it becomes large. If the current draw size is large, it becomes
	 * small.
	 */
	public static void setSmallLarge() {
		if (size == SMALLSIZE) {
			size = LARGESIZE;
		} else {
			size = SMALLSIZE;
		}
	}

	public static int getSize() {
		return size;
	}

	/**
	 * Get the delay object that regulates the speed at which geometry is
	 * animated.
	 * 
	 * @return a <tt>Delay</tt> object.
	 */
	public static int getDelay() {
		return delay;
	}
}
