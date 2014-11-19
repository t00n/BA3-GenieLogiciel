package be.ac.ulb.infof307.g05;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import be.ac.ulb.infof307.g05.canvas.CanvasJme;
import be.ac.ulb.infof307.g05.canvas.MenuBarCanvas;
import be.ac.ulb.infof307.g05.canvas.StatusBarCanvas;
import be.ac.ulb.infof307.g05.canvas.TabCanvas;



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
	
	public int popUpYesOrNo(String[] choices, String question, String title) {
		int input = JOptionPane.showOptionDialog(null,
				question,
				title,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				choices,
				choices[0]);
		return input;
	}
	
	public String popUpChoice(String[] choices, String question, String title){
		/** this method display a pop-up window to ask user which project to load **/
		//FIXME Franklin
		
		String input = (String) JOptionPane.showInputDialog(null, 
				question, 
				title, 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				choices, 
				null);
		return input;
	}
	
	public String popUpInput(String question, String title) {
		String input = (String) JOptionPane.showInputDialog(null,
				question,
				title,
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				null);
		return input;
	}
	
	public ProjectStruct popUpNew() {
		JTextField projectName = new JTextField();
		JTextField width = new JTextField();
		JTextField length = new JTextField();
		JComponent[] choices = new JComponent[] {
				new JLabel("Project name"),
				projectName,
				new JLabel("Width of the first floor"),
				width,
				new JLabel("Length of the first floor"),
				length
		};
		JOptionPane.showMessageDialog(null,
				choices,
				"New Project",
				JOptionPane.PLAIN_MESSAGE);
		ProjectStruct ret = new ProjectStruct();
		ret.name = projectName.getText();
		ret.width = Integer.parseInt(width.getText());
		ret.length = Integer.parseInt(length.getText());
		return ret;
	}
	
	public void update(){
		_canvasUI.update();
		_canvasJme.update();
		_canvasMenuBar.update();
		_canvasStatusBar.update();
	}
}
