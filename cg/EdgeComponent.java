package cg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import util.ColorSpecial;

class EdgeComponent extends AbstractGeometry implements Edge {

	private Edge next;
	private Point o;
	private Edge rot; // the dual of this edge (counterclockwise)
	private boolean visited;

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
	public boolean visited() {
		return visited;
	}

	@Override
	public void setVisited() {
		visited = !visited;
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
		Color oldG = g.getColor(), c = super.getColor();
		if (c != null) {
			g.setColor(c);
		}
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(GeometryManager.getSize()));
		g2D.drawLine((int) orig().getX(), (int) orig().getY(), (int) dest()
				.getX(), (int) dest().getY());
		g2D.setStroke(new BasicStroke());
		orig().paint(g);
		dest().paint(g);
		g.setColor(oldG);
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
	public void setDest(Point d) {
		GeometryManager.destroy(d);
		animateChange(this.dest(), d);
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
		GeometryManager.destroy(o);
		animateChange(this.o, o);
		// set state
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

	private void animateChange(Point from, Point to) {
		if (from == null) {
			return;
		}
		// animateChange
		Point tmp = new PointComponent(from);
		Point change = from.sub(to).div(GeometryManager.getDelay());
		for (int i = 0; i < GeometryManager.getDelay(); i++) {
			tmp = tmp.sub(change);
			Segment anim = GeometryManager.newSegment(tmp, dest());
			anim.setColorNoAnim(ColorSpecial.YELLOW_ROSE);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GeometryManager.destroy(anim);
		}
	}
}
