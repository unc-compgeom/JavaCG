package util;

public interface CGObserver {
	public void update(CGObservable o, int delay);

	public void update(CGObservable o);
}
