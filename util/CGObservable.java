package util;

import java.util.List;

public interface CGObservable {
	public void addObserver(CGObserver o);

	public void addObservers(List<CGObserver> observers);

	public void removeObserver(CGObserver o);

	public void removeAllObservers();

	public List<CGObserver> getObservers();
}
