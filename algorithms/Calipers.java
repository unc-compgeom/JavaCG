package algorithms;

import java.awt.Color;

import cg.Point;
import cg.PointSet;
import cg.Polygon;
import cg.PolygonComponent;

public class Calipers {

	public static void doCalipers(PointSet points, Polygon hull,
			Polygon diamline) {
		Polygon support1 = new PolygonComponent();
		Polygon support2 = new PolygonComponent();
		support1.addObservers(hull.getObservers());
		support1.setColor(Color.BLUE);
		support2.addObservers(hull.getObservers());
		support2.setColor(Color.BLUE);
		// get convex hull with Jarvis march
		JarvisMarch.doJarvisMarch(points, hull);
		int i = 0;
		int j = 1;
		double diam = Double.POSITIVE_INFINITY;
		double tempdiam;
		while (cwIntersection(0, j, hull, true)) {
			j++;
		}
		while (j != 0) {
			Point pi = hull.get(i);
			Point pj = hull.get(j);
			tempdiam = Math.sqrt(Math.hypot(
					((double) pi.getX() - (double) pj.getX()),
					((double) pi.getY() - (double) pj.getY())));
			if (tempdiam < diam) {
				diam = tempdiam;
				if (!diamline.isEmpty()) {
					diamline.remove();
					diamline.remove();
				}
				/*
				 * if(!support1.isEmpty()){ support1.remove();
				 * support1.remove(); } if(!support2.isEmpty()){
				 * support2.remove(); support2.remove(); }
				 */
				diamline.add(pi);
				diamline.add(pj);
				/*
				 * The support lines intersect the convex hull at this point
				 * since they are simply perpendicular to the diameter double
				 * slope =
				 * (((double)pi.getY()-(double)pj.getY())/((double)pi.getX
				 * ()-(double)pj.getX())); slope = -1/slope; double bi =
				 * pi.getY()-(pi.getX()*slope); double bj =
				 * pj.getY()-(pj.getX()*slope); support1.add(new
				 * PointComponent((
				 * int)pi.getX()+60,(int)((slope*(pi.getX()+60))+bi)));
				 * support1.add(new
				 * PointComponent((int)pi.getX()-60,(int)((slope
				 * *(pi.getX()-60))+bi))); support2.add(new
				 * PointComponent((int)pj
				 * .getX()+60,(int)((slope*(pj.getX()+60))+bj)));
				 * support2.add(new
				 * PointComponent((int)pj.getX()-60,(int)((slope
				 * *(pj.getX()-60))+bj)));
				 */
			}
			if (cwIntersection(i + 1, j, hull, false) || i + 1 == j) {
				j = (j + 1) % hull.size();
			} else {
				i++;
			}
		}
	}

	private static boolean cwIntersection(int i, int j, Polygon hull,
			boolean first) {
		Point pi = hull.get(i);
		// Point pi1 = first ? hull.getLast() : hull.get((i+1) % hull.size());
		int temp = (i - 1) % hull.size();
		if (temp < 0) {
			temp += hull.size();
		}
		Point pi1 = hull.get(temp);
		Point pj = hull.get(j % hull.size());
		Point pj1 = hull.get((j + 1) % hull.size());
		double slopei = (((double) pi.getY() - (double) pi1.getY()) / ((double) pi
				.getX() - (double) pi1.getX()));
		double slopej = (((double) pj.getY() - (double) pj1.getY()) / ((double) pj
				.getX() - (double) pj1.getX()));
		double bi = (double) pi.getY() - ((double) pi.getX() * slopei);
		double bj = (double) pj.getY() - ((double) pj.getX() * slopej);
		if ((slopej - slopei) == 0) {
			return false;
		}
		double xint = (bi - bj) / (slopej - slopei);
		double yint = bi + (slopei * xint);
		double det = (((double) pj.getX() - (double) pi.getX()) * (yint - (double) pi
				.getY()))
				- (((double) pj.getY() - (double) pi.getY()) * (xint - (double) pi
						.getX()));
		if (det < 0) {
			return false;
		}
		return true;
	}

}