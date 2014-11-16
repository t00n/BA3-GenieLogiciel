package be.ac.ulb.infof307.g05;

import java.util.HashMap;
import java.util.Vector;

import com.jme3.math.Vector3f;


public class ToolController {

	private HashMap<String, Boolean> _flagTools = new HashMap<String, Boolean>();
	private Vector<Vector3f> 		 _clickQueue = new Vector<Vector3f>();
	
	
	public ToolController(){
	}
	
	public void addTool(String tool_name){
		_flagTools.put(tool_name, false);
	}
	
	public void addPosition(Vector3f position){
		if(!getEnabledTool().isEmpty()){
			_clickQueue.add(position);
			System.out.println("[DEBUG][ToolController::addPosition] : " + position);
		}
	}
	
	public void enableTool(String tool_name){		
		if(_flagTools.containsKey(tool_name)){
			System.out.println("[DEBUG][ToolController::enableTool] : " + tool_name);
			for(String tool:_flagTools.keySet()){
				if(tool.equals(tool_name))
					_flagTools.put(tool_name, !_flagTools.get(tool_name));
				else
				_flagTools.put(tool, false);
			}
			_clickQueue.clear();
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
	
	public void make(){
		/** this method start the execution of the tool **/
		String tool = new String(getEnabledTool());

		if(!tool.isEmpty()){
			if(tool.equals("Wall")){
				createWall();
			}else if(tool.equals("Ground")){
				createGround();
			}
		}
		_clickQueue.clear();
		_flagTools.put(tool, false);
	}
	
	private void createWall(){
		System.out.println("[DEBUG][ToolController::make] : " + _clickQueue);
	}
	
	private void createGround(){
		System.out.println("[DEBUG][ToolController::make] : " + _clickQueue);
	}
}
