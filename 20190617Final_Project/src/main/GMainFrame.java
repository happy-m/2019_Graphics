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

	// components(new�ؾ� �ڽ��̴�)
	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;

	public GMainFrame() { // �Ӽ��� ������ �ȿ���
		// attributes(�Ӽ�)
		this.setTitle("�׸���");
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
		
		// components(�ڽĵ�)
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar); // new�ؼ� ����� �ڽĵ��

		this.setLayout(new BorderLayout());
		this.toolBar = new GToolBar();
		this.add(toolBar, BorderLayout.NORTH);

		this.drawingPanel = new GDrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);

		// associations(�ڽĵ� ����) ����⿬���� �ϸ� �� �ȴ�(����Ŭ�� ����� �� �ȴ�)
	}

	public void initialize() {
		this.menuBar.associate(this.drawingPanel);
		this.toolBar.associate(this.drawingPanel);

		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
	}
}
