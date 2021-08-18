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

public abstract class GShape implements Cloneable, Serializable { // shape ī�� ����
	// attributes
	private static final long serialVersionUID = 1L;
	protected int px;
	protected int py; // ī���ϸ� ���� ī�ǵȴ�(px,py)

	public enum EOnState { // ���콺�� ���� ���� �ִ���
		eOnShape, eOnResize, eOnRotate;
	};

	// components
	protected Shape shape; // ���̺귯���� �ִ� �� ����� , ������(�� �ٸ� ���� �޸𸮰� �ֱ� ������ ī�� �� �ȴ�)
	private GAnchors anchors; // ������(��������)

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
			this.anchors.setBoundingRect(this.shape.getBounds()); // �ڱ⸦ �ѷ��ΰ� �ִ� �׸� �ҷ��� ��Ŀ���� �༭ ��Ŀ�� ��ǥ���
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
		this.px = x; // �ʱ�ȭ��Ű�� ��
		this.py = y;
		if (!this.selected) {
			// �̹� �������� �Ǿ������� ��Ŀ �׸��� ���ƶ� > if (!selected)
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
			this.anchors.draw(graphics2d); // ������ ���� ��Ŀ ���󰡰� �ϴ� ��
		}
	}

	public EOnState onShape(int x, int y) { // ������ ������ �� ���� �ִ���, ���� �ƴϴ��� �� ����!
		if (this.selected) { // selection�� �Ǹ� ��Ŀ�� ����� ��Ŀ���� ��� ���׶�̿� �ִ��� ����� �����Ѵ�
			EAnchors eAnchor = this.anchors.onShape(x, y);
			if (eAnchor == EAnchors.RR) { // Rotate
				return EOnState.eOnRotate;
			} else if (eAnchor == null) { //
				if (this.contains(x, y)) { // ��Ŀ���� ���� �������� �ֳ�? > move
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
