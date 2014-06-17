package cg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.LinkedBlockingQueue;

public class PolygonComponent extends AbstractGeometry implements Polygon {
	private static final long serialVersionUID = -1523644503244611934L;
	private Collection<HalfEdge> edges;
	private Face face;
	private VertexSet vertices;

	public PolygonComponent() {
		vertices = new VertexSetComponent();
		edges = new LinkedBlockingQueue<HalfEdge>();
		face = null;
		// TODO fix face
	}

	@Override
	public void add(int index, Vertex v) {
		// manipulate all the pointers
		// assume vertex v only contains x and y coordinates.
		Vertex after;
		Vertex before;
		if (size() == 0) {
			before = after = v;

			HalfEdge left = new HalfEdgeComponent();
			HalfEdge right = new HalfEdgeComponent();

			after.setIncident(left);
			before.setIncident(left);

			left.setIncidentFace(face);
			right.setIncidentFace(null);
			left.setTwin(right);
			right.setTwin(left);
			left.setOrigin(v);
			right.setOrigin(v);
			left.setNext(left);
			right.setNext(right);
			left.setPrevious(left);
			right.setPrevious(right);
			vertices.add(index, v);
			edges.add(left);
			edges.add(right);
			notifyObservers();
			return;
		}
		after = get(index >= size() ? index - size() : index);
		before = after.getIncident().getPrevious().getOrigin();

		HalfEdge newLeft = new HalfEdgeComponent();
		HalfEdge newRight = new HalfEdgeComponent();

		HalfEdge beforeL = before.getIncident();
		HalfEdge afterL = after.getIncident();
		HalfEdge beforeR = after.getIncident().getTwin();
		HalfEdge afterR = before.getIncident().getTwin();

		newLeft.setIncidentFace(face);
		newLeft.setNext(afterL);
		newLeft.setPrevious(beforeL);
		newLeft.setOrigin(v);
		newLeft.setTwin(newRight);

		newRight.setIncidentFace(null);
		newRight.setNext(afterR);
		newRight.setPrevious(beforeR);
		newRight.setOrigin(afterL.getOrigin());
		newRight.setTwin(newLeft);

		// edge construction complete
		edges.add(newLeft);
		edges.add(newRight);
		// vertex construction complete
		v.setIncident(newLeft);
		vertices.add(index, v);
		// update other pointers
		beforeL.setNext(newLeft);
		afterL.setPrevious(newLeft);
		beforeR.setNext(newRight);
		afterR.setPrevious(newRight);
		afterR.setOrigin(v);
		if (size() == 1) {
			beforeL.setPrevious(afterL);
			beforeR.setPrevious(afterR);
		}
		// finish
		notifyObservers();
	}

	@Override
	public boolean add(Vertex v) {
		add(0, v);
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends Vertex> c) {
		return addAll(0, c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Vertex> c) {
		for (Iterator<? extends Vertex> iterator = c.iterator(); iterator
				.hasNext();) {
			Vertex vertex = iterator.next();
			add(index, vertex);
			index++;
		}
		return true;
	}

	@Override
	public void addFirst(Vertex v) {
		add(0, v);
	}

	@Override
	public void addLast(Vertex v) {
		// the polygon is always closed, so this is analogous to addFirst();
		add(size(), v);
	}

	@Override
	public void clear() {
		vertices.removeAllObservers();
		vertices = new VertexSetComponent();
		edges = new HashSet<HalfEdge>();
		face = null;
		notifyObservers();
	}

	@Override
	public Polygon clone() {
		Polygon p = this.cloneEmpty();
		p.addAll(vertices);
		return p;
	}

	@Override
	public Polygon cloneEmpty() {
		Polygon p = new PolygonComponent();
		p.addObservers(getObservers());
		p.setSize(getSize());
		p.setColor(getColor());
		return p;
	}

	@Override
	public boolean contains(Object o) {
		return vertices.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return vertices.containsAll(c);
	}

	@Override
	public Iterator<Vertex> descendingIterator() {
		return vertices.descendingIterator();
	}

	@Override
	public Vertex element() {
		return get(0);
	}

	@Override
	public Vertex get(int index) {
		return vertices.get(index);
	}

	@Override
	public Vertex getFirst() {
		return get(0);
	}

	@Override
	public Vertex getLast() {
		return get(size() - 1);
	}

	@Override
	public Vertex getSecond() {
		return get(1);
	}

	@Override
	public Vertex getSecondToLast() {
		return get(size() - 2);
	}

	@Override
	public int indexOf(Object o) {
		ListIterator<Vertex> it = listIterator();
		Vertex v;
		while (it.hasNext()) {
			v = it.next();
			if (v.equals(o)) {
				return it.nextIndex() - 1;
			}
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<Vertex> iterator() {
		return vertices.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		ListIterator<Vertex> it = listIterator();
		Vertex v = null;
		while (it.hasNext()) {
			v = it.next();
		}
		while (it.hasPrevious()) {
			if (o.equals(v)) {
				return it.previousIndex() + 1;
			}
			v = it.previous();
		}
		return -1;
	}

	@Override
	public ListIterator<Vertex> listIterator() {
		return vertices.listIterator();
	}

	@Override
	public ListIterator<Vertex> listIterator(int index) {
		return vertices.listIterator(index);
	}

	@Override
	public boolean offer(Vertex v) {
		return offerLast(v);
	}

	@Override
	public boolean offerFirst(Vertex v) {
		addFirst(v);
		return true;
	}

	@Override
	public boolean offerLast(Vertex v) {
		addLast(v);
		return true;
	}

	@Override
	public void paintComponent(Graphics g) {
		for (int i = 0; i < size(); i++) {
			for (HalfEdge e : edges) {
				e.setSize(getSize());
				e.setColor(super.getColor());
				e.paintComponent(g);
			}
		}
	}

	@Override
	public Vertex peek() {
		return peekFirst();
	}

	@Override
	public Vertex peekFirst() {
		if (isEmpty()) {
			return null;
		}
		return getFirst();
	}

	@Override
	public Vertex peekLast() {
		if (isEmpty()) {
			return null;
		}
		return getLast();
	}

	@Override
	public Vertex poll() {
		return pollFirst();
	}

	@Override
	public Vertex pollFirst() {
		if (isEmpty()) {
			return null;
		}
		return removeFirst();
	}

	@Override
	public Vertex pollLast() {
		if (isEmpty()) {
			return null;
		}
		return removeLast();
	}

	@Override
	public Vertex pop() {
		return removeFirst();
	}

	@Override
	public void push(Vertex v) {
		addFirst(v);
	}

	@Override
	public Vertex remove() {
		return remove(0);
	}

	@Override
	public Vertex remove(int index) {
		if (size() == 0) {
			return null;
		} else if (size() == 1) {
			Vertex v = get(index);
			edges.remove(v.getIncident());
			edges.remove(v.getIncident().getTwin());
			vertices.remove(v);
			return v;
		}
		// manipulate all the pointers
		// assume vertex v is properly constructed with an incident edge
		// add at the end of the list
		Vertex v = get(index);

		HalfEdge afterL = v.getIncident().getNext();
		HalfEdge beforeL = v.getIncident().getPrevious();
		HalfEdge beforeR = afterL.getTwin();
		HalfEdge afterR = beforeL.getTwin();

		afterL.setPrevious(beforeL);
		beforeL.setNext(afterL);

		afterR.setOrigin(afterL.getOrigin());
		afterR.setPrevious(beforeR);
		beforeR.setNext(afterR);

		// edge removal complete
		edges.remove(v.getIncident());
		edges.remove(v.getIncident().getTwin());
		// vertex removal complete
		vertices.remove(v);

		// finish
		notifyObservers();
		v.setIncident(null);
		return v;
	}

	@Override
	public boolean remove(Object o) {
		if (remove(vertices.indexOf(o)) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void remove(Vertex pt) {
		remove((Object) pt);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		clear();
		return true;
	}

	@Override
	public Vertex removeFirst() {
		return remove(0);
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		if (o == null) {
			return false;
		}
		Vertex remove = null;
		for (Vertex v : this) {
			if (v.equals(o)) {
				remove = v;
				break;
			}
		}
		remove(remove);
		return true;
	}

	@Override
	public Vertex removeLast() {
		return remove(size() - 1);
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		if (o == null) {
			return false;
		}
		Vertex remove = null;
		Iterator<Vertex> it = descendingIterator();
		while (it.hasNext()) {
			Vertex v = it.next();
			if (v.equals(o)) {
				remove = (Vertex) o;
				break;
			}
		}
		remove(remove);
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Vertex set(int index, Vertex v) {
		get(index).setX(v.getX());
		get(index).setY(v.getY());
		return v;
	}

	@Override
	public void setColor(Color c) {
		super.setColor(c);
		vertices.setColor(c);
	}

	@Override
	public int size() {
		return vertices.size();
	}

	@Override
	public List<Vertex> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return vertices.toString();
	}
}
