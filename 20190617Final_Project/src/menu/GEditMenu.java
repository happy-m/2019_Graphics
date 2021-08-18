package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import GDrawingPanel.GDrawingPanel;
import global.GConstants.EEditMenu;

public class GEditMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private GDrawingPanel drawingPanel;

	public GEditMenu(String text) {
		super(text);

		ActionHandler actionHandler = new ActionHandler();

		for (EEditMenu eMenuItem : EEditMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getText());
			menuItem.setActionCommand(eMenuItem.getMethod());
			menuItem.addActionListener(actionHandler);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(eMenuItem.getHotKey(), InputEvent.CTRL_MASK));
			add(menuItem);
		}
	}

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public void initialize() {
	}

	private void invokeMethod(String name) {
		try {
			this.getClass().getMethod(name).invoke(this); // this = fileMenu 함수 호출 시킨 것이다 이 함수는 모든 클래스의 공통인 함수이다(함수가 그
															// 클래스 안에 있는 데이터를 참조할 수 있다)
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}

	public void undo() {
		
	}

	public void redo() {

	}
	
	public void delete() {
		this.drawingPanel.delete();
	}

	public void cut() {
		this.drawingPanel.cut();
	}

	public void copy() {
		this.drawingPanel.copy();
	}

	public void paste() {
		this.drawingPanel.paste();
	}

	public void group() {
		
	}

	public void ungroup() {

	}

	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			invokeMethod(event.getActionCommand());
		}
	}
}
