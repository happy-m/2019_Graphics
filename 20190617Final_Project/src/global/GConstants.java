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
		선택("resource/select1.png","resource/select2.png", new GGroup()), 
		사각형("resource/rectangle1.png","resource/rectangle2.png", new GRectangle()), 
		원("resource/oval1.png", "resource/oval2.png", new GEllipse()),
		선("resource/line1.png", "resource/line2.png", new GLine()),
		다각형("resource/polygon1.png", "resource/polygon2.png", new GPolygon()),
		호("resource/arc1.png", "resource/arc2.png", new GArc()),
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
		fileMenu("파일"), 
		editMenu("편집"),
		colorMenu("색")
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
		newItem("새로 만들기", "nnew", 'N'), // 함수 이름을 바로 집어넣어 비교를 하지 않아도 된다 > reflection
		openItem("열기", "open", 'O'),
		saveItem("저장", "save", 'S'),
		saveAsItem("다른이름으로", "saveAs", 'A'),
		printItem("인쇄하기", "print", 'P'),
		closeItem("닫기", "exit", 'E'),
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
		undo("되돌리기","undo", 'Z'),
		redo("다시실행", "redo", 'R'),
		delete("지우기", "delete", 'D'),
		cut("잘라내기", "cut", 'X'),
		copy("복사하기", "copy", 'C'),
		paste("붙여넣기", "paste", 'V'),
		group("모으기", "group", 'G'),
		ungroup("나누기", "ungroup", 'U')
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
