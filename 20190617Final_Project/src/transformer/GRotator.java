package transformer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class GRotator extends GTransformer {

	private Point2D.Double center, previous;
	
	public GRotator() {
		this.center = new Point2D.Double();
		this.previous = new Point2D.Double();
	}
	
	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.center.setLocation(
				this.getgShape().getCenterX(),
				this.getgShape().getCenterY());
		this.previous.setLocation(x, y);
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		this.getgShape().draw(graphics2d);
		AffineTransform affineTransform = new AffineTransform();
		double rotationAngle = computeRotationAngle(this.center, this.previous, new Point2D.Double(x,y));
		affineTransform.setToRotation(Math.toRadians(rotationAngle),center.getX(),center.getY());
		this.getgShape().transformShape(affineTransform);
		
		this.getgShape().draw(graphics2d);
		this.previous.setLocation(x, y);
	}

	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
		
	}

	private double computeRotationAngle(Point2D center, Point2D previous, Double current ){
		double startAngle = Math.toDegrees(
				Math.atan2(center.getX() - previous.getX(), center.getY() - previous.getY()));
		double endAngle = Math.toDegrees(
				Math.atan2(center.getX() - current.getX(), center.getY() - current.getY()));
		double angle = startAngle - endAngle;
		if(angle < 0) angle += 360;
		return angle;
	}
}
