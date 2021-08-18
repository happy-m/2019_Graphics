package transformer;

import java.awt.Graphics2D;

import Shape.GShape;

public abstract class GTransformer {

	protected GShape gShape;
	public GTransformer() {
		this.setgShape(null);
	}

	public GShape getgShape() { return gShape; }
	public void setgShape(GShape gShape) { this.gShape = gShape; }
	
	public abstract void initTransforming(Graphics2D graphics2d, int x, int y);
	public abstract void keepTransforming(Graphics2D graphics2d, int x, int y);
	public abstract void finishTransforming(Graphics2D graphics2d, int x, int y);
	public void continueTransforming(Graphics2D g2d, int x, int y) {}

	public void moveLittle() {
	}
}
