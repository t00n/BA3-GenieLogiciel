package be.ac.ulb.infof307.g05.tools;

import com.jme3.math.Vector3f;

import be.ac.ulb.infof307.g05.model.Stage;

public class MoveRoomTool extends AbstractStageTool {

	public MoveRoomTool (Stage stage) {
		super(stage);
	}
	
	@Override
	public void addCollision(String id) {
		if (this.room == null) {
			this.room = this.currentStage.getRoomByCollisionId(Integer.parseInt(id));
		}
		else {
			this.room = null;
			this.purge();
		}
	}
	
	@Override
	public void addPosition(Vector3f position) {
		if (this.room != null) {
			this.room.moveTo(position);
		}
	}
	
	@Override
	public void use() {
		// TODO Auto-generated method stub

	}

}
