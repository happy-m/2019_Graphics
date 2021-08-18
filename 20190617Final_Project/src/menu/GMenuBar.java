package menu;
import javax.swing.JMenuBar;

import GDrawingPanel.GDrawingPanel;
import global.GConstants.EMenu;

public class GMenuBar extends JMenuBar {
	// attributes
	private static final long serialVersionUID = 1L;
	
	private GFileMenu fileMenu; // �� Ŭ���������� �ٸ� �Լ����� ���� ���ɼ��� �ֱ� ������ ���� �÷� �����Ų ��
	
	// associations
	private GDrawingPanel drawingPanel;
	private GEditMenu editMenu;
	private GColorMenu colorMenu;

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public GMenuBar() {
		// initialize attributes
		
		// create components ģ���� initialize�ϴ� �� �ƴϴ�
		this.fileMenu = new GFileMenu(EMenu.fileMenu.getText()); 
		this.add(this.fileMenu);
		this.editMenu = new GEditMenu(EMenu.editMenu.getText()); 
		this.add(this.editMenu);
		this.colorMenu = new GColorMenu(EMenu.colorMenu.getText());
		this.add(this.colorMenu);
	}

	public void initialize() { // �θ� �ʱ�ȭ, ģ���� �ʱ�ȭ ���Ѵ�
		// associate
		this.fileMenu.associate(this.drawingPanel);
		this.editMenu.associate(this.drawingPanel);
		this.colorMenu.associate(this.drawingPanel);
		// initialize components
		this.fileMenu.initialize();
		this.editMenu.initialize();
	}
}
