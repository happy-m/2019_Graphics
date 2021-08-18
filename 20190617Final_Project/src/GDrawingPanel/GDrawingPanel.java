package GDrawingPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import Shape.GGroup;
import Shape.GPolygon;
import Shape.GShape;
import Shape.GShape.EOnState;
import global.GConstants.EToolBar;
import transformer.GDrawer;
import transformer.GMover;
import transformer.GResizer;
import transformer.GRotator;
import transformer.GTransformer;

//������ ��纰�� �������Ѵ�, �����̶�� �������� ���ս��Ѿ� ��
public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private MouseHandler mouseHandler;
	private enum EActionState {
		eReady, eTransforming, ePolyTransforming
	};

	private EActionState eActionState;
	private Clipboard clipboard;
	private Vector<GShape> shapeVector; // �׸� �� �׸��� ����ٰ� �����ϴ� ��

	public Vector<GShape> getShapeVector() {
		return this.shapeVector;
	}

	@SuppressWarnings("unchecked")
	public void setShapeVector(Object shapeVector) {
		this.shapeVector = (Vector<GShape>) shapeVector;
		this.repaint();
	}

	@SuppressWarnings("unchecked")
	public void restoreShapeVector(Object shapeVector) {
		if (shapeVector == null) {
			this.shapeVector.clear();
		} else {
			this.shapeVector = (Vector<GShape>) shapeVector;
		}
		this.repaint();
	}

	private GShape currentShape; // ���� �׸��� �ִ� ��
	private GShape currentTool; // currentTool�ϰ� ������ ���ϰ� �и��Ǿ�� �Ѵ�

	public void setCurrentTool(EToolBar currentTool) {
		this.currentTool = currentTool.getShape();
	}

	private GTransformer transformer;

	private boolean updated;

	public boolean isUpdated() {
		return this.updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public GDrawingPanel() {
		this.eActionState = EActionState.eReady;
		this.updated = false;

		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);

		this.mouseHandler = new MouseHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);

		this.clipboard = new Clipboard();

		this.shapeVector = new Vector<GShape>();
		this.transformer = null;
	}

	public void initialize() {

	}

	public void paint(Graphics graphics) { // �׸������� new ����� ��� ��ǥ�� �������ش�(��ü�� �������� �Ѵ�)
		Graphics2D graphics2d = (Graphics2D) graphics;
		super.paint(graphics2d); // �θ���� �׸��� �ϴ� ��

		for (GShape shape : this.shapeVector) {
			shape.draw(graphics2d);
		}
	}// finishDrawing���� �׸��� �����ؾ� �Ѵ�

	private void clearSelected() {
		for (GShape shape : this.shapeVector) {
			shape.setSelected(false);
		}
	}

	private EOnState onShape(int x, int y) { // Ŭ�� �� ������ �ִ��� Ȯ���ϴ� �� ������ �׸��� ������ move���Ѷ�
		this.currentShape = null;
		for (GShape shape : this.shapeVector) { // �� shape���� ������ �ִ��� ������ �����
			EOnState eOnState = shape.onShape(x, y);
			if (eOnState != null) {
				if (shape.contains(x, y)) {
					this.currentShape = shape;
					return eOnState;
				}
			}
		}
		return null;
	}

	private void defineActionState(int x, int y) { // UI���� : �׸��� ������ �׸���, �׸��� ������ �ٸ� ���� �ض�
		EOnState eOnState = onShape(x, y);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage("resource/Cursor.png");
		Cursor myCursor = tk.createCustomCursor(img, new Point(10, 10),"de");
		if (eOnState == null) { // null�̸� drawing�̴�
			this.clearSelected();
			this.transformer = new GDrawer(); // this�� ���� : �ʸ� ���� ����� �ִ� �� // return�� Ȯ���ϱ� ��(?
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else {
			if (!(this.currentShape.isSelected())) {
				this.clearSelected();
				this.currentShape.setSelected(true);
			}
			switch (eOnState) {
			case eOnShape:
				this.transformer = new GMover();
				setCursor(new Cursor(Cursor.MOVE_CURSOR));
				break;
			case eOnResize:
				this.transformer = new GResizer();
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				break;
			case eOnRotate:
				this.transformer = new GRotator();
				setCursor(myCursor);
				break;
			default:
				// exception
				this.eActionState = null;
				break;
			}
		}
	}

	private void initTransforming(int x, int y) { // ���⼭ ��ü�� ���� ������ �� �ֵ���?
		if (this.transformer instanceof GDrawer) {
			this.currentShape = this.currentTool.newInstance(); // currentTool�� ī���ؼ� ���� �׷��� currentShape�� �ִ� ��
		}
		this.transformer.setgShape(this.currentShape);
		this.transformer.initTransforming((Graphics2D) this.getGraphics(), x, y);
	}

	private void keepTransforming(int x, int y) { // n-1 ~ n������ ���� �̾��ִ�
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		float dash[] = { 1, 3f };
		graphics2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, dash, 0));
		this.transformer.keepTransforming(graphics2d, x, y);
	}

	private void finishTransforming(int x, int y) { // setPoint : ������ �� ��� ��
		this.transformer.finishTransforming((Graphics2D) this.getGraphics(), x, y);

		if (this.transformer instanceof GDrawer) {
			if (this.currentShape instanceof GGroup) {
				((GGroup) (this.currentShape)).contains(this.shapeVector);
			} else {
				this.shapeVector.add(this.currentShape);
			}
		}
		this.repaint();
		this.updated = true;
	}// �������� ������ ���� �����̴� (���� ����)

	public void cut() {
		Vector<GShape> selectedShapes = new Vector<GShape>();
		for (int i = this.shapeVector.size() - 1; i >= 0; i--) { // index�� �Ųٷ� ������
			if (this.shapeVector.get(i).isSelected()) {
				selectedShapes.add(this.shapeVector.get(i));
				this.shapeVector.remove(i);
				break;
			}
		}
		this.clipboard.setContents(selectedShapes); // ������ set
		this.repaint();
	}

	public void copy() {
		Vector<GShape> selectedShapes = new Vector<GShape>();
		for (int i = this.shapeVector.size() - 1; i >= 0; i--) { // index�� �Ųٷ� ������
			if (this.shapeVector.get(i).isSelected()) {
				selectedShapes.add(this.shapeVector.get(i));
				break;
			}
		}
		this.clipboard.setContents(selectedShapes); // ������ set
		this.repaint();
	}

	public void paste() {
		Vector<GShape> shapes = this.clipboard.getContents(); // �޾ƿͼ�
		this.transformer.moveLittle();
		this.shapeVector.addAll(shapes); // �ִ´�
		this.repaint();
	}

	public void delete() {
		Vector<GShape> selectedShapes = new Vector<GShape>();
		for (int i = this.shapeVector.size() - 1; i >= 0; i--) { // index�� �Ųٷ� ������
			if (this.shapeVector.get(i).isSelected()) {
				this.shapeVector.remove(i);
				break;
			}
		}
		this.clipboard.setContents(selectedShapes); // ������ set
		this.repaint();
	}

	private void continueTransforming(int x, int y) {
		Graphics2D g2D = (Graphics2D) this.getGraphics();
		g2D.setXORMode(this.getBackground());
		this.transformer.continueTransforming(g2D, x, y);
	}

	private class MouseHandler implements MouseListener, MouseMotionListener { // �巡�� : ���� ���� ����ϱ� (������ �������� ����)
		// ���콺�ڵ鷯�� ���������� �ϴ� ������ ���̳� ������ �־ ����ϰ� �ϸ� ���� ���� �ڵ��̴�
		// private int x0, y0, x1, y1; //Shape�� ����ϴ� ��ǥ�̹Ƿ� Shape�� ������ ����ִ� ���� ����
		@Override
		public void mouseClicked(MouseEvent e) { // Pressed�� Released�� ���� ��ġ���� �߻��� ��
			if (e.getClickCount() == 1) {
				mouse1Clicked(e);
				// ó�����̸� �̴�, �ι�°���� ��Ƽ�� ȣ���ϱ�
			} else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}
		}

		private void mouse1Clicked(MouseEvent e) {
			if (eActionState == EActionState.eReady) { // ����
				if (currentTool instanceof GPolygon && (transformer instanceof GDrawer)) {
					initTransforming(e.getX(), e.getY());
					eActionState = EActionState.ePolyTransforming;
				}
			} else if (eActionState == EActionState.ePolyTransforming) {
				continueTransforming(e.getX(), e.getY());
			}
		}

		private void mouse2Clicked(MouseEvent e) {
			if (eActionState == EActionState.ePolyTransforming) {
				finishTransforming(e.getX(), e.getY());
				eActionState = EActionState.eReady;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) { // Ŭ���� �ϰ� �׸��� �׸��� ��쿡�� �߻��ϵ��� �ϱ�
			if (eActionState == EActionState.ePolyTransforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) { // P > R > C������ ������ ����
			if (eActionState == EActionState.eReady) { // drawingPanel�� ����
				// ActionState�Ǻ���, ���� ���� �ִ��� üũ��, � �������� �̹� �Ǵ��ع�����(��������, 2PD ��),
				// ��Ƽ�����̺�Ʈ�� �Ǵ��Ѵ� // ���������� ���Ƽ� ���� ����..?
				defineActionState(e.getX(), e.getY());
				if (!((currentShape instanceof GPolygon) && (transformer instanceof GDrawer))) {
					initTransforming(e.getX(), e.getY());
					eActionState = EActionState.eTransforming;
				}

			}
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eActionState == EActionState.eTransforming) {
				finishTransforming(e.getX(), e.getY());
				eActionState = EActionState.eReady;
				repaint();
			}
		}

		// ������ �� �� ������ �Űܶ�
		@Override
		public void mouseDragged(MouseEvent e) {
			if (eActionState == EActionState.eTransforming) { // �׸��� �׸��� �����ϸ�
				keepTransforming(e.getX(), e.getY());
				repaint();
			}
		}

		// ����ٴ� ���� �� ���� ����� �ٽ� �׸��� ���̴�, ����� �׷��� ���ִ� ������ ĥ�ϴ� ���� �ƴϴ� (��׶���(���)�� ������ �ٽ�
		// �׷���)

		@Override
		public void mouseEntered(MouseEvent e) {
		}
	}

	public void setLineColor(Color color) {
		Graphics2D g2D = (Graphics2D) this.getGraphics();
		g2D.setXORMode(this.getBackground());
		this.currentShape.draw(g2D);
		if (this.currentShape != null) {
			this.currentShape.setLineColor(color);
			System.out.println(color);
		}
		this.currentShape.draw(g2D);
	}

	public void setFillColor(Color color) {
		Graphics2D g2D = (Graphics2D) this.getGraphics();
		g2D.setXORMode(this.getBackground());
		this.currentShape.draw(g2D);
		if (this.currentShape != null) {
			this.currentShape.setFillColor(color);
		}
		this.currentShape.draw(g2D);
	}
}
//