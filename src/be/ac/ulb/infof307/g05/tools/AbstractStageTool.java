package be.ac.ulb.infof307.g05.tools;

import be.ac.ulb.infof307.g05.model.Room;
import be.ac.ulb.infof307.g05.model.Stage;

public abstract class AbstractStageTool extends AbstractTool {
	protected Stage currentStage;
	protected Room room;
	
	public AbstractStageTool(Stage stage) {
		this.currentStage = stage;
	}

}
