package be.ac.ulb.infof307.g05.model;

import java.util.Vector;

public class DataBase {
	private Vector<Stage> _project = new Vector<Stage>();
	
	public DataBase(){
	}
	
	public void createStage(){
		_project.add(new Stage(("Stage "+ (new Integer(_project.size()-1)).toString()), _project.size()-1));
	}
	
	public Stage getStage(int stage_number){
		if(stage_number >= _project.size()){
			createStage();
			stage_number = _project.size()-1;	
		}
		return _project.get(stage_number);
	}
	
	public void loadProject(String path){
		if(path != ""){
			//FIXME load project
		}
	}
}