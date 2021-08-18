package toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import GDrawingPanel.GDrawingPanel;
import global.GConstants.EToolBar;

public class GToolBar extends JToolBar {
	// attributes 값
	private static final long serialVersionUID = 1L;

	// Components 자식
	private Vector<JRadioButton> buttons; //배타버튼

	// associations(친구, 형제 : 자식아님)
	private GDrawingPanel drawingPanel;

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public GToolBar() {
		ButtonGroup buttonGroup = new ButtonGroup();

		this.buttons = new Vector<JRadioButton>();
		ActionHandler actionHandler = new ActionHandler();
		for (EToolBar eToolBar : EToolBar.values()) { // values : object하나하나의 array
			JRadioButton button = new JRadioButton();
			button.setActionCommand(eToolBar.name());
			button.addActionListener(actionHandler);
			button.setIcon(new ImageIcon(eToolBar.getIconFileName()));
			button.setSelectedIcon(new ImageIcon(eToolBar.getIconSLTFileName()));
			this.buttons.add(button);
			this.add(button); // 부모한테 자기를 등록시킨 것
			button.setToolTipText(eToolBar.name());
			buttonGroup.add(button);
		}
		// constructor에서 초기화를 하지 못하는 상황 > 초기화하는 함수를 만들어줘야 한다 > initialize
	}
	
	public void initialize() {
		this.buttons.get(EToolBar.사각형.ordinal()).doClick(); //ordinal > 몇번째 인덱스에 있는지 물어보는 것
	}

	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			drawingPanel.setCurrentTool(EToolBar.valueOf(event.getActionCommand())); // ()안의 값을 찾아서 실행해라
		}
	}
}
//어떤 버튼이 눌렸는지만 알려주는 코드로 바꾸자