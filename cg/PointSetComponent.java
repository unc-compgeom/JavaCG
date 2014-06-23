package cg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class PointSetComponent extends AbstractGeometry implements PointSet {
	private static final long serialVersionUID = -1545417749354389726L;
	private final LinkedList<Point> points;

	PointSetComponent() {
		super();
		points = new LinkedList<Point>();
	}

	@Override
	public void add(int index, Point p) {
		p.setColor(getColor());
		synchronized (this) {
			points.add(index, p);
		}
		notifyObservers();
	}

	@Override
	public boolean add(Point p) {
		p.setColor(getColor());
		boolean b;
		synchronized (this) {
			b = points.add(p);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean addAll(Collection<? extends Point> c) {
		for (Point p : c) {
			p.setColor(getColor());
		}
		boolean b;
		synchronized (this) {
			b = points.addAll(c);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Point> c) {
		for (Point p : c) {
			p.setColor(getColor());
		}
		boolean b;
		synchronized (this) {
			b = points.addAll(index, c);
		}
		notifyObservers();
		return b;
	}

	@Override
	public void addFirst(Point p) {
		p.setColor(getColor());
		synchronized (this) {
			points.addFirst(p);
		}
		notifyObservers();
	}

	@Override
	public void addLast(Point p) {
		p.setColor(getColor());
		synchronized (this) {
			points.addLast(p);
		}
		notifyObservers();
	}

	@Override
	public void addNoDelay(Point p) {
		p.setColor(getColor());
		synchronized (this) {
			points.addLast(p);
		}
		notifyObserversNoDelay();
	}

	@Override
	public void clear() {
		synchronized (this) {
			points.clear();
		}
		notifyObservers();
	}

	@Override
	public boolean contains(Object o) {
		synchronized (this) {
			return points.contains(o);
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		synchronized (this) {
			return points.containsAll(c);
		}
	}

	@Override
	public Iterator<Point> descendingIterator() {
		synchronized (this) {
			return points.descendingIterator();
		}
	}

	@Override
	public Point element() {
		synchronized (this) {
			return points.element();
		}
	}

	@Override
	public Point get(int index) {
		synchronized (this) {
			return points.get(index);
		}

	}

	@Override
	public Point getFirst() {
		synchronized (this) {
			return points.getFirst();
		}
	}

	@Override
	public Point getLast() {
		synchronized (this) {
			return points.getLast();
		}
	}

	@Override
	public Point getSecond() {
		synchronized (this) {
			return points.get(1);
		}
	}

	@Override
	public Point getSecondToLast() {
		synchronized (this) {
			return points.get(size() - 2);
		}
	}

	@Override
	public int indexOf(Object o) {
		synchronized (this) {
			return points.indexOf(o);
		}
	}

	@Override
	public boolean isEmpty() {
		synchronized (this) {
			return points.isEmpty();
		}
	}

	@Override
	public Iterator<Point> iterator() {
		synchronized (this) {
			return points.iterator();
		}
	}

	@Override
	public int lastIndexOf(Object o) {
		synchronized (this) {
			return points.lastIndexOf(o);
		}
	}

	@Override
	public ListIterator<Point> listIterator() {
		synchronized (this) {
			return points.listIterator();
		}
	}

	@Override
	public ListIterator<Point> listIterator(int index) {
		synchronized (this) {
			return points.listIterator(index);
		}
	}

	@Override
	public boolean offer(Point p) {
		p.setColor(getColor());
		boolean b;
		synchronized (this) {
			b = points.offer(p);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean offerFirst(Point p) {
		p.setColor(getColor());
		boolean b;
		synchronized (this) {
			b = points.offerFirst(p);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean offerLast(Point p) {
		p.setColor(getColor());
		boolean b;
		synchronized (this) {
			b = points.offerLast(p);
		}
		notifyObservers();
		return b;
	}

	@Override
	public void paintComponent(Graphics g) {
		if (isInvisible()) {
			return;
		}
		synchronized (this) {
			for (Point p : this) {
				p.paintComponent(g);
			}
		}
	}

	@Override
	public Point peek() {
		synchronized (this) {
			return points.peek();
		}
	}

	@Override
	public Point peekFirst() {
		synchronized (this) {
			return points.peekFirst();
		}
	}

	@Override
	public Point peekLast() {
		synchronized (this) {
			return points.peekLast();
		}
	}

	@Override
	public Point poll() {
		Point v;
		synchronized (this) {
			v = points.poll();
		}
		notifyObservers();
		return v;
	}

	@Override
	public Point pollFirst() {
		Point v;
		synchronized (this) {
			v = points.pollFirst();
		}
		notifyObservers();
		return v;
	}

	@Override
	public Point pollLast() {
		Point v;
		synchronized (this) {
			v = points.pollLast();
		}
		notifyObservers();
		return v;
	}

	@Override
	public Point pop() {
		Point v;
		synchronized (this) {
			v = points.pop();
		}
		notifyObservers();
		return v;
	}

	@Override
	public void push(Point p) {
		p.setColor(getColor());
		synchronized (this) {
			points.push(p);
		}
		notifyObservers();
	}

	@Override
	public Point remove() {
		Point v;
		synchronized (this) {
			v = points.remove();
		}
		notifyObservers();
		return v;
	}

	@Override
	public Point remove(int i) {
		Point v;
		synchronized (this) {
			v = points.remove(i);

		}
		notifyObservers();
		return v;
	}

	@Override
	public boolean remove(Object o) {
		boolean b;
		synchronized (this) {
			b = points.remove(o);
		}
		notifyObservers();
		return b;
	}

	@Override
	public void remove(Point v) {
		synchronized (this) {
			points.remove(v);
		}
		notifyObservers();
	}

	@Override
	public boolean removeAll(java.util.Collection<?> c) {
		boolean b;
		synchronized (this) {
			b = points.removeAll(c);
		}
		notifyObservers();
		return b;
	}

	@Override
	public Point removeFirst() {
		Point p;
		synchronized (this) {
			p = points.removeFirst();
		}
		notifyObservers();
		return p;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		boolean b;
		synchronized (this) {
			b = points.removeFirstOccurrence(o);
		}
		notifyObservers();
		return b;
	}

	@Override
	public Point removeLast() {
		synchronized (this) {
			Point p = points.removeLast();
			notifyObservers();
			return p;
		}
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		synchronized (this) {
			return points.removeLastOccurrence(o);
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		synchronized (this) {
			return points.retainAll(c);
		}
	}

	@Override
	public Point set(int index, Point p) {
		p.setColor(getColor());
		Point pt;
		synchronized (this) {
			pt = points.set(index, p);
		}
		notifyObservers();
		return pt;
	}

	@Override
	public void setColor(Color c) {
		super.setColor(c);
		synchronized (points) {
			for (Point p : points) {
				p.setColor(c);
			}
		}
	}

	@Override
	public int size() {
		synchronized (this) {
			return points.size();
		}
	}

	@Override
	public List<Point> subList(int fromIndex, int toIndex) {
		synchronized (this) {
			return points.subList(fromIndex, toIndex);
		}
	}

	@Override
	public Object[] toArray() {
		synchronized (this) {
			return points.toArray();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		synchronized (this) {
			return points.toArray(a);
		}
	}
}
