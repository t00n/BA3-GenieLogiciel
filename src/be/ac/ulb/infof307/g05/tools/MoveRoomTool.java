package be.ac.ulb.infof307.g05.tools;

import com.jme3.math.Vector3f;

import be.ac.ulb.infof307.g05.model.Stage;

public class MoveRoomTool extends AbstractStageTool {

	public MoveRoomTool (Stage stage) {
		super(stage);
	}
	
	@Override
	public void addCollision(String id) {
		if (this.currentObject == null) {
			try {
				this.currentObject = this.currentStage.getById(Integer.parseInt(id));
			}
			catch (NumberFormatException e) {
				
			}
		}
		else {
			this.currentObject = null;
			this.purge();
		}
	}
	
	@Override
	public void addPosition(Vector3f position) {
		if (this.currentObject != null) {
			this.currentObject.moveTo(position);
		}
	}
	
	@Override
	public void use() {
		// TODO Auto-generated method stub

	}

}
