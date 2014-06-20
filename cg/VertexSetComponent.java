package cg;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class VertexSetComponent extends AbstractGeometry implements VertexSet {
	private static final long serialVersionUID = -1545417749354389726L;
	private final LinkedList<Vertex> vertices;

	protected VertexSetComponent() {
		super();
		vertices = new LinkedList<Vertex>();
	}

	@Override
	public void add(int index, Vertex element) {
		synchronized (this) {
			vertices.add(index, element);
		}
		notifyObservers();
	}

	@Override
	public boolean add(Vertex v) {
		boolean b;
		synchronized (this) {
			b = vertices.add(v);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean addAll(Collection<? extends Vertex> c) {
		boolean b;
		synchronized (this) {
			b = vertices.addAll(c);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Vertex> c) {
		boolean b;
		synchronized (this) {
			b = vertices.addAll(index, c);
		}
		notifyObservers();
		return b;
	}

	@Override
	public void addFirst(Vertex v) {
		synchronized (this) {
			vertices.addFirst(v);
		}
		notifyObservers();
	}

	@Override
	public void addLast(Vertex v) {
		synchronized (this) {
			vertices.addLast(v);
		}
		notifyObservers();
	}

	@Override
	public void addNoDelay(Vertex v) {
		synchronized (this) {
			vertices.addLast(v);
		}
		notifyObserversNoDelay();
	}

	@Override
	public void clear() {
		synchronized (this) {
			vertices.clear();
		}
		notifyObservers();
	}

	@Override
	public VertexSet clone() {
		VertexSet v = new VertexSetComponent();
		v.setColor(getColor());
		synchronized (this) {
			v.addAll(this);
		}
		return v;
	}

	@Override
	public boolean contains(Object o) {
		synchronized (this) {
			return vertices.contains(o);
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		synchronized (this) {
			return vertices.containsAll(c);
		}
	}

	@Override
	public Iterator<Vertex> descendingIterator() {
		synchronized (this) {
			return vertices.descendingIterator();
		}
	}

	@Override
	public Vertex element() {
		synchronized (this) {
			return vertices.element();
		}
	}

	@Override
	public Vertex get(int index) {
		synchronized (this) {
			return vertices.get(index);
		}

	}

	@Override
	public Vertex getFirst() {
		synchronized (this) {
			return vertices.getFirst();
		}
	}

	@Override
	public Vertex getLast() {
		synchronized (this) {
			return vertices.getLast();
		}
	}

	@Override
	public Vertex getSecond() {
		synchronized (this) {
			return vertices.get(1);
		}
	}

	@Override
	public Vertex getSecondToLast() {
		synchronized (this) {
			return vertices.get(size() - 2);
		}
	}

	@Override
	public int indexOf(Object o) {
		synchronized (this) {
			return vertices.indexOf(o);
		}
	}

	@Override
	public boolean isEmpty() {
		synchronized (this) {
			return vertices.isEmpty();
		}
	}

	@Override
	public Iterator<Vertex> iterator() {
		synchronized (this) {
			return vertices.iterator();
		}
	}

	@Override
	public int lastIndexOf(Object o) {
		synchronized (this) {
			return vertices.lastIndexOf(o);
		}
	}

	@Override
	public ListIterator<Vertex> listIterator() {
		synchronized (this) {
			return vertices.listIterator();
		}
	}

	@Override
	public ListIterator<Vertex> listIterator(int index) {
		synchronized (this) {
			return vertices.listIterator(index);
		}
	}

	@Override
	public boolean offer(Vertex e) {
		boolean b;
		synchronized (this) {
			b = vertices.offer(e);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean offerFirst(Vertex e) {
		boolean b;
		synchronized (this) {
			b = vertices.offerFirst(e);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean offerLast(Vertex e) {
		boolean b;
		synchronized (this) {
			b = vertices.offerLast(e);
		}
		notifyObservers();
		return b;
	}

	@Override
	public void paintComponent(Graphics g) {
		synchronized (this) {
			for (Iterator<Vertex> it = iterator(); it.hasNext();) {
				Vertex v = it.next();
				v.setColor(super.getColor());
				v.paintComponent(g);
			}
		}
	}

	@Override
	public Vertex peek() {
		synchronized (this) {
			return vertices.peek();
		}
	}

	@Override
	public Vertex peekFirst() {
		synchronized (this) {
			return vertices.peekFirst();
		}
	}

	@Override
	public Vertex peekLast() {
		synchronized (this) {
			return vertices.peekLast();
		}
	}

	@Override
	public Vertex poll() {
		Vertex v;
		synchronized (this) {
			v = vertices.poll();
		}
		notifyObservers();
		return v;
	}

	@Override
	public Vertex pollFirst() {
		Vertex v;
		synchronized (this) {
			v = vertices.pollFirst();
		}
		notifyObservers();
		return v;
	}

	@Override
	public Vertex pollLast() {
		Vertex v;
		synchronized (this) {
			v = vertices.pollLast();
		}
		notifyObservers();
		return v;
	}

	@Override
	public Vertex pop() {
		Vertex v;
		synchronized (this) {
			v = vertices.pop();
		}
		notifyObservers();
		return v;
	}

	@Override
	public void push(Vertex v) {
		synchronized (this) {
			vertices.push(v);
		}
		notifyObservers();
	}

	@Override
	public Vertex remove() {
		Vertex v;
		synchronized (this) {
			v = vertices.remove();
		}
		notifyObservers();
		return v;
	}

	@Override
	public Vertex remove(int i) {
		Vertex v;
		synchronized (this) {
			v = vertices.remove(i);

		}
		notifyObservers();
		return v;
	}

	@Override
	public boolean remove(Object o) {
		boolean b;
		synchronized (this) {
			b = vertices.remove(o);
		}
		notifyObservers();
		return b;
	}

	@Override
	public void remove(Vertex v) {
		synchronized (this) {
			vertices.remove(v);
		}
		notifyObservers();
	}

	@Override
	public boolean removeAll(java.util.Collection<?> c) {
		boolean b;
		synchronized (this) {
			b = vertices.removeAll(c);
		}
		notifyObservers();
		return b;
	}

	@Override
	public Vertex removeFirst() {
		Vertex p;
		synchronized (this) {
			p = vertices.removeFirst();
		}
		notifyObservers();
		return p;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		boolean b;
		synchronized (this) {
			b = vertices.removeFirstOccurrence(o);
		}
		notifyObservers();
		return b;
	}

	@Override
	public Vertex removeLast() {
		synchronized (this) {
			Vertex p = vertices.removeLast();
			notifyObservers();
			return p;
		}
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		synchronized (this) {
			return vertices.removeLastOccurrence(o);
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		synchronized (this) {
			return vertices.retainAll(c);
		}
	}

	@Override
	public Vertex set(int index, Vertex element) {
		Vertex v;
		synchronized (this) {
			v = vertices.set(index, element);
		}
		notifyObservers();
		return v;
	}

	@Override
	public int size() {
		synchronized (this) {
			return vertices.size();
		}
	}

	@Override
	public List<Vertex> subList(int fromIndex, int toIndex) {
		synchronized (this) {
			return vertices.subList(fromIndex, toIndex);
		}
	}

	@Override
	public Object[] toArray() {
		synchronized (this) {
			return vertices.toArray();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		synchronized (this) {
			return vertices.toArray(a);
		}
	}
}
