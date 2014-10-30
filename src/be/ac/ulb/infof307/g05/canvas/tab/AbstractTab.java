package be.ac.ulb.infof307.g05.canvas.tab;

import javax.swing.JPanel;

import be.ac.ulb.infof307.g05.EventControler;


public abstract class AbstractTab extends JPanel {

	protected EventControler _eventControler;
	protected String 		 _name;

	public String getName(){
		return _name;
	}
}
