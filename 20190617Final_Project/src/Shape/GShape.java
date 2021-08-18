package Shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Shape.GAnchors.EAnchors;

public abstract class GShape implements Cloneable, Serializable { // shape 카피 가능
	// attributes
	private static final long serialVersionUID = 1L;
	protected int px;
	protected int py; // 카피하면 같이 카피된다(px,py)

	public enum EOnState { // 마우스가 누구 위에 있는지
		eOnShape, eOnResize, eOnRotate;
	};

	// components
	protected Shape shape; // 라이브러리에 있는 걸 사용할 , 포인터(또 다른 곳에 메모리가 있기 때문에 카피 안 된다)
	private GAnchors anchors; // 포인터(마찬가지)

	public Shape getShape() {
		return this.shape;
	}

	private boolean selected;
	private Color lineColor, fillColor;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (this.selected) {
			this.anchors.setBoundingRect(this.shape.getBounds()); // 자기를 둘러싸고 있는 네모를 불러서 앵커에게 줘서 앵커가 좌표계산
		}
	}

	public GShape() {
		this.selected = false;
		this.anchors = new GAnchors();

		this.lineColor = Color.BLACK;
		this.fillColor = null;
	}

	public abstract void setOrigin(int x, int y);
	public abstract void setPoint(int x, int y);
	public abstract void addPoint(int x, int y);
	public abstract void moveLittle();

	public void setLineColor(Color color) {
		this.lineColor = color;
	}

	public void setFillColor(Color color) {
		this.fillColor = color;
	}

	public void initMoving(Graphics2D graphics2d, int x, int y) {
		this.px = x; // 초기화시키는 것
		this.py = y;
		if (!this.selected) {
			// 이미 셀렉션이 되어있으면 앵커 그리지 말아라 > if (!selected)
			this.anchors.setBoundingRect(this.shape.getBounds());
			this.anchors.draw(graphics2d); //
		}
	}

	public abstract void keepMoving(Graphics2D graphics2d, int x, int y);
	public abstract void finishMoving(Graphics2D graphics2d, int x, int y);

	public GShape clone() {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(this);

			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			return (GShape) objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	abstract public GShape newInstance();

	public void draw(Graphics2D graphics2d) {
		if (this.fillColor != null) {
			graphics2d.setColor(fillColor);
			graphics2d.fill(this.shape);
		}
		graphics2d.setColor(this.lineColor);
		graphics2d.draw(this.shape);
		if (this.selected) {
			this.anchors.setBoundingRect(this.shape.getBounds());
			this.anchors.draw(graphics2d); // 도형에 따라 앵커 따라가게 하는 것
		}
	}

	public EOnState onShape(int x, int y) { // 언젠가 셀렉션 한 적이 있느냐, 현재 아니더라도 한 번은!
		if (this.selected) { // selection이 되면 앵커가 생긴다 앵커한테 어느 동그라미에 있는지 물어보고 리턴한다
			EAnchors eAnchor = this.anchors.onShape(x, y);
			if (eAnchor == EAnchors.RR) { // Rotate
				return EOnState.eOnRotate;
			} else if (eAnchor == null) { //
				if (this.contains(x, y)) { // 앵커위에 없고 도형위에 있냐? > move
					return EOnState.eOnShape;
				}
			} else { // Resize
				return EOnState.eOnResize;
			}
		} else {
			if (this.contains(x, y)) {
				return EOnState.eOnShape;
			}
		}
		return null;
	}

	public void transformShape(AffineTransform affineTransform) {
		this.shape = affineTransform.createTransformedShape(this.shape);
	}

	public void drawAnchors(Graphics2D graphics2d) {
		this.anchors.draw(graphics2d);
	}

	public boolean contains(int x, int y) {
		if (this.anchors.contains(x, y)) {
			return true;
		}
		return this.shape.getBounds().contains(x, y);
	}

	public EAnchors getSelectedAnchor() {
		return this.anchors.getSelectedAnchor();
	}

	public GAnchors getAnchorList() {
		return anchors;
	}

	public double getWidth() {
		return this.shape.getBounds2D().getWidth();
	}

	public double getHeight() {
		return this.shape.getBounds2D().getHeight();
	}

	public double getCenterX() {
		return this.shape.getBounds2D().getCenterX();
	}

	public double getCenterY() {
		return this.shape.getBounds2D().getCenterY();
	}

}
