package cg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import util.ColorSpecial;

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
	public void paint(final GraphicsContext gc) {
		if (isInvisible()) {
			return;
		}
		// push old values onto the "stack"
		final Paint oldFill = gc.getFill();
		final Paint oldStroke = gc.getStroke();
		final double oldSize = gc.getLineWidth();
		final Color c = super.getColor();
		if (c != null) {
			gc.setFill(c);
			gc.setStroke(c.darker());
		}
		gc.setLineWidth(GeometryManager.getSize());

		gc.strokeLine(orig().getX(), orig().getY(), dest().getX(), dest()
				.getY());

		// restore gc from the "stack"
		gc.setFill(oldFill);
		gc.setStroke(oldStroke);
		gc.setLineWidth(oldSize);
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
	public void setCoordinates(final Point origin, final Point destination) {
		if (o == null || dest() == null) {
			setOrig(origin);
			setDest(destination);
		} else {
			final Point oldO = new PointComponent(o), newO = new PointComponent(
					origin);
			final Point oldD = new PointComponent(dest()), newD = new PointComponent(
					destination);
			setInvisible(true);

			final int delay = GeometryManager.getDelay();
			final Point oChange = oldO.sub(newO).div(delay);
			final Point dChange = oldD.sub(newD).div(delay);
			for (int i = 0; i < delay; i++) {
				final Segment anim = GeometryManager.newSegment(
						oldO.sub(oChange.mult(i)), oldD.sub(dChange.mult(i)));
				anim.setColorNoAnim(ColorSpecial.YELLOW_ROSE);
				try {
					Thread.sleep(1);
				} catch (final InterruptedException ignored) {
				}
				GeometryManager.destroy(anim);
			}
			setOrig(origin);
			setDest(destination);
			setInvisible(false);
		}

	}

	@Override
	public void setDest(final Point d) {
		GeometryManager.destroy(d);
		sym().setOrig(d);
	}

	@Override
	public void setInvisible(final boolean isInvisible) {
		super.setInvisible(isInvisible);
		o.setInvisible(isInvisible);
		sym().orig().setInvisible(isInvisible);
	}

	@Override
	public void setNext(final Edge next) {
		this.next = next;
	}

	@Override
	public void setOrig(final Point o) {
		GeometryManager.destroy(o);
		this.o = o;
	}

	@Override
	public void setRot(final Edge rot) {
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
