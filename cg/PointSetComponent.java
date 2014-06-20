package cg;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class PointSetComponent extends AbstractGeometry implements PointSet {
	private static final long serialVersionUID = -1545417749354389726L;
	private final LinkedList<Point> vertices;

	protected PointSetComponent() {
		super();
		vertices = new LinkedList<Point>();
	}

	@Override
	public void add(int index, Point element) {
		synchronized (this) {
			vertices.add(index, element);
		}
		notifyObservers();
	}

	@Override
	public boolean add(Point v) {
		boolean b;
		synchronized (this) {
			b = vertices.add(v);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean addAll(Collection<? extends Point> c) {
		boolean b;
		synchronized (this) {
			b = vertices.addAll(c);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Point> c) {
		boolean b;
		synchronized (this) {
			b = vertices.addAll(index, c);
		}
		notifyObservers();
		return b;
	}

	@Override
	public void addFirst(Point v) {
		synchronized (this) {
			vertices.addFirst(v);
		}
		notifyObservers();
	}

	@Override
	public void addLast(Point v) {
		synchronized (this) {
			vertices.addLast(v);
		}
		notifyObservers();
	}

	@Override
	public void addNoDelay(Point v) {
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
	public PointSet clone() {
		PointSet v = new PointSetComponent();
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
	public Iterator<Point> descendingIterator() {
		synchronized (this) {
			return vertices.descendingIterator();
		}
	}

	@Override
	public Point element() {
		synchronized (this) {
			return vertices.element();
		}
	}

	@Override
	public Point get(int index) {
		synchronized (this) {
			return vertices.get(index);
		}

	}

	@Override
	public Point getFirst() {
		synchronized (this) {
			return vertices.getFirst();
		}
	}

	@Override
	public Point getLast() {
		synchronized (this) {
			return vertices.getLast();
		}
	}

	@Override
	public Point getSecond() {
		synchronized (this) {
			return vertices.get(1);
		}
	}

	@Override
	public Point getSecondToLast() {
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
	public Iterator<Point> iterator() {
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
	public ListIterator<Point> listIterator() {
		synchronized (this) {
			return vertices.listIterator();
		}
	}

	@Override
	public ListIterator<Point> listIterator(int index) {
		synchronized (this) {
			return vertices.listIterator(index);
		}
	}

	@Override
	public boolean offer(Point e) {
		boolean b;
		synchronized (this) {
			b = vertices.offer(e);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean offerFirst(Point e) {
		boolean b;
		synchronized (this) {
			b = vertices.offerFirst(e);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean offerLast(Point e) {
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
			for (Iterator<Point> it = iterator(); it.hasNext();) {
				Point v = it.next();
				v.setColor(super.getColor());
				v.paintComponent(g);
			}
		}
	}

	@Override
	public Point peek() {
		synchronized (this) {
			return vertices.peek();
		}
	}

	@Override
	public Point peekFirst() {
		synchronized (this) {
			return vertices.peekFirst();
		}
	}

	@Override
	public Point peekLast() {
		synchronized (this) {
			return vertices.peekLast();
		}
	}

	@Override
	public Point poll() {
		Point v;
		synchronized (this) {
			v = vertices.poll();
		}
		notifyObservers();
		return v;
	}

	@Override
	public Point pollFirst() {
		Point v;
		synchronized (this) {
			v = vertices.pollFirst();
		}
		notifyObservers();
		return v;
	}

	@Override
	public Point pollLast() {
		Point v;
		synchronized (this) {
			v = vertices.pollLast();
		}
		notifyObservers();
		return v;
	}

	@Override
	public Point pop() {
		Point v;
		synchronized (this) {
			v = vertices.pop();
		}
		notifyObservers();
		return v;
	}

	@Override
	public void push(Point v) {
		synchronized (this) {
			vertices.push(v);
		}
		notifyObservers();
	}

	@Override
	public Point remove() {
		Point v;
		synchronized (this) {
			v = vertices.remove();
		}
		notifyObservers();
		return v;
	}

	@Override
	public Point remove(int i) {
		Point v;
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
	public void remove(Point v) {
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
	public Point removeFirst() {
		Point p;
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
	public Point removeLast() {
		synchronized (this) {
			Point p = vertices.removeLast();
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
	public Point set(int index, Point element) {
		Point v;
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
	public List<Point> subList(int fromIndex, int toIndex) {
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
