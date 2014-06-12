package cg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import util.CGObservable;
import util.CGObserver;

public abstract class AbstractGeometry implements CGObservable {
	private Color c;
	private final List<CGObserver> observers;

	AbstractGeometry() {
		observers = new LinkedList<CGObserver>();
	}

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
	public abstract void paintComponent(Graphics g);

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
}
