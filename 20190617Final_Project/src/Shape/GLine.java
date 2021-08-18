package Shape;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;


public class GLine extends GShape {
	private static final long serialVersionUID = 1L;
	private Line2D line;
	
	public GLine() {
		super();
		shape = new Line2D.Double();
		this.line = (Line2D) shape;
	}

	public void setOrigin(int x, int y) { // 원점 세팅
		this.line.setLine(x, y, 
				this.line.getX2(), this.line.getY2());
	}

	public void setPoint(int x, int y) { // 움직이는 좌표 세팅
		this.line.setLine(this.line.getX1(), this.line.getY1(), x , y);
	}
	
	public void addPoint(int x, int y) {}

	@Override
	public void moveLittle() {}

	@Override
	public void keepMoving(Graphics2D graphics2d, int x, int y) {
		
	}

	@Override
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
		
	}

	@Override
	public GShape newInstance() {
		return new GLine();
	}
}