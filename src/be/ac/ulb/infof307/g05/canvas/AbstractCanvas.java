package be.ac.ulb.infof307.g05.canvas;

import java.awt.GridBagConstraints;

import be.ac.ulb.infof307.g05.EventControler;

public abstract class AbstractCanvas<PanelType> {

	protected PanelType		 _panel;
	protected EventControler _eventControler;
	
	
	public PanelType getPanel(){
		return _panel;
	}
}
