package cg;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class PointSetComponent extends AbstractGeometry implements PointSet {
	private static final long serialVersionUID = -1545417749354389726L;
	private final LinkedList<Point> points;

	PointSetComponent() {
		super();
		points = new LinkedList<>();
	}

	@Override
	public void add(final int index, final Point p) {
		GeometryManager.destroy(p);
		synchronized (this) {
			points.add(index, p);
		}
		notifyObservers();
	}

	@Override
	public boolean add(final Point p) {
		GeometryManager.destroy(p);
		boolean b;
		synchronized (this) {
			b = points.add(p);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean addAll(final Collection<? extends Point> c) {
		synchronized (c) {
			c.forEach(GeometryManager::destroy);
		}
		if (c instanceof PointSet) {
			GeometryManager.destroy((PointSet) c);
		}
		boolean b;
		synchronized (this) {
			b = points.addAll(c);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends Point> c) {
		synchronized (c) {
			c.forEach(GeometryManager::destroy);
		}
		boolean b;
		synchronized (this) {
			b = points.addAll(index, c);
		}
		notifyObservers();
		return b;
	}

	@Override
	public void addFirst(final Point p) {
		GeometryManager.destroy(p);
		synchronized (this) {
			points.addFirst(p);
		}
		notifyObservers();
	}

	@Override
	public void addLast(final Point p) {
		GeometryManager.destroy(p);
		synchronized (this) {
			points.addLast(p);
		}
		notifyObservers();
	}

	@Override
	public void addNoDelay(final float x, final float y) {
		synchronized (this) {
			points.addLast(new PointComponent(x, y));
		}
		notifyObserversNoDelay();
	}

	@Override
	public void addNoDelay(final Point p) {
		GeometryManager.destroy(p);
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
	public boolean contains(final Object o) {
		synchronized (this) {
			return points.contains(o);
		}
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
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
	public Point get(final int index) {
		// this method should thrown an exception if the index is out of bounds
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
	public Point getSecond() throws NoSuchElementException {
		synchronized (this) {
			if (size() < 2) {
				throw new NoSuchElementException();
			}
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
	public int indexOf(final Object o) {
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
	public int lastIndexOf(final Object o) {
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
	public ListIterator<Point> listIterator(final int index) {
		synchronized (this) {
			return points.listIterator(index);
		}
	}

	@Override
	public boolean offer(final Point p) {
		GeometryManager.destroy(p);
		boolean b;
		synchronized (this) {
			b = points.offer(p);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean offerFirst(final Point p) {
		GeometryManager.destroy(p);
		boolean b;
		synchronized (this) {
			b = points.offerFirst(p);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean offerLast(final Point p) {
		GeometryManager.destroy(p);
		boolean b;
		synchronized (this) {
			b = points.offerLast(p);
		}
		notifyObservers();
		return b;
	}

	@Override
	public void paint(final GraphicsContext gc) {
		if (isInvisible()) {
			return;
		}
		// push old values onto the "stack"
		final Paint oldFill = gc.getFill();
		final Paint oldStroke = gc.getStroke();
		// double oldSize = gc.getLineWidth(); // we don't need this for points
		final Color c = super.getColor();
		if (c != null) {
			gc.setFill(c);
			gc.setStroke(c.darker());
		}
		// gc.setLineWidth(GeometryManager.getSize());

		for (final Point p : points) {
			p.paint(gc);
		}

		// restore gc from the "stack"
		gc.setFill(oldFill);
		gc.setStroke(oldStroke);
		// gc.setLineWidth(oldSize);
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
	public Point peekSecond() {
		synchronized (this) {
			if (points.size() < 2) {
				return null;
			}
			return points.get(1);
		}
	}

	@Override
	public Point peekSecondToLast() {
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
	public void push(final Point p) {
		GeometryManager.destroy(p);
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
	public Point remove(final int i) {
		Point v;
		synchronized (this) {
			v = points.remove(i);

		}
		notifyObservers();
		return v;
	}

	@Override
	public boolean remove(final Object o) {
		boolean b;
		synchronized (this) {
			b = points.remove(o);
		}
		notifyObservers();
		return b;
	}

	@Override
	public boolean removeAll(final java.util.Collection<?> c) {
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
	public boolean removeFirstOccurrence(final Object o) {
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
			final Point p = points.removeLast();
			notifyObservers();
			return p;
		}
	}

	@Override
	public boolean removeLastOccurrence(final Object o) {
		synchronized (this) {
			return points.removeLastOccurrence(o);
		}
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		synchronized (this) {
			return points.retainAll(c);
		}
	}

	@Override
	public Point set(final int index, final Point p) {
		GeometryManager.destroy(p);
		Point pt;
		synchronized (this) {
			pt = points.set(index, p);
		}
		notifyObservers();
		return pt;
	}

	@Override
	public int size() {
		synchronized (this) {
			return points.size();
		}
	}

	@Override
	public List<Point> subList(final int fromIndex, final int toIndex) {
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
	public <T> T[] toArray(final T[] a) {
		synchronized (this) {
			return points.toArray(a);
		}
	}
}
