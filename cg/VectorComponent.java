package cg;

import java.awt.Graphics;

public class VectorComponent extends AbstractGeometry implements Vector {

	private Vertex tail;
	private Vertex head;
	int xdir;
	int ydir;
	private double[] homogeneous = {0,0,0};
	
	/*
	 * The vector will have an origin indicating the location of the tail of the vector.
	 * The xdir and ydir give magnitudes in the x and y directions.
	 * The homogeneous array stores the homogeneous coordinates of the vector as <W,X,Y>.
	 */
	
	public VectorComponent(Vertex v1, Vertex v2){
		this(v1.getX(),v1.getY(),v2.getX(),v2.getY());
	}
	
	public VectorComponent(int x1, int y1, int x2, int y2){
		tail = new VertexComponent(x1,y1);
		head = new VertexComponent(x2,y2);
		xdir = x2-x1;
		ydir = y2-y1;
		homogeneous[0]=(x1*y2) - (y1*x2);
		homogeneous[1]=y1-y2;
		homogeneous[2]=x2-x1;
	}
	
	@Override
	public Vertex getTail() {
		return tail;
	}
	
	@Override
	public Vertex getHead(){
		return head;
	}
	
	@Override
	public int getXdir(){
		return xdir;
	}
	
	@Override
	public int getYdir(){
		return ydir;
	}
	
	@Override
	public double length() {
		return Math.hypot(xdir, ydir);
	}
	
	@Override
	public double lengthSquared() {
		return Math.pow(xdir, 2) + Math.pow(ydir, 2);
	}
	
	@Override
	public Vector unitVector() {
		return new VectorComponent(0, 0,(int)(xdir/this.length()),(int)(ydir/this.length()));
	}

	@Override
	public double dotProduct(Vector v) {
		return (this.xdir*v.getYdir()) - (this.ydir*v.getXdir());
	}
	
	public double getHomogeneous(int index){
		while(index<0){
			index=index+3;
		}
		return homogeneous[index%3];
	}

	@Override
	public Vertex intersection(Vector v) {
		double x0 = homogeneous[1]*v.getHomogeneous(2) - homogeneous[2]*v.getHomogeneous(1);
		double x1 = -homogeneous[0]*v.getHomogeneous(2) + homogeneous[2]*v.getHomogeneous(0);
		double x2 = homogeneous[0]*v.getHomogeneous(1) - homogeneous[1]*v.getHomogeneous(0);
		return new VertexComponent((int)(x1/x0),(int)(x2/x0));
	}

	@Override
	public Vector perpendicular() {
		return new VectorComponent(tail.getX(),tail.getY(),-ydir+tail.getX(),xdir+tail.getY());
	}
	
	@Override
	public Vector originReflection(){
		return new VectorComponent(tail.getX(),tail.getY(),tail.getX()-xdir,tail.getY()-ydir);
	}

	@Override
	public Vector add(Vector v) {
		int x = head.getX()+v.getXdir();
		int y = head.getY()+v.getYdir();
		return new VectorComponent(tail.getX(), tail.getY(), x, y);
	}

	@Override
	public void translate(Vertex v) {
		head.setX(head.getX()-tail.getX()+v.getX());
		head.setY(head.getY()-tail.getY()+v.getY());
		tail.setX(v.getX());
		tail.setY(v.getY());
		homogeneous[0]=(tail.getX()*head.getY()) - (tail.getY()*head.getX());
		notifyObservers();
	}

	@Override
	public void update(Vertex v1, Vertex v2) {
		update(v1.getX(), v1.getY(), v2.getX(),v2.getY());
	}

	@Override
	public void update(int x1, int y1, int x2, int y2) {
		tail = new VertexComponent(x1,y1);
		head = new VertexComponent(x2,y2);
		xdir = x2-x1;
		ydir = y2-y1;
		homogeneous[0]=(x1*y2) - (y1*x2);
		homogeneous[1]=y1-y2;
		homogeneous[2]=x2-x1;
		notifyObservers();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(super.getColor());
		g.drawLine(tail.getX(), tail.getY(), head.getX(), head.getY());
	}

}
