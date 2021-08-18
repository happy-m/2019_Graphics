package main;

public class GMain {

	static private GMainFrame mainFrame;
	
	public static void main(String[] args) {
		mainFrame = new GMainFrame();
		mainFrame.initialize();
		mainFrame.setVisible(true); //코드시작 루핑(polling)돈다, paint라는 이벤트
	}
}
