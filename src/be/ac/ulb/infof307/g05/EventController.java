package be.ac.ulb.infof307.g05;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.ac.ulb.infof307.g05.MainWindow;
import be.ac.ulb.infof307.g05.SaveThread;
import be.ac.ulb.infof307.g05.ToolController;
import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Project;

import com.j256.ormlite.dao.Dao;
import com.jme3.math.Vector3f;


/**
 * The Class EventController.
 */
public class EventController implements ActionListener {

	/** The _window. */
	private MainWindow _window;
	
	/** The _flag2 d. */
	private boolean  _flag2D = true;
	
	/** The _flag3 d. */
	private boolean  _flag3D = true;
	
	/** The _tool controller. */
	private ToolController   _toolController;
	
	/** The _current project. */
	private Project		 _currentProject;
	
	/** The _dao project. */
	private Dao<Project, Integer> _daoProject = Project.getDao(Project.class);
	
	/** The _projects. */
	private List<Project> _projects;
	
	/** The _save thread. */
	private SaveThread _saveThread;
	
	public ToolController getToolController() { return _toolController; }
	
	/**
	 * Instantiates a new event controller that take care of all the events,
	 * if no event corresponds to this controller, it forward the event to the ToolController.
	 *
	 * @param window the window
	 */
	public EventController(MainWindow window){
		/** constructor **/
		_window = window;
		//FIXME use _window.popUpLoad()

		_toolController = new ToolController();
		this.loadProject();
		this._toolController.setStage(_currentProject.getStage(0));
	}
	
	public Project getProject() { return _currentProject; }
	
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
	
	/**
	 * Adds the tool to the ToolController.
	 *
	 * @param tool_name the tool_name
	 */
	public void addTool(String tool_name) {
		_toolController.addTool(tool_name);
	}
	
	public String getEnableTool(){
		return _toolController.getEnabledTool();
	}
	
	/**
	 * Launch save thread.
	 */
	public void launchSaveThread() {
		if (this._saveThread != null) {
			this._saveThread.interrupt();
		}
		this._saveThread = new SaveThread(_currentProject);
		this._saveThread.start();
	}
	
	/**
	 * Load project (context menu).
	 */
	public void loadProject() {
		try {
			_projects = _daoProject.queryForAll();
			ArrayList<Project> currentProjects = (ArrayList<Project>) _daoProject.queryForEq("current", true);
			if (currentProjects.size() == 1)
			{
				_currentProject = currentProjects.get(0);
				this.launchSaveThread();
			}
			else if (_projects.size() == 0) {
				this.newProject();
			}
			else {
				this.askProject();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Ask project (at the beginning of the software).
	 */
	public void askProject() {
		int input = _window.popUpYesOrNo(new String[] {"Open project", "New Project"}, "Choose an option...", "Open Project");
		if (input == 0) {
			this.openProject();
		}
		else if (input == 1){
			this.newProject();
		}
	}
	
	/**
	 * Open project.
	 */
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
					project.refresh();
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
	
	/**
	 * Make a new project.
	 */
	public void newProject() {
		ProjectStruct newP = _window.popUpNew();
		Project newProject = new Project(newP.name, newP.width, newP.length);
		newProject.addStage(0);
        newProject.save();
        
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
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
		}else if(command == "Quit"){
			_window.dispose();
		}else {
			_toolController.actionPerformed(event);
		}
		
		_window.update();
	}
}