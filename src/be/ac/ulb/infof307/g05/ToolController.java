package be.ac.ulb.infof307.g05;

import java.awt.event.ActionEvent;

import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Stage;
import be.ac.ulb.infof307.g05.tools.AbstractTool;
import be.ac.ulb.infof307.g05.tools.DrawRoomTool;
import be.ac.ulb.infof307.g05.tools.MoveRoomTool;

import com.jme3.math.Vector3f;

/**
 * The Class ToolController that receive the events from the EventController which are generated by tools.
 */
public class ToolController {
	private AbstractTool			 _currentTool = null;
	private CompositeObject			 _lastCollision;
	private Vector3f				 _cursor = new Vector3f();
	private Stage 					 _currentStage;
	
	public Vector3f getCursor(){
		return _cursor;	}
	
	public void setStage(Stage stage) {
		this._currentStage = stage;
	}
	
	public void addPosition(){
		if (_currentTool != null) {
			_currentTool.addPosition(new Vector3f(_cursor));
		}
	}
	
	public void setCurrentStage(Stage stage){
		_currentStage = stage;
	}
	
	public Stage getStage(){
		return _currentStage;
	}
	
/**	private void toolDraw(String command, ActionEvent event){
		if(command == "ENTER"){
			if(_currentDrawingType.equals("Rectangle") && acceptDrawing("Rectangle"))
				drawRectangle();
			else if(_currentDrawingType.equals("Polygon") && acceptDrawing("Polygon"))
				drawPolygon();
			else if(_currentDrawingType.equals("Oval") && acceptDrawing("Oval"))
				drawOval();
			else
				purge();
		}else if(command == "ESCAPE"){
			purge();
		}else if(command == "CURSOR_CLICK_DOWN"){
			addPosition();
		}else if(command == "comboBoxChanged"){
			String option_choice = ((JComboBox)(event.getSource())).getSelectedItem().toString();			
			purge();
			if(option_choice == "Rectangle"){
				_currentDrawingType = "Rectangle";
			}else if(option_choice == "Polygon"){
				_currentDrawingType = "Polygon";
			}else if(option_choice == "Oval"){
				_currentDrawingType = "Oval";
			}
		}else{
			enableTool(command);
		}
	}
	
	public void toolPullUp(String command, ActionEvent event){
		if(command=="COLLISION"){
			_lastCollision = getFloor();
			if(isInteger((String) event.getSource())){
				_compositeObjectSelected = _lastCollision.getWithId(Integer.parseInt((String) event.getSource()));
			}
		}else if(command=="ZOOM_IN"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.extendAxeY((float)0.1);
		}else if(command=="ZOOM_OUT"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.extendAxeY((float)-0.1);	
		}else
			enableTool(command);
	}
	
	public void toolMove(String command, ActionEvent event){
		if(command=="COLLISION"){
			_lastCollision = getFloor();
			if(isInteger((String) event.getSource())){
				_compositeObjectSelected = _lastCollision.getWithId(Integer.parseInt((String) event.getSource()));
			}
		}else if(command=="KEY_LEFT"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.moveAxeX((float)0.1);
		}else if(command=="KEY_RIGHT"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.moveAxeX((float)-0.1);
		}else if(command=="KEY_UP"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.moveAxeZ((float)0.1);
		}else if(command=="KEY_DOWN"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.moveAxeZ((float)-0.1);
		}else if(command=="ZOOM_IN"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.moveAxeY((float)0.1);
		}else if(command=="ZOOM_OUT"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.moveAxeY((float)-0.1);	
		}else
			enableTool(command);
	}
	
	private void toolRotate(String command, ActionEvent event){
		if(command=="COLLISION"){
			_lastCollision = getFloor();
			if(isInteger((String) event.getSource())){
				_compositeObjectSelected = _lastCollision.getWithId(Integer.parseInt((String) event.getSource()));
			}
		}else if(command=="KEY_LEFT"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.rotateAroundX((float)0.1);
		}else if(command=="KEY_RIGHT"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.rotateAroundX((float)-0.1);
		}else if(command=="KEY_UP"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.rotateAroundZ((float)0.1);
		}else if(command=="KEY_DOWN"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.rotateAroundZ((float)-0.1);
		}else if(command=="ZOOM_IN"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.rotateAroundY((float)0.1);
		}else if(command=="ZOOM_OUT"){
			if(_compositeObjectSelected != null)
				_compositeObjectSelected.rotateAroundY((float)-0.1);	
		}else
			enableTool(command);
	}**/
	
	public void actionPerformed(ActionEvent event){
		String command = event.getActionCommand();

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
			else {
				_currentTool = null;
			}
		}else if(_currentTool == null && command != "comboBoxChanged"){
			if (command == "Draw")
			{
				_currentTool = new DrawRoomTool(_currentStage);
			}
			else if (command == "Move")
			{
				_currentTool = new MoveRoomTool(_currentStage);
			}
		}
	}
}
