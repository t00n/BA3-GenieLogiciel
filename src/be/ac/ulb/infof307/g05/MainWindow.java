package be.ac.ulb.infof307.g05;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import be.ac.ulb.infof307.g05.canvas.CanvasJme;
import be.ac.ulb.infof307.g05.canvas.MenuBarCanvas;
import be.ac.ulb.infof307.g05.canvas.StatusBarCanvas;
import be.ac.ulb.infof307.g05.canvas.TabCanvas;
import be.ac.ulb.infof307.g05.model.DataBase;
import be.ac.ulb.infof307.g05.model.Stage;



public class MainWindow extends JFrame {

	private EventController _eventController;

	private TabCanvas    _canvasUI;
	private CanvasJme     _canvasJme;
	private MenuBarCanvas _canvasMenuBar;
	private StatusBarCanvas _canvasStatusBar;
	
	
	public MainWindow(String title){
		/** constructor **/
		
		_eventController = new EventController(this);
		_canvasUI = new TabCanvas(_eventController);
		_canvasJme = new CanvasJme(_eventController);
		_canvasMenuBar = new MenuBarCanvas(_eventController);
		_canvasStatusBar = new StatusBarCanvas(_eventController);
				
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setTitle(title);
		this.setSize(screenSize.width, screenSize.height);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.setLayout(new BorderLayout());
		this.add(_canvasUI.getPanel(), BorderLayout.WEST);
		this.add(_canvasJme.getPanel(), BorderLayout.CENTER);
		this.add(_canvasMenuBar.getPanel(), BorderLayout.NORTH);
		this.add(_canvasStatusBar.getPanel(), BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	public void update(){
		_canvasUI.update();
		_canvasJme.update();
		_canvasMenuBar.update();
		_canvasStatusBar.update();
	}
}
