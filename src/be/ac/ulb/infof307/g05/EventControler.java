package be.ac.ulb.infof307.g05;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import be.ac.ulb.infof307.g05.canvas.jme.JmeWorld;

import com.jme3.app.SimpleApplication;


public class EventControler implements ActionListener {

	private JmeWorld _jmeWorld;
	
	public EventControler(){
		/** constructor **/
	}
	
	public void setJmeWorld(JmeWorld jmeWorld){
		_jmeWorld = jmeWorld;
	}
	
	public void actionPerformed(ActionEvent event){
		String command = event.getActionCommand();
		System.out.println("[DEBUG][EVENT] " + command);
		if(command == "2D view")
			_jmeWorld.setViews(true, false);
		else if(command == "3D view")
			_jmeWorld.setViews(false, true);
		else if(command == "2D/3D view")
			_jmeWorld.setViews(true, true);
	}
}
