package Shape;

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;

public class GArc extends GShape {
	private static final long serialVersionUID = 1L;
	private Arc2D arc;
	private int px, py;
	
	public GArc() {
		super();
		this.shape = new Arc2D.Double();
		this.arc = (Arc2D) this.shape;
	}

	public void setOrigin(int x, int y) { // 원점 세팅
		this.arc.setFrame(x, y, 0, 0);
		this.arc.setArcType(Arc2D.PIE);
		this.arc.setAngleStart(0);
		this.arc.setAngleExtent(90);
	}

	public void setPoint(int x, int y) { // 움직이는 좌표 세팅
		this.arc.setFrame(this.arc.getX(), this.arc.getY(), x-this.arc.getX(), (y-this.arc.getY())*2);
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
		return new GArc();
	}
}
