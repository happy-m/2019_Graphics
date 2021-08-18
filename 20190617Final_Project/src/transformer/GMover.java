package transformer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class GMover extends GTransformer {
	private Point2D previous;
	
	public GMover() {
		this.previous = new Point2D.Double();
	}

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.getgShape().initMoving(graphics2d, x, y);
		this.previous.setLocation(x, y);
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		this.getgShape().draw(graphics2d);
		
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(x-this.previous.getX(), y-this.previous.getY());
		this.getgShape().transformShape(affineTransform);
		
		this.getgShape().keepMoving(graphics2d, x, y);
		this.getgShape().draw(graphics2d);
		this.previous.setLocation(x, y);
	}

	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
		this.getgShape().finishMoving(graphics2d, x, y);
	}
	
	public void moveLittle() {
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(10, 10);
		this.gShape.transformShape(affineTransform);
	}
}
