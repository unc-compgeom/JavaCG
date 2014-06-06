package util;

import java.util.List;

import cg.Drawable;

public interface CGObservable extends Drawable {
	public void addObserver(CGObserver o);

	public void addObservers(List<CGObserver> observers);

	public void removeObserver(CGObserver o);

	public void removeAllObservers();
}
