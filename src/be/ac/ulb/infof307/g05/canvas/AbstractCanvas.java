package be.ac.ulb.infof307.g05.canvas;

import be.ac.ulb.infof307.g05.EventController;

public abstract class AbstractCanvas<PanelType> {

	protected PanelType		 _panel;
	protected EventController _eventController;
	
	
	public PanelType getPanel(){
		return _panel;
	}

	public void update(){
		
	}
}
