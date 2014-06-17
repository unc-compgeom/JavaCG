package cg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import util.CGObservable;
import util.CGObserver;

public class VertexSetComponent extends LinkedList<Vertex> implements
		VertexSet, CGObservable {
	private static final long serialVersionUID = -1545417749354389726L;
	private final List<CGObserver> observers;
	private int size;
	private Color c;

	public VertexSetComponent() {
		super();
		this.size = 1;
		this.observers = new LinkedList<CGObserver>();
	}

	public VertexSetComponent(Vertex[] pts) {
		this();
		Collections.addAll(this, pts);
	}

	// /////////////// // LinkedList OVERRIDES // /////////////// //
	@Override
	public void clear() {
		super.clear();
		notifyObservers();
	}

	@Override
	public boolean add(Vertex v) {
		v.setSize(getSize());
		boolean tf = super.add(v);
		notifyObservers();
		return tf;
	}

	@Override
	public void add(int index, Vertex element) {
		super.add(index, element);
		notifyObservers();
	}

	@Override
	public Vertex get(int index) {
		try {
			return super.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}

	}

	@Override
	public Vertex remove(int i) {
		Vertex removed = super.remove(i);
		notifyObservers();
		return removed;
	}

	@Override
	public boolean removeAll(java.util.Collection<?> c) {
		boolean b = super.removeAll(c);
		notifyObservers();
		return b;
	}

	@Override
	public void addFirst(Vertex v) {
		super.addFirst(v);
		notifyObservers();
	}

	@Override
	public void addLast(Vertex v) {
		super.addLast(v);
		notifyObservers();
	}

	@Override
	public Vertex removeFirst() {
		Vertex p = super.removeFirst();
		notifyObservers();
		return p;
	}

	@Override
	public Vertex removeLast() {
		Vertex p = super.removeLast();
		notifyObservers();
		return p;
	}

	@Override
	public void push(Vertex v) {
		super.push(v);
		notifyObservers();
	}

	@Override
	public Vertex pop() {
		Vertex p = super.pop();
		notifyObservers();
		return p;
	}

	@Override
	public Iterator<Vertex> iterator() {
		return super.iterator();
	}

	// /////////////// // LinkedList additions // /////////////// //
	@Override
	public VertexSet clone() {
		VertexSet v = this.cloneEmpty();
		v.addAll(this);
		return v;
	}

	@Override
	public VertexSet cloneEmpty() {
		VertexSet v = new VertexSetComponent();
		v.addObservers(getObservers());
		v.setColor(getColor());
		v.setSize(getSize());
		return v;
	}

	@Override
	public void remove(Vertex v) {
		super.remove(v);
		notifyObservers();
	}

	@Override
	public Vertex getSecond() {
		return get(1);
	}

	@Override
	public Vertex getSecondToLast() {
		return get(size() - 2);
	}

	// /////////////// // Drawable functionality // /////////////// //

	@Override
	public void addObserver(CGObserver o) {
		observers.add(o);
	}

	@Override
	public void addObservers(List<CGObserver> observers) {
		this.observers.addAll(observers);
	}

	@Override
	public Color getColor() {
		return this.c;
	}

	@Override
	public List<CGObserver> getObservers() {
		return observers;
	}

	protected void notifyObservers() {
		for (CGObserver o : observers) {
			o.update(this);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		for (Iterator<Vertex> it = iterator(); it.hasNext();) {
			Vertex v = it.next();
			v.setColor(c);
			v.paintComponent(g);
		}
	}

	@Override
	public void removeAllObservers() {
		observers.removeAll(observers);
	}

	@Override
	public void removeObserver(CGObserver o) {
		observers.remove(o);
	}

	@Override
	public void setColor(Color c) {
		this.c = c;
	}

	@Override
	public void setSize(int size) {
		this.size = size;
		for (Vertex v : this) {
			v.setSize(size);
		}
	}

	@Override
	public int getSize() {
		return size;
	}
}
