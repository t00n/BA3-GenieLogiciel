package be.ac.ulb.infof307.g05;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import be.ac.ulb.infof307.g05.AutoSaveThread;
import be.ac.ulb.infof307.g05.model.Project;
import be.ac.ulb.infof307.g05.model.Stage;
import be.ac.ulb.infof307.g05.tools.AbstractTool;
import be.ac.ulb.infof307.g05.tools.DrawRoomTool;
import be.ac.ulb.infof307.g05.tools.MoveRoomTool;
import be.ac.ulb.infof307.g05.tools.RotateRoomTool;
import be.ac.ulb.infof307.g05.view.MainWindow;
import be.ac.ulb.infof307.g05.view.ProjectStruct;

import com.j256.ormlite.dao.Dao;
import com.jme3.math.Vector3f;


/**
 * The Class EventController.
 */
public class EventController implements ActionListener {
	private MainWindow _window;
	
	private boolean  _flag2D = true;
	private boolean  _flag3D = true;
	private boolean  _toolIsActivated = false;
	
	private Project		 _currentProject;
	private Stage 	_currentStage;
	private Dao<Project, Integer> _daoProject = Project.getDao(Project.class);
	private List<Project> _projects;
	
	private AutoSaveThread _saveThread;

	private AbstractTool _currentTool;

	private Vector3f _cursor = new Vector3f();
	
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

		this.loadProject();
	}
	
	public Project getProject() { return _currentProject; }
	
	public boolean getFlag2D(){
		return _flag2D;
	}
	
	public boolean getFlag3D(){
		return _flag3D;
	}
	
	public boolean getToolIsActivated(){
		return _toolIsActivated;
	}
	
	public void setToolIsActivated(boolean toolIsActivated){
		_toolIsActivated = toolIsActivated;
	}
	
	public Stage getStage(){
		/** this method return the root node of the scene built by toolController **/
		return _currentStage;
	}
	
	public Vector3f getCursor() {
		return _cursor;
	}
	
	/**
	 * Launch save thread.
	 */
	public void launchSaveThread() {
		if (this._saveThread != null) {
			this._saveThread.interrupt();
		}
		this._saveThread = new AutoSaveThread(_currentProject);
		this._saveThread.start();
	}
	
	/**
	 * Load project (context menu).
	 */
	public void loadProject() {
		try {
			_projects = _daoProject.queryForAll();
			if (_projects.size() == 0) {
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
		this._currentStage = _currentProject.getStage(0);
		this.launchSaveThread();
	}
	
	public void setStageByName(String stageName) {
		Collection<Stage> stages = _currentProject.getStages();
		for (Stage stage : stages) {
			if (stage.getLevel() == Integer.parseInt(stageName)) {
				this._currentStage = stage;
			}
		}
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
		this._currentStage = _currentProject.getStage(0);
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
			_saveThread.interrupt();
			_window.quit();
		}else if (command == "Draw"){
			_currentTool = new DrawRoomTool(_currentStage);
		}
		else if (command == "Move"){
			_currentTool = new MoveRoomTool(_currentStage);
		} else if (command == "SelectObjectFromTree"){
			_currentTool = new MoveRoomTool(_currentStage);
		} else if (command == "SelectStageFromTree"){ // TODO Useless at the moment
			_currentTool = new MoveRoomTool(_currentStage);
		} else if(command == "ADD STAGE"){
			_currentProject.addStage(_currentProject.getStages().size() + 1);
			System.out.println("Nombre d'etages : " + _currentProject.getStages().size());
		}
		else if (command == "Rotate"){
			_currentTool = new RotateRoomTool(_currentStage);
		}
		if (_currentTool != null) {
			if(command == "cursor_move"){
				_currentTool.addPosition((Vector3f) event.getSource());
			}
			else if (command == "COLLISION") {
				_currentTool.addCollision((String) event.getSource());
			}
			else if (command == "CURSOR_CLICK_DOWN") {
				_currentTool.addClick((Vector3f) event.getSource());
			}
			else if (command == "comboBoxChanged") {
				// FIXME
			}
			else if (command == "ENTER") {
				_currentTool.use();
			}
			else if (command == "ESCAPE") {
				_currentTool.purge();
			}
		}
		
		_window.update();
	}
}