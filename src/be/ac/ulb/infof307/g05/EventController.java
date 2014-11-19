package be.ac.ulb.infof307.g05;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import be.ac.ulb.infof307.g05.MainWindow;
import be.ac.ulb.infof307.g05.SaveThread;
import be.ac.ulb.infof307.g05.ToolController;
import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Project;
import be.ac.ulb.infof307.g05.model.Stage;

import com.j256.ormlite.dao.Dao;
import com.jme3.math.Vector3f;


public class EventController implements ActionListener {

	private MainWindow _window;
	
	private boolean  _flag2D = true;
	private boolean  _flag3D = true;
	private Vector3f _cursor = new Vector3f();
	
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
		
		
		Vector<Vector3f> position_queue = new Vector<Vector3f>();
		position_queue.add(_cursor);

		this.loadProject();
		_toolController = new ToolController((Stage)_currentProject.getStages().toArray()[0], position_queue);
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
		/** this method add a flag for the new tool **/
		_toolController.addTool(tool_name);
	}
	
	public CompositeObject getStage(){
		/** this method return the root node of the scene built by toolController **/
		return _toolController.getStage();
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
			if (currentProjects.size() != 0)
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
		this.launchSaveThread();
	}
	
	public void newProject() {
		be.ac.ulb.infof307.g05.newProject newP = _window.popUpNew();
		Project newProject = new Project(newP.name);
		_projects.add(newProject);
		try {
			newProject.create();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		}else if(command == "cursor_move"){
			_cursor.set((Vector3f) event.getSource());
			_toolController.drawInConstruction();
		}else {
			_toolController.actionPerformed(event);
		}
		
		_window.update();
	}
}