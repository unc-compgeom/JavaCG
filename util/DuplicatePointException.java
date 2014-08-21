package util;

import cg.Point;

public class DuplicatePointException extends Exception {

	private static final long serialVersionUID = -4845293946512047629L;
	private final Point duplicate;

	public DuplicatePointException() {
		this(null, null, null);
	}

	public DuplicatePointException(final Point duplicate) {
		this(null, null, duplicate);
	}

	public DuplicatePointException(final String message, final Point duplicate) {
		this(message, null, duplicate);
	}

	private DuplicatePointException(final String message,
			final Throwable cause, final Point duplicate) {
		super(message, cause);
		this.duplicate = duplicate;
	}

	public Point getDuplicate() {
		return duplicate;
	}
}
