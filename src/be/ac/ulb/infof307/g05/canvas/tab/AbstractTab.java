package be.ac.ulb.infof307.g05.canvas.tab;

import javax.swing.JPanel;

import be.ac.ulb.infof307.g05.EventController;

/**
 * The Class AbstractTab which has to be inmplemented by all the concretes tabs.
 */
public abstract class AbstractTab extends JPanel {

	/** The _event controller. */
	protected EventController _eventController;
	
	/** The _name. */
	protected String 		 _name;

	public String getName(){
		return _name;
	}
}
