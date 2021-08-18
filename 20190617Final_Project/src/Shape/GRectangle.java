package Shape;

import java.awt.Graphics2D;

public class GRectangle extends GShape {
	private static final long serialVersionUID = 1L;
	
	private java.awt.Rectangle rectangle; // ���̺귯���� �ִ� �� ����� ��

	@Override
	public GShape newInstance() {
		return new GRectangle();
	}
	
	public GRectangle() {
		super();
		this.shape = new java.awt.Rectangle(); // protected int x1, y1, x2, y2;�� rectangle�ȿ� �� �ִ�.
		this.rectangle = (java.awt.Rectangle) this.shape;
	}
	// protected int x1, y1, x2, y2;

	public void setOrigin(int x, int y) { // ���� ����
		this.rectangle.setBounds(x, y, 0, 0);
	}

	public void setPoint(int x, int y) { // �����̴� ��ǥ ����
		this.rectangle.setSize(x - this.rectangle.x, y - this.rectangle.y);
	}

	public void addPoint(int x, int y) {

	}
	
	public void keepMoving(Graphics2D graphics2d, int x, int y) {
		int dw = x - this.px;
		int dh = y - this.py;
		
		this.rectangle.setLocation(this.rectangle.x+dw, this.rectangle.y+dh);
		
		this.px = x;
		this.py = y;
	}
	
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
	
	}

	@Override
	public void moveLittle() {
		this.rectangle.setLocation(this.rectangle.x+30, this.rectangle.y+30);
	}
}
