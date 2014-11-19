package be.ac.ulb.infof307.g05;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JComboBox;

import be.ac.ulb.infof307.g05.MainWindow;
import be.ac.ulb.infof307.g05.SaveThread;
import be.ac.ulb.infof307.g05.ToolController;
import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Order;
import be.ac.ulb.infof307.g05.model.Project;
import be.ac.ulb.infof307.g05.model.Stage;
import be.ac.ulb.infof307.g05.model.Vertex;

import com.j256.ormlite.dao.Dao;
import com.jme3.math.Vector3f;


public class EventController implements ActionListener {

	private MainWindow _window;
	
	private boolean  _flag2D = true;
	private boolean  _flag3D = true;
	
	private ToolController   _toolController;
	private Project		 _currentProject;
	private Dao<Project, Integer> _daoProject = Project.getDao(Project.class);
	private List<Project> _projects;
	
	private SaveThread _saveThread;
	
	public ToolController getToolController() { return _toolController; }
	
	public EventController(MainWindow window){
		/** constructor **/
		_window = window;
		//FIXME use _window.popUpLoad()

		_toolController = new ToolController();
		this.loadProject();
		this._toolController.setStage(_currentProject.getStage(0));
	}
	
	public boolean getFlag2D(){
		return _flag2D;
	}
	
	public boolean getFlag3D(){
		return _flag3D;
	}
	
	public Vector3f getCursor(){
		return _toolController.getCursor();
	}
	
	public CompositeObject getStage(){
		/** this method return the root node of the scene built by toolController **/
		return _toolController.getFloor();
	}
	
	public void addTool(String tool_name) {
		_toolController.addTool(tool_name);
	}
	
	public String getEnableTool(){
		return _toolController.getEnabledTool();
	}
	
	public void launchSaveThread() {
		if (this._saveThread != null) {
			this._saveThread.interrupt();
		}
		this._saveThread = new SaveThread(_currentProject);
		this._saveThread.start();
	}
	
	public void loadProject() {
		try {
			_projects = _daoProject.queryForAll();
			ArrayList<Project> currentProjects = (ArrayList<Project>) _daoProject.queryForEq("current", true);
			if (currentProjects.size() == 1)
			{
				_currentProject = currentProjects.get(0);
				this.launchSaveThread();
			}
			else {
				this.askProject();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void askProject() {
		int input = _window.popUpYesOrNo(new String[] {"Open project", "New Project"}, "Choose an option...", "Open Project");
		if (input == 0) {
			this.openProject();
		}
		else if (input == 1){
			this.newProject();
		}
	}
	
	public void openProject() {
		try {
			String[] choices = new String[_projects.size()];
			int i = 0;
			for (Project project: _projects) {
				choices[i] = project.getName();
				++i;
			}
			String input = _window.popUpChoice((String[]) choices, "Choose a project...", "Choose Project");
			for (Project project: _projects) {
				if (input == project.getName()) {
					_currentProject = project;
					project.setCurrent(true);
				}
				else {
					project.setCurrent(false);
				}
				project.update();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this._toolController.setStage(_currentProject.getStage(0));
		this.launchSaveThread();
	}
	
	public void newProject() {
		be.ac.ulb.infof307.g05.newProject newP = _window.popUpNew();
		Project newProject = new Project(newP.name);
        Stage stage = new Stage(newProject, 0);
        
        Vector3f vertex1 = new Vector3f(0,0,0);
        Vector3f vertex2 = new Vector3f(newP.width,0.1f,newP.length);

        Cube cube = new Cube(vertex1, vertex2);
        CompositeObject object = new CompositeObject(null, cube.getVertices(), cube.getOrder());
        stage.setFloor(object);
        
        try {
        	newProject.create();
			object.create();
			for (Order order: object.getMeshOrder()) {
				order.create();
			}
			for (Vertex vertex: object.getVertices()) {
				vertex.create();
			}
			stage.create();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int id = newProject.getId();
        try {
			_projects = _daoProject.queryForAll();
	        _currentProject = _daoProject.queryForId(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this._toolController.setStage(_currentProject.getStage(0));
		this.launchSaveThread();
	}
	
	@Override
	public void actionPerformed(ActionEvent event){
		/** this method manage events **/
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
		}else if(command == "Open project.."){
			this.openProject();
		}else if(command == "New Project"){
			//FIXME ask to save the current project or not
			this.newProject();
		}else if(command == "Save"){
			_currentProject.save();
		}else {
			_toolController.actionPerformed(event);
		}
		
		_window.update();
	}
}