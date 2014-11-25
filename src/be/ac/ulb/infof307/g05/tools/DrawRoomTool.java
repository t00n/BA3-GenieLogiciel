package be.ac.ulb.infof307.g05.tools;

import be.ac.ulb.infof307.g05.model.Stage;

public class DrawRoomTool extends AbstractSceneTool {

	public DrawRoomTool(Stage stage) {
		super(stage);
	}
	
	@Override
	public void use() {
		if (this.mouseClicks.size() >= 3) {
			this.currentStage.addRoom("auto", this.mouseClicks);
			this.purge();
		}
	}

}
