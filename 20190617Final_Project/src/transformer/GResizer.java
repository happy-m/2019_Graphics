package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import Shape.GAnchors.EAnchors;



public class GResizer extends GTransformer {
	private Point2D.Double previous;
	
	public GResizer() {
		previous = new Point2D.Double();
	}

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.previous.setLocation(x, y);
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		this.getgShape().draw(graphics2d);
		Point2D p = new Point2D.Double(x,y);
		AffineTransform affineTransform = new AffineTransform();
		Point2D resizeOrigin = this.getResizeAnchor();
		affineTransform.translate(resizeOrigin.getX(), resizeOrigin.getY());
		Point2D resizeFactor = this.computeResizeFactor(this.previous, p);
		affineTransform.scale(resizeFactor.getX(), resizeFactor.getY());
		affineTransform.translate(-resizeOrigin.getX(), -resizeOrigin.getY());
		
		this.getgShape().transformShape(affineTransform);

		this.getgShape().draw(graphics2d);
		this.previous.setLocation(x, y);
	}

	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
	}
	
	private Point2D computeResizeFactor(Point2D previousP, Point2D currentP) {
		double px = previousP.getX();
		double py = previousP.getY();
		double cx = currentP.getX();
		double cy = currentP.getY();
		double deltaW = 0;
		double deltaH = 0;
		switch (this.getgShape().getSelectedAnchor()) {
		case EE:
			deltaW = cx - px;
			deltaH = 0;
			break;
		case WW:
			deltaW = -(cx - px);
			deltaH = 0;
			break;
		case SS:
			deltaW = 0;
			deltaH = cy - py;
			break;
		case NN:
			deltaW = 0;
			deltaH = -(cy - py);
			break;
		case SE:
			deltaW = cx - px;
			deltaH = cy - py;
			break;
		case NE:
			deltaW = cx - px;
			deltaH = -(cy - py);
			break;
		case SW:
			deltaW = -(cx - px);
			deltaH = cy - py;
			break;
		case NW:
			deltaW = -(cx - px);
			deltaH = -(cy - py);
			break;
		default:
			break;
		}
		
		double currentW = this.getgShape().getWidth();
		double currentH = this.getgShape().getHeight();
		double xFactor = 1.0;
		if (currentW > 0.0)
			xFactor = (1.0 + deltaW / currentW);
		double yFactor = 1.0;
		if (currentH > 0.0)
			yFactor = (1.0 + deltaH / currentH);
		return new Point.Double(xFactor, yFactor);
	}
	
	private Point getResizeAnchor() {
		Point resizeAnchor = new Point();
		if (getgShape().getSelectedAnchor() == Shape.GAnchors.EAnchors.NW) {
			resizeAnchor.setLocation(getgShape().getAnchorList().getAnchors().get(Shape.GAnchors.EAnchors.SE.ordinal()).getX(),
					getgShape().getAnchorList().getAnchors().get(EAnchors.SE.ordinal()).getY());
		} else if (getgShape().getSelectedAnchor() == EAnchors.NN) {
			resizeAnchor.setLocation(0, getgShape().getAnchorList().getAnchors().get(EAnchors.SS.ordinal()).getY());
		} else if (getgShape().getSelectedAnchor() == EAnchors.NE) {
			resizeAnchor.setLocation(getgShape().getAnchorList().getAnchors().get(EAnchors.SW.ordinal()).getX(),
					getgShape().getAnchorList().getAnchors().get(EAnchors.SW.ordinal()).getY());
		} else if (getgShape().getSelectedAnchor() == EAnchors.WW) {
			resizeAnchor.setLocation(getgShape().getAnchorList().getAnchors().get(EAnchors.EE.ordinal()).getX(), 0);
		} else if (getgShape().getSelectedAnchor() == EAnchors.EE) {
			resizeAnchor.setLocation(getgShape().getAnchorList().getAnchors().get(EAnchors.WW.ordinal()).getX(), 0);
		} else if (getgShape().getSelectedAnchor() == EAnchors.SW) {
			resizeAnchor.setLocation(getgShape().getAnchorList().getAnchors().get(EAnchors.NE.ordinal()).getX(),
					getgShape().getAnchorList().getAnchors().get(EAnchors.NE.ordinal()).getY());
		} else if (getgShape().getSelectedAnchor() == EAnchors.SS) {
			resizeAnchor.setLocation(0, getgShape().getAnchorList().getAnchors().get(EAnchors.NN.ordinal()).getY());
		} else if (getgShape().getSelectedAnchor() == EAnchors.SE) {
			resizeAnchor.setLocation(getgShape().getAnchorList().getAnchors().get(EAnchors.NW.ordinal()).getX(),
					getgShape().getAnchorList().getAnchors().get(EAnchors.NW.ordinal()).getY());
		}
		return resizeAnchor;
	}
}
