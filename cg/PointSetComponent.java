package cg;

import java.util.*;

public class PointSetComponent extends AbstractGeometry implements PointSet {
	private static final long serialVersionUID = -1545417749354389726L;
	private final LinkedList<Point> points;

	PointSetComponent() {
		super();
		points = new LinkedList<Point>();
	}

	@Override
	public synchronized void add(int index, Point p) {
		isReady = false;
		GeometryManager.destroy(p);
		points.add(index, p);
		isReady = true;
		notifyObservers(this);
		notifyAll();
	}

	@Override
	public synchronized boolean add(Point p) {
		isReady = false;
		GeometryManager.destroy(p);
		boolean b;
		b = points.add(p);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return b;
	}

	@Override
	public synchronized boolean addAll(Collection<? extends Point> c) {
		isReady = false;
		synchronized (c) {
			for (Point point : c) {
				GeometryManager.destroy(point);
			}
		}
		if (c instanceof PointSet) {
			GeometryManager.destroy((PointSet) c);
		}
		boolean b;
		b = points.addAll(c);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return b;
	}

	@Override
	public synchronized boolean addAll(int index, Collection<? extends Point> c) {
		isReady = false;
		synchronized (c) {
			for (Point point : c) {
				GeometryManager.destroy(point);
			}
		}
		boolean b;
		b = points.addAll(index, c);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return b;
	}

	@Override
	public synchronized void addFirst(Point p) {
		isReady = false;
		GeometryManager.destroy(p);
		points.addFirst(p);
		isReady = true;
		notifyObservers(this);
		notifyAll();
	}

	@Override
	public synchronized void addLast(Point p) {
		isReady = false;
		GeometryManager.destroy(p);
		points.addLast(p);
		isReady = true;
		notifyObservers(this);
		notifyAll();
	}

	@Override
	public synchronized void addNoDelay(Point p) {
		isReady = false;
		GeometryManager.destroy(p);
		points.addLast(p);
		isReady = true;
		notifyObserversNoDelay(this);
		notifyAll();
	}

	@Override
	public synchronized void addNoDelay(float x, float y) {
		isReady = false;
		points.addLast(new PointComponent(x, y));
		isReady = true;
		notifyObserversNoDelay(this);
		notifyAll();
	}

	@Override
	public synchronized void clear() {
		isReady = false;
		points.clear();
		isReady = true;
		notifyObservers(this);
		notifyAll();
	}

	@Override
	public synchronized boolean contains(Object o) {
		return points.contains(o);
	}

	@Override
	public synchronized boolean containsAll(Collection<?> c) {
		return points.containsAll(c);
	}

	@Override
	public synchronized Iterator<Point> descendingIterator() {
		return points.descendingIterator();
	}

	@Override
	public synchronized Point element() {
		return points.element();
	}

	@Override
	public synchronized Point get(int index) {
		return points.get(index);
	}

	@Override
	public synchronized Point getFirst() {
		return points.getFirst();
	}

	@Override
	public synchronized Point getLast() {
		return points.getLast();
	}

	@Override
	public synchronized Point getSecond() throws NoSuchElementException {
		if (size() < 2) {
			throw new NoSuchElementException();
		}
		return points.get(1);
	}

	@Override
	public synchronized Point getSecondToLast() {
		return points.get(size() - 2);
	}

	@Override
	public synchronized int indexOf(Object o) {
		return points.indexOf(o);
	}

	@Override
	public synchronized boolean isEmpty() {
		return points.isEmpty();
	}

	@Override
	public synchronized Iterator<Point> iterator() {
		return points.iterator();
	}

	@Override
	public synchronized int lastIndexOf(Object o) {
		return points.lastIndexOf(o);
	}

	@Override
	public synchronized ListIterator<Point> listIterator() {
		return points.listIterator();
	}

	@Override
	public synchronized ListIterator<Point> listIterator(int index) {
		return points.listIterator(index);
	}

	@Override
	public synchronized boolean offer(Point p) {
		isReady = false;
		GeometryManager.destroy(p);
		boolean b;
		b = points.offer(p);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return b;
	}

	@Override
	public synchronized boolean offerFirst(Point p) {
		isReady = false;
		GeometryManager.destroy(p);
		boolean b;
		b = points.offerFirst(p);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return b;
	}

	@Override
	public synchronized boolean offerLast(Point p) {
		isReady = false;
		GeometryManager.destroy(p);
		boolean b;
		b = points.offerLast(p);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return b;
	}

	@Override
	public synchronized Point peek() {
		return points.peek();
	}

	@Override
	public synchronized Point peekFirst() {
		return points.peekFirst();
	}

	@Override
	public synchronized Point peekLast() {
		return points.peekLast();
	}

	@Override
	public synchronized Point peekSecond() {
		if (points.size() < 2) {
			return null;
		}
		return points.get(1);
	}

	@Override
	public synchronized Point peekSecondToLast() {
		if (points.isEmpty()) {
			return null;
		}
		if (points.size() < 2) {
			return points.get(0);
		} else {
			return points.get(size() - 2);
		}
	}

	@Override
	public synchronized Point poll() {
		Point v;
		v = points.poll();
		return v;
	}

	@Override
	public synchronized Point pollFirst() {
		Point v;
		v = points.pollFirst();
		return v;
	}

	@Override
	public synchronized Point pollLast() {
		Point v;
		v = points.pollLast();
		return v;
	}

	@Override
	public synchronized Point pop() {
		Point v;
		v = points.pop();
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return v;
	}

	@Override
	public synchronized void push(Point p) {
		isReady = false;
		GeometryManager.destroy(p);
		points.push(p);
		isReady = true;
		notifyObservers(this);
		notifyAll();
	}

	@Override
	public synchronized Point remove() {
		isReady = false;
		Point v;
		v = points.remove();
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return v;
	}

	@Override
	public synchronized Point remove(int i) {
		isReady = false;
		Point v = points.remove(i);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return v;
	}

	@Override
	public synchronized boolean remove(Object o) {
		isReady = false;
		boolean b;
		b = points.remove(o);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return b;
	}

	@Override
	public synchronized boolean removeAll(java.util.Collection<?> c) {
		isReady = false;
		boolean b;
		b = points.removeAll(c);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return b;
	}

	@Override
	public synchronized Point removeFirst() {
		isReady = false;
		Point p;
		p = points.removeFirst();
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return p;
	}

	@Override
	public synchronized boolean removeFirstOccurrence(Object o) {
		isReady = false;
		boolean b;
		b = points.removeFirstOccurrence(o);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return b;
	}

	@Override
	public synchronized Point removeLast() {
		isReady = false;
		Point p = points.removeLast();
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return p;
	}

	@Override
	public synchronized boolean removeLastOccurrence(Object o) {
		isReady = false;
		boolean b = points.removeLastOccurrence(o);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return b;
	}

	@Override
	public synchronized boolean retainAll(Collection<?> c) {
		isReady = false;
		boolean b =points.retainAll(c);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return b;
	}

	@Override
	public synchronized Point set(int index, Point p) {
		isReady = false;
		GeometryManager.destroy(p);
		Point pt;
		pt = points.set(index, p);
		isReady = true;
		notifyObservers(this);
		notifyAll();
		return pt;
	}

	@Override
	public synchronized int size() {
		return points.size();
	}

	@Override
	public synchronized List<Point> subList(int fromIndex, int toIndex) {
		return points.subList(fromIndex, toIndex);
	}

	@Override
	public synchronized Object[] toArray() {
		return points.toArray();
	}

	@Override
	public synchronized <T> T[] toArray(T[] a) {
		return points.toArray(a);
	}
}
