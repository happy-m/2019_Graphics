package menu;
import javax.swing.JMenuBar;

import GDrawingPanel.GDrawingPanel;
import global.GConstants.EMenu;

public class GMenuBar extends JMenuBar {
	// attributes
	private static final long serialVersionUID = 1L;
	
	private GFileMenu fileMenu; // 이 클래스내에서 다른 함수에서 쓰일 가능성이 있기 때문에 위로 올려 노출시킨 것
	
	// associations
	private GDrawingPanel drawingPanel;
	private GEditMenu editMenu;
	private GColorMenu colorMenu;

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public GMenuBar() {
		// initialize attributes
		
		// create components 친구는 initialize하는 거 아니다
		this.fileMenu = new GFileMenu(EMenu.fileMenu.getText()); 
		this.add(this.fileMenu);
		this.editMenu = new GEditMenu(EMenu.editMenu.getText()); 
		this.add(this.editMenu);
		this.colorMenu = new GColorMenu(EMenu.colorMenu.getText());
		this.add(this.colorMenu);
	}

	public void initialize() { // 부모만 초기화, 친구는 초기화 못한다
		// associate
		this.fileMenu.associate(this.drawingPanel);
		this.editMenu.associate(this.drawingPanel);
		this.colorMenu.associate(this.drawingPanel);
		// initialize components
		this.fileMenu.initialize();
		this.editMenu.initialize();
	}
}
