package be.ac.ulb.infof307.g05;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import be.ac.ulb.infof307.g05.canvas.CanvasJme;
import be.ac.ulb.infof307.g05.canvas.MenuBarCanvas;
import be.ac.ulb.infof307.g05.canvas.StatusBarCanvas;
import be.ac.ulb.infof307.g05.canvas.ToolCanvas;
import be.ac.ulb.infof307.g05.controller.Controller;
import be.ac.ulb.infof307.g05.model.Database;
import be.ac.ulb.infof307.g05.model.Stage;



public class MainWindow extends JFrame {

	private EventController _eventControler;

	private ToolCanvas    _canvasUI;
	private CanvasJme     _canvasJme;
	private MenuBarCanvas _canvasMenuBar;
	private StatusBarCanvas _canvasStatusBar;
	
	// MOOK CODE :
	private Controller _controller;	
	
	
	public MainWindow(String title){
		/** constructor **/
		
		_eventControler = new EventController(this);
		_canvasUI = new ToolCanvas(_eventControler);
		_canvasJme = new CanvasJme(_eventControler);
		_canvasMenuBar = new MenuBarCanvas(_eventControler);
		_canvasStatusBar = new StatusBarCanvas(_eventControler);
		
		
		_controller = new Controller(new Database());
		_controller.getDatabase().addStage(0, new Stage());
		// Si on change d'étage, ne pas oublier de le dire au controlleur
		// _controller.setStage(_controller.getDatabase().getStage(numeroEtage));

		
		
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
	
	public Controller getController() {
		return _controller;
	}
}
