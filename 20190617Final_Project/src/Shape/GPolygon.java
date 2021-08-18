package Shape;

import java.awt.Graphics2D;

public class GPolygon extends GShape {
	private static final long serialVersionUID = 1L;
	
	private java.awt.Polygon polygon;

	public GPolygon() {
		super();
		this.polygon = new java.awt.Polygon();
		this.shape = this.polygon;
	}
	
	public GShape newInstance() {
		return new GPolygon();
	}

	public void setOrigin(int x, int y) { // 원점 세팅
		this.polygon.addPoint(x, y);
		this.polygon.addPoint(x, y);
	}

	public void setPoint(int x, int y) { // 움직이는 좌표 세팅, 제일 마지막점을 바꾸고 있어야한다(nPoints - 1)
		this.polygon.xpoints[this.polygon.npoints - 1] = x;
		this.polygon.ypoints[this.polygon.npoints - 1] = y;
	}

	public void addPoint(int x, int y) {
		this.polygon.addPoint(x, y);
	}
	
	public void initMoving(Graphics2D graphics2d, int x, int y) {
		this.px = x;
		this.py = y;
	}

	public void keepMoving(Graphics2D graphics2d, int x, int y) {
		int dw = x - this.px;
		int dh = y - this.py;
		
		this.polygon.translate(dw, dh);
		this.px = x;
		this.py = y;
	}
	
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
	
	}

	@Override
	public void moveLittle() {
		this.polygon.translate(10, 10);
	}
}
