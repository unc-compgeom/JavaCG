package cg;

import util.ColorSpecial;


class EdgeComponent extends AbstractGeometry implements Edge {

	private Edge next;
	private Point o;
	private Edge rot; // the dual of this edge (counterclockwise)

	EdgeComponent() {
		super();
		isReady = true;
	}

	@Override
	public synchronized Point dest() {
		return sym().orig();
	}

	@Override
	public synchronized Edge dNext() {
		return sym().oNext().sym();
	}

	@Override
	public synchronized Edge dPrev() {
		return invRot().oNext().invRot();
	}

	@Override
	public synchronized Edge invRot() {
		return rot.rot().rot();
	}

	@Override
	public synchronized Edge lNext() {
		return invRot().oNext().rot();
	}

	@Override
	public synchronized Edge lPrev() {
		return oNext().sym();
	}

	@Override
	public synchronized Edge oNext() {
		return next;
	}

	@Override
	public synchronized Edge oPrev() {
		return rot().oNext().rot();
	}

	@Override
	public synchronized Point orig() {
		return o;
	}

	@Override
	public synchronized Edge rNext() {
		return rot().oNext().invRot();
	}

	@Override
	public synchronized Edge rot() {
		return rot;
	}

	@Override
	public synchronized Edge rPrev() {
		return sym().oNext();
	}

	@Override
	public synchronized void setCoordinates(Point origin, Point destination) {
		isReady = false;
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
				Segment anim = GeometryManager.newSegment(
						oldO.sub(oChange.mult(i)), oldD.sub(dChange.mult(i)));
				anim.setColorNoAnim(ColorSpecial.YELLOW_ROSE);
				try {
					Thread.sleep(1);
				} catch (InterruptedException ignored) {
				}
				GeometryManager.destroy(anim);
			}
			setOrig(origin);
			setDest(destination);
			setInvisible(false);
			isReady = true;
			notifyObservers(this);
			synchronized (this) {
				notifyAll();
			}
		}

	}

	@Override
	public synchronized void setDest(Point d) {
		isReady = false;
		GeometryManager.destroy(d);
		sym().setOrig(d);
		isReady = true;
		notifyObservers(this);
		notifyAll();
	}


	@Override
	public synchronized void setNext(Edge next) {
		this.next = next;
	}

	@Override
	public synchronized void setOrig(Point o) {
		isReady = false;
		GeometryManager.destroy(o);
		this.o = o;
		isReady = true;
		notifyObservers(this);
		notifyAll();
	}

	@Override
	public synchronized void setRot(Edge rot) {
		this.rot = rot;
	}

	@Override
	public synchronized Edge sym() {
		return rot.rot();
	}

	@Override
	public synchronized String toString() {
		return orig() + "-" + dest();
	}
}
