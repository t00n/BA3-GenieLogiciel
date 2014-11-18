package be.ac.ulb.infof307.g05.canvas;

import java.awt.Canvas;
import java.util.logging.Level;

import com.jme3.system.JmeCanvasContext;

import be.ac.ulb.infof307.g05.EventController;
import be.ac.ulb.infof307.g05.canvas.jme.JmeWorld;


public class CanvasJme extends AbstractCanvas<Canvas> {
	
	private JmeWorld _jmeWorld;
	
	public CanvasJme(EventController eventController){
		/** constructor **/
		java.util.logging.Logger.getLogger("").setLevel(Level.WARNING);
		_eventController = eventController;
		_jmeWorld = new JmeWorld(_eventController);
		_jmeWorld.createCanvas();
		_panel = (Canvas) ((JmeCanvasContext) _jmeWorld.getContext()).getCanvas();
		_jmeWorld.startCanvas();
	}
	
	public void update(){
		_jmeWorld.setViews(_eventController.getFlag2D(), _eventController.getFlag3D());
	}
}