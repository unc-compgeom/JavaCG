package algorithm;

public class StepperImpl implements Stepper {
	private SteppableAlgorithm algorithm;
	private int stepCount;

	public StepperImpl(SteppableAlgorithm alg) {
		algorithm = alg;
		stepCount = 0;
	}

	@Override
	public void runToCompletion() {
		if (algorithm == null)
			return;
		while (algorithm.isCompleted()) {
			algorithm.step();
			stepCount++;
		}
	}

	@Override
	public void step() {
		if (algorithm == null)
			return;
		if (!algorithm.isCompleted()) {
			algorithm.step();
			stepCount++;
		}
	}

	@Override
	public void reverseStep() {
		if (algorithm == null)
			return;
		if (stepCount > 0) {
			algorithm.reverseStep();
			stepCount--;
		}
	}

	@Override
	public void reset() {
		if (algorithm == null)
			return;
		algorithm.reset();
		stepCount = 0;
	}

	@Override
	public int getStepCount() {
		return stepCount;
	}
}
