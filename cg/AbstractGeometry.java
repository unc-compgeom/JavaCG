package cg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import util.CGObservable;
import util.CGObserver;

public abstract class AbstractGeometry implements CGObservable {
	private Color c;
	private List<CGObserver> observers;

	AbstractGeometry() {
		observers = new LinkedList<CGObserver>();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(c);
		// paint must be completed by the subclass
	}

	@Override
	public void setColor(Color c) {
		this.c = c;
	}

	@Override
	public void addObserver(CGObserver o) {
		observers.add(o);
	}

	protected void notifyObservers() {
		for (CGObserver o : observers) {
			o.update(this);
		}
	}

	@Override
	public void addObservers(List<CGObserver> observers) {
		this.observers.addAll(observers);
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
