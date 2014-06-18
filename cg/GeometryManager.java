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

	public static void addObserver(CGObserver o) {
		observers.add(o);
	}

	public static void setSize(int size) {
		GeometryManager.size = size;
		for (Drawable d : dispersedObjects) {
			d.setSize(size);
		}
	}

	public static Circle getCircle(VertexSet vertices) {
		Circle c = new CircleComponent(vertices);
		return (Circle) buildGeometry(c);
	}

	public static Circle getCircle(int x, int y, int radiusSquared) {
		Circle c = new CircleComponent(x, y, radiusSquared);
		return (Circle) buildGeometry(c);
	}

	public static HalfEdge getHalfEdge() {
		HalfEdge he = new HalfEdgeComponent();
		return (HalfEdge) buildGeometry(he);
	}

	public static Polygon getPolygon() {
		Polygon p = new PolygonComponent();
		return (Polygon) buildGeometry(p);
	}

	public static Vertex getVertex(int x, int y) {
		Vertex v = new VertexComponent(x, y);
		return (Vertex) buildGeometry(v);
	}

	public static Vertex getVertex(int x, int y, HalfEdge incident) {
		Vertex v = new VertexComponent(x, y, incident);
		return (Vertex) buildGeometry(v);
	}

	public static VertexSet getVertexSet() {
		VertexSet vs = new VertexSetComponent();
		return (VertexSet) buildGeometry(vs);
	}

	private static Drawable buildGeometry(Drawable d) {
		d.addObservers(observers);
		d.setSize(size);
		dispersedObjects.add(d);
		return d;
	}

	public static void destroyGeometry(Drawable d) {
		d.removeAllObservers();
		dispersedObjects.remove(d);
		d = null;
	}

	public static List<Drawable> getAllGeometry() {
		return dispersedObjects;
	}

	public static List<CGObserver> getObservers() {
		return observers;
	}

	public static void removeObserver(CGObserver o) {
		observers.remove(o);
	}

	public static void removeAllObservers() {
		observers.removeAll(observers);
	}

	public static void removeAllGeometry() {
		for (Drawable d : dispersedObjects) {
			d.removeAllObservers();
			d = null;
		}
		dispersedObjects.removeAll(dispersedObjects);
	}
}
