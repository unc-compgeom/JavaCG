package cg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import util.CGObserver;
import visualizer.Delay;

public abstract class AbstractGeometry implements Drawable {
	private Color c;
	private final List<CGObserver> observers;
	private int size;
	private Delay delay;

	AbstractGeometry() {
		this.c = Color.black;
		this.size = 1;
		delay = new Delay(0);
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
	public int getDrawSize() {
		return size;
	}

	@Override
	public List<CGObserver> getObservers() {
		return observers;
	}

	protected void notifyObservers() {
		for (CGObserver o : observers) {
			o.update(this);
		}
		try {
			Thread.sleep(delay.get());
		} catch (InterruptedException e) {
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

	@Override
	public void setDelay(Delay delay) {
		this.delay = delay;
	}

	@Override
	public void setSize(Integer size) {
		this.size = size;
		notifyObservers();
	}
}
