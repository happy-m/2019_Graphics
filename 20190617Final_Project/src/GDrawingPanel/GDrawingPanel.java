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

//구조를 모양별로 나눠야한다, 도형이라는 개념으로 통합시켜야 함
public class GDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private MouseHandler mouseHandler;
	private enum EActionState {
		eReady, eTransforming, ePolyTransforming
	};

	private EActionState eActionState;
	private Clipboard clipboard;
	private Vector<GShape> shapeVector; // 그림 다 그리면 여기다가 저장하는 것

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

	private GShape currentShape; // 현재 그리고 있는 애
	private GShape currentTool; // currentTool하고 복제한 애하고 분리되어야 한다

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

	public void paint(Graphics graphics) { // 그릴때마다 new 해줘야 모든 좌표를 저장해준다(객체를 만들어줘야 한다)
		Graphics2D graphics2d = (Graphics2D) graphics;
		super.paint(graphics2d); // 부모먼저 그리게 하는 것

		for (GShape shape : this.shapeVector) {
			shape.draw(graphics2d);
		}
	}// finishDrawing에서 그림을 저장해야 한다

	private void clearSelected() {
		for (GShape shape : this.shapeVector) {
			shape.setSelected(false);
		}
	}

	private EOnState onShape(int x, int y) { // 클릭 후 도형이 있는지 확인하는 것 없으면 그리고 있으면 move시켜라
		this.currentShape = null;
		for (GShape shape : this.shapeVector) { // 각 shape마다 도형이 있는지 없는지 물어본다
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

	private void defineActionState(int x, int y) { // UI행위 : 그림이 없으면 그리고, 그림이 있으면 다른 것을 해라
		EOnState eOnState = onShape(x, y);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage("resource/Cursor.png");
		Cursor myCursor = tk.createCustomCursor(img, new Point(10, 10),"de");
		if (eOnState == null) { // null이면 drawing이다
			this.clearSelected();
			this.transformer = new GDrawer(); // this쓴 이유 : 너만 직접 쓰라고 주는 것 // return이 확실하긴 함(?
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

	private void initTransforming(int x, int y) { // 여기서 객체를 만들어서 저장할 수 있도록?
		if (this.transformer instanceof GDrawer) {
			this.currentShape = this.currentTool.newInstance(); // currentTool를 카피해서 새로 그려서 currentShape에 넣는 것
		}
		this.transformer.setgShape(this.currentShape);
		this.transformer.initTransforming((Graphics2D) this.getGraphics(), x, y);
	}

	private void keepTransforming(int x, int y) { // n-1 ~ n까지의 점을 이어주는
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		float dash[] = { 1, 3f };
		graphics2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, dash, 0));
		this.transformer.keepTransforming(graphics2d, x, y);
	}

	private void finishTransforming(int x, int y) { // setPoint : 마지막 점 찍는 거
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
	}// 폴리건은 마지막 점이 원점이다 (닫힌 도형)

	public void cut() {
		Vector<GShape> selectedShapes = new Vector<GShape>();
		for (int i = this.shapeVector.size() - 1; i >= 0; i--) { // index를 거꾸로 돌린다
			if (this.shapeVector.get(i).isSelected()) {
				selectedShapes.add(this.shapeVector.get(i));
				this.shapeVector.remove(i);
				break;
			}
		}
		this.clipboard.setContents(selectedShapes); // 저장은 set
		this.repaint();
	}

	public void copy() {
		Vector<GShape> selectedShapes = new Vector<GShape>();
		for (int i = this.shapeVector.size() - 1; i >= 0; i--) { // index를 거꾸로 돌린다
			if (this.shapeVector.get(i).isSelected()) {
				selectedShapes.add(this.shapeVector.get(i));
				break;
			}
		}
		this.clipboard.setContents(selectedShapes); // 저장은 set
		this.repaint();
	}

	public void paste() {
		Vector<GShape> shapes = this.clipboard.getContents(); // 받아와서
		this.transformer.moveLittle();
		this.shapeVector.addAll(shapes); // 넣는다
		this.repaint();
	}

	public void delete() {
		Vector<GShape> selectedShapes = new Vector<GShape>();
		for (int i = this.shapeVector.size() - 1; i >= 0; i--) { // index를 거꾸로 돌린다
			if (this.shapeVector.get(i).isSelected()) {
				this.shapeVector.remove(i);
				break;
			}
		}
		this.clipboard.setContents(selectedShapes); // 저장은 set
		this.repaint();
	}

	private void continueTransforming(int x, int y) {
		Graphics2D g2D = (Graphics2D) this.getGraphics();
		g2D.setXORMode(this.getBackground());
		this.transformer.continueTransforming(g2D, x, y);
	}

	private class MouseHandler implements MouseListener, MouseMotionListener { // 드래그 : 넓이 높이 계산하기 (끝점과 시작점의 차이)
		// 마우스핸들러는 교통정리를 하는 것이지 값이나 로직을 넣어서 계산하게 하면 좋지 않은 코드이다
		// private int x0, y0, x1, y1; //Shape이 사용하는 좌표이므로 Shape안 쪽으로 집어넣는 것이 좋다
		@Override
		public void mouseClicked(MouseEvent e) { // Pressed와 Released가 같은 위치에서 발생한 것
			if (e.getClickCount() == 1) {
				mouse1Clicked(e);
				// 처음점이면 이닛, 두번째부턴 컨티뉴 호출하기
			} else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}
		}

		private void mouse1Clicked(MouseEvent e) {
			if (eActionState == EActionState.eReady) { // 상태
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
		public void mouseMoved(MouseEvent e) { // 클릭을 하고 그림을 그리는 경우에만 발생하도록 하기
			if (eActionState == EActionState.ePolyTransforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) { // P > R > C순으로 실행이 들어간다
			if (eActionState == EActionState.eReady) { // drawingPanel의 상태
				// ActionState판별함, 누구 위에 있는지 체크함, 어떤 상태인지 이미 판단해버린다(로테이팅, 2PD 등),
				// 멀티스탭이벤트를 판단한다 // 제약조건이 많아서 따로 정리..?
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

		// 폴리곤 할 때 원점을 옮겨라
		@Override
		public void mouseDragged(MouseEvent e) {
			if (eActionState == EActionState.eTransforming) { // 그림을 그리기 시작하면
				keepTransforming(e.getX(), e.getY());
				repaint();
			}
		}

		// 지운다는 것은 그 위에 배경을 다시 그리는 것이다, 배경을 그려서 없애는 것이지 칠하는 것이 아니다 (백그라운드(배경)의 색으로 다시
		// 그려라)

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