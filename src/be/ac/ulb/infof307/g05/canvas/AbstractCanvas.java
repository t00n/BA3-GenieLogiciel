package be.ac.ulb.infof307.g05.canvas;

import be.ac.ulb.infof307.g05.EventController;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractCanvas, all the concretes canevas have to implement this class in order to fit the window.
 *
 * @param <PanelType> the generic type
 */
public abstract class AbstractCanvas<PanelType> {

	/** The _panel. */
	protected PanelType		 _panel;
	
	/** The _event controller. */
	protected EventController _eventController;
	
	
	public PanelType getPanel(){
		return _panel;
	}

	/**
	 * Update.
	 */
	public void update(){
		
	}
}
