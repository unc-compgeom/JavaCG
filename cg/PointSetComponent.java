package cg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import util.CGObservable;
import util.CGObserver;

public class PointSetComponent extends LinkedList<Point> implements
		Deque<Point>, PointSet, CGObservable {
	private static final long serialVersionUID = -1545417749354389726L;
	private List<CGObserver> observers;
	private Color c;

	public PointSetComponent() {
		observers = new LinkedList<CGObserver>();
	}

	public PointSetComponent(Point[] pts) {
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
	public boolean add(Point pt) {
		boolean tf = super.add(pt);
		notifyObservers();
		return tf;
	}

	@Override
	public Point remove(int i) {
		Point removed = super.remove(i);
		notifyObservers();
		return removed;
	}

	@Override
	public void addFirst(Point e) {
		super.addFirst(e);
		notifyObservers();
	}

	@Override
	public void addLast(Point e) {
		super.addLast(e);
		notifyObservers();
	}

	@Override
	public Point removeFirst() {
		Point p = super.removeFirst();
		notifyObservers();
		return p;
	}

	@Override
	public Point removeLast() {
		Point p = super.removeLast();
		notifyObservers();
		return p;
	}

	@Override
	public void push(Point p) {
		super.push(p);
		notifyObservers();
	}

	@Override
	public Point pop() {
		Point p = super.pop();
		notifyObservers();
		return p;
	}

	// /////////////// // LinkedList additions // /////////////// //
	@Override
	public void remove(Point pt) {
		super.remove(pt);
		notifyObservers();
	}

	@Override
	public void removeAll() {
		super.removeAll(this);
		notifyObservers();
	}

	@Override
	public Point getSecond() {
		return get(1);
	}

	@Override
	public Point getSecondToLast() {
		return get(size() - 2);
	}

	// /////////////// // Drawable functionality // /////////////// //

	@Override
	public void paintComponent(Graphics g) {
		for (Point p : this) {
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
