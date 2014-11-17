package be.ac.ulb.infof307.g05.model;

import java.util.Vector;

public class DataBase {
	private Vector<Stage> _project = new Vector<Stage>();
	
	public DataBase(){
	}
	
	public void addStage(Stage stage){
		_project.add(stage);
	}
	
	public Stage getStage(int stageNumber){
		return _project.get(stageNumber);
	}	
}