package cg;

import util.DuplicatePointException;
import util.MalformedTriangulationException;

public interface Subdivision extends Drawable {
	public void insertSite(Point p) throws DuplicatePointException,
			MalformedTriangulationException;
}
