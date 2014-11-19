package be.ac.ulb.infof307.g05;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JComboBox;

import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Stage;

import com.jme3.math.Vector3f;


public class ToolController {

	private HashMap<String, Boolean> _flagTools = new HashMap<String, Boolean>();
	private Stack<Vector3f> 		 _positionStack = new Stack<Vector3f>();
	private String					 _lastCollision;
	private Vector3f				 _cursor = new Vector3f();
	
	private Stage _currentStage;
	
	
	public ToolController(){
		/** constructor **/
	}
	
	public Vector3f getCursor(){
		return _cursor;	}
	
	public void setStage(Stage stage) {
		this._currentStage = stage;
	}
	
	public void addTool(String tool_name){
		/** this method add a flag for the new tool **/
		_flagTools.put(tool_name, false);
	}
	
	public void addPosition(){
		/** this method push a new vector to position stack if a tool is active **/
		if(!getEnabledTool().isEmpty()){
			_positionStack.push(new Vector3f(_cursor));
			System.out.println("[DEBUG][ToolController::addPosition] : " + _positionStack);
		}
	}
	
	public void enableTool(String tool_name){
		/** this method enable a tool and disable others **/
		if(_flagTools.containsKey(tool_name)){
			System.out.println("[DEBUG][ToolController::enableTool] : " + tool_name);
			for(String tool:_flagTools.keySet()){
				if(tool.equals(tool_name))
					_flagTools.put(tool_name, !_flagTools.get(tool_name));
				else
				_flagTools.put(tool, false);
			}
			purge();
		}
	}
	
	public String getEnabledTool(){
		/** this method return the active tool name **/
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
	
	public CompositeObject getStage(){
		/** this method retrun the root node of the current stage **/
		return _currentStage.getFloor();
	}

	private void purge(){
		/** this method pop all position from position stack (except cursor) **/
		_positionStack.removeAllElements();
	}
	
	public void actionPerformed(ActionEvent event){
		/** this method manage event sends from EventController **/
		String command = event.getActionCommand();
		
		if(command == "cursor_move"){
			// cursor is moving
			_cursor.set((Vector3f) event.getSource());
		}else if(getEnabledTool().isEmpty() && command != "comboBoxChanged"){
			// user tries to enable a tool (!= comboBoxChanged because need to get the default value of comboBoxes from options, event send before)
			enableTool(command);
		}else{
			// when a tool is enabled
			if(command == "ENTER"){
				//FIXME if(drawing && current_drawing_object.getType() == polygon) force draw, attach to father, current_drawing_object = new object(type=polygon)
			}else if(command == "ESCAPE"){
				purge();
			}else if(command == "collision"){
				_lastCollision = (String) event.getSource();
				System.out.println("[DEBUG][ToolController::ActionPerformed] Collision : " + _lastCollision);
				//FIXME if(drawing && current_drawing_object.getFather() == null) current_drawing_object.setFather(_lastCollision)
			}else if(command == "cursor_click_up"){
				//FIXME if(drawing && current_drawing_object.addPosition(_cursor) == true) attach to father, current_drawing_object = new object(type=last drawn)
				addPosition();
				if (_positionStack.size() == 2) {
					System.out.println("[Debug][ToolController::actionPerformed] : Draw");
					Vector3f one = _positionStack.pop();
					Vector3f two = _positionStack.pop();
					two.y += 0.1;
					Cube newMesh = new Cube(one, two);
					_currentStage.getFloor().add(new CompositeObject(_currentStage.getFloor(), newMesh.getVertices(), newMesh.getOrder()));
				}
			}else if(command == "comboBoxChanged"){
				String option_choice = ((JComboBox)(event.getSource())).getSelectedItem().toString();
				
				if(getEnabledTool().equals("Draw")){
					purge();
					if(option_choice == "Rectangle"){
						//FIXME current_drawing_object = new rectangle
					}else if(option_choice == "Polygon"){
						//FIXME current_drawing_object = new polygon
					}else if(option_choice == "Oval"){
						//FIXME current_drawing_object = new oval
					}
				}
			}else{
				enableTool(command);
			}
		}
	}
}
