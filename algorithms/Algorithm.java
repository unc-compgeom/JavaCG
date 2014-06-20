package algorithms;

public enum Algorithm {
	BENTLEY_FAUST_PREPARATA("Bentley Faust Preparata"), CALIPERS("Calipers"), CHAN(
			"Chan"), GRAHM_SCAN("Grahm scan"), JARVIS_MARCH("Jarvis march"), MELKMAN(
			"Melkman"), MONOTONE_CHAIN("Monotone chain"), QUICKHULL("Quickhull"), Welzl(
			"Welzl");

	private String s;

	private Algorithm(String s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return s;
	}

	public static Algorithm fromString(String s) {
		if (s != null) {
			for (Algorithm a : Algorithm.values()) {
				if (s.equalsIgnoreCase(a.toString())) {
					return a;
				}
			}
		}
		throw new IllegalArgumentException("No constant with text " + s
				+ " found");
	}
}
