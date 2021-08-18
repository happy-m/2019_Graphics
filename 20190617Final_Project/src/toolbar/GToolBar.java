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
	// attributes ��
	private static final long serialVersionUID = 1L;

	// Components �ڽ�
	private Vector<JRadioButton> buttons; //��Ÿ��ư

	// associations(ģ��, ���� : �ڽľƴ�)
	private GDrawingPanel drawingPanel;

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public GToolBar() {
		ButtonGroup buttonGroup = new ButtonGroup();

		this.buttons = new Vector<JRadioButton>();
		ActionHandler actionHandler = new ActionHandler();
		for (EToolBar eToolBar : EToolBar.values()) { // values : object�ϳ��ϳ��� array
			JRadioButton button = new JRadioButton();
			button.setActionCommand(eToolBar.name());
			button.addActionListener(actionHandler);
			button.setIcon(new ImageIcon(eToolBar.getIconFileName()));
			button.setSelectedIcon(new ImageIcon(eToolBar.getIconSLTFileName()));
			this.buttons.add(button);
			this.add(button); // �θ����� �ڱ⸦ ��Ͻ�Ų ��
			button.setToolTipText(eToolBar.name());
			buttonGroup.add(button);
		}
		// constructor���� �ʱ�ȭ�� ���� ���ϴ� ��Ȳ > �ʱ�ȭ�ϴ� �Լ��� �������� �Ѵ� > initialize
	}
	
	public void initialize() {
		this.buttons.get(EToolBar.�簢��.ordinal()).doClick(); //ordinal > ���° �ε����� �ִ��� ����� ��
	}

	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			drawingPanel.setCurrentTool(EToolBar.valueOf(event.getActionCommand())); // ()���� ���� ã�Ƽ� �����ض�
		}
	}
}
//� ��ư�� ���ȴ����� �˷��ִ� �ڵ�� �ٲ���