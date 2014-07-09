package predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cg.GeometryManager;

public class PredicateTest {

	@Test
	public void testIsPointInCirclePointPointPointPoint() {
		assertTrue("false", Predicate.isPointInCircle(
				GeometryManager.newPoint(1, 1), GeometryManager.newPoint(0, 0),
				GeometryManager.newPoint(2, 0), GeometryManager.newPoint(0, 2)));
		assertFalse("Point is on boundary", Predicate.isPointInCircle(
				GeometryManager.newPoint(1, 0), GeometryManager.newPoint(0, 1),
				GeometryManager.newPoint(2, 1), GeometryManager.newPoint(1, 0)));
		assertTrue("false", Predicate.isPointInCircle(
				GeometryManager.newPoint(1, 1), GeometryManager.newPoint(0, 1),
				GeometryManager.newPoint(1, 0), GeometryManager.newPoint(2, 1)));
		assertFalse("Point is on boundary", Predicate.isPointInCircle(
				GeometryManager.newPoint(2, 1), GeometryManager.newPoint(0, 1),
				GeometryManager.newPoint(1, 0), GeometryManager.newPoint(2, 1)));
		assertFalse("Point is on boundary", Predicate.isPointInCircle(
				GeometryManager.newPoint(1, 0), GeometryManager.newPoint(0, 1),
				GeometryManager.newPoint(1, 0), GeometryManager.newPoint(2, 1)));
		assertFalse("true", Predicate.isPointInCircle(
				GeometryManager.newPoint(1, 2), GeometryManager.newPoint(0, 1),
				GeometryManager.newPoint(1, 0), GeometryManager.newPoint(2, 1)));
	}

}
