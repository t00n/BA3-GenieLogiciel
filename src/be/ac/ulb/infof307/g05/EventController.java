package be.ac.ulb.infof307.g05;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import be.ac.ulb.infof307.g05.MainWindow;
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
	private Project		 _project;
	
	
	public EventController(MainWindow window){
		/** constructor **/
		_window = window;
		//FIXME use _window.popUpLoad()
		
		this.openProject();
		
		Vector<Vector3f> position_queue = new Vector<Vector3f>();
		position_queue.add(_cursor);
		
//		_project.loadProject(_window.popUpLoad());
		_toolController = new ToolController((Stage)_project.getStages().toArray()[0], position_queue);
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
	
	public void openProject() {
		try {
			Dao<Project, Integer> dao = Project.getDao(Project.class);
			List<Project> projects = dao.queryForAll();
			String[] choices = new String[projects.size()];
			int i = 0;
			for (Project project: projects) {
				choices[i] = project.getName();
				++i;
			}
			String input = _window.popUpChoice((String[]) choices, "Choose a project...", "Choose Project");
			for (Project project: projects) {
				if (input == project.getName()) {
					_project = project;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			System.out.println("caca");
			//FIXME ask window to display pop-up menu, load it in database, load a stage in toolController
			this.openProject();
		}else if(command == "Save"){
			//FIXME ask database to save the project 
		}else if(command == "New"){
			//FIXME ask database to load new project (and save the current if not empty)
		}else if(command == "cursor_move"){
			_cursor.set((Vector3f) event.getSource());
			_toolController.drawInConstruction();
		}else {
			_toolController.actionPerformed(event);
		}
		
		_window.update();
	}
}