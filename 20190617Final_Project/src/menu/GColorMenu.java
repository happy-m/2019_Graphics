package menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import GDrawingPanel.GDrawingPanel;
import global.GConstants.EColorMenu;

public class GColorMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private GDrawingPanel drawingPanel;
	
	public GColorMenu(String text) {
		super(text);
		
		ActionHandler actionHandler = new ActionHandler();

		for (EColorMenu eMenuItem : EColorMenu.values()) {
			JMenuItem menuItem = eMenuItem.getMenuItem();
			menuItem.setActionCommand(eMenuItem.getActionCommend());
			menuItem.addActionListener(actionHandler);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(eMenuItem.getHotKey(), InputEvent.CTRL_MASK));
			add(menuItem);
		}
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
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			invokeMethod(event.getActionCommand());
		}
	}
	
	public void line() {
		Color color = JColorChooser.showDialog(this.drawingPanel, "Line Color", this.drawingPanel.getForeground());
		this.drawingPanel.setLineColor(color);
	}
	
	public void fill() {
		Color color = JColorChooser.showDialog(this.drawingPanel, "Line Color", this.drawingPanel.getForeground());
		this.drawingPanel.setFillColor(color);
	}

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
}
