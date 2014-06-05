package algorithm;

public interface SteppableAlgorithm {
	public abstract boolean isReversible();

	public abstract void step();

	public abstract boolean isCompleted();

	public abstract void reset();

	public abstract void reverseStep();

}
