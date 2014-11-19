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
	

	private HashMap<String, Boolean> _flagTools = new HashMap<String, Boolean>();
	private Stack<Vector3f> 		 _positionStack = new Stack<Vector3f>();
	private String					 _lastCollision;
	private Vector3f				 _cursor = new Vector3f();
	
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

	private void purge(){
		/** this method pop all position from position stack (except cursor) **/
		_positionStack.removeAllElements();
	}
	
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
		return _currentProject.getStage(0).getFloor();
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
			for (Vertex vertex: object.getPositions()) {
				vertex.create();
			}
			stage.create();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int id = newProject.getId_project();
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
					_currentProject.getStage(0).getFloor().add(new CompositeObject(_currentProject.getStage(0).getFloor(), newMesh.getVertices(), newMesh.getOrder()));
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