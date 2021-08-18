package global;

import java.awt.Cursor;

import javax.swing.JMenuItem;

import Shape.GEllipse;
import Shape.GGroup;
import Shape.GLine;
import Shape.GPolygon;
import Shape.GRectangle;
import Shape.GShape;
import Shape.GArc;

public class GConstants {

	public enum EMainFrame {
		x(400), 
		y(100), 
		w(1000), 
		h(700),
		;
		private int value;
		private EMainFrame(int value) {
			this.value = value;
		}
		public int getValue() {
			return this.value;
		}
	}
	
	public enum EToolBar {
		����("resource/select1.png","resource/select2.png", new GGroup()), 
		�簢��("resource/rectangle1.png","resource/rectangle2.png", new GRectangle()), 
		��("resource/oval1.png", "resource/oval2.png", new GEllipse()),
		��("resource/line1.png", "resource/line2.png", new GLine()),
		�ٰ���("resource/polygon1.png", "resource/polygon2.png", new GPolygon()),
		ȣ("resource/arc1.png", "resource/arc2.png", new GArc()),
		;
		private String iconFileName;
		private String iconSLTFileName;
		private GShape selectedTool;
		
		private EToolBar(String iconFileName, String iconSLTFileName, GShape selectedTool) {
			this.iconFileName = iconFileName;
			this.iconSLTFileName = iconSLTFileName;
			this.selectedTool = selectedTool;
		}

		public String getIconFileName() {
			return this.iconFileName;
		}

		public String getIconSLTFileName() {
			return this.iconSLTFileName;
		}

		public GShape getShape() {
			return this.selectedTool;
		}
	}
	public enum EMenu {
		fileMenu("����"), 
		editMenu("����"),
		colorMenu("��")
		;
		private String text;
		private EMenu(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
	public enum EFileMenu {
		newItem("���� �����", "nnew", 'N'), // �Լ� �̸��� �ٷ� ����־� �񱳸� ���� �ʾƵ� �ȴ� > reflection
		openItem("����", "open", 'O'),
		saveItem("����", "save", 'S'),
		saveAsItem("�ٸ��̸�����", "saveAs", 'A'),
		printItem("�μ��ϱ�", "print", 'P'),
		closeItem("�ݱ�", "exit", 'E'),
		;
		private String text;
		private String method;
		private char hotKey;
		
		private EFileMenu(String text, String method, char hotKey) {
			this.text = text;
			this.method = method;
			this.hotKey = hotKey;
		}
		public String getText() {
			return this.text;
		}
		public String getMethod() {
			return this.method;
		}
		
		public char getHotKey() {
			return this.hotKey;
		}
	}
	
	public enum EEditMenu {
		undo("�ǵ�����","undo", 'Z'),
		redo("�ٽý���", "redo", 'R'),
		delete("�����", "delete", 'D'),
		cut("�߶󳻱�", "cut", 'X'),
		copy("�����ϱ�", "copy", 'C'),
		paste("�ٿ��ֱ�", "paste", 'V'),
		group("������", "group", 'G'),
		ungroup("������", "ungroup", 'U')
		;
		private String text;
		private String method;
		private char hotKey;
		
		private EEditMenu(String text, String method, char hotKey) {
			this.text = text;
			this.method = method;
			this.hotKey = hotKey;
		}
		public String getText() {
			return this.text;
		}
		public String getMethod() {
			return this.method;
		}
		public char getHotKey() {
			return this.hotKey;
		}
	}
	public enum EColorMenu {
		eLine(new JMenuItem("line"), "line", 'L'), 
		eFill(new JMenuItem("fill"), "fill", 'F')
		;
		private JMenuItem menuItem;
		private String actionCommand;
		private char hotKey;

		private EColorMenu(JMenuItem menuItem, String actionCommand, char hotKey) {
			this.menuItem = menuItem;
			this.actionCommand = actionCommand;
			this.hotKey = hotKey;
		}

		public JMenuItem getMenuItem() {
			return this.menuItem;
		}

		public String getActionCommend() {
			return this.actionCommand;
		}
		
		public char getHotKey() {
			return this.hotKey;
		}
	}
	
	public enum EAnchors {
		NN(Cursor.N_RESIZE_CURSOR), SS(Cursor.S_RESIZE_CURSOR), EE(Cursor.E_RESIZE_CURSOR), WW(Cursor.W_RESIZE_CURSOR), NE(
				Cursor.NE_RESIZE_CURSOR), NW(Cursor.NW_RESIZE_CURSOR), SE(
						Cursor.SE_RESIZE_CURSOR), SW(Cursor.SW_RESIZE_CURSOR), R(Cursor.W_RESIZE_CURSOR);
		
		private int cursorType;
		

		private EAnchors(int cursorType) {
			this.cursorType = cursorType;
		}

		public int getCursor() {
			return this.cursorType;
		}

	};

}
