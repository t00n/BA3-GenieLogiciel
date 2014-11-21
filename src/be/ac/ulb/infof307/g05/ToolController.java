package be.ac.ulb.infof307.g05;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JComboBox;

import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Stage;

import com.jme3.math.Vector3f;

/**
 * The Class ToolController that receive the events from the EventController which are generated by tools.
 */
public class ToolController {
	private HashMap<String, Boolean> _flagTools = new HashMap<String, Boolean>();
	private Stack<Vector3f> 		 _positionStack = new Stack<Vector3f>();
	private CompositeObject			 _lastCollision;
	private Vector3f				 _cursor = new Vector3f();
	private Stage 					 _currentStage;
	private String 					 _currentDrawingType = "Rectangle";	
	private CompositeObject 		 _compositeObjectSelected = null;
	
	private EventController _eventController;
	
	public ToolController(EventController eventController){
		_eventController = eventController;
	}
	
	public Vector3f getCursor(){
		return _cursor;	}
	
	public void setStage(Stage stage) {
		this._currentStage = stage;
	}

	public void addTool(String tool_name){
		_flagTools.put(tool_name, false);
	}
	
	public void addPosition(){
		if(!getEnabledTool().isEmpty()){
			_positionStack.push(new Vector3f(_cursor));
			System.out.println("[DEBUG][ToolController::addPosition] : " + _positionStack);
		}
	}
	
	public void enableTool(String tool_name){
		if(_flagTools.containsKey(tool_name)){
			System.out.println("[DEBUG][ToolController::enableTool] : " + tool_name);
			for(String tool:_flagTools.keySet()){
				if(tool.equals(tool_name)){
					if(_flagTools.get(tool_name))
						_eventController.setToolIsActivated(false);
					else
						_eventController.setToolIsActivated(true);
					_flagTools.put(tool_name, !_flagTools.get(tool_name));
				}else
					_flagTools.put(tool, false);
			}
			purge();
		}
	}
	
	public String getEnabledTool(){
		String enabled_tool = new String();
		
		for(String tool:_flagTools.keySet()){
			if(_flagTools.get(tool)){
				enabled_tool += tool;
			}
		}
		return enabled_tool;
	}
	
	public void setCurrentStage(Stage stage){
		_currentStage = stage;
	}
	
	public CompositeObject getFloor(){
		return _currentStage.getFloor();
	}

	private void purge(){
		_positionStack.removeAllElements();
	}
	
	private boolean acceptDrawing(String optionChoice){
		boolean ret = false;
		if(optionChoice.equals("Rectangle") && (_positionStack.size() == 2))
			ret = true;
		else if(optionChoice.equals("Polygon") && (_positionStack.size() > 2))
			ret = true;
		else if(optionChoice.equals("Polygon") && (_positionStack.size() == 2))
			ret = true;
		return ret;
	}
	
	private void drawRectangle(){
		Vector3f one = _positionStack.pop();
		Vector3f two = _positionStack.pop();
		Cube newMesh = new Cube(one, two);
		CompositeObject newObject = new CompositeObject(this.getFloor(), newMesh.getVertices(), newMesh.getOrder());
		this._currentStage.addObject(this.getFloor(), newObject);	
		_compositeObjectSelected = newObject;
	}

	private void drawPolygon(){
		//FIXME draw polygon
	}
	
	private void drawOval(){
		//FIXME draw oval
	}
	
	private boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    }catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	public void actionPerformed(ActionEvent event){
		String command = event.getActionCommand();
		
		if(command == "cursor_move"){
			_cursor.set((Vector3f) event.getSource());
		}else if(getEnabledTool().isEmpty() && command != "comboBoxChanged"){
			System.out.println("COMMAND: "+command);
			enableTool(command);
		}else if(getEnabledTool().equals("Draw")){
			if(command == "ENTER"){
				if(_currentDrawingType.equals("Rectangle") && acceptDrawing("Rectangle"))
					drawRectangle();
				else if(_currentDrawingType.equals("Polygon") && acceptDrawing("Polygon"))
					drawPolygon();
				else if(_currentDrawingType.equals("Oval") && acceptDrawing("Oval"))
					drawOval();
				else
					purge();
			}else if(command == "ESCAPE"){
				purge();
			}else if(command == "CURSOR_CLICK_DOWN"){
				addPosition();
			}else if(command == "comboBoxChanged"){
				String option_choice = ((JComboBox)(event.getSource())).getSelectedItem().toString();			
				purge();
				if(option_choice == "Rectangle"){
					_currentDrawingType = "Rectangle";
				}else if(option_choice == "Polygon"){
					_currentDrawingType = "Polygon";
				}else if(option_choice == "Oval"){
					_currentDrawingType = "Oval";
				}
			}else{
				enableTool(command);
			}
		}else if(getEnabledTool().equals("Pull-Up")){
			if(command=="COLLISION"){
				_lastCollision = getFloor();
				if(isInteger((String) event.getSource())){
					_compositeObjectSelected = _lastCollision.getWithId(Integer.parseInt((String) event.getSource()));
				}
			}else if(command=="ZOOMIN"){
				if(_compositeObjectSelected != null)
					_compositeObjectSelected.extendUp((float)0.1);
			}else if(command=="ZOOMOUT"){
				if(_compositeObjectSelected != null)
					_compositeObjectSelected.extendUp((float)-0.1);	
			}else
				enableTool(command);
		}else if(getEnabledTool().equals("Move")){
			
		}
	}
}
