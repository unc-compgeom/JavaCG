package algorithms;

import java.awt.Color;

import predicates.Predicate;
import predicates.Predicate.Orientation;
import util.CG;
import cg.GeometryManager;
import cg.Polygon;
import cg.Vector;
import cg.VectorComponent;
import cg.Vertex;
import cg.VertexSet;

public class Calipers {

	/*
	 * 
	 * Rotating caliper algorithm input Vertex set in 2D. Output is maximum
	 * diameter of the convex hull and the minimum width of the convex hull.
	 */

		public static void doCalipers(VertexSet Vertices, Polygon hull) {
		int i=0;
		int j=1;
		double diam = -1;
		double width = Double.POSITIVE_INFINITY;
		
		//Visualization Variables
		Vector support1 = new VectorComponent(-1,-1,-1,-1);
		Vector support2 = new VectorComponent(-1,-1,-1,-1);
		Vector support3 = new VectorComponent(-1,-1,-1,-1);
		Vector support4 = new VectorComponent(-1,-1,-1,-1);
		Vector widthline = new VectorComponent(-1,-1,-1,-1);
		Vector diamline = new VectorComponent(-1,-1,-1,-1);
		setObservable(support1,Color.BLUE,hull);
		setObservable(support2,Color.BLUE,hull);
		setObservable(support3,Color.BLUE,hull);
		setObservable(support4,Color.BLUE,hull);
		setObservable(widthline,Color.ORANGE,hull);
		setObservable(diamline,Color.GREEN,hull);
		
		hull = getConvexHull(Vertices, hull);
		
		//Initialization Step
		while(cwIntersection(0,j,hull)){
			j++;
		}
		
		//Rotation Step
		while(j<hull.size()){
			diam = checkDiameter(diam, i, j, hull, diamline, support1, support2);
			width = checkWidth(width, i, j, hull, widthline, support3, support4);
			if(cwIntersection(i+1,j,hull)){
				j++;
			}
			else if(cwIntersection(j+1,i,hull)){
				i++;
			}
			else{
				j++;
				i++;
			}
		}
	}

	/** Uses Jarvis march to compute convex hull */

	private static Polygon getConvexHull(VertexSet points, Polygon hull){
		Vertex min = CG.findSmallestYX(points);
		Vertex p, q = min;
		int i = 0;
		do {
			hull.addLast(q);
			p = hull.get(i);
			q = nextHullPoint(points, p);
			i++;
		} while (!q.equals(min));
		return hull;
	}

	private static Vertex nextHullPoint(VertexSet points, Vertex p) {
		Vertex q = p;
		for (int i = 0; i < points.size(); i++) {
			Vertex r = points.get(i);
			Orientation o = Predicate.findOrientation(p, q, r);
			if (o == Orientation.COUNTERCLOCKWISE
					|| (o == Orientation.COLINEAR && CG.distSquared(p, r) > CG
							.distSquared(p, q))) {
				q = r;
			}
		}
		return q;
	}

	private static boolean cwIntersection(int i, int j, Polygon hull){
		Vertex Pa = new VertexComponent(hull.get(i-1));
		Vertex Pb = new VertexComponent(hull.get(i));
		Vertex Pd = new VertexComponent(hull.get(j+1));
		Pd.setX(Pd.getX()-hull.get(j).getX()+Pb.getX());
		Pd.setY(Pd.getY()-hull.get(j).getY()+Pb.getY());
		return Predicate.findOrientation(Pa, Pb, Pd)==Predicate.Orientation.CLOCKWISE;
	}
	
	public static void setObservable(Vector component, Color color, Polygon hull){
		component.addObservers(hull.getObservers());
		component.setColor(color);
	}
	
	public static double checkDiameter(double diam, int i, int j, Polygon hull, Vector diamline, Vector support1, Vector support2){
		Vertex pi = hull.get(i);
		Vertex pj = hull.get(j);
		double tempdiam = pi.distanceSquared(pj);
		if(tempdiam>diam){
			int tan1,tan2;
			diam=tempdiam;
			diamline.update(pi,pj);
			if(cwIntersection(i+1,j,hull)){
				tan1=j;
				tan2=j+1;
			}
			else{
				tan1=i;
				tan2=i+1;
			}
			Vector supportBasis = new VectorComponent(hull.get(tan1),hull.get(tan2));
			supportBasis.translate(pi);
			support1.update(supportBasis.getHead(), supportBasis.originReflection().getHead());
			supportBasis.translate(pj);
			support2.update(supportBasis.getHead(), supportBasis.originReflection().getHead());
		}
		return diam;
	}
	
	public static double checkWidth(double width, int i, int j, Polygon hull, Vector widthline, Vector support3, Vector support4){
		int r,p,q;
		double tempwidth;
		if(cwIntersection(i+1,j,hull)){
			r=i;
			p=j;
			q=j+1;
		}
		else{
			r=j;
			p=i;
			q=i+1;
		}
		Vector pq = new VectorComponent(hull.get(p),hull.get(q));
		Vector rrq = new VectorComponent(hull.get(p),hull.get(q));
		rrq.translate(hull.get(r));
		Vertex intersection = rrq.perpendicular().intersection(pq);
		tempwidth = intersection.distanceSquared(hull.get(r));
		if(tempwidth<width){
			width = tempwidth;
			widthline.update(hull.get(r), intersection);
			Vector supportBasis = new VectorComponent(rrq.getTail(),intersection);
			supportBasis = supportBasis.perpendicular();
			supportBasis.translate(rrq.getTail());
			support3.update(supportBasis.getHead(), supportBasis.originReflection().getHead());
			supportBasis.translate(intersection);
			support4.update(supportBasis.getHead(), supportBasis.originReflection().getHead());
		}
		return width;
	}
}