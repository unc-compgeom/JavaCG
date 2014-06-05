package cg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import util.CG;
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

	@Override
	public void clear() {
		this.clear();
		notifyObservers();
	}

	@Override
	public boolean isEmpty() {
		return this.isEmpty();
	}

	@Override
	public int numPoints() {
		return this.size();
	}

	@Override
	public boolean add(Point pt) {
		boolean tf = super.add(pt);
		notifyObservers(false);
		return tf;
	};

	@Override
	public void addPoint(Point pt) {
		this.add(pt);
	}

	@Override
	public void removePoint(Point pt) {
		this.remove(pt);
		notifyObservers();
	}

	@Override
	public void removeElementAt(int i) {
		this.remove(i);
		notifyObservers();
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
	};

	@Override
	public Point getPoint(int index) {
		return this.get(index);
	}

	@Override
	public Point getSecond() {
		Point tmp = super.removeFirst();
		Point ret = getFirst();
		super.addFirst(tmp);
		return ret;
	}

	@Override
	public Point getSecondToLast() {
		Point tmp = super.removeLast();
		Point ret = getLast();
		super.addLast(tmp);
		return ret;
	}

	@Override
	public void setPoint(int index, Point pt) {
		this.get(index).setPoint(pt);
		notifyObservers();
	}

	@Override
	public void setPoint(int index, int x, int y) {
		this.get(index).setPoint(x, y);
		notifyObservers();
	}

	@Override
	public void push(Point p) {
		super.push(p);
		notifyObservers();
	};

	@Override
	public Point pop() {
		Point p = super.pop();
		notifyObservers();
		return p;
	};

	@Override
	public void paintComponent(Graphics g) {
		for (Point p : this) {
			p.setColor(c);
			p.paintComponent(g);
		}
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

	private void notifyObservers() {
		notifyObservers(true);
	}

	private void notifyObservers(boolean sleep) {
		for (CGObserver o : observers) {
			o.update(this);
		}
		if (sleep)
			CG.sleep();
	}
}
