package algorithms;

import predicates.Predicate;
import util.CG;
import cg.Point;
import cg.PointComponent;
import cg.Polygon;

public class Calipers {
	
	public static void doCalipers(Polygon points, Polygon hull, Polygon diamline, Polygon support1, Polygon support2) {
		hull = getConvexHull(points, hull);
		int i=0;
		int j=1;
		double diam = Double.POSITIVE_INFINITY;
		double tempdiam;
		while(cwIntersection(0,j,hull,true)){
			j++;
		}
		while(j!=0){
			Point pi = hull.getPoint(i);
			Point pj = hull.getPoint(j);
			tempdiam = Math.sqrt(Math.hypot(((double)pi.getX()-(double)pj.getX()),((double)pi.getY()-(double)pj.getY())));
			if(tempdiam<diam){
				diam=tempdiam;
				if(!diamline.isEmpty()){
					diamline.remove();
					diamline.remove();
				}
				if(!support1.isEmpty()){
					support1.remove();
					support1.remove();
				}
				if(!support2.isEmpty()){
					support2.remove();
					support2.remove();
				}
				diamline.addPoint(pi);
				diamline.addPoint(pj);
				CG.sleep();
			/*	The support lines intersect the convex hull at this point
			 * since they are simply perpendicular to the diameter
				double slope = (((double)pi.getY()-(double)pj.getY())/((double)pi.getX()-(double)pj.getX()));
				slope = -1/slope;
				double bi = pi.getY()-(pi.getX()*slope);
				double bj = pj.getY()-(pj.getX()*slope);
				support1.addPoint(new PointComponent((int)pi.getX()+60,(int)((slope*(pi.getX()+60))+bi)));
				support1.addPoint(new PointComponent((int)pi.getX()-60,(int)((slope*(pi.getX()-60))+bi)));
				support2.addPoint(new PointComponent((int)pj.getX()+60,(int)((slope*(pj.getX()+60))+bj)));
				support2.addPoint(new PointComponent((int)pj.getX()-60,(int)((slope*(pj.getX()-60))+bj)));
			*/
			}
			if(cwIntersection(i+1,j,hull,false) || i+1==j){
				j=(j+1)%hull.numPoints();
			}
			else{
				i++;
			}
		}
	}
	
	/** Uses Jarvis march to compute convex hull */
	private static Polygon getConvexHull(Polygon pl, Polygon hull){
		Point min = leftmost(pl);
		do {
			hull.addPoint(min);
			CG.sleep();
			min = pl.getPoint(min != pl.getPoint(0) ? 0 : 1);
			for (int i = 1; i < pl.numPoints(); i++) {
				if (Predicate.isPointLeftOfLine(hull.getLast(), min,pl.getPoint(i))) {
					min = pl.getPoint(i);
				}
			}
		} while (!min.equals(hull.getPoint(0)));
		return hull;
	}
	
	private static Point leftmost(Polygon p) {
		Point leftmost = p.getPoint(0);
		for (int i = 1; i < p.numPoints(); i++) {
			if (leftmost.getX() > p.getPoint(i).getX()) {
				leftmost = p.getPoint(i);
			}
		}
		return leftmost;
	}

	private static boolean cwIntersection(int i, int j, Polygon hull, boolean first){
		Point pi = hull.getPoint(i);
		//Point pi1 = first ? hull.getLast() : hull.getPoint((i+1) % hull.numPoints());
		int temp = (i-1)%hull.numPoints();
		if (temp<0){temp+=hull.numPoints();}
		Point pi1 = hull.getPoint(temp);
		Point pj = hull.getPoint(j % hull.numPoints());
		Point pj1 = hull.getPoint((j+1) % hull.numPoints());
		double slopei = (((double)pi.getY()-(double)pi1.getY())/((double)pi.getX()-(double)pi1.getX()));
		double slopej = (((double)pj.getY()-(double)pj1.getY())/((double)pj.getX()-(double)pj1.getX()));
		double bi = (double)pi.getY()-((double)pi.getX()*slopei);
		double bj = (double)pj.getY()-((double)pj.getX()*slopej);
		if((slopej-slopei)==0){
			return false;
		}
		double xint = (bi-bj)/(slopej-slopei);
		double yint = bi + (slopei*xint);
		double det = (((double)pj.getX() - (double)pi.getX()) * (yint - (double)pi.getY())) - (((double)pj.getY() - (double)pi.getY()) * (xint - (double)pi.getX()));
		if(det<0){
			return false;
		}
		return true;
	}
	
}