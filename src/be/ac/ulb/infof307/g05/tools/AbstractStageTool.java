package be.ac.ulb.infof307.g05.tools;

import be.ac.ulb.infof307.g05.model.Stage;

public abstract class AbstractStageTool extends AbstractTool {
	protected Stage currentStage;
	
	public AbstractStageTool(Stage stage) {
		this.currentStage = stage;
	}

}
