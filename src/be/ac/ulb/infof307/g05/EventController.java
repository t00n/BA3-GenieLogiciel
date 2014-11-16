package be.ac.ulb.infof307.g05;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;




import com.jme3.math.Vector3f;


public class EventController implements ActionListener {

	private MainWindow _window;
		
	private boolean  _flag2D = true;
	private boolean  _flag3D = true;
	private Vector3f _cursor = new Vector3f();
	
	private ToolController   _toolController = new ToolController();
	
	
	public EventController(MainWindow window){
		/** constructor **/
		_window = window;
	}
	
	public boolean getFlag2D(){
		return _flag2D;
	}
	
	public boolean getFlag3D(){
		return _flag3D;
	}
	
	public Vector3f getCursor(){
		return _cursor;
	}
	
	public void addTool(String tool_name){
		_toolController.addTool(tool_name);
	}
	
	@Override
	public void actionPerformed(ActionEvent event){
		String command = event.getActionCommand();
		
		if(command == "2D view"){
			_flag2D = true;
			_flag3D = false;
		}else if(command == "3D view"){
			_flag2D = false;
			_flag3D = true;
		}else if(command == "2D/3D view"){
			_flag2D = true;
			_flag3D = true;
		}else if(command == "cursor_move"){
			_cursor.set((Vector3f) event.getSource());
		}else if(command == "ENTER"){
			_toolController.make();
		}else if((!_toolController.getEnabledTool().isEmpty()) && command == "cursor_click_up"){
			_toolController.addPosition((Vector3f) event.getSource());

		}else {
			_toolController.enableTool(command);
		}
		
		_window.update();
	}
}
