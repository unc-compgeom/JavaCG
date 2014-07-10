package visualizer;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import predicates.Predicate;
import util.CG;
import cg.GeometryManager;

public class Main {
	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		// set up and start the view;
		ViewModel m = new ViewModel();
		ViewController c = new ViewController(m);
		View v = new View(c);
		GeometryManager.addObserver(v);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Predicate.ahead);
		System.out.println(Predicate.ccw);
		System.out.println(Predicate.findOrientation);
		System.out.println(Predicate.isLeftOrInside);
		System.out.println(Predicate.isPointInCircle);
		System.out.println(Predicate.leftOf);
		System.out.println(Predicate.leftOrAhead);
		System.out.println(Predicate.onEdge);
		System.out.println(Predicate.rightOf);
		System.out.println(Predicate.rightOrAhead);
		System.out.println(CG.cross);
		System.out.println(CG.distSquared);
		System.out.println(CG.findSmallestYX);
		System.out.println(CG.lexicographicalSort);
		System.out.println(CG.randomColor);
		System.out.println(CG.sortByAngle);
	}
}
