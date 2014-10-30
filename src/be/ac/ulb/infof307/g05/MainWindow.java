package be.ac.ulb.infof307.g05;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import be.ac.ulb.infof307.g05.canvas.CanvasJme;
import be.ac.ulb.infof307.g05.canvas.MenuBarCanvas;
import be.ac.ulb.infof307.g05.canvas.ToolCanvas;



public class MainWindow extends JFrame {
	
	private EventControler _eventControler = new EventControler();
	
	private ToolCanvas    _canvasUI = new ToolCanvas(_eventControler);
	private CanvasJme     _canvasJme = new CanvasJme(_eventControler);
	private MenuBarCanvas _menuBar = new MenuBarCanvas(_eventControler);
		
	
	public MainWindow(String title){
		/** constructor **/
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setTitle(title);
		this.setSize(screenSize.width, screenSize.height);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.setLayout(new BorderLayout());
		this.add(_canvasUI.getPanel(), BorderLayout.WEST);
		this.add(_canvasJme.getPanel(), BorderLayout.CENTER);
		this.add(_menuBar.getPanel(), BorderLayout.NORTH);
		
		this.setVisible(true);
	}
}
