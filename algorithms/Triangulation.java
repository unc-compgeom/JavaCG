//package algorithms;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//import java.util.TreeSet;
//
//import util.CG;
//import cg.Edge;
//import cg.Vertex;
//import cg.VertexSet;
//
//public class Triangulation {
//	private enum VertexType {
//		START, END, SPLIT, MERGE, REGULAR
//	}
//
////	public static void makeMonotone(VertexSet points, List<Edge> partitions) {
////		// 1. sort the points
////		VertexSet sorted = CG.lexicographicalSort(points);
////		// 2. initialize a binary search tree
////		Set<Vertex> t = new TreeSet<Vertex>();
////
////		while (!sorted.isEmpty()) {
////			// 3. remove the first point in the sorted list
////			// 4. and handle it appropriately depending on its type
////			Vertex p = new Vertex();
////			Vertex pt = sorted.removeFirst();
////			p.x = pt.getX();
////			p.y = pt.getY();
////			switch (type(p)) {
////			case END:
////				handleEndVertex(p, partitions, t);
////				break;
////			case MERGE:
////				handleMergeVertex(p, partitions, t);
////				break;
////			case REGULAR:
////				handleRegularVertex(p, partitions, t);
////				break;
////			case SPLIT:
////				handleSplitVertex(p, partitions, t);
////				break;
////			case START:
////				handleStartVertex(p, partitions, t);
////				break;
////			default:
////				System.out.println("missing case in makeMonotone() for "
////						+ type(p));
////				break;
////			}
////		}
////	}
//
//	private static void handleStartVertex(Vertex p, List<Edge> partitions,
//			Set<Vertex> t) {
//		// insert p into t and set helper(p) to p
//		t.add(p);
//		// TODO Auto-generated method stub
//
//	}
//
//	private static void handleSplitVertex(Vertex p, List<Edge> partitions,
//			Set<Vertex> t) {
//		// TODO Auto-generated method stub
//
//	}
//
//	private static void handleRegularVertex(Vertex p, List<Edge> partitions,
//			Set<Vertex> t) {
//		// TODO Auto-generated method stub
//
//	}
//
//	private static void handleMergeVertex(Vertex p, List<Edge> partitions,
//			Set<Vertex> t) {
//		// TODO Auto-generated method stub
//
//	}
//
//	private static void handleEndVertex(Vertex p, List<Edge> partitions,
//			Set<Vertex> t) {
//		// TODO Auto-generated method stub
//
//	}
//
//	private static VertexType type(Vertex p) {
//		// TODO Find out the vertex type;
//		return VertexType.START;
//	}
//
//}