package main;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.UIManager;

import GDrawingPanel.GDrawingPanel;
import menu.GMenuBar;
import toolbar.GToolBar;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	// components(new해야 자식이다)
	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;

	public GMainFrame() { // 속성은 무조건 안에서
		// attributes(속성)
		this.setTitle("그림판");
		try {
			UIManager.setLookAndFeel(
					"com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		int height = (int) ge.getMaximumWindowBounds().getHeight();
		int width = (int) ge.getMaximumWindowBounds().getWidth();
		setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// components(자식들)
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar); // new해서 만들고 자식등록

		this.setLayout(new BorderLayout());
		this.toolBar = new GToolBar();
		this.add(toolBar, BorderLayout.NORTH);

		this.drawingPanel = new GDrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);

		// associations(자식들 연결) 양방향연결은 하면 안 된다(사이클이 생기면 안 된다)
	}

	public void initialize() {
		this.menuBar.associate(this.drawingPanel);
		this.toolBar.associate(this.drawingPanel);

		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
	}
}
