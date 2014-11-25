package be.ac.ulb.infof307.g05.tools;

import be.ac.ulb.infof307.g05.model.Stage;

public abstract class AbstractSceneTool extends AbstractTool {
	protected Stage currentStage;
	
	public AbstractSceneTool(Stage stage) {
		this.currentStage = stage;
	}

}
