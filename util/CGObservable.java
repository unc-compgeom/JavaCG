package util;

import cg.Drawable;

public interface CGObservable extends Drawable {
	public void addObserver(CGObserver o);

}
