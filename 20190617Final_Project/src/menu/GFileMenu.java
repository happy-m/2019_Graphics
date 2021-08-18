package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import GDrawingPanel.GDrawingPanel;
import global.GConstants.EFileMenu;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private File directory, file;
	// associations
	private GDrawingPanel drawingPanel;
	
	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public GFileMenu(String text) {
		super(text);

		this.file = null;
		this.directory = new File("data");

		ActionHandler actionHandler = new ActionHandler();

		for (EFileMenu eMenuItem : EFileMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getText());
			menuItem.setActionCommand(eMenuItem.getMethod());
			menuItem.addActionListener(actionHandler);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(eMenuItem.getHotKey(), InputEvent.CTRL_MASK));
			add(menuItem);
		}
	}

	public void initialize() {

	}

	public boolean check() {
		int message = JOptionPane.NO_OPTION;
		boolean cancel = false;
		if (this.drawingPanel.isUpdated() == true) {
			message = JOptionPane.showConfirmDialog(this.drawingPanel, "변경내용을 저장하시겠습니까?");
			if (message == JOptionPane.CANCEL_OPTION) {
				cancel = true;
			}
		}
		if (!cancel) {
			if (message == JOptionPane.OK_OPTION) {
				this.save();
			}
		}
		return cancel;
	}

	public void nnew() {
		boolean cancel = this.check();
		if (!cancel) {
			this.drawingPanel.restoreShapeVector(null);
		}
	}

	public void readObject() {
		try {
			ObjectInputStream objectInputStream;
			objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			Object object = objectInputStream.readObject();
			this.drawingPanel.restoreShapeVector(object);
			objectInputStream.close();
			this.drawingPanel.setUpdated(false);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void open() throws IOException {
		boolean cancel = this.check();
		if (!cancel) {
			JFileChooser chooser = new JFileChooser(this.directory);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data", "god");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(this.drawingPanel);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.directory = chooser.getCurrentDirectory();
				this.file = chooser.getSelectedFile();
				this.readObject();
			}
		}
	}

	private void writeObject() {
		try {
			ObjectOutputStream objectOutputStream;
			objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			objectOutputStream.writeObject(this.drawingPanel.getShapeVector());
			objectOutputStream.close();
			this.drawingPanel.setUpdated(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		if (this.drawingPanel.isUpdated()) { // 수정되어있을때만 저장해라
			this.saveAs();
		} else {
			this.writeObject();
		}
	}

	public void saveAs() {
		JFileChooser chooser = new JFileChooser(this.directory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data", "god");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this.drawingPanel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.file != null) {
				JOptionPane.showConfirmDialog(this.drawingPanel, "이미 저장된 파일이 있습니다. 덮어씌우시겠습니까?", "중복파일",
						JOptionPane.WARNING_MESSAGE);
			}
			this.directory = chooser.getCurrentDirectory();
			this.file = chooser.getSelectedFile();
			this.writeObject();
		}
	}

	public void print() {
		PrinterJob printerjob = PrinterJob.getPrinterJob();
		if (printerjob.printDialog()) {
			try {
				printerjob.print();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void exit() {
		boolean cancel = this.check();
		if (!cancel) {
			System.exit(0);
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
}
