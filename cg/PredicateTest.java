package cg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class PredicateTest {

	@Test
	public void testTriArea() {
		assertEquals(1, Predicate.triArea(GeometryManager.newPoint(0, 0),
				GeometryManager.newPoint(1, 0), GeometryManager.newPoint(0, 1)));
		assertEquals(2, Predicate.triArea(GeometryManager.newPoint(0, 0),
				GeometryManager.newPoint(1, 0), GeometryManager.newPoint(0, 2)));
		assertEquals(2, Predicate.triArea(GeometryManager.newPoint(0, 0),
				GeometryManager.newPoint(2, 0), GeometryManager.newPoint(0, 1)));
		assertEquals(25, Predicate.triArea(GeometryManager.newPoint(0, 0),
				GeometryManager.newPoint(5, 0), GeometryManager.newPoint(0, 5)));

	}

	@Test
	public void testInCircle() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCcw() {
		assertEquals(
				true,
				Predicate.ccw(GeometryManager.newPoint(0, 0),
						GeometryManager.newPoint(1, 0),
						GeometryManager.newPoint(2, 2)));
		assertEquals(
				true,
				Predicate.ccw(GeometryManager.newPoint(10, 0),
						GeometryManager.newPoint(10, 10),
						GeometryManager.newPoint(0, 10)));
		assertEquals(
				false,
				Predicate.ccw(GeometryManager.newPoint(0, 0),
						GeometryManager.newPoint(1, 0),
						GeometryManager.newPoint(2, 0)));
		assertEquals(
				false,
				Predicate.ccw(GeometryManager.newPoint(2, 2),
						GeometryManager.newPoint(1, 0),
						GeometryManager.newPoint(0, 0)));
		assertEquals(
				false,
				Predicate.ccw(GeometryManager.newPoint(2, 2),
						GeometryManager.newPoint(0, 0),
						GeometryManager.newPoint(0, 10)));
		assertEquals(
				true,
				Predicate.ccw(GeometryManager.newPoint(2, 2),
						GeometryManager.newPoint(0, 10),
						GeometryManager.newPoint(0, 0)));
	}

	@Test
	public void testRightOf() {
		Edge e = QuadEdge.makeEdge();
		e.setOrig(new PointComponent(0, 0));
		e.setDest(new PointComponent(0, 10));
		assertEquals(true, Predicate.rightOf(GeometryManager.newPoint(2, 2), e));
		e = QuadEdge.makeEdge();
		e.setOrig(new PointComponent(0, 0));
		e.setDest(new PointComponent(100, 0));
		assertEquals(true,
				Predicate.rightOf(GeometryManager.newPoint(50, -50), e));
	}

	@Test
	public void testLeftOf() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testOnEdge() {
		Edge e = QuadEdge.makeEdge();
		e.setOrig(new PointComponent(0, 1));
		e.setDest(new PointComponent(0, 2));
		assertEquals(true, Predicate.onEdge(GeometryManager.newPoint(0, 1), e));
		assertEquals(false,
				Predicate.onEdge(GeometryManager.newPoint(45, -1), e));
		assertEquals(false, Predicate.onEdge(GeometryManager.newPoint(0, 3), e));
		assertEquals(false, Predicate.onEdge(GeometryManager.newPoint(0, 0), e));
		assertEquals(true, Predicate.onEdge(GeometryManager.newPoint(0, 2), e));
	}

}
