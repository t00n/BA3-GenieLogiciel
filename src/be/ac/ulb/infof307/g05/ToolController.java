package be.ac.ulb.infof307.g05;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JComboBox;

import com.jme3.math.Vector3f;


public class ToolController {

	private HashMap<String, Boolean> _flagTools = new HashMap<String, Boolean>();
	private Vector<Vector3f> 		 _positionStack;
	
	
	public ToolController(Vector<Vector3f> position_stack){
		/** constructor **/
		_positionStack = position_stack;
	}
	
	public void addTool(String tool_name){
		/** this method add a flag for the new tool **/
		_flagTools.put(tool_name, false);
	}
	
	public void addPosition(Vector3f position){
		/** this method push a new vector to position stack if a tool is active **/
		if(!getEnabledTool().isEmpty()){
			_positionStack.add(0,position);
			System.out.println("[DEBUG][ToolController::addPosition] : " + position);
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
	
	public void update(){
		/** this method re-draw the "active shape" **/
		if(!getEnabledTool().isEmpty()){
			System.out.println("[DEBUG][ToolController::make] : " + _positionStack);
		}
	}

	private void purge(){
		/** this method pop all position from position stack (except cursor) **/
		while(_positionStack.size() > 1){
			_positionStack.removeElementAt(0);
		}
		//FIXME remove selected figure
	}
	
	public void actionPerformed(ActionEvent event){
		/** this method manage event sends from EventController **/
		String command = event.getActionCommand();

		if(!getEnabledTool().isEmpty()){
			if(command == "ENTER"){
				//FIXME when draw a polygon
			}else if(command == "ESCAPE"){
				purge();
			}else if(command == "cursor_click_up"){
				addPosition((Vector3f) event.getSource());
			}else if(command == "comboBoxChanged"){
				if(((JComboBox)event.getSource()).getSelectedItem() == "Rectangle"){
					System.out.println("RECTANGLE");
				}
			}
		}else {
			enableTool(command);
		}
	}
}
