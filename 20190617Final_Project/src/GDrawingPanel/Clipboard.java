package GDrawingPanel;

import java.util.Vector;
import Shape.GShape;

public class Clipboard {
	private Vector<GShape> shapes;
	
	public Clipboard() {
		this.shapes = new Vector<GShape>();
	}
	public void setContents(Vector<GShape> shapes) {
		this.shapes.clear();
		this.shapes.addAll(shapes);
	}
	
	public Vector<GShape> getContents() {
		Vector<GShape> clonedShapes = new Vector<GShape>();
		for (GShape shape : this.shapes) {
			GShape clonedShape = shape.clone();
			clonedShapes.add(clonedShape);
		}
		return clonedShapes;
	}
}
