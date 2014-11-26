package be.ac.ulb.infof307.g05.tools;

import be.ac.ulb.infof307.g05.model.Stage;

public class MoveRoomTool extends AbstractStageTool {

	public MoveRoomTool (Stage stage) {
		super(stage);
	}
	
	@Override
	public void addCollision(String id) {
		super.addCollision(id);
		try {
			this.room = this.currentStage.getRoomByCollisionId(Integer.parseInt(id));
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		System.out.println(this.room);
	}
	
	@Override
	public void use() {
		// TODO Auto-generated method stub

	}

}
