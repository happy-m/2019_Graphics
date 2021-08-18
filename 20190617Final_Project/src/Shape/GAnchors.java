package Shape;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.Vector;

public class GAnchors implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final int w = 10;
	private final int h = 10;
	private final int dw = w/2;
	private final int dh = h/2;
	
	public enum EAnchors {
		NW, NN, NE, EE, SE, SS, SW, WW, RR
	}
	private EAnchors eSelectedAnchor;
	private Vector<Ellipse2D> anchors; // 33번째 줄 > 여기다가 저장해놓고 빠져야 한다

	@SuppressWarnings("unused")
	public GAnchors() {
		this.anchors = new Vector<Ellipse2D>();
		for (EAnchors eAnchor : EAnchors.values()) {
			this.anchors.add(new Ellipse2D.Double(0, 0, w, h)); // ?
		}
		this.eSelectedAnchor = null;
	}

	public EAnchors onShape(int x, int y) {
		for (int i= 0; i < EAnchors.values().length; i++) {
			if (this.anchors.get(i).contains(x, y)) {
				return EAnchors.values()[i];
			}
		}
		return null; // 9개의 상태를 리턴시킨다
	}
	
	public void draw(Graphics2D graphics2d) {
		for (Shape shape : this.anchors) {
			graphics2d.draw(shape);
		}
	}
	
	public void setBoundingRect(Rectangle r) {
		for (EAnchors eAnchor: EAnchors.values()) {	
			int x=0, y=0;
			switch (eAnchor) {
			case NW:
				x = r.x;
				y = r.y;
				break;
			case NN:
				x = r.x + r.width/2;
				y = r.y ;
				break;
			case NE:
				x = r.x + r.width;
				y = r.y ;
				break;
			case EE:
				x = r.x + r.width;
				y = r.y + r.height/2;
				break;
			case SE:
				x = r.x + r.width;
				y = r.y + r.height;
				break;
			case SS:
				x = r.x + r.width/2;
				y = r.y + r.height;
				break;
			case SW:
				x = r.x;
				y = r.y + r.height;
				break;
			case WW:
				x = r.x;
				y = r.y + r.height/2;
				break;
			case RR:
				x = r.x + r.width/2;
				y = r.y - 50;
				break;
				default:
			}
			x = x - dw;
			y = y - dh; // 앵커 원을 가운데로 맞춰주는 것
			this.anchors.get(eAnchor.ordinal()).setFrame(x, y, w, h);	
		}
	}
	
	public boolean contains(int x, int y) {
		for (int i=0; i<this.anchors.size(); i++) {
			if(this.anchors.get(i).contains(x,y)){
				this.eSelectedAnchor = EAnchors.values()[i];
				return true;
			}
		}
		this.eSelectedAnchor = null;
		return false;
	}

	public EAnchors getSelectedAnchor() {
		return eSelectedAnchor;
	}
	
	public Vector<Ellipse2D> getAnchors() {
		return anchors;
	}
}
