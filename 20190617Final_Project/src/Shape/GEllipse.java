package Shape;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class GEllipse extends GShape {
	private static final long serialVersionUID = 1L;
	private Ellipse2D ellipse;
	private int px, py;
	
	public GEllipse() {
		super();
		this.shape = new Ellipse2D.Double();
		this.ellipse = (Ellipse2D) shape;
	}

	public void setOrigin(int x, int y) { // 원점 세팅
		this.ellipse.setFrame(x, y, 
				this.ellipse.getWidth(), this.ellipse.getHeight());
	}

	public void setPoint(int newX, int newY) { // 움직이는 좌표 세팅
		double w = newX - this.ellipse.getX();
		double h = newY - this.ellipse.getY();
		this.ellipse.setFrame(this.ellipse.getX(), this.ellipse.getY(), w, h);
	}

	public void addPoint(int x, int y) {
	}

	@Override
	public void moveLittle() {
		
	}

	@Override
	public void keepMoving(Graphics2D graphics2d, int x, int y) {
	
	}

	@Override
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
		
	}

	@Override
	public GShape newInstance() {
		return new GEllipse();
	}
}
