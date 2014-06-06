package visualizer;

public class Main {
	public static void main(String[] args) {
		// set up and start the view;
		Model m = new Model();
		Controller c = new Controller(m); 
		View v = new View(c);
		m.addObserver(v);
	}

}
