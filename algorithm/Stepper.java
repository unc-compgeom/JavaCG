package algorithm;

public interface Stepper {

	public void runToCompletion();

	public void step();

	public void reverseStep();

	public void reset();

	public int getStepCount();

}
