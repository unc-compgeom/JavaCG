package cg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

class EdgeComponent extends AbstractGeometry implements Edge {

	private Edge next;
	private Point o;
	private Edge rot; // the dual of this edge (counterclockwise)

	EdgeComponent() {
		super();

	}

	@Override
	public Point dest() {
		return sym().orig();
	}

	@Override
	public Edge dNext() {
		return sym().oNext().sym();
	}

	@Override
	public Edge dPrev() {
		return invRot().oNext().invRot();
	}

	@Override
	public Edge invRot() {
		return rot.rot().rot();
	}

	@Override
	public Edge lNext() {
		return invRot().oNext().rot();
	}

	@Override
	public Edge lPrev() {
		return oNext().sym();
	}

	@Override
	public Edge oNext() {
		return next;
	}

	@Override
	public Edge oPrev() {
		return rot().oNext().rot();
	}

	@Override
	public Point orig() {
		return o;
	}

	@Override
	public void paint(Graphics g) {
		if (isInvisible()) {
			return;
		}
		g.setColor(getColor());
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(GeometryManager.getSize()));
		g2D.drawLine((int) orig().getX(), (int) orig().getY(), (int) dest()
				.getX(), (int) dest().getY());
		g2D.setStroke(new BasicStroke());
		orig().paint(g);
		dest().paint(g);
	}

	@Override
	public Edge rNext() {
		return rot().oNext().invRot();
	}

	@Override
	public Edge rot() {
		return rot;
	}

	@Override
	public Edge rPrev() {
		return sym().oNext();
	}

	@Override
	public void setColor(Color c) {
		super.setColor(c);
		o.setColor(c);
		sym().orig().setColor(c);
	}

	@Override
	public void setDest(Point d) {
		sym().setOrig(d);
	}

	@Override
	public void setInvisible(boolean isInvisible) {
		super.setInvisible(isInvisible);
		o.setInvisible(isInvisible);
		sym().orig().setInvisible(isInvisible);
	}

	@Override
	public void setNext(Edge next) {
		this.next = next;
	}

	@Override
	public void setOrig(Point o) {
		this.o = o;
	}

	@Override
	public void setRot(Edge rot) {
		this.rot = rot;
	}

	@Override
	public Edge sym() {
		return rot.rot();
	}

	@Override
	public String toString() {
		return orig() + "-" + dest();
	}

}
