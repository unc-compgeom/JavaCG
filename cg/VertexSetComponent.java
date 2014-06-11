package cg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import util.CGObservable;
import util.CGObserver;

public class VertexSetComponent extends LinkedList<Vertex> implements
		VertexSet, CGObservable {
	private static final long serialVersionUID = -1545417749354389726L;
	private List<CGObserver> observers;
	private Color c;

	public VertexSetComponent() {
		observers = new LinkedList<CGObserver>();
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
		boolean tf = super.add(v);
		notifyObservers();
		return tf;
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

	// /////////////// // LinkedList additions // /////////////// //
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
	public void paintComponent(Graphics g) {
		for (Vertex p : this) {
			p.setColor(c);
			p.paintComponent(g);
		}
		g.setColor(c);
	}

	@Override
	public void setColor(Color c) {
		this.c = c;
	}

	protected Color getColor() {
		return c;
	}

	@Override
	public void addObserver(CGObserver o) {
		this.observers.add(o);
	}

	@Override
	public void addObservers(List<CGObserver> observers) {
		this.observers.addAll(observers);
	}

	private void notifyObservers() {
		for (CGObserver o : observers) {
			o.update(this);
		}
	}

	@Override
	public void removeObserver(CGObserver o) {
		observers.remove(o);
	}

	@Override
	public void removeAllObservers() {
		observers.removeAll(observers);
	}

	@Override
	public List<CGObserver> getObservers() {
		return observers;
	}
}
