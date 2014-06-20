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
			notifyObservers();
		}
	}

	@Override
	public boolean add(Vertex v) {
		synchronized (this) {
			boolean b = vertices.add(v);
			notifyObservers();
			return b;
		}
	}

	@Override
	public boolean addAll(Collection<? extends Vertex> c) {
		synchronized (this) {
			boolean b = vertices.addAll(c);
			notifyObservers();
			return b;
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends Vertex> c) {
		synchronized (this) {
			boolean b = vertices.addAll(index, c);
			notifyObservers();
			return b;
		}
	}

	@Override
	public void addFirst(Vertex v) {
		synchronized (this) {
			vertices.addFirst(v);
			notifyObservers();
		}
	}

	@Override
	public void addLast(Vertex v) {
		synchronized (this) {
			vertices.addLast(v);
			notifyObservers();
		}
	}

	@Override
	public void addNoDelay(Vertex v) {
		synchronized (this) {
			vertices.addLast(v);
			notifyObserversNoDelay();
		}
	}

	@Override
	public void clear() {
		synchronized (this) {
			vertices.clear();
			notifyObservers();
		}
	}

	@Override
	public VertexSet clone() {
		VertexSet v = new VertexSetComponent();
		v.setColor(getColor());
		v.addAll(this);
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
			try {
				return vertices.get(index);
			} catch (IndexOutOfBoundsException e) {
				return null;
			}
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
		synchronized (this) {
			return vertices.offer(e);
		}
	}

	@Override
	public boolean offerFirst(Vertex e) {
		synchronized (this) {
			return vertices.offerFirst(e);
		}
	}

	@Override
	public boolean offerLast(Vertex e) {
		synchronized (this) {
			return vertices.offerLast(e);
		}
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
		synchronized (this) {
			return vertices.poll();
		}
	}

	@Override
	public Vertex pollFirst() {
		synchronized (this) {
			return vertices.pollFirst();
		}
	}

	@Override
	public Vertex pollLast() {
		synchronized (this) {
			return vertices.pollLast();
		}
	}

	@Override
	public Vertex pop() {
		synchronized (this) {
			Vertex p = vertices.pop();
			notifyObservers();
			return p;
		}
	}

	@Override
	public void push(Vertex v) {
		synchronized (this) {
			vertices.push(v);
			notifyObservers();
		}
	}

	@Override
	public Vertex remove() {
		synchronized (this) {
			return vertices.remove();
		}
	}

	@Override
	public Vertex remove(int i) {
		synchronized (this) {
			Vertex removed = vertices.remove(i);
			notifyObservers();
			return removed;
		}
	}

	@Override
	public boolean remove(Object o) {
		synchronized (this) {
			return vertices.remove(o);
		}
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
		synchronized (this) {
			boolean b = vertices.removeAll(c);
			notifyObservers();
			return b;
		}
	}

	@Override
	public Vertex removeFirst() {
		synchronized (this) {
			Vertex p = vertices.removeFirst();
			notifyObservers();
			return p;
		}
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		synchronized (this) {
			return vertices.removeFirstOccurrence(o);
		}
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
		synchronized (this) {
			return vertices.set(index, element);
		}
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
