package algorithms;

import predicates.Predicate;
import util.CG;
import cg.Point;
import cg.PointComponent;
import cg.PointSet;
import cg.Polygon;

public class Calipers {
	
	/*
	 * 
	 * Rotating caliper algorithm input point set in 2D.  Output is maximum diameter of the convex hull
	 * and the minimum width of the convex hull.
	 * 
	 */
	
	public static void doCalipers(PointSet points, Polygon hull, Polygon diamline, Polygon support1, Polygon support2) {
		hull = getConvexHull(points, hull);
		int i=0;
		int j=1;
		double diam = -1;
//		double width = Double.POSITIVE_INFINITY;
		double tempdiam;
		while(cwIntersection(0,j,hull)){
			j++;
		}
		while(j!=0){
			Point pi = hull.getPoint(i);
			Point pj = hull.getPoint(j);
			tempdiam = Math.pow((pi.getX()-pj.getX()), 2) + Math.pow((pi.getY()-pj.getY()), 2);
			if(tempdiam>diam){
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
				Point vector = perpendicular(i,j,hull);
				support1.add(new PointComponent(pi.getX()+vector.getX(),pi.getY()+vector.getY()));
				support1.add(new PointComponent(pi.getX()-vector.getX(),pi.getY()-vector.getY()));
				support2.add(new PointComponent(pj.getX()+vector.getX(),pj.getY()+vector.getY()));
				support2.add(new PointComponent(pj.getX()-vector.getX(),pj.getY()-vector.getY()));
				CG.sleep();
			}
			if(cwIntersection(i+1,j,hull)){
				j=(j+1)%hull.numPoints();
			}
			else{
				i++;
			}
		}
	}
	
	/** Uses Jarvis march to compute convex hull */
	private static Polygon getConvexHull(PointSet pl, Polygon hull){
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
	
	private static Point leftmost(PointSet p) {
		Point leftmost = p.getPoint(0);
		for (int i = 1; i < p.numPoints(); i++) {
			if (leftmost.getX() > p.getPoint(i).getX()) {
				leftmost = p.getPoint(i);
			}
		}
		return leftmost;
	}

	private static boolean cwIntersection(int i, int j, Polygon hull){
		int tempi = i==0 ? hull.numPoints()-1 : i-1;
		Point Pa = new PointComponent(hull.getPoint(tempi));
		Point Pb = new PointComponent(hull.getPoint(i));
		Point Pd = new PointComponent(hull.getPoint((j+1)%hull.numPoints()));
		Pd.setPoint(Pd.getX()-hull.getPoint(j).getX()+Pb.getX(), Pd.getY()-hull.getPoint(j).getY()+Pb.getY());
		return Predicate.findOrientation(Pa, Pb, Pd)==Predicate.Orientation.CLOCKWISE;
	}
	
	private static Point perpendicular(int i, int j, Polygon hull){
		double [][]  perp1 = {{0.0,-1.0},{1.0,0.0}};
		double [][]  vector1 = {{0},{0}};
		double [][]  vector2 = {{0},{0}};
		double [][]  vector3 = {{0},{0}};
		double [][]  result1 = {{0},{0}};
		if(cwIntersection(i+1,j,hull)){
			i = i==0 ? hull.numPoints()-1 : i-1;
		}
		else{
			j = j==0 ? hull.numPoints()-1 : j-1;
		}
		vector1[0][0] = hull.getPoint((i+1)%hull.numPoints()).getX()-hull.getPoint(i).getX();
		vector1[1][0] = hull.getPoint((i+1)%hull.numPoints()).getY()-hull.getPoint(i).getY();
		double dist = Math.sqrt(Math.pow((hull.getPoint((i+1)%hull.numPoints()).getX()-hull.getPoint(i).getX()), 2) + Math.pow((hull.getPoint((i+1)%hull.numPoints()).getY()-hull.getPoint(i).getY()), 2));
		vector1[0][0] = vector1[0][0]/dist;
		vector1[1][0] = vector1[1][0]/dist;
		vector2[0][0] = hull.getPoint((j+1)%hull.numPoints()).getX()-hull.getPoint(j).getX();
		vector2[1][0] = hull.getPoint((j+1)%hull.numPoints()).getY()-hull.getPoint(j).getY();
		dist = Math.sqrt(Math.pow((hull.getPoint((j+1)%hull.numPoints()).getX()-hull.getPoint(j).getX()), 2) + Math.pow((hull.getPoint((j+1)%hull.numPoints()).getY()-hull.getPoint(j).getY()), 2));
		vector2[0][0] = vector2[0][0]/dist;
		vector2[1][0] = vector2[1][0]/dist;
		vector3[0][0]=(vector1[0][0]+vector2[0][0])/2;
		vector3[1][0]=(vector1[1][0]+vector2[1][0])/2;
		for (int a = 0; a < 2; a++) {
            for (int b = 0; b < 1; b++) {
                for (int c = 0; c < 2; c++) {
                    result1[a][b] += perp1[a][c] * vector3[c][b];
                }
            }
        }
		return new PointComponent((int)(result1[0][0]*90),(int)(result1[1][0]*90));
	}
	
	
	
}