package be.ac.ulb.infof307.g05.tools;

import com.jme3.math.Vector3f;

import be.ac.ulb.infof307.g05.model.Stage;

public class RotateRoomTool extends AbstractStageTool {

	public RotateRoomTool(Stage stage) {
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
		super.addPosition(position);
		if (this.currentObject != null && this.mousePositions.size() >= 2) {
			float angle = this.mousePositions.get(this.mousePositions.size()-1).x - this.mousePositions.get(this.mousePositions.size()-2).x;
			this.currentObject.rotateAroundY(angle);
		}
	}
	
	@Override
	public void use() {
		
	}

}
