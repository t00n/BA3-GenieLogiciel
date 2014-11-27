package be.ac.ulb.infof307.g05.tools;

import be.ac.ulb.infof307.g05.model.CompositeObject;
import be.ac.ulb.infof307.g05.model.Stage;

public abstract class AbstractStageTool extends AbstractTool {
	protected Stage currentStage;
	protected CompositeObject currentObject;
	
	public AbstractStageTool(Stage stage) {
		this.currentStage = stage;
	}

}
