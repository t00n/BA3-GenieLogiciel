package be.ac.ulb.infof307.g05.tools;

import be.ac.ulb.infof307.g05.model.CompositeObject;


public abstract class AbstractRoomTool extends AbstractTool {
	protected CompositeObject currentObject;
	
	public AbstractRoomTool(CompositeObject object) {
		this.currentObject = object;
	}
}
