/*
 * The JTS Topology Suite is a collection of Java classes that
 * implement the fundamental operations required to validate a given
 * geo-spatial data set to a known topological specification.
 *
 * Copyright (C) 2001 Vivid Solutions
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * For more information, contact:
 *
 *     Vivid Solutions
 *     Suite #1A
 *     2328 Government Street
 *     Victoria BC  V8T 5G5
 *     Canada
 *
 *     (250)385-6040
 *     www.vividsolutions.com
 */

package quadEdge;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import util.CGObserver;
import cg.Polygon;
import cg.Vertex;

/**
 * A class that represents the edge data structure which implements the quadedge
 * algebra. The quadedge algebra was described in a well-known paper by Guibas
 * and Stolfi,
 * "Primitives for the manipulation of general subdivisions and the computation of Voronoi diagrams"
 * , <i>ACM Transactions on Graphics</i>, 4(2), 1985, 75-123.
 * <p>
 * Each edge object is part of a quartet of 4 edges, linked via their
 * <tt>rot</tt> references. Any edge in the group may be accessed using a series
 * of {@link #getDual()} operations. Quadedges in a subdivision are linked
 * together via their <tt>next</tt> references. The linkage between the quadedge
 * quartets determines the topology of the subdivision.
 * <p>
 * The edge class does not contain separate information for vertices or faces; a
 * vertex is implicitly defined as a ring of edges (created using the
 * <tt>next</tt> field).
 * 
 * @author David Skea
 * @author Martin Davis
 */
public class QuadEdge<D> implements Polygon {

	private static final long serialVersionUID = -3755403303739498466L;
	private Vertex first, last;
	// the dual of this edge, directed from right to left
	private QuadEdge<D> dual;
	private Vertex vertex; // The vertex that this edge represents
	private QuadEdge<D> next; // A reference to a connected edge
	private D data = null;

	// private int visitedKey = 0;

	/**
	 * Creates a new QuadEdge quartet from {@link Vertex} o to {@link Vertex} d.
	 * 
	 * @param o
	 *            the origin Vertex
	 * @param d
	 *            the destination Vertex
	 * @return the new QuadEdge quartet
	 */
	public static QuadEdge<Vertex> makeEdge(Vertex o, Vertex d) {
		QuadEdge<Vertex> q0 = new QuadEdge<Vertex>();
		QuadEdge<Vertex> q1 = new QuadEdge<Vertex>();
		QuadEdge<Vertex> q2 = new QuadEdge<Vertex>();
		QuadEdge<Vertex> q3 = new QuadEdge<Vertex>();

		q0.dual = q1;
		q1.dual = q2;
		q2.dual = q3;
		q3.dual = q0;

		q0.setNext(q0);
		q1.setNext(q3);
		q2.setNext(q2);
		q3.setNext(q1);

		QuadEdge<Vertex> base = q0;
		base.setOrigin(o);
		base.setDestination(d);
		return base;
	}

	/**
	 * Creates a new QuadEdge connecting the destination of a to the origin of
	 * b, in such a way that all three have the same left face after the
	 * connection is complete. Additionally, the data pointers of the new edge
	 * are set.
	 * 
	 * @return the connected edge.
	 */
	public static QuadEdge<Vertex> connect(QuadEdge<Vertex> a,
			QuadEdge<Vertex> b) {
		QuadEdge<Vertex> e = makeEdge(a.dest(), b.getOrigin());
		splice(e, a.getLNext());
		splice(e.getSymmetric(), b);
		return e;
	}

	/**
	 * Splices two edges together or apart. Splice affects the two edge rings
	 * around the origins of a and b, and, independently, the two edge rings
	 * around the left faces of <tt>a</tt> and <tt>b</tt>. In each case, (i) if
	 * the two rings are distinct, Splice will combine them into one, or (ii) if
	 * the two are the same ring, Splice will break it into two separate pieces.
	 * Thus, Splice can be used both to attach the two edges together, and to
	 * break them apart.
	 * 
	 * @param a
	 *            an edge to splice
	 * @param b
	 *            an edge to splice
	 * 
	 */
	public static void splice(QuadEdge<Vertex> a, QuadEdge<Vertex> b) {
		QuadEdge<Vertex> alpha = a.getONext().getDual();
		QuadEdge<Vertex> beta = b.getONext().getDual();

		QuadEdge<Vertex> t1 = b.getONext();
		QuadEdge<Vertex> t2 = a.getONext();
		QuadEdge<Vertex> t3 = beta.getONext();
		QuadEdge<Vertex> t4 = alpha.getONext();

		a.setNext(t1);
		b.setNext(t2);
		alpha.setNext(t3);
		beta.setNext(t4);
	}

	/**
	 * Turns an edge counterclockwise inside its enclosing quadrilateral.
	 * 
	 * @param e
	 *            the quadedge to turn
	 */
	public static void swap(QuadEdge<Vertex> e) {
		QuadEdge<Vertex> a = e.getOPrev();
		QuadEdge<Vertex> b = e.getSymmetric().getOPrev();
		splice(e, a);
		splice(e.getSymmetric(), b);
		splice(e, a.getLNext());
		splice(e.getSymmetric(), b.getLNext());
		e.setOrigin(a.dest());
		e.setDestination(b.dest());
	}

	/**
	 * Quadedges must be made using {@link makeEdge}, to ensure proper
	 * construction.
	 */
	private QuadEdge() {

	}

	/**
	 * Gets the primary edge of this quadedge and its <tt>sym</tt>. The primary
	 * edge is the one for which the origin and destination coordinates are
	 * ordered according to the standard {@link Coordinate} ordering
	 * 
	 * @return the primary quadedge
	 */
	public QuadEdge<D> getPrimary() {
		if (getOrigin().compareTo(dest()) <= 0)
			return this;
		else
			return getSymmetric();
	}

	/**
	 * Sets the external data value for this edge.
	 * 
	 * @param data
	 *            an object containing external data
	 */
	public void setData(D data) {
		this.data = data;
	}

	/**
	 * Gets the external data value for this edge.
	 * 
	 * @return the data object
	 */
	public D getData() {
		return data;
	}

	/**
	 * Marks this quadedge as being deleted. This does not free the memory used
	 * by this quadedge quartet, but indicates that this edge no longer
	 * participates in a subdivision.
	 * 
	 */
	public void delete() {
		dual = null;
	}

	/**
	 * Tests whether this edge has been deleted.
	 * 
	 * @return true if this edge has not been deleted.
	 */
	public boolean isLive() {
		return dual != null;
	}

	/**
	 * Sets the connected edge
	 * 
	 * @param nextEdge
	 *            edge
	 */
	public void setNext(QuadEdge<D> next) {
		this.next = next;
	}

	/***************************************************************************
	 * QuadEdge Algebra
	 *************************************************************************** 
	 */

	/**
	 * Gets the dual of this edge, directed from its right to its left.
	 * 
	 * @return the rotated edge
	 */
	public final QuadEdge<D> getDual() {
		return dual;
	}

	/**
	 * Gets the dual of this edge, directed from its left to its right.
	 * 
	 * @return the inverse rotated edge.
	 */
	public final QuadEdge<D> getDualInverse() {
		return dual.getSymmetric();
	}

	/**
	 * Gets the edge from the destination to the origin of this edge.
	 * 
	 * @return the sym of the edge
	 */
	public final QuadEdge<D> getSymmetric() {
		return dual.dual;
	}

	/**
	 * Gets the next CCW edge around the origin of this edge.
	 * 
	 * @return the next linked edge.
	 */
	public final QuadEdge<D> getONext() {
		return next;
	}

	/**
	 * Gets the next CW edge around (from) the origin of this edge.
	 * 
	 * @return the previous edge.
	 */
	public final QuadEdge<D> getOPrev() {
		return dual.next.dual;
	}

	/**
	 * Gets the next CCW edge around (into) the destination of this edge.
	 * 
	 * @return the next destination edge.
	 */
	public final QuadEdge<D> getDNext() {
		return this.getSymmetric().getONext().getSymmetric();
	}

	/**
	 * Gets the next CW edge around (into) the destination of this edge.
	 * 
	 * @return the previous destination edge.
	 */
	public final QuadEdge<D> getDPrev() {
		return this.getDualInverse().getONext().getDualInverse();
	}

	/**
	 * Gets the CCW edge around the left face following this edge.
	 * 
	 * @return the next left face edge.
	 */
	public final QuadEdge<D> getLNext() {
		return this.getDualInverse().getONext().getDual();
	}

	/**
	 * Gets the CCW edge around the left face before this edge.
	 * 
	 * @return the previous left face edge.
	 */
	public final QuadEdge<D> getLPrev() {
		return next.getSymmetric();
	}

	/**
	 * Gets the edge around the right face ccw following this edge.
	 * 
	 * @return the next right face edge.
	 */
	public final QuadEdge<D> getRNext() {
		return dual.next.getDualInverse();
	}

	/**
	 * Gets the edge around the right face ccw before this edge.
	 * 
	 * @return the previous right face edge.
	 */
	public final QuadEdge<D> getRPrev() {
		return this.getSymmetric().getONext();
	}

	/***********************************************************************************************
	 * Data Access
	 **********************************************************************************************/
	/**
	 * Sets the vertex for this edge's origin
	 * 
	 * @param o
	 *            the origin vertex
	 */
	void setOrigin(Vertex o) {
		vertex = o;
	}

	/**
	 * Sets the vertex for this edge's destination
	 * 
	 * @param d
	 *            the destination vertex
	 */
	void setDestination(Vertex d) {
		getSymmetric().setOrigin(d);
	}

	/**
	 * Gets the vertex for the edge's origin
	 * 
	 * @return the origin vertex
	 */
	public final Vertex getOrigin() {
		return vertex;
	}

	/**
	 * Gets the vertex for the edge's destination
	 * 
	 * @return the destination vertex
	 */
	public final Vertex dest() {
		return getSymmetric().getOrigin();
	}

	@Override
	public void addObserver(CGObserver o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addObservers(List<CGObserver> observers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeObserver(CGObserver o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAllObservers() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CGObserver> getObservers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFirst(Vertex e) {
		if (first == null) {
			makeEdge(e, e);
			first = e;
			last = e;
		} else {
			makeEdge(e, first);
			first = e;
		}
	}

	@Override
	public void addLast(Vertex e) {
		if (last == null) {
			makeEdge(e, e);
			last = e;
			first = e;
		} else {
			makeEdge(last, e);
			last = e;
		}
	}

	@Override
	public boolean offerFirst(Vertex e) {
		try {
			addFirst(e);
		} catch (IllegalStateException ex) {
			return false;
		}
		return true;
	}

	@Override
	public boolean offerLast(Vertex e) {
		try {
			addLast(e);
		} catch (IllegalStateException ex) {
			return false;
		}
		return true;
	}

	@Override
	public Vertex removeFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex removeLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex pollFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex pollLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex getFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex peekFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex peekLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean add(Vertex e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offer(Vertex e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vertex remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void push(Vertex e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vertex pop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<Vertex> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Vertex> descendingIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Vertex> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Vertex pt) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vertex getSecond() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex getSecondToLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Vertex> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vertex get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex set(int index, Vertex element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, Vertex element) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vertex remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<Vertex> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<Vertex> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vertex> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}