package Shape;

import java.util.Vector;

public class GGroup extends GRectangle {
	private static final long serialVersionUID = 1L;

	private Vector<GShape> containedShapes;
	
	public GGroup(){
		this.containedShapes = new Vector<GShape>();
	}
	
	public GShape newInstance() {
		return new GGroup();
	}

	public void contains(Vector<GShape> shapeVector) {
		for (GShape shape : shapeVector) {
			if (this.getShape().contains(shape.getShape().getBounds())) {
				this.containedShapes.add(shape);
				shape.setSelected(true);
			}
			// 각각의 shape에 있는 contains를 꺼내와야 한다(shape을 끌어와야 한다)
		}
	}
}
