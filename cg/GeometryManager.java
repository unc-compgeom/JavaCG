package cg;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import util.CGObserver;

// TODO  the entire GeometryManager might be unavailable while it is iterating over something in one of its submethods.... 

public class GeometryManager {
	private static int delay = 25;
	private static List<Drawable> dispersedObjects = Collections
			.synchronizedList(new LinkedList<Drawable>());
	private static final int LARGESIZE = 5;
	private static List<CGObserver> observers = Collections
			.synchronizedList(new LinkedList<CGObserver>());
	private static int size = 1;
	private static final int SMALLSIZE = 1;

	public static void addObserver(CGObserver o) {
		synchronized (observers) {
			observers.add(o);
		}
	}

	private static Drawable buildGeometry(Drawable d) {
		synchronized (dispersedObjects) {
			dispersedObjects.add(d);
			return d;
		}
	}

	/**
	 * Remove one <tt>Drawable</tt> object from the set of drawn objects.
	 * 
	 * @param d
	 *            The <tt>Drawable</tt> to remove.
	 */
	public static void destroyGeometry(Drawable d) {
		synchronized (dispersedObjects) {
			dispersedObjects.remove(d);
		}
		d = null;
		notifyAllObservers();
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
	 * Get the delay object that regulates the speed at which geometry is
	 * animated.
	 * 
	 * @return a <tt>Delay</tt> object.
	 */
	public static int getDelay() {
		return delay;
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
	 * Gets the draw size for all geometry objects (to be used in the
	 * <tt>paintComponent()</tt> method).
	 * 
	 * @return the draw size
	 */
	public static int getSize() {
		return size;
	}

	/**
	 * Creates a new Vector from <tt>Vertex(x1, y1)</tt> to
	 * <tt>Vertex(x2, y2)</tt>. This factory constructor automatically registers
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
	 * @return a <tt>Vertex</tt> object
	 */
	public static Vector getVector(int x1, int y1, int x2, int y2) {
		Vector v = new VectorComponent(x1, y1, x2, y2);
		return (Vector) buildGeometry(v);
	}

	/**
	 * Creates a new Vector from <tt>v1</tt> to <tt>v2</tt>. This factory
	 * constructor automatically registers observers with the object and sets
	 * its draw size.
	 * 
	 * @param v1
	 *            The tail of the vertex
	 * @param v2
	 *            The head of the vertex
	 * @return a <tt>Vertex</tt> object
	 */
	public static Vector getVector(Vertex v1, Vertex v2) {
		Vector v = new VectorComponent(v1, v2);
		return (Vector) buildGeometry(v);
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
	 * Creates a <tt>VertexSet</tt> populated with <tt>vertices</tt>. This
	 * factory constructor automatically registers observers with the object and
	 * sets its draw size.
	 * 
	 * @param vertices
	 *            A <tt>VertexSet</tt> to clone and insert into the new
	 *            </tt>VertexSet</tt>
	 * @return A clone of the <tt>vertices</tt>
	 */
	public static VertexSet getVertexSet(VertexSet vertices) {
		VertexSet vs = new VertexSetComponent();
		for (Vertex vertex : vertices) {
			vs.add(vertex.clone());
		}
		return (VertexSet) buildGeometry(vs);
	}

	private static void notifyAllObservers() {
		synchronized (observers) {
			for (CGObserver o : observers) {
				o.update(null);
			}
		}
	}

	protected static void notifyObservers() {
		notifyAllObservers();
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected static void notifyObserversNoDelay() {
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
	 * Remove <tt>CGObserver o</tt> from all geometry objects.
	 * 
	 * @param o
	 *            The <tt>CGObserver</tt> to remove
	 */
	public static void removeObserver(CGObserver o) {
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
		notifyAllObservers();
	}
}
